package ru.ezhov.duplicate.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.ezhov.duplicate.domain.duplicate.PartSelectedRepository
import java.io.File
import java.util.concurrent.ConcurrentHashMap

@Component
class FilePartSelectedRepository(
        @Value("\${storage.folder}") private val storageFolder: String
) : PartSelectedRepository {
    private val file: File = File(storageFolder, "selected.yml")
    private val objectMapper: ObjectMapper = ObjectMapper(YAMLFactory()).apply { registerModule(JavaTimeModule()) }
    private val selected: ConcurrentHashMap<String, MutableList<String>> = ConcurrentHashMap<String, MutableList<String>>()

    init {
        load()
    }

    override fun select(uploadId: String, idPart: String): Boolean {
        var list = selected[uploadId]
        return if (list == null) {
            list = mutableListOf(idPart)
            selected[uploadId] = list
            save()
            true
        } else {
            if (list.contains(idPart)) {
                list.remove(idPart)
                save()
                false
            } else {
                list.add(idPart)
                save()
                true
            }
        }
    }

    override fun selectIds(uploadId: String): List<String> = selected[uploadId] ?: emptyList()

    private fun load() {
        val folder = File(storageFolder)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        if (file.exists()) {
            val sp = objectMapper.readValue(file, SelectedPartsData::class.java)
            selected.clear()
            sp.selectedPart.forEach {
                selected[it.uploadId] = it.partIds
            }
        }
    }

    private fun save() {
        val folder = File(storageFolder)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val selectedPartsData = SelectedPartsData()
        selectedPartsData.selectedPart =
                selected
                        .map { e ->
                            SelectedPartData().apply {
                                uploadId = e.key
                                partIds = e.value
                            }
                        }
        objectMapper.writeValue(
                file,
                selectedPartsData
        )
    }
}