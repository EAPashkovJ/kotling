package com.eapashkov.user_info.service

import java.io.IOException
import javax.xml.bind.JAXBException

interface UserInfoService {

    @Throws(JAXBException::class, IOException::class)
    fun saveUserInfoFromXml(id: String?)
}