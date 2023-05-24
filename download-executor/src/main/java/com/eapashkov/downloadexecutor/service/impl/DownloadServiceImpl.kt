package com.eapashkov.downloadexecutor.service.impl

import com.eapashkov.downloadexecutor.model.FileExchanger
import com.eapashkov.downloadexecutor.service.DownloadService
import com.mongodb.BasicDBObject
import mu.KotlinLogging
import org.apache.commons.io.IOUtils
import org.apache.tomcat.util.http.fileupload.FileUploadException
import org.bson.types.ObjectId
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsOperations
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import org.xml.sax.SAXException
import java.io.FileNotFoundException
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator

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
                    val errorMessage = mutableListOf<String>()

                    for (file in files) {
                        if (file != null) {
                            if (!file.isEmpty && (file.contentType == "application/xml")) {
                                try {
                                    val xsdResource = ClassPathResource("user_info.xsd")
                                    val xsdStream = xsdResource.inputStream

                                    val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                                    val schema = schemaFactory.newSchema(StreamSource(xsdStream))

                                    val validator: Validator = schema.newValidator()
                                    validator.validate(StreamSource(file.inputStream))


                                } catch (e: SAXException) {
                                    errorMessage.add("Файл '${file.originalFilename}' не прошел валидацию: ${e.message}")
                                } catch (e: Exception) {
                                    errorMessage.add("Ошибка при обработке файла '${file.originalFilename}': ${e.message}")
                                }
                            } else {
                                errorMessage.add("Файл '${file.originalFilename}' не является допустимым XML-файлом")
                            }
                        }
                        
                    }
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
        return throw FileNotFoundException()
    }

    override fun download(fileId: String?): FileExchanger? {

            if (fileId == null) {
                logger.error {  }
                throw IllegalArgumentException("fileId cannot be null")
            }

            val gridFSFile = gridFsTemplate.findOne(Query(Criteria.where("_id").`is`(ObjectId(fileId))))
            if (gridFSFile == null) {
                logger.error { "File with fileID: $fileId not found" }
                     return throw FileNotFoundException("File not found")
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

