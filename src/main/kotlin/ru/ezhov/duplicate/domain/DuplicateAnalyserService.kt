package ru.ezhov.duplicate.domain

interface DuplicateAnalyserService {
    fun duplicates(fingerprints: List<Fingerprint>): List<AnalyseDuplicate>
}