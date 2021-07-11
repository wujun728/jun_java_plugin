package kr.hwb.example.ctrl;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.hwb.example.entity.UploadFile;
import kr.hwb.example.service.ImageService;
import kr.hwb.example.util.MediaUtils;

@Controller
public class ImageController {
    
    @Autowired
    ImageService imageService;
    
    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        
        model.addAttribute("files", imageService.loadAll().collect(Collectors.toList()));
        
        return "index";
    }
    
    @GetMapping("/image/{fileId}")
    @ResponseBody
    public ResponseEntity<?> serveFile(@PathVariable int fileId) {
        try {
            UploadFile uploadedFile = imageService.load(fileId);
            HttpHeaders headers = new HttpHeaders();
            
            String fileName = uploadedFile.getFileName();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

            if (MediaUtils.containsImageMediaType(uploadedFile.getContentType())) {
                headers.setContentType(MediaType.valueOf(uploadedFile.getContentType()));
            } else {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }

            Resource resource = imageService.loadAsResource(uploadedFile.getSaveFileName());
            return ResponseEntity.ok().headers(headers).body(resource);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/image")
    @ResponseBody
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            UploadFile uploadedFile = imageService.store(file);
            return ResponseEntity.ok().body("/image/" + uploadedFile.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    
}
