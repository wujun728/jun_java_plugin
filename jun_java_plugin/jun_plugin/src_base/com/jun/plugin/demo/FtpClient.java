package com.jun.plugin.demo;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

public class FtpClient {
    static final boolean debug = false;
    public static final int FTP_PORT = 21;  // ftp�Ķ˿�
    static int FTP_SUCCESS = 1;
    static int FTP_TRY_AGAIN = 2;
    static int FTP_ERROR = 3;

    private Socket  dataSocket = null;
    private boolean replyPending = false;
    private boolean binaryMode = false;
    private boolean passiveMode = false;
    String user = null;  // ��¼�õ��û���
    String password = null;  // ��¼�õ�����
    String command;  // ���һ������
    int lastReplyCode;  // ������Ӧ��Ϣ
    public String welcomeMsg; // �������Ļ�ӭ��Ϣ

    protected Vector serverResponse = new Vector(1);
    protected Socket serverSocket = null; // �������ͨ�ŵ��׽���
    public PrintWriter  serverOutput;  
    public InputStream  serverInput;  // �ӷ�������ȡ��Ӧ�Ļ�����

    /** ���ط��������ӵ�״̬ */
    public boolean serverIsOpen() {
        return serverSocket != null;
    }

	/** ����Ϊ����ģʽ */
	public void setPassive(boolean mode) {
		passiveMode = mode;
	}
	
	/** ��ȡ����������Ӧ��Ϣ */
    public int readServerResponse() throws IOException {
        StringBuffer replyBuf = new StringBuffer(32);
        int c;
        int continuingCode = -1;
        int code = -1;
        String response;
        if (debug) System.out.println("readServerResponse start");
        try{
        while (true) {
            if (debug) System.out.println("readServerResponse outer while loop: "+ serverInput.available());    
            while ((c = serverInput.read()) != -1) {
               if (c == '\r') {
                    if ((c = serverInput.read()) != '\n')
                        replyBuf.append('\r');
                }
                replyBuf.append((char)c);               
                if (c == '\n')
                    break;                
            }
            if (debug) System.out.println("Now past inner while loop");
            response = replyBuf.toString();
            replyBuf.setLength(0);
            if (debug) {
                System.out.print(response);
            }
            try {
                code = Integer.parseInt(response.substring(0, 3));
            } catch (NumberFormatException e) {
                code = -1;
            } catch (StringIndexOutOfBoundsException e) {
                // ���в�������Ӧ�룬ѭ������һ��
                continue;
            }
            serverResponse.addElement(response);
            if (continuingCode != -1) {
                /* we've seen a XXX- sequence */
                if (code != continuingCode ||
                    (response.length() >= 4 && response.charAt(3) == '-')) {
                    continue;
                } else {
                    continuingCode = -1; // �������Ľ�β
                    break;
                }
            } else if (response.length() >= 4 && response.charAt(3) == '-') {
                continuingCode = code;
                continue;
            } else {
                break;
            }
        }
        }catch(Exception e){e.printStackTrace();}
        if (debug) System.out.println("readServerResponse done");
        return lastReplyCode = code;
    }

    /** �������� <i>cmd</i> ������� */
    public void sendServer(String cmd) {
        if (debug) System.out.println("sendServer start");
        serverOutput.println(cmd);
        if (debug) System.out.println("sendServer done");

    }
   
    /** ���ط�������������Ӧ�ַ� */
    public String getResponseString() {
        String s = new String();
        for(int i = 0;i < serverResponse.size();i++) {
           s+=serverResponse.elementAt(i);
        }
        serverResponse = new Vector(1);
        return s;
    }

    /** ��ȡ��Ӧ�ַ� */
   public String getResponseStringNoReset() {
        String s = new String();
        for(int i = 0;i < serverResponse.size();i++) {
           s+=serverResponse.elementAt(i);
        }
        return s;
    }
   
    /** ���� QUIT �������������ر����� */
    public void closeServer() throws IOException {
        if (serverIsOpen()) {
            issueCommand("QUIT");
        if (! serverIsOpen()) {
              return;
            }
            serverSocket.close();
            serverSocket = null;
            serverInput = null;
            serverOutput = null;
        }
    }

    protected int issueCommand(String cmd) throws IOException {
        command = cmd;
        int reply;
        if (debug) System.out.println(cmd);
        if (replyPending) {
            if (debug) System.out.println("replyPending");
            if (readReply() == FTP_ERROR)
                System.out.print("Error reading pending reply\n");
        }
        replyPending = false;
        do {
            sendServer(cmd);
            reply = readReply();
            if (debug) System.out.println("in while loop of issueCommand method");
        } while (reply == FTP_TRY_AGAIN);
        return reply;
    }

    // �������
    protected void issueCommandCheck(String cmd) throws IOException {
        if (debug) System.out.println("issueCommandCheck");
        if (issueCommand(cmd) != FTP_SUCCESS) {            
            throw new FtpProtocolException(cmd);
            }
    }

    /** ��ȡ������� */
    protected int readReply() throws IOException {
		lastReplyCode = readServerResponse();
		switch (lastReplyCode / 100) {
		case 1:
			replyPending = true;

		case 2:// ���case�����Ժ���չ����

		case 3:
			return FTP_SUCCESS;

		case 5:
			if (lastReplyCode == 530) {
				if (user == null) {
					throw new FtpLoginException("Not logged in");
				}
				return FTP_ERROR;
			}
			if (lastReplyCode == 550) {
				if (!command.startsWith("PASS"))
					throw new FileNotFoundException(command);
				else
					throw new FtpLoginException("Error: Wrong Password!");
			}
		}
		return FTP_ERROR;
	}

	//�����������
	protected Socket openDataConnection(String cmd) throws IOException {
		ServerSocket portSocket = null;
		String portCmd;
		InetAddress myAddress = InetAddress.getLocalHost();
		byte addr[] = myAddress.getAddress();
		int shift;
		String ipaddress;
		int port = 0;
		IOException e;
		if (this.passiveMode) {
			// ���ȳ��Ա���ģʽ����
			try { 
				getResponseString();
				if (issueCommand("PASV") == FTP_ERROR) {
					e = new FtpProtocolException("PASV");
					throw e;
				}
				String reply = getResponseStringNoReset();
				reply = reply.substring(reply.lastIndexOf("(") + 1, reply
						.lastIndexOf(")"));
				StringTokenizer st = new StringTokenizer(reply, ",");
				String[] nums = new String[6];
				int i = 0;
				while (st.hasMoreElements()) {
					try {
						nums[i] = st.nextToken();
						i++;
					} catch (Exception a) {
						a.printStackTrace();
					}
				}
				ipaddress = nums[0] + "." + nums[1] + "." + nums[2] + "."
						+ nums[3];
				try {
					int firstbits = Integer.parseInt(nums[4]) << 8;
					int lastbits = Integer.parseInt(nums[5]);
					port = firstbits + lastbits;
				} catch (Exception b) {
					b.printStackTrace();
				}
				if ((ipaddress != null) && (port != 0)) {
					dataSocket = new Socket(ipaddress, port);
				} else {
					e = new FtpProtocolException("PASV");
					throw e;
				}
				if (issueCommand(cmd) == FTP_ERROR) {
					e = new FtpProtocolException(cmd);
					throw e;
				}
			} catch (FtpProtocolException fpe) { 
				portCmd = "PORT ";
				// ����host��ַ
				for (int i = 0; i < addr.length; i++) {
					portCmd = portCmd + (addr[i] & 0xFF) + ",";
				}
				try {
					portSocket = new ServerSocket(20000);
					// ���Ӷ˿�
					portCmd = portCmd
							+ ((portSocket.getLocalPort() >>> 8) & 0xff) + ","
							+ (portSocket.getLocalPort() & 0xff);
					if (issueCommand(portCmd) == FTP_ERROR) {
						e = new FtpProtocolException("PORT");
						throw e;
					}
					if (issueCommand(cmd) == FTP_ERROR) {
						e = new FtpProtocolException(cmd);
						throw e;
					}
					dataSocket = portSocket.accept();
				} finally {
					if (portSocket != null)
						portSocket.close();
				}
				dataSocket = portSocket.accept();
				portSocket.close();
			}
		}
		else { // �˿ڴ���
			portCmd = "PORT ";
			// ����host��ַ
			for (int i = 0; i < addr.length; i++) {
				portCmd = portCmd + (addr[i] & 0xFF) + ",";
			}
			try {
				portSocket = new ServerSocket(20000);
				// ���Ӷ˿ں�
				portCmd = portCmd + ((portSocket.getLocalPort() >>> 8) & 0xff)
						+ "," + (portSocket.getLocalPort() & 0xff);
				if (issueCommand(portCmd) == FTP_ERROR) {
					e = new FtpProtocolException("PORT");
					throw e;
				}
				if (issueCommand(cmd) == FTP_ERROR) {
					e = new FtpProtocolException(cmd);
					throw e;
				}
				dataSocket = portSocket.accept();
			} finally {
				if (portSocket != null)
					portSocket.close();
			}
			dataSocket = portSocket.accept();
			portSocket.close();
		}
		return dataSocket; 
	}

    /** ��һ���� <i>host </i>��FTP���� */
    public void openServer(String host) throws IOException, 
	                                           UnknownHostException {
        int port = FTP_PORT;
        if (serverSocket != null)
            closeServer(); 
        serverSocket = new Socket(host, FTP_PORT);    
        serverOutput = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()),true);
        serverInput = new BufferedInputStream(serverSocket.getInputStream());
    }

    /** �򿪵� host <i>host </i> �˿�Ϊ <i>port </i>��FTP���� */
    public void openServer(String host, int port) throws IOException, UnknownHostException {
        if (serverSocket != null)
            closeServer();
        serverSocket = new Socket(host, port);
        serverOutput = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()),
                                       true);
        serverInput = new BufferedInputStream(serverSocket.getInputStream());

        if (readReply() == FTP_ERROR)
            throw new FtpConnectException("Welcome message");
    }
 
     /** ʹ���û��� <i>user</i> ������ <i>password</i> ��¼*/
    public void login(String user, String password) throws IOException {
        if (!serverIsOpen())
            throw new FtpLoginException("Error: not connected to host.\n");
        this.user = user;
        this.password = password;
        if (issueCommand("USER " + user) == FTP_ERROR)
            throw new FtpLoginException("Error: User not found.\n");
        if (password != null && issueCommand("PASS " + password) == FTP_ERROR)
            throw new FtpLoginException("Error: Wrong Password.\n");       
    }

    /** ֻ���û��� <i>user</i> ���������¼ */
    public void login(String user) throws IOException {

        if (!serverIsOpen())
            throw new FtpLoginException("not connected to host");
        this.user = user;        
        if (issueCommand("USER " + user) == FTP_ERROR)
            throw new FtpLoginException("Error: Invalid Username.\n");                 
    }

    /** ��Ascii ģʽ��FTP server��ȡһ���ļ� */
    public BufferedReader getAscii(String filename) throws IOException {     
        Socket  s = null;
        try {
            s = openDataConnection("RETR " + filename);
        } catch (FileNotFoundException fileException) {fileException.printStackTrace();}
        return new BufferedReader( new InputStreamReader(s.getInputStream()));          
    }

    /** ��Binary ģʽ��FTP server��ȡһ���ļ� */
    public BufferedInputStream getBinary(String filename) throws IOException {     
        Socket  s = null;
        try {
            s = openDataConnection("RETR " + filename);
        } catch (FileNotFoundException fileException) {fileException.printStackTrace();}
        return new BufferedInputStream(s.getInputStream());          
    }


    /** ��Ascii ģʽ����һ���ļ� */
    public BufferedWriter putAscii(String filename) throws IOException {
        Socket s = openDataConnection("STOR " + filename);
        return new BufferedWriter(new OutputStreamWriter(s.getOutputStream()),4096);
    }
     
    /** ��Binary ģʽ��server����һ���ļ� */
    public BufferedOutputStream putBinary(String filename) throws IOException {
        Socket s = openDataConnection("STOR " + filename);
        return new BufferedOutputStream(s.getOutputStream());
    }

    /** ��Ascii ģʽ��server�ϴ��������һ���ļ� */
    public BufferedWriter appendAscii(String filename) throws IOException {
        Socket s = openDataConnection("APPE " + filename);
        return new BufferedWriter(new OutputStreamWriter(s.getOutputStream()),4096);
    }

    /** ��Binary ģʽ��server�ϴ��������һ���ļ� */
    public BufferedOutputStream appendBinary(String filename) throws IOException {
        Socket s = openDataConnection("APPE " + filename);
        return new BufferedOutputStream(s.getOutputStream());
    }

   /** NLIST �ļ���Զ�� FTP server */
    public BufferedReader nlist() throws IOException {
        Socket s = openDataConnection("NLST");        
        return new BufferedReader( new InputStreamReader(s.getInputStream()));
    }

    /** LIST files ��Զ�� FTP server */
    public BufferedReader list() throws IOException {
        Socket s = openDataConnection("LIST");        
        return new BufferedReader( new InputStreamReader(s.getInputStream()));
    }

    /** ��FTP server�ϸı��ļ�·�� */
    public void cd(String remoteDirectory) throws IOException {
        issueCommandCheck("CWD " + remoteDirectory);
    }

    /** ��server�ϸı��ļ��� */
    public void rename(String oldFile, String newFile) throws IOException {
         issueCommandCheck("RNFR " + oldFile);
         issueCommandCheck("RNTO " + newFile);
    }
      
    /** Site ���� */ 
    public void site(String params) throws IOException {
         issueCommandCheck("SITE "+ params);
    }            
	
    /** ����Ϊ'I'����ģʽ */
    public void binary() throws IOException {
        issueCommandCheck("TYPE I");
        binaryMode = true;
    }

    /** ����Ϊ'A'����ģʽ */
    public void ascii() throws IOException {
        issueCommandCheck("TYPE A");
        binaryMode = false;
    }

    /** ����Abort ���� */
    public void abort() throws IOException {
        issueCommandCheck("ABOR");
    }

    /** ��Զ��ϵͳ�����һ��Ŀ¼ */
    public void cdup() throws IOException {
        issueCommandCheck("CDUP");
    }

    /** ��Զ��ϵͳ�д���һ��Ŀ¼ */
    public void mkdir(String s) throws IOException {
        issueCommandCheck("MKD " + s);
    }

    /** ��Զ��ϵͳ��ɾ��ĳ��·�� */
    public void rmdir(String s) throws IOException {
        issueCommandCheck("RMD " + s);
    }

    /** ɾ���ļ� */
    public void delete(String s) throws IOException {
        issueCommandCheck("DELE " + s);
    }

    /** ��ȡ��ǰĿ¼�� */
    public void pwd() throws IOException {
        issueCommandCheck("PWD");
    }
    
    /** ��ȡԶ��ϵͳ����Ϣ */
    public void syst() throws IOException {
        issueCommandCheck("SYST");
    }


    /** �µ�FTP�ͻ������ӵ�host <i>host</i>. */
    public FtpClient(String host) throws IOException {      
        openServer(host, FTP_PORT);      
    }

    /** �µ�FTP�ͻ������ӵ�host <i>host</i>, port <i>port</i>. */
    public FtpClient(String host, int port) throws IOException {
        openServer(host, port);
    }
    
    /** ��ʾ */
    public static void main (String args []) throws IOException{
      System.out.println("Demo of FtpClient class.\n");
      // ��׼��½��� 
      FtpClient f = new FtpClient("ftp.zsu.edu.cn");
      System.out.print(f.getResponseString());
      f.login("anonymous");
      System.out.print(f.getResponseString());                       
      f.pwd(); 
      System.out.println(f.command);                  
      System.out.print(f.getResponseString());
      f.setPassive(true);
      
      // ʹ���б�
      System.out.println("\nDemo of nlist() function");
      f.ascii();  // �ѿͻ�����ΪASCIIģʽ�Ի�ȡ�ı��б�   
      System.out.println(f.command);              
      System.out.print(f.getResponseString());     
      BufferedReader t = f.nlist();     // f.list�ṩ�˸���һЩϸ����Ϣ
      System.out.println(f.command);
      System.out.print(f.getResponseString());  
      while( true ) {
         String stringBuffer = t.readLine();
         if( stringBuffer == null ) break;
         else System.out.println(stringBuffer);               
      }
      t.close();
      System.out.print(f.getResponseString());  
      // ����ʹ�� getAscii() �����ȡ�ļ�.  ���� getBinary() Ҳ���� 
      System.out.println("\nDemo of getAscii() function");
      f.ascii();       //  ���ô���ģʽΪASCII
      System.out.println(f.command);
      System.out.print(f.getResponseString());  
      BufferedReader bufGet = f.getAscii("welcome.msg");
      System.out.println(f.command);      
      System.out.print(f.getResponseString());      
      PrintWriter pOut = new PrintWriter(new BufferedWriter(new FileWriter("welcome.msg")));
      int i;                                
      char c[] = new char[4096];
      while ((i = bufGet.read(c)) != -1) 
        pOut.write(c,0,i);                                                   
      bufGet.close();
      pOut.close();            
      System.out.print(f.getResponseString());         
      // ����ʹ��appendAscii()�������һ���ļ���appendBinary()�����ʹ������
      System.out.println("\nDemo of appendAscii() function");
      BufferedWriter bufAppe;
      String localFile = "file name goes here"; 
      f.ascii();
      System.out.println(f.command);
      try {
      bufAppe = f.appendAscii(localFile);
      System.out.println(f.command);
      System.out.print(f.getResponseString());          
	  FileReader fIn = new FileReader(localFile);            
      BufferedReader bufIn = new BufferedReader(fIn);
      int k;
      char b[] = new char[1024];
      while ((k = bufIn.read(b)) != -1) 
        bufAppe.write(b,0,k);                                  
      bufIn.close();
      bufAppe.flush();
      bufAppe.close();                 
      }catch(Exception appendErr) {
         System.out.println(appendErr.toString());//printStackTrace();
         
      }
      System.out.print(f.getResponseString()); 
      // ����ʹ��putBianary()������һ���������ļ�������putAscii()����
      System.out.println("\nDemo of putBinary() function");
      String localFile2 = "file name goes here"; 
      f.binary();
      System.out.println(f.command);
      BufferedOutputStream bufPut = f.putBinary(localFile2);
      System.out.println(f.command);
      System.out.print(f.getResponseString());     
      BufferedInputStream bufIn = new BufferedInputStream(new FileInputStream(localFile2));
      int j;
      byte b[] = new byte[1024];
      while ((j = bufIn.read(b)) != -1) 
        bufPut.write(b,0,j);                                  
      bufIn.close();
      bufPut.flush();
      bufPut.close();                 
      System.out.print(f.getResponseString()); 
      // �ر�����
      f.closeServer();
      System.out.println(f.command);
      System.out.print(f.getResponseString());
    }
}

class FtpLoginException extends FtpProtocolException {
    FtpLoginException(String s) {
        super(s);
    }
}
class FtpConnectException extends FtpProtocolException {
    FtpConnectException(String s) {
        super(s);
    }
}

class FtpProtocolException extends IOException {
    FtpProtocolException(String s) {
        super(s);     
    }
}