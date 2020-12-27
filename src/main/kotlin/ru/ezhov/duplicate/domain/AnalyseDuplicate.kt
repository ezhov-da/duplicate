package ru.ezhov.duplicate.domain

data class AnalyseDuplicate(
        val id: String,
        val parts: List<AnalysePart>
)