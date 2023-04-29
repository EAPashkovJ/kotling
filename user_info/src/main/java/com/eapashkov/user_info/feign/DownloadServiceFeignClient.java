package com.eapashkov.user_info.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${feign.url.download-service}", name = "downloadService")
public interface DownloadServiceFeignClient {

    @GetMapping("/download/{id}")
    ByteArrayResource getXMLUserInfo(@PathVariable String id);
}
