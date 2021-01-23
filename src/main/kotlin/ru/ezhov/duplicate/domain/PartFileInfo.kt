package ru.ezhov.duplicate.domain

import java.io.InputStream

class PartFileInfo(
        val mimeType: String,
        val data: InputStream,
)