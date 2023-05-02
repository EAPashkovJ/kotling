package com.eapashkov.user_info.controller

import com.eapashkov.user_info.service.implement.UserInfoServiceImpl
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import javax.xml.bind.JAXBException

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/userinfo")
class UserInfoController(
    private val userInfoService: UserInfoServiceImpl
) {

    @GetMapping("/{id}")
    @Throws(IOException::class, JAXBException::class)
    fun addUserInfoFromXml(@PathVariable id: String?): ResponseEntity<String> {
        userInfoService.saveUserInfoFromXml(id)

        return ResponseEntity("User has been added to DB ", HttpStatus.CREATED)
    }
}
