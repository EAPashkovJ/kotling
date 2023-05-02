package com.eapashkov.user_info.service.implement;

import com.eapashkov.user_info.feign.DownloadServiceFeignClient;
import com.eapashkov.user_info.model.UserInfoFromWorkSheet;
import com.eapashkov.user_info.repository.UserInfoFromWorkSheetRepository;
import com.eapashkov.user_info.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoFromWorkSheetRepository userInfoFromWorkSheetRepository;
    private final DownloadServiceFeignClient downloadServiceFeignClient;


    public void saveUserInfoFromXml(String id) throws JAXBException, IOException {

        ByteArrayResource xmlFromDownloadService = downloadServiceFeignClient.getXMLUserInfo(id);
        UserInfoFromWorkSheet userInfoFromWorkSheet = unmarshalToModel(xmlFromDownloadService);
        userInfoFromWorkSheetRepository.save(userInfoFromWorkSheet);

        log.info("User with ID: {} has been saved to DB", userInfoFromWorkSheet.getId());

    }

    private UserInfoFromWorkSheet unmarshalToModel(ByteArrayResource resource) throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(UserInfoFromWorkSheet.class);

        return (UserInfoFromWorkSheet) jaxbContext.createUnmarshaller()
                .unmarshal(resource.getInputStream());

    }


}
