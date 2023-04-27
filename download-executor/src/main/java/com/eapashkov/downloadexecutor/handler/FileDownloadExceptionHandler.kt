package com.eapashkov.downloadexecutor.handler

import com.eapashkov.downloadexecutor.exception.FileFailureDownloadException
import com.eapashkov.downloadexecutor.model.errors.ResponseError
import lombok.AllArgsConstructor
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.FileNotFoundException

@RestControllerAdvice
@AllArgsConstructor
class FileDownloadExceptionHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(FileNotFoundException::class)
    fun fileNotFoundExceptionHandler(e: FileFailureDownloadException): ResponseEntity<ResponseError> {
        logger.error("Caught not found exception: {}", e.toString())
        val responseError = ResponseError(e.message)
        return ResponseEntity(responseError, HttpStatus.NOT_FOUND)
    }
}
