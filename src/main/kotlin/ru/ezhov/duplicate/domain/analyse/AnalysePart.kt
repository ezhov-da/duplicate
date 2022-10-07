package ru.ezhov.duplicate.domain.analyse

import java.io.File

data class AnalysePart(
        val id: String,
        val name: String,
        val file: File,
        val selected: Boolean = false
)