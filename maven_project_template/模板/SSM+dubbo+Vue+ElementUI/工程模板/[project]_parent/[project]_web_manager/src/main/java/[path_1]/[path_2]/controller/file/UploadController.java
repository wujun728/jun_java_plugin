package [path_1].[path_2].controller.file;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/native")
    public String nativeUpload(@RequestParam("file") MultipartFile file) {
        String path=request.getSession().getServletContext().getRealPath("img");
        String filePath = path +"/"+ file.getOriginalFilename();
        File desFile = new File(filePath);
        if(!desFile.getParentFile().exists()){
            desFile.mkdirs();
        }
        try {
            file.transferTo(desFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("path:---"+filePath);
        return "http://localhost:9101/img/"+file.getOriginalFilename();
    }

    @Autowired
    private OSSClient ossClient;

    @PostMapping("/oss")
    public String ossUpload(@RequestParam("file") MultipartFile file,String folder){
        String bucketName = "qing-cheng";
        String fileName= folder+"/"+ UUID.randomUUID()+"_"+file.getOriginalFilename();
        try {
            ossClient.putObject(bucketName, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "http://"+bucketName+"."+ ossClient.getEndpoint().toString().replace("http://","") +"/"+fileName;
    }


}