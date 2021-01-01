package ru.ezhov.duplicate.infrastructure

import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.Fingerprint
import ru.ezhov.duplicate.domain.FingerprintParser
import ru.ezhov.duplicate.domain.FingerprintParserException
import java.io.ByteArrayInputStream
import java.io.File
import java.util.*

@Service
class Md5SumLinuxFingerprintParser(
        private val osDetector: OsDetector
) : FingerprintParser {
    override fun parse(name: String, bytes: ByteArray): List<Fingerprint> {
        Scanner(ByteArrayInputStream(bytes), "UTF-8").use { scanner ->
            val files: MutableList<Fingerprint> = ArrayList<Fingerprint>()
            while (scanner.hasNextLine()) {
                val line: String = scanner.nextLine()
                val index = line.indexOf('*')
                if (index == -1) {
                    throw FingerprintParserException("Error format '$name'")
                }
                val sum = line.substring(0, index)
                val path = line.substring(index + 1)
                val filePath = if (osDetector.isWindows()) convertPath(path) else path
                files.add(Fingerprint(id = sum.trim(), filePath = filePath))
            }
            return files
        }
    }

    private fun convertPath(path: String): String {
        val begin = path.substring(0, 2)
        val another = path.substring(3)
        val disk = begin.substring(1)
        return disk.toUpperCase() + ":\\" + another.replace(oldValue = "/", newValue = "\\")
    }
}