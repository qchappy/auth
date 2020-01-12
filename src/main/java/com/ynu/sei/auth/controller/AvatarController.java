package com.ynu.sei.auth.controller;

import com.ynu.sei.auth.domain.User;
import com.ynu.sei.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class AvatarController {

    @Autowired
    private UserRepository userRepository;

    private static final String BASE_PATH = "/Users/zhaorunxuan/Documents/dev-images/";
    private static final String BASE_URL = "/api/images/";

    @CrossOrigin
    @RequestMapping(value = "/api/avatar", method = RequestMethod.POST)
    public String changeAvatar(
            @RequestParam(value = "file", required = true) MultipartFile file,
            @RequestParam(value = "username", required = true) String username
    ) {
        if (file == null)
            throw new RuntimeException("File is empty!");

        User user = userRepository.getByUserName(username);
        if (user == null) throw new RuntimeException("No such user!");
        String fileName = user.getId() + ".png";
        String savePath = BASE_PATH + fileName;
        File dest = new File(savePath);

        // 当图片路径存在时，将其删除
        if (dest.exists()) dest.delete();
        // 将文件写入本地
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = BASE_URL + fileName;
        user.setAvatar(url);
        userRepository.save(user);
        return "ok!";
    }
}
