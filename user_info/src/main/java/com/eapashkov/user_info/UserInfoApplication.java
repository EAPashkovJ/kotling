package com.eapashkov.user_info;

import com.eapashkov.user_info.feign.DownloadServiceFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * При отправке id возвращает XML файл с полями пользователя
 * парсит его через jaXB и добавляет в БД как юзера чтобы дальше что нибудь да сделать
 */
@SpringBootApplication
@EnableFeignClients(clients = {DownloadServiceFeignClient.class})
public class UserInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserInfoApplication.class, args);
    }

}
