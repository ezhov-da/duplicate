package ru.ezhov.duplicate.infrastructure

import ru.ezhov.duplicate.domain.Part
import java.io.File

class PartData {
    var id: String = ""
    var duplicateId: String = ""
    var name: String = ""
    var path: String = ""

    companion object {
        fun from(part: Part) = PartData().apply {
            id = part.id
            duplicateId = part.duplicateId
            name = part.name
            path = part.file.absolutePath
        }
    }

    fun convert() = Part(
            id = this.id,
            duplicateId = this.duplicateId,
            name = this.name,
            file = File(this.path)
    )
}