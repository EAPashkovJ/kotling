package com.eapashkov.user_info.controller;

import com.eapashkov.user_info.model.UserInfoFromWorkSheet;
import com.eapashkov.user_info.service.implement.UserInfoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/userinfo")
public class UserInfoController {

    private final UserInfoServiceImpl userInfoService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)

    public UserInfoFromWorkSheet getUserInfo(@PathVariable String id) throws IOException {
        return userInfoService.getusermodel(id);
    }


}
