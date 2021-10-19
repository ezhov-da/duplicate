package ru.ezhov.duplicate.domain

import java.io.File

data class Part(
        val id: String,
        val duplicateId: String,
        val name: String,
        val file: File
) {
    val fileType: String = file.extension.lowercase().ifEmpty { "other" }
}