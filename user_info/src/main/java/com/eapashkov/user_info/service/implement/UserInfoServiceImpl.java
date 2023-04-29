package com.eapashkov.user_info.service.implement;

import com.eapashkov.user_info.feign.DownloadServiceFeignClient;
import com.eapashkov.user_info.model.UserInfoFromWorkSheet;
import com.eapashkov.user_info.repository.UserInfoFromWorkSheetRepository;
import com.eapashkov.user_info.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    // private final UserInfoFromWorkSheetRepository userInfoFromWorkSheetRepository;
    private final DownloadServiceFeignClient downloadServiceFeignClient;
    private final Jaxb2Marshaller jaxbMarshaller;


    public UserInfoFromWorkSheet getusermodel(String id) throws IOException {
        ByteArrayResource xmlFromDownloadService = downloadServiceFeignClient.getXMLUserInfo(id);
        UserInfoFromWorkSheet userInfoFromWorkSheet = xmlToEntityMapper(xmlFromDownloadService);


        return userInfoFromWorkSheet;
    }

    private UserInfoFromWorkSheet xmlToEntityMapper(ByteArrayResource res) throws IOException {

        jaxbMarshaller.setClassesToBeBound(UserInfoFromWorkSheet.class);
        JAXBElement<UserInfoFromWorkSheet> jaxbElement =
                (JAXBElement<UserInfoFromWorkSheet>) jaxbMarshaller.unmarshal(new StreamSource(res.getInputStream()));
        return jaxbElement.getValue();
    }


}
