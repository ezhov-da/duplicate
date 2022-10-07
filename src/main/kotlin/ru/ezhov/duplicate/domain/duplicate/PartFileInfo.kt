package ru.ezhov.duplicate.domain.duplicate

import java.io.InputStream

class PartFileInfo(
        val mimeType: String,
        val data: InputStream,
)