package com.eapashkov.downloadexecutor.service.impl

import com.eapashkov.downloadexecutor.model.FileExchanger
import com.eapashkov.downloadexecutor.service.DownloadService
import com.mongodb.BasicDBObject
import mu.KotlinLogging
import org.apache.commons.io.IOUtils
import org.apache.tomcat.util.http.fileupload.FileUploadException
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsOperations
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException

@Service
@Transactional
class DownloadServiceImpl(
    private val gridFsTemplate: GridFsTemplate,
    private val gridFsOperations: GridFsOperations
) : DownloadService {

    private val logger = KotlinLogging.logger {}

    override fun upload(files: Array<MultipartFile?>?): List<String?>? {

        val fileIds = ArrayList<String>()

        try {
            if (files != null) {
                for (file in files) {
                    val metadata = BasicDBObject()
                    metadata["filesize"] = file?.size
                    val fileId = gridFsTemplate.store(
                        file?.inputStream!!,
                        file.originalFilename,
                        file.contentType,
                        metadata
                    )
                    fileIds.add(fileId.toString())
                    logger.info("{} has been uploaded!", StringUtils.capitalize(file.originalFilename!!))
                }
            }

            return fileIds
        }catch (e: FileUploadException){
             logger.error { "File is missing"}
        }
        return null
    }

    override fun download(fileId: String?): FileExchanger? {

            if (fileId == null) {
                logger.error {  }
                throw IllegalArgumentException("fileId cannot be null")
            }

            val gridFSFile = gridFsTemplate.findOne(Query(Criteria.where("_id").`is`(ObjectId(fileId))))
            if (gridFSFile == null) {
                logger.error { "File with fileID: $fileId not found" }
                     return throw FileNotFoundException()
            }

            val fileExchanger = FileExchanger()
            if (gridFSFile.metadata != null) {
                fileExchanger.filename = gridFSFile.filename
                fileExchanger.fileType = gridFSFile.metadata?.get("_contentType").toString()
                fileExchanger.fileSize = gridFSFile.metadata?.get("filesize").toString()
                fileExchanger.metadata = IOUtils.toByteArray(gridFsOperations.getResource(gridFSFile).inputStream)

                logger.info("{} has been downloaded!", StringUtils.capitalize(gridFSFile.filename))
            }
            return fileExchanger
        }
    }

