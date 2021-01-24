package ru.ezhov.duplicate.domain

interface RemovePartsScriptBuilder {
    fun buildScript(absoluteFilePaths: List<String>): String
}