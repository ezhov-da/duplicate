package ru.ezhov.duplicate.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import ru.ezhov.duplicate.domain.duplicate.model.Upload
import ru.ezhov.duplicate.domain.duplicate.UploadRepository
import java.io.File
import java.util.concurrent.ConcurrentHashMap

@Repository
class FileUploadRepository(
        @Value("\${storage.folder}") private val storageFolder: String
) : UploadRepository {
    private val file: File = File(storageFolder, "uploads.yml")
    private val objectMapper: ObjectMapper = ObjectMapper(YAMLFactory()).apply { registerModule(JavaTimeModule()) }
    private val uploads: ConcurrentHashMap<String, Upload> = ConcurrentHashMap<String, Upload>()

    override fun all(): List<Upload> {
        if (uploads.isEmpty()) {
            load()
        }
        return uploads.values.toList()
    }

    override fun save(upload: List<Upload>) {
        upload.forEach {
            uploads.putIfAbsent(it.id, it)
        }
        save()
    }

    override fun by(id: String): Upload? {
        if (uploads.isEmpty()) {
            load()
        }

        return uploads[id]
    }

    private fun load() {
        val folder = File(storageFolder)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        if (file.exists()) {
            val up = objectMapper.readValue(file, UploadsData::class.java)
            uploads.clear()
            up.uploads.forEach {
                uploads[it.id] = it.convert()
            }
        }
    }

    private fun save() {
        val folder = File(storageFolder)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val uploadsData = UploadsData()
        uploadsData.uploads = uploads
                .values
                .map {
                    UploadData.from(it)
                }
        objectMapper.writeValue(
                file,
                uploadsData
        )
    }
}