

import java.io.IOException;

import com.jun.plugin.gzip.compressclient.compress.CompressUtils;
import com.jun.plugin.gzip.compressclient.encrypt.AESUtils;

/**
 * 
 * @author Wujun
 *
 */
public class GzipTest {

    public static void main(String [] args){
        String content=" ";
        System.out.println("Step1 压缩前长度:" + content.length());
        try{
        	// 压缩
            String afterCompressStr = CompressUtils.compress(content);
            System.out.println("Step2 压缩后长度:" + afterCompressStr.length());

            // 加密
            String afterEncryptStr = AESUtils.encrypt(afterCompressStr);
            System.out.println("Step3 先压缩后加密的长度:" + afterEncryptStr.length());
            System.out.println("Step3 先压缩后加密的content:" + afterEncryptStr);

            // 先加密后压缩
            String _afterEncryptStr = AESUtils.encrypt(content);
            System.out.println("先加密的长度:" +_afterEncryptStr);
            String _afterCompressStr = CompressUtils.compress(_afterEncryptStr);
            System.out.println("先加密后压缩的长度:" + _afterCompressStr.length());
            
            // 解密
//            String afterEncryptStrVal=afterEncryptStr;
            String afterEncryptStrVal="a56bcd7ae5fbc0d5a26007f5334725798a3d7da3bd0c566e970c49066a216a841792da1657d135adea91dbe8f71fbd1b398f23c57b95423ccf9391d47f6f13c73fded8f84dfd0b77ce0f619927dc17999f93929ec5a8d93af8d566e2dd3787f050e64f693725aaf286c096683f9c66ffed7d2f827960766a1d151896d8a016498cadcdca7724d053780dd94c6ea4fa80b13dd1f65e193bad79101eacff164dd6396ee02b37b0f68a38f7384a0842913935caf493c60a0f73bacb611a492c795d4f5aabc36044d6033c4f297919fef65a80329c71e8575c9d68a13c4f376957fb8ddef41653a2a745835522fb2dbbe7ed2d9b7875167d187907440adca23185b14c174ab04fff06055c306622018afa58783ac066aacd647df32d7472f6a1d013a7c02e7fb0f0f4affdbc8de73428cd8b62e53b3983efb7dda5f4fbe8656133ae21afc904255d97875b25ae50e8581c55a5350160783c87b41c7472401a5ea0d6be28a924dac1523020b2ff1929bd25b9735542e6c7d6260a2258567138e00975aeb32a00c0c2f2c51cbc4ffd0d8a778ff718ba898f7ea883309f71d9611673f2f90110a739b7346b7fa7725104ecbf2de3c08c5df5e31054ab3ef60ac7b39f6f9061f22373b85879aaca7265a2e582b2cdda561d1bce5db10bb09321828df315b45d95b9818d19169195580b7df768ab63d3f2428fd73d5e1038d01e0de5ca762ed0792807ea874021b57514b9f8f4a14b8e2986e7b9a1f2423bb1b4e2591e63760cd11225842679539a1ff47c5431163c1ee3428af54135932f0e0c1fb314ebbafa271b18a667d1dcca8bb7a1ee35a1c193e86ad97bfd66633447eb32d64df0b890df55da903284c44890a0d4547b8cbd613caaadf64b756bbcdc5b6efdfc0e4ea25e2e8d16e8e899ac0d1605b7e0aedb03b7243d09dd276fa8297a323d91343340e9b2a8c5948fe1da41b0f4c26d9edc2fae250665bfc646d299e9763f574d24b85265e506735a97c312c2baa55373c0b1aadf64b36d36d4050f36e33664c1e55c965ed22a52df8b0f876f665f751f6f4895926ee67e48bdf35e7fede5246afd60d9a5306aafa7535e12e878c2b781bf7fa96d5705bcec09878a8a2a77c638985c4e5fe29ea47940ddb9f72c7d350d6370f59733f1c70c3103fec787eb01b06d14b34452dc98255cf4bc894e1bc628ebc5f238e33c0ce34e78cefcaa8cd5fe9e3bf691d1210a7d940b725e7a1de1bc7ba99ce29dd84994641fb1020e705f6ff16a2b29be28d70f53b0769bff5677710da0d43aaf87d86caa14822171407ffef0adb8431087af2ffe2a7e86c6379f0c78d82b7edd253f7013a136ccff8097888a3e1e1d535e19bf4f236ed7f6285192ecadab999ab82ebfe67ca184fb5ce2648e58a8ef65ae10b59f93bae5aa25a1eed4e4b46b6b250818f5670704a2c4925b5156f21bebfbd7a4f776fcf89d6b8d1238a0f424508c2e22bc96f2181e9a871effe6f612436b1606e3bd02e30c1ba9b029f8651cf1fc203b238a17c81b40cbb678cdfac751950452dea97d29d46863dae14f66761063820b415d7d1e534f4898c508bbd899b7639dc1af377532f1c96504cf26d8781a8a3247c795abeae039413e72b6cecb247d9cc6731c949d8e10f61a82d50f4ec240bbd4d6f45d16ddf40cc5b5fab7d501dc143d3a8e38255185f911e173d8a323dd2af33e06e1c3cfa07a71ce00011f9bc213300e2b52d23f82b69bbd4dd4feb231271f2f29f072d22f628fb7453c8587ee525b109fc9423ab04d1e6bcb53db4063a258456a7ac4146ed7b615c2a7845283227323b114f85378dbe991fc5cede2aab7d0d975180b6ac9c64dee79a6430685a44cfefee78467a596d1a41e962c6a06b62302ccdf7477a578439c8d4c4c66d22ed8656f18a94bcbfce9fec848fa62261cbcf808957f1acac6df042ec686a69df6916715d5b1484abaa1901fe5a90af9f47c0c5d50f28c151fa272b82fc0baf9ce90505267b3043fa5d1115fb3d800a1acf148b4dd72ab9f5bb369eba24cc75596029e8ebfe3a737dcb129a35675edaf70dbb5bb15e4fd644a9c4ab6be238a416c9b05c5e898dd270b14776cf6d7b2db725a2c5a07a26e91c5cb0b5456c5b4711ae84b56e96b02bd33cf97948ec4491c9b7b31e6c8c297a7903ac618a5a5114de394290e9bd24789f247fc8a2e0ea79182a9fe9bdc515278864c2c8b105e6790dbd05c3dbfd59231a5c2d26533a87e7fe86f04564d857384ad880781910a07f7c4311ce29e8335ed602e13e6cacf269f8277ef6081d1924252082cfb506f703cf7a794175dd9625d75fd4fa3eea10440fc077f443016f6156f235e8767350b4453dd2ac6114cdb37f84f9e428da043adb4d0fdfcaa7230347ade13bca6f6a5ef01671f681f54eebc6676398ce21f8665f603fe4273808e988ea61475fe97f8a0fcbe35c48c27cfda743f59699f0d8145fc3ade5b4b2fda2c8a";
            String afterDecryptStr = AESUtils.decrypt(afterEncryptStrVal);
            System.out.println("Step4 解密:" + afterDecryptStr.length());
            

            // 解压缩
            String afterDecryptContent = CompressUtils.decompressToStr(afterDecryptStr);
            System.out.println("Step5 解压缩:" + afterDecryptContent.length());
            System.out.println("Step5 解压缩:" + afterDecryptContent);
            
            

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
