package ru.ezhov.duplicate.domain

interface FingerprintParser {
    fun parse(name: String, bytes: ByteArray): List<Fingerprint>
}