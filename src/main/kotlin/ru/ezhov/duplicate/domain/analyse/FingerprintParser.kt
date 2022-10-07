package ru.ezhov.duplicate.domain.analyse

interface FingerprintParser {
    fun parse(name: String, bytes: ByteArray): List<Fingerprint>
}