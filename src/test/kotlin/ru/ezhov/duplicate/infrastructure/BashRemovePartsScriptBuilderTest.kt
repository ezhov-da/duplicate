package ru.ezhov.duplicate.infrastructure

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BashRemovePartsScriptBuilderTest {
    @Test
    fun shouldBeOkWhenIsWindowsAndWindowsPath() {
        val bashRemovePartsScriptBuilder = BashRemovePartsScriptBuilder(object : OsDetector {
            override fun isWindows(): Boolean {
                return true
            }
        })

        val script = bashRemovePartsScriptBuilder.buildScript(
                listOf(
                        "D:\\repository\\duplicate\\src\\main\\kotlin\\ru\\ezhov\\duplicate\\infrastructure\\DuplicateData.kt",
                        "D:\\repository\\duplicate\\src\\main\\kotlin\\ru\\ezhov\\duplicate\\infrastructure\\InMemoryPartSelectedRepository.kt",
                        "D:\\repository\\duplicate\\src\\main\\kotlin\\ru\\ezhov\\duplicate\\infrastructure\\InMemoryUploadRepository.kt"
                )
        )

        assertThat(script).isEqualTo(
                "#!/usr/bin/env bash\n" +
                        "#rm -v \"__ABSOLUTE_PATH__\" >> \$REPORT_FILE 2>&1\n" +
                        "\n" +
                        "NOW=\$(date +%Y-%m-%d_%H-%M-%S);\n" +
                        "REPORT_FILE=delete_duplicate_report-\${NOW}.txt;\n" +
                        "\n" +
                        "rm -v \"/d/repository/duplicate/src/main/kotlin/ru/ezhov/duplicate/infrastructure/DuplicateData.kt\" >> \$REPORT_FILE 2>&1\n" +
                        "rm -v \"/d/repository/duplicate/src/main/kotlin/ru/ezhov/duplicate/infrastructure/InMemoryPartSelectedRepository.kt\" >> \$REPORT_FILE 2>&1\n" +
                        "rm -v \"/d/repository/duplicate/src/main/kotlin/ru/ezhov/duplicate/infrastructure/InMemoryUploadRepository.kt\" >> \$REPORT_FILE 2>&1\n" +
                        "\n" +
                        "echo deletion completed, report file \\'\$(pwd)/\$REPORT_FILE\\'"
        )
    }

    @Test
    fun shouldBeOkWhenIsLinuxAndLinuxPath() {
        val bashRemovePartsScriptBuilder = BashRemovePartsScriptBuilder(object : OsDetector {
            override fun isWindows(): Boolean {
                return false
            }
        })

        val script = bashRemovePartsScriptBuilder.buildScript(
                listOf(
                        "/d/repository/duplicate/src/main/kotlin/ru/ezhov/duplicate/infrastructure/DuplicateData.kt",
                        "/d/repository/duplicate/src/main/kotlin/ru/ezhov/duplicate/infrastructure/InMemoryPartSelectedRepository.kt",
                        "/d/repository/duplicate/src/main/kotlin/ru/ezhov/duplicate/infrastructure/InMemoryUploadRepository.kt"
                )
        )

        assertThat(script).isEqualTo(
                "#!/usr/bin/env bash\n" +
                        "#rm -v \"__ABSOLUTE_PATH__\" >> \$REPORT_FILE 2>&1\n" +
                        "\n" +
                        "NOW=\$(date +%Y-%m-%d_%H-%M-%S);\n" +
                        "REPORT_FILE=delete_duplicate_report-\${NOW}.txt;\n" +
                        "\n" +
                        "rm -v \"/d/repository/duplicate/src/main/kotlin/ru/ezhov/duplicate/infrastructure/DuplicateData.kt\" >> \$REPORT_FILE 2>&1\n" +
                        "rm -v \"/d/repository/duplicate/src/main/kotlin/ru/ezhov/duplicate/infrastructure/InMemoryPartSelectedRepository.kt\" >> \$REPORT_FILE 2>&1\n" +
                        "rm -v \"/d/repository/duplicate/src/main/kotlin/ru/ezhov/duplicate/infrastructure/InMemoryUploadRepository.kt\" >> \$REPORT_FILE 2>&1\n" +
                        "\n" +
                        "echo deletion completed, report file \\'\$(pwd)/\$REPORT_FILE\\'"
        )
    }
}