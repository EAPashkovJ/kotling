package com.eapashkov.user_info

import com.eapashkov.user_info.feign.DownloadServiceFeignClient
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.openfeign.EnableFeignClients


@SpringBootApplication
@EnableFeignClients(clients = [DownloadServiceFeignClient::class])
class UserInfoApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(UserInfoApplication::class.java, *args)
        }
    }
}
