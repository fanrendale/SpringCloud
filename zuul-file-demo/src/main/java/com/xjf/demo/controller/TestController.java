package com.xjf.demo.controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author xjf
 * @date 2020/1/29 16:59
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(){
//        System.out.println(2/0);
        return "Hello World";
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/file/upload")
    public String fileUpload(@RequestParam("file")MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        File fileToSave = new File(file.getOriginalFilename());
        FileCopyUtils.copy(bytes,fileToSave);

        return fileToSave.getAbsolutePath();
    }
}
