package com.eapashkov.user_info.service.implement

import com.eapashkov.user_info.feign.DownloadServiceFeignClient
import com.eapashkov.user_info.model.UserInfoFromWorkSheet
import com.eapashkov.user_info.repository.UserInfoFromWorkSheetRepository
import com.eapashkov.user_info.service.UserInfoService
import mu.KotlinLogging
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException

@Service
@Transactional
class UserInfoServiceImpl(
    private val userInfoFromWorkSheetRepository: UserInfoFromWorkSheetRepository,
    private val downloadServiceFeignClient: DownloadServiceFeignClient
) : UserInfoService {

    private val logger = KotlinLogging.logger {}


    @Throws(JAXBException::class, IOException::class)
    override fun saveUserInfoFromXml(id: String?) {

        val xmlFromDownloadService = downloadServiceFeignClient.getXMLUserInfo(id)
        val userInfoFromWorkSheet = xmlFromDownloadService?.let { unmarshalToModel(it) }
        if (userInfoFromWorkSheet != null) {
            userInfoFromWorkSheetRepository.save(userInfoFromWorkSheet)
        }

        logger.info("User with ID: {} has been saved to DB", userInfoFromWorkSheet)
    }

    @Throws(IOException::class, JAXBException::class)
    private fun unmarshalToModel(resource: ByteArrayResource): UserInfoFromWorkSheet {
        val jaxbContext = JAXBContext.newInstance(UserInfoFromWorkSheet::class.java)
        return jaxbContext.createUnmarshaller()
            .unmarshal(resource.inputStream) as UserInfoFromWorkSheet
    }
}
