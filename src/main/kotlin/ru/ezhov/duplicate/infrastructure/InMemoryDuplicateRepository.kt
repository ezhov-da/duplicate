package ru.ezhov.duplicate.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.ezhov.duplicate.domain.duplicate.model.Duplicate
import ru.ezhov.duplicate.domain.duplicate.DuplicateRepository
import java.io.File
import java.util.concurrent.ConcurrentLinkedQueue

@Component
class InMemoryDuplicateRepository
(
        @Value("\${storage.folder}") private val storageFolder: String
) : DuplicateRepository {
    private val file: File = File(storageFolder, "duplicates.yml")
    private val objectMapper: ObjectMapper = ObjectMapper(YAMLFactory()).apply { registerModule(JavaTimeModule()) }
    private val duplicatesList: ConcurrentLinkedQueue<Duplicate> = ConcurrentLinkedQueue<Duplicate>()

    override fun all(): List<Duplicate> {
        if (duplicatesList.isEmpty()) {
            load()
        }

        return duplicatesList.toList()
    }

    override fun by(uploadId: String): List<Duplicate> {
        if (duplicatesList.isEmpty()) {
            load()
        }

        return duplicatesList.filter { it.uploadId == uploadId }
    }

    override fun save(duplicates: List<Duplicate>) {
        duplicatesList.addAll(duplicates)

        save()
    }

    private fun load() {
        val folder = File(storageFolder)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        if (file.exists()) {
            val dd = objectMapper.readValue(file, DuplicatesData::class.java)
            duplicatesList.clear()
            dd.duplicates.forEach {
                duplicatesList.add(it.convert())
            }
        }
    }

    private fun save() {
        val folder = File(storageFolder)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val duplicatesData = DuplicatesData()
        duplicatesData.duplicates = duplicatesList
                .map {
                    DuplicateData.from(it)
                }
        objectMapper.writeValue(
                file,
                duplicatesData
        )
    }
}