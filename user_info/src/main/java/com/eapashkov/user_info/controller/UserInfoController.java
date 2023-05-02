package com.eapashkov.user_info.controller;

import com.eapashkov.user_info.service.implement.UserInfoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/userinfo")
public class UserInfoController {

    private final UserInfoServiceImpl userInfoService;

    @GetMapping("/{id}")
    public ResponseEntity<String> addUserInfoFromXml(@PathVariable String id) throws IOException, JAXBException {
        userInfoService.saveUserInfoFromXml(id);

        return new ResponseEntity<>("User has been added to DB ", HttpStatus.CREATED);

    }


}
