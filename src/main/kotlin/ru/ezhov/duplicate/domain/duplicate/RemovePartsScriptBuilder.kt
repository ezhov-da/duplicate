package ru.ezhov.duplicate.domain.duplicate

interface RemovePartsScriptBuilder {
    fun buildScript(absoluteFilePaths: List<String>): String
}