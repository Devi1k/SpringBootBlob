package com.example.springbootblog.controller.admin;

import com.example.springbootblog.utils.MyBlogUtils;
import com.example.springbootblog.utils.Result;
import com.example.springbootblog.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class UploadController {

    @Value("${upload.file.path}")
    public String uploadPath;

    @PostMapping("/upload/file")
    @ResponseBody
    public Result upload(HttpServletRequest request, @RequestParam("file") MultipartFile multipartFile) throws URISyntaxException {
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffix);
        String newFileName = tempName.toString();
        File fileDirectory = new File(uploadPath);
        File destFile = new File(uploadPath + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为" + newFileName);
                }
            }
            multipartFile.transferTo(destFile);
            Result resultSuccess = ResultGenerator.genSuccessResult();
            resultSuccess.setData(MyBlogUtils.getHost(new URI(request.getRequestURI() + "")) + "/upload/" + newFileName);
            return resultSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }
}

