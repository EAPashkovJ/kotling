package com.eapashkov.downloadexecutor.controller

import com.eapashkov.downloadexecutor.exception.FileFailureDownloadException
import com.eapashkov.downloadexecutor.service.DownloadService
import lombok.RequiredArgsConstructor
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
class DownloadController(private val downloadService: DownloadService) {

    @PostMapping("/upload")
    fun uploadFiles(@RequestParam("files") files: Array<MultipartFile?>): ResponseEntity<List<String?>?> {
        return ResponseEntity(downloadService.upload(files), HttpStatus.CREATED)
    }

    @GetMapping("/download/{id}")
    fun download(@PathVariable id: String?): ResponseEntity<ByteArrayResource>? {
        val fileExchanger = downloadService.download(id) ?: throw FileFailureDownloadException("File not found!")

        fileExchanger.fileType.let { MediaType.parseMediaType(it) }?.let {

            return ResponseEntity.ok()
                .contentType(it)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileExchanger.filename + "\"")
                .body(fileExchanger.metadata?.let { it1 -> ByteArrayResource(it1) })
        }

        return  throw FileFailureDownloadException("File not found!")
    }
}