package ru.ezhov.duplicate.domain.duplicate.model

import java.io.File
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Part(
        @Id
        val id: String,
        val duplicateId: String,
        val name: String,
        val file: File,
        val selected: Boolean = false,
) {
    val fileType: String = file.extension.lowercase().ifEmpty { "other" }
}