package ru.ezhov.duplicate.infrastructure

import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.RemovePartsScriptBuilder
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

@Service
class BashRemovePartsScriptBuilder(
        private val osDetector: OsDetector
) : RemovePartsScriptBuilder {
    companion object {
        const val ABSOLUTE_PATH_PLACEHOLDER = "__ABSOLUTE_PATH__"
        const val COMMANDS_PLACEHOLDER = "__COMMANDS__"
    }

    override fun buildScript(absoluteFilePaths: List<String>): String {

        InputStreamReader(this.javaClass.getResourceAsStream("/remove_parts_script.sh"), StandardCharsets.UTF_8)
                .use {
                    val text = it.readText()
                    val arr = text.split("\n")
                    val commandString = arr[1].substring(1)
                    val absoluteLinuxPaths = if (osDetector.isWindows()) {
                        absoluteFilePaths.map { s -> convertPathFromWinToLinux(s) }
                    } else {
                        absoluteFilePaths
                    }
                    val commands = absoluteLinuxPaths
                            .joinToString(separator = "\n") { s -> scriptCommandRemoveFile(s, commandString, ABSOLUTE_PATH_PLACEHOLDER) }
                    return text.replace(COMMANDS_PLACEHOLDER, commands)
                }
    }

    private fun convertPathFromWinToLinux(absoluteFilePath: String): String {
        val withLinuxSlash = absoluteFilePath
                .replace("\\\\", "\\")
                .replace("\\", "/")

        val colonIndex = withLinuxSlash.indexOf(":")

        val root = withLinuxSlash.substring(0, colonIndex).toLowerCase()
        val otherPath = withLinuxSlash.substring(colonIndex + 1)

        return """/$root$otherPath"""
    }

    private fun scriptCommandRemoveFile(absoluteLinuxFilePath: String, commandString: String, placeholder: String) =
            commandString.replace(placeholder, absoluteLinuxFilePath)
}