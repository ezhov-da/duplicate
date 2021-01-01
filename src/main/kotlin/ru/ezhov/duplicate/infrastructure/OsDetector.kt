package ru.ezhov.duplicate.infrastructure

interface OsDetector {
    fun isWindows(): Boolean
}