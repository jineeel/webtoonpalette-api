package com.dev.webtoonpalette.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class FileUtil {

    @Value("${com.dev.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init(){
        File tempFolder = new File(uploadPath);
        if(!tempFolder.exists()){
            tempFolder.mkdirs();
        }
        uploadPath = tempFolder.getAbsolutePath();
    }

    public String saveFile(MultipartFile file) throws RuntimeException{

        if(file == null){
            return null;
        }

        String uploadName;
        String saveName = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
        Path savePath = Paths.get(uploadPath, saveName);

        try{
            Files.copy(file.getInputStream(), savePath);
            String contentType = file.getContentType();

            if(contentType != null || contentType.startsWith("image")){
                Path thumnailPath = Paths.get(uploadPath,"s_"+saveName);
                Thumbnails.of(savePath.toFile()).size(200,200).toFile(thumnailPath.toFile());
            }
            uploadName = saveName;

        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return uploadName;
    }

    public ResponseEntity<Resource> getFile(String fileName){

        Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);

        if(!resource.isReadable()){
            resource = new FileSystemResource(uploadPath+File.separator+"default.png");
        }
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().headers(headers).body(resource);

    }

    public void deleteFile(String fileName){
        if(fileName == null){
            return ;
        }

        //썸네일 삭제
        String thumbnailFileName = "s_"+fileName;
        Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName); //썸네일 path
        Path filePath = Paths.get(uploadPath, fileName); //원본 path

        try{
            Files.deleteIfExists(filePath);
            Files.deleteIfExists(thumbnailPath);
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }

    }

}
