package ru.ezhov.duplicate.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.duplicate.model.Part
import ru.ezhov.duplicate.domain.duplicate.PartRepository
import java.io.File
import java.util.concurrent.ConcurrentLinkedQueue

@Service
class InMemoryPartRepository(
        @Value("\${storage.folder}") private val storageFolder: String
) : PartRepository {
    private val file: File = File(storageFolder, "parts.yml")
    private val objectMapper: ObjectMapper = ObjectMapper(YAMLFactory()).apply { registerModule(JavaTimeModule()) }
    private val partsList: ConcurrentLinkedQueue<Part> = ConcurrentLinkedQueue<Part>()

    override fun by(id: String): Part? {
        if (partsList.isEmpty()) {
            load()
        }

        return partsList.firstOrNull { it.id == id }
    }

    override fun save(parts: List<Part>) {
        partsList.addAll(parts)

        save()
    }

    private fun load() {
        val folder = File(storageFolder)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        if (file.exists()) {
            val pd = objectMapper.readValue(file, PartsData::class.java)
            partsList.clear()
            pd.partsData.forEach {
                partsList.add(it.convert())
            }
        }
    }

    private fun save() {
        val folder = File(storageFolder)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val partsData = PartsData()
        partsData.partsData = partsList
                .map {
                    PartData.from(it)
                }
        objectMapper.writeValue(
                file,
                partsData
        )
    }
}