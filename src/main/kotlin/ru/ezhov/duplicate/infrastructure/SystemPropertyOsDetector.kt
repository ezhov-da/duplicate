package ru.ezhov.duplicate.infrastructure

import org.springframework.stereotype.Service

@Service
class SystemPropertyOsDetector : OsDetector {
    override fun isWindows(): Boolean =
            System.getProperty("os.name").toLowerCase().contains(other = "windows")
}