

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
            String afterEncryptStrVal="7135a7ef01b1ed0a84484d07f7881b1081eb24eaeb420856f519de3aeb158f258f4e335951de71c66658314393007c83dd8b7c16377423c7431f13f4e550ab8bf63f57a4319dc8624a16f1bd8cddc7c3a1404eada665cc09cfa564c1119024dcb33f8a104a461ff0424220656af6662c4ff19b5341d1847ed234da6f8c4d714512f6b979ed76813ddfeb6303c022ddee4c5c61c16baf8ea1f27f77a09f50ba75a868e6b8ab8cbff8ea33131306c8f86ec0db6d7070636ec5ae7b4ae5a5a2e780017c541349f26b36c29d015d77e41ab095e5aa605b8440a0c35bc9162e2695a72a0c885c4a45ad2a1d805a1251e79378833212b4ff073db486b7e962ead9902a2bcb97a89e5bf50b06ff9236ea4417a1273c075264036e9cb231adab521e55ca3cf42c7cdb01c71aa2823c5ae4243172642f55b04cf573d508242c55dd7b606428e4b391533f41fc35f9b2e6650cd62644feae266e587f810f88395558670f41f7c9c145b67647e6ace87c440702b105a1b020ba45f4e823ceed2e0e71955939ff16e2896efb5f8514e256cfadfdd62923079b24644f340d7209113ff2f261036cb3ff7ed81b865c95068fc77bea4d948e6a4a6217b581646760d3a80a76ae60f5fcbe516301aeb8671ea0f3ace4ca22297f06699f2c0140af3d4e41c3324fc36ad2377289c62091973c8441cc306f67fbd739722a5075a2dc818d34ede1bdca268d79c4241a8f04e7cf8f0b6420ac18bcf6bcad51c40080423c52811552c241ffdfc113e3087f53fe9aedf1c8cf3c1b2c57e2bf8036eeea452e9da625a4f908fe43b624a2b9dd3f5c0dff63404cf83ec8d1618ef119fb8536cb5f5744ee340823aaaa04d410a69e58492dbaa7403d5eaf48b968924a97af3399ade6638191d5277d31d03599a65698e6671d9d5e6f506ff60c4344d7cffe57f1a528383caf4dca9098669093eaabf5d2b698804ccba8e3d8f93f53e246171926a76070d96444f43f8d1002a3435f7f7713bebf209ded6c0f9c1b5b1f0fc8daa626acf04591f32c6e19a63c5f3e7b743df8508d9cd3e5be6f948c02be926fa1a5b0486b4cbe61d6c1fa5d49ccfc4b2c9b9c9c331a0374325e0b4c8bbf5d9b934599f99619c39d706314dd28e8eeabca5ccf6518332695ac088384f56ae2f36acd31d38606fd71b0ca8eb55c6df8088aff6601a03a086d427fed53ab1bfb2549126463bba8433b86ad1ebabfad5179d6754512ff38c212d6009e08a79bafd9c126f0bb4a41851fda162c1e43d430cc8ea6958089fecd1c68b098f507af93fa43d0975eea23925544e956878fd27c39ed7d05fa73ce2123c11fa22b8dd39ec2f6f4a0c9581b49a192b83104cb215f149547d2235d455fcaeba8938aa8d15b0366a1bba7de368aa248e964e494d1094066304a528ef0e87043a36f901bde83f609c4e7f95775cd3e6dad6ff23a8051786a1cd590b599300a0723502a347d9c04b80792c3c3e95e7959bfd29f07ac7ef23b30443d1f0e8855e30421cbc48c9cfb10a848440503a5b46e0e947b51a4287ba9e74b6072752d64874a80cbf03a0c6b868139155ca6b492";
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
