import java.util.ArrayList;
import java.util.List;

public class SplitUtil {

	public static void main(String[] args) {
		String content="854ad05a51616c372058622b9d7144b2be4215583cc0f2200451f951a93eb74809780b1fe29f3e8e09eef565ff65a4166730" + 
        		"d5f0b7bff0149dfeda5aaf7835a41c5b05d50817484803684c2ac95c1113aa5f00bb2ab23d1accff478545d43c3318dc2474" + 
        		"3954f0b165f9787710f5363471675f5a1a264f49332e8c235929ae04af97f4172b1c9c11109ce19c4e5251005475ff8f0751" + 
        		"1eff29fb469fc9ae8708f27a2bb4d9c1cd6c0ae2523f21c4859fec3a05edf7cc16eae196c79ec51954a493e7cbc48d2bccb5" + 
        		"7a3f3ea8c4c94ea58606863d4dd604173090a302fb163ad8554ffcd9e6c40b062002e96614d1018f652dc3ec0ec837509d4b" + 
        		"d2b0b2995f281cb938c233afdc4c3ac97932cf69725b05da0a38de624269794d279301dfed12110f2aa5e09906eac17ffe9b" + 
        		"e1b1e08bef74de49a8fdd0ecd4d34fcb06957694ed656f92cc52db876f36c164505e3975ea827404b8df817f7a166257a784" + 
        		"3b1e5c2f503a5bef2f43e9f960f0aaae39c0d680ab48791c952d30974ae64f5955bd03c864084942c690944696b383e8c53f" + 
        		"a132bedc000f082cac3948f957976567e7bd8f700d66780e018a7e610d19008f80d302f4a639723b83494c2425498803cc31" + 
        		"15e90dd604222bc7356bd6e189e1200cad6586e043fc4525c7a94c359e4b8f31238db96c70a51aade677f743fd44ad4dfd4f" + 
        		"11237347b7e07241f7003753632ae95871923a10ebe555c07397d201862d606fdbaede9f2ec8b87e14df25b53dbdd162e43d" + 
        		"bf2f35b118db4df7e5f6b0cd52a473520800802e519e23fb1cafd199959678c6b57699ae0bc23af69c248d0391f99593bf55" + 
        		"34308b850c1ae4923c42e6deec8bc19b4caeac789e6264ad83ed1602488553495c449c8895276713d21ab9dc3cd60550d4ab" + 
        		"4fad148b18b2ba5a6743bc5c2dd95e7f5b96d9c6fbed54a515a37a7a8664674b28d01d76727058e580eb4c12f84ae576cc1b" + 
        		"816891505707346638a1ba8d8d929f837c6a8b998caa870e4f1d24557cd7ac1500f5b07758b5ec754a214a5a98cb1c44add0" + 
        		"aed64195dcc28e10a8574d2072c9165a67550b0f193788c3714153408b62836f56db9ffdd690ba80403be974a93161c60a99" + 
        		"f1a050b8966c068ca451301b17e4482470ba26d43fde708850c14d8f7891efe464420354e235dc4cff714798829b70fd8f42" + 
        		"00b41871144a7a417ae395b046092faa19efeba7373e0449745f049fee7836eb34e46f9ab657dc366357721102f6fd95bb0e" + 
        		"ded99a1224545e75e831abeabe0d6923612be30e29405dbde57904b3c6042d4853057868c7dae68efd989e1c58c9e155765c" + 
        		"6a3be50c183d772eb6d61191114116094c76c23b73b2c79c00bc2a079698de6f1cc643e521cbe1c352068987531d55279cad" + 
        		"3dc97c672cea81874f1f644bab5b5a02598c147a58989d4bd17ca9e24650ec089aeab282150f4c13292a573faf9e9e12a171" + 
        		"69bde6c143a842f1f587c794c1c201a76d9943c1b0031dcff42a938840199bafe9ec5125a7ba16f275309b0244b396509c0e" + 
        		"87a608a985ff2660c0527f85f07b70f169976ab42a654c816293998968d35bf8ebd5e8bd1f3c2f01ae6be6c0afaa974c8b0d" + 
        		"9004227375089e14802f31b6750814a072c1d23e8d06b9e38e616bd4f37509c6542ad36d4322bb435a848ca4ccee3245ec0f" + 
        		"cc1b3c5679cde7f0cf2c733598b6b9529bf3259e2fa8798d1825bdf3183e141688111e4add57814915bd9297c6a98d141b00" + 
        		"43beea0cfc469c1f4af905e1dd1e1d45f79283eb21b36b44431fec60034ffe97c8fe02880cfd6038f822696bded8e39c2829" + 
        		"80c09e7a1dc105bf65398cdbb878a73122d3b96ebc76674c830a3914e90b3351fc6001730ee481df60ce7091f98e3210e88b" + 
        		"244192a0680e71325862d4fa67bf6633b9035ca145305fe8bba7231c84c4503e66768a80426de7b5b00ce94215da0661d464" + 
        		"17d4f9fa461590ac2f3de6799aaf053857c6cfaa731303cdbd7402fed1e467ea8b95e21c7e75c62e0ed5c187b23b9af0333b" + 
        		"ae7dcb266bf99776d8caed8123c949ba248e794a4c49cc785a803ad3a0aae4e35000aa3cb0d2bc469af1ab96f5b5e0c6016b" + 
        		"55b97f5ef0b6e39cc392ed781f348ed8bc9765329ea69bd354b2950d99377769f1c555720fa03648f98cae95993df4b00539" + 
        		"b3d964efff0da0e00d7c9454dc27985d33843064bffc4f6e22d4de6ab5ce22a395a434a37179a2941b49e5767f2bcbe9a1a3" + 
        		"b17368a1386f888d40fb15dfc9bcdc5fed4729d1cf4e2b9627225b8fbbd71e6aa8b243a57247eaa06effd97670a9b3fab4a2" + 
        		"3b11719e103b4ced57cc6ea1b3a1d36f76ca84312c8bfb58972358b68e680c2d0f17325af1958de4e3403eba558f39c48eb1" + 
        		"83c1634cbe36dc61f810918681ef91fdf8cdb36935aa9d8ff46c71e4187fcc66091d61f89ff1364a4601d16b7154c2f7ffdd" + 
        		"ef4ff4673cdf1d25ea907a8300073499793f1da3f3b952eba9eb3e56a03413e0952568eb7381d5fa1586bd2f2030b6f4c246" + 
        		"48894b0f38be1042ad8a92375e1ab70d7d55e4c053c11c29966dcb4cde684fe37f3e7b2857403299f9d300c0b3cb547d1882" + 
        		"b3b33df13e4c9dff3fc9d68d2c7f47ca53d1244c93279fb2befc3db94eed8ba3cdfab3060b3587f2174136084c6d8e41a9e0" + 
        		"36222138a4d176d1e2ca8710e30f6a2dcf37e388";
		SplitUtil.getStrList(content, 100).forEach(str->{
			System.out.println(str);
		});
	}
	
	/**
     * 把原始字符串分割成指定长度的字符串列表
     * 
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @return
     */
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }
 
 /**
     * 把原始字符串分割成指定长度的字符串列表
     * 
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @param size
     *            指定列表大小
     * @return
     */
    public static List<String> getStrList(String inputString, int length,
            int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }
 
 /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     * 
     * @param str
     *            原始字符串
     * @param f
     *            开始位置
     * @param t
     *            结束位置
     * @return
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }

}
