package com.trade.global.controller;

import com.trade.global.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    @Value("${upload.path:./uploads}")
    private String uploadPath;

    @PostMapping
    public Result<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }
        
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return Result.error("文件名不能为空");
        }
        
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String[] allowedExts = {".jpg", ".jpeg", ".png", ".gif", ".webp", ".pdf", ".doc", ".docx", ".xls", ".xlsx"};
        boolean isAllowed = false;
        for (String allowedExt : allowedExts) {
            if (ext.toLowerCase().equals(allowedExt)) {
                isAllowed = true;
                break;
            }
        }
        if (!isAllowed) {
            return Result.error("不支持的文件格式");
        }
        
        long fileSize = file.getSize();
        if (fileSize > 10 * 1024 * 1024) {
            return Result.error("文件大小不能超过10MB");
        }
        
        String datePath = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String savePath = uploadPath + File.separator + datePath;
        
        try {
            Path path = Paths.get(savePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            
            String newFilename = UUID.randomUUID().toString().replace("-", "") + ext;
            File dest = new File(path.toFile(), newFilename);
            file.transferTo(dest);
            
            String url = "/uploads/" + datePath + "/" + newFilename;
            
            Map<String, String> result = new HashMap<>();
            result.put("url", url);
            result.put("filename", newFilename);
            result.put("originalFilename", originalFilename);
            result.put("size", String.valueOf(fileSize));
            
            return Result.success(result);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传失败: " + e.getMessage());
        }
    }
}
