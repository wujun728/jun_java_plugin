package cn.skynethome.redisx.Test;

import cn.skynethome.redisx.RedisSharedMasterSlaveSentinelUtil;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx]    
  * 文件名称:[TestRedisUtils]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 上午11:44:03]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 上午11:44:03]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class TestRedisUtils
{
    public static String value="2017年春运来得早，又赶上火车票预售期由60天调整至30天，购票期相对集中。对准备回家过年的人们而言，回家的火车票还好买吗？大家对买车票有什么样的诉求，又有哪些槽点想要一吐为快？记者进行了采访。——编者2017年春运从1月13日至2月21日，共40天，是最近5年来开始时间最早的春运。日前，随着2017年春运火车票开售，又一场全球规模最大、最集中的人口流动拉开了序幕。日前，记者在京进行随机走访，采集了人们的抢票心声。各有各的抢票高招对于绝大多数在外漂泊的中国人来说，“过年回家吗？”是个略显多余的问题，回答几乎都是斩钉截铁的“回！必须回！不回还能叫过年吗？！”于是，抢票成为绕不过去的回家前奏。马师傅老家在沈阳，现在在北京市朝阳区送快递。马师傅说，他住在崇文门附近，离北京站不远，以往经验告诉他，找一天上班前，6点多上售票大厅直接买就行。“年底北京都快变空城了，快递少，我们能早走一点。”此外，记者在北京站售票大厅看到，多个窗口支持支付宝买票，这为习惯于手机支付的人提供了不少方便。　　在银行上班的李著韧，具有一定代表性，很依赖抢票软件。以前用12306官网比较多。如今，360抢票软件、高铁管家，成了时下风靡的抢票神器。李著韧介绍，她一般会提前算好出行时间，提前在软件里登记好基本信息、绑定好银行卡，提前上好闹钟坐等放票；一放票，果断下手，抢到票的几率很高。“现在，有的软件还有预约功能，当有余票的时候它会发出提示，有的提前付款后还能自动抢票。”青年编剧覃皓珺说。　　对很多人来说，网络抢票，最担心网速慢，尤其是到了支付环节，被告知排队人数多或链接已失效。对此，美发师李多多有自己的妙招——“其他设备断网，网络专供抢票”。不过今年过年，他不用这么拼了，年前美发的人多，他打算大年初一再回内蒙古老家。　　对于一些难度系数较大的车票，“人海战术”是很多人的杀手锏。在北京师范大学学生罗晴看来，春运抢票得组团，把身份证信息发给同学、家人，在放票时间一起上，“总有人能拼上人品。”在抢票族中，学生们令人羡慕，因为学生可以提前72天购票。　　在北京当保姆的范阿姨，打算抢腊月二十六回山西的动车票。“一年熬到头了，终于很快就能回家了！”说起久别的老家，抢票在范阿姨嘴里也甜蜜起来。而她的抢票神器是亲闺女，“我女儿是大学生，她帮我抢，她可懂呢！”　　验证码有点折磨人，信息泄露成公众担忧　　说起购票过程中最折磨人的环节，罗晴脱口而出：“验证码！”　　验证码，过去曾经是互联网购票的吐槽点。很多网友表示“被验证码打败”，还有人调侃，与验证码隔空喊话，“来啊，互相伤害啊！”在李著韧看来，验证码的主要问题是“像素太低了，经常看不清楚，购票输入3到4遍，是常有的事儿。”而罗晴表述得更加直接，“看验证码的时候，我觉得自己是色盲加色弱。”　　李著韧希望，将验证码中的8张图减少到2—3张。罗晴则建议，验证码中的图片能否再清晰、简单一些，或者加入语音验证。　　“看新闻，今年购票系统中使用的验证码比往年变少了，这种困扰应该会减轻些。”李多多很乐意看到，铁路总公司能不断改进消费者不满意的地方。　　身份证、电话号码、银行卡信息遭泄露，则是人们在购票环节中最担心的问题。“轻则遭到短信、电话骚扰，重则可能有金融诈骗的问题。”市民陈先生说。　　总的来看，12306官网给人的安全感最高。不论是大学生、白领，还是打工者，都相信铁路总公司会保护消费者的隐私。　　同时，对潜在的隐私泄露风险，很多人也表示很无奈。“说不好，很难保证，但为了回家都值了吧！”李著韧说。此外，也有人持“开放”的心态，覃皓珺说：“我觉得，我的信息在各种网站上应该早就被泄露了吧。”实在抢不着票，就拦顺风车回家";

    public static void main(String[] args) throws InterruptedException
    {
        
        for (int i = 0; i < 1; i++)
        {
            new ThreadTest("treahd"+i).start();

        }

       
       // RedisUtils.setObject("aaaabbba111", "11xdfdfdsddf11");
//        Thread.sleep(5000);
        //RedisUtils.del("aaaabbba111");
        //System.out.println(RedisUtils.getObject("aaaabbba111", String.class));

    }
}

class ThreadTest extends Thread
{
    private String name;

    public ThreadTest()
    {
        super();
    }

    public ThreadTest(String name)
    {
        super();
        this.name = name;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            String string = RedisSharedMasterSlaveSentinelUtil.setObject(name+i,TestRedisUtils.value+name+i);             
                    System.out.println(string + "=" + i);

        }

        for (int i = 0; i < 10; i++)
        {
            String value = RedisSharedMasterSlaveSentinelUtil.getObject(name + i, String.class);
            if(null != value)
            {
                System.out.println(value);
            }
            long l = RedisSharedMasterSlaveSentinelUtil.del(name + i);
            System.out.println(l + "=" + i);

        }
    }
}
