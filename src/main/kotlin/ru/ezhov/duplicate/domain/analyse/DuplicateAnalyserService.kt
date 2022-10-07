package ru.ezhov.duplicate.domain.analyse

interface DuplicateAnalyserService {
    fun duplicates(fingerprints: List<Fingerprint>): List<AnalyseDuplicate>
}