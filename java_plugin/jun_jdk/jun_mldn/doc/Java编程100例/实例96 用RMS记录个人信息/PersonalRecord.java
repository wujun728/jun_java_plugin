
public class PersonalRecord {

    private final static int NAME_INDEX = 0;  //姓名索引位置
    private final static int PHONE_INDEX = 20; //号码索引位置
    private final static int EMAIL_INDEX = 40;  //电子邮件索引位置
	private final static int FIELD_LEN = 20; //文本域宽度
    private final static int MAX_REC_LEN = 60; //记录最大长度

    private static StringBuffer recBuf = new StringBuffer(MAX_REC_LEN); //字符缓冲

    private PersonalRecord() {}

    private static void clearBuf() {
        for (int i = 0; i < MAX_REC_LEN; i++) {
            recBuf.insert(i, ' '); //在字符缓冲中插入字符,初始化字符缓冲区
        }
        recBuf.setLength(MAX_REC_LEN); //设置缓冲长度
    }

    public static byte[] createRecord(String name, String phone, String email) {
        clearBuf();
        recBuf.insert(NAME_INDEX, name); //插入姓名
        recBuf.insert(PHONE_INDEX, phone); //插入电话号码
        recBuf.insert(EMAIL_INDEX, email);  //插入电子邮件
        recBuf.setLength(MAX_REC_LEN);
        return recBuf.toString().getBytes();
    }
    

    public static String getName(byte[] b) { //返回姓名
        return new String(b, NAME_INDEX, FIELD_LEN).trim();
    }

    public static String getPhone(byte[] b) {  //返回电话号码
        return new String(b, PHONE_INDEX, FIELD_LEN).trim();
    }

    public static String getEmail(byte[] b) {  //返回电子邮件
        return new String(b, EMAIL_INDEX, FIELD_LEN).trim();
    }
    
    
}
