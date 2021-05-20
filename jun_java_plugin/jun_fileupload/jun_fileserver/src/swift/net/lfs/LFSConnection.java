package swift.net.lfs;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class LFSConnection
{
	static public int MAX = 100;
	static public int LENGTH = 0;

	static public int DELAY = 1000 * 60;
	static public boolean TIMER_AUTO_START = false;

	static public String SERVER_HOST = "0.0.0.0";
	static public int SERVER_PORT = 9002;

	static private LinkedList<Socket> _list;
	static private Timer _timer;

	static public synchronized Socket getConnection()
	{
		Socket socket = null;
		if (null == _list) {
			_list = new LinkedList<Socket>();
			if (TIMER_AUTO_START == true) {
				timerStart();
			}
		}
		else {
			while (_list.size() > 0) {
				socket = _list.removeFirst();
				if (socket.isClosed() == false) {
					break;
				}
				socket = null;
			}
		}
		if (null == socket) {
			try {
				ByteArray b = new ByteArray(12);
				socket = new Socket(SERVER_HOST, SERVER_PORT);
				InputStream in = socket.getInputStream();
				int size = 4;
				int bytesAvalibale = 0;
				while (b.position < size) {
					bytesAvalibale = in.read(b.buf, b.position, size - b.position);
					if (bytesAvalibale > 0) {
						b.position += bytesAvalibale;
					}
					else if (bytesAvalibale < 0) {
						socket.close();
						throw new IOException("Broken Pipe");
					}
				}
				b.position = 0;
				size = b.readInt() + 4;
				b.ensureCapacity(size);
				while (b.position < size) {
					bytesAvalibale = in.read(b.buf, b.position, size - b.position);
					if (bytesAvalibale > 0) {
						b.position += bytesAvalibale;
					}
					else if (bytesAvalibale < 0) {
						socket.close();
						throw new IOException("Broken Pipe");
					}
				}
				b.position = 4;
				int status = b.readInt();
				b.clear();
				if (status != 0) {
					System.out.println("connection closed. status: " + status);
					socket.close();
					socket = null;
				}
				else {
					LENGTH++;
				}
			} catch (Exception e) {
				socket = null;
				e.printStackTrace();
			}
		}
		return socket;
	}

	static public synchronized void put(Socket connection)
	{
		if (null != connection && connection.isClosed() == false) {
			if (LENGTH < MAX) {
				_list.add(connection);
			}
			else {
				close(connection);
			}
		}
	}

	static public void close(Socket connection)
	{
		if (null != connection && connection.isClosed() == false) {
			LENGTH--;
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static public synchronized void timerStart()
	{
		if (null != _timer) return;
		_timer = new Timer();
		_timer.schedule(new TimerTask() {

			@Override
			public void run() {
				closeAll();
			}
		}, 0, DELAY);
	}

	static public synchronized void timerStop()
	{
		if (null != _timer) {
			_timer.cancel();
			_timer = null;
		}
	}

	static public synchronized void closeAll()
	{
		if (null != _list) {
			Iterator<Socket> i = _list.iterator();
			while (i.hasNext()) {
				LENGTH--;
				try {
					i.next().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			_list.clear();
			i = null;
		}
	}

	static public synchronized void clear()
	{
		timerStop();
		closeAll();
		_list = null;
	}

}