package io.github.isliqian;

import java.util.Random;

/**
 * Created by LiQian_Nice on 2018/3/15
 */
public class VerificationCode {

    //    所有候选组成验证码的数字和字母
    public static String[] verificationCodeArrary={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J", "K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
    };
    //   所有候选组成验证码的数字
    public static String[] verificationNumberArrary={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",};
    //   所有候选组成验证码的英文
    public static String[] verificationEnglishArrary={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J", "K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    private static String verificationCode = "";
    private static Random random;

    /**
     * 获取自定义几位随机数字验证码
     * @param size
     * @return
     */
    public static String code(int size,String[] type) {
        if (size==0){
            size=6;
        }
        if (type==null||type!=verificationCodeArrary||type!=verificationNumberArrary||type!=verificationEnglishArrary){
            type=verificationCodeArrary;
        }
        random = new Random();
        //此处是生成验证码的核心了，利用一定范围内的随机数做为验证码数组的下标，循环组成我们需要长度的验证码，做为页面输入验证、邮件、短信验证码验证都行
        for (int i = 0; i < size; i++) {
            verificationCode += type[random.nextInt(type.length)];
        }
        return verificationCode;
    }




}
