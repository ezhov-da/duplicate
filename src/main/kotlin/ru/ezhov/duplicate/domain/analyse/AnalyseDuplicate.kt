package ru.ezhov.duplicate.domain.analyse

data class AnalyseDuplicate(
        val id: String,
        val parts: List<AnalysePart>
)