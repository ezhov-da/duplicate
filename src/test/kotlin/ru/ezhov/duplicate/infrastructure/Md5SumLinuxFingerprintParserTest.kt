package ru.ezhov.duplicate.infrastructure

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

internal class Md5SumLinuxFingerprintParserTest {
    @Test
    fun shouldParseOnWindowsServerOk() {
        val osDetector = Mockito.mock(OsDetector::class.java)
        `when`(osDetector.isWindows()).then { true }
        val md5SumLinuxFingerprintParser = Md5SumLinuxFingerprintParser(osDetector)

        val fingerprints = md5SumLinuxFingerprintParser.parse(
                "test",
                this.javaClass.getResource("/duplicate-2020-12-30.txt").readBytes()
        )

        assertThat(fingerprints).hasSize(3)

        var fingerprint = fingerprints[0]
        assertThat(fingerprint.id).isEqualTo("d41d8cd98f00b204e9800998ecf8427e")
        assertThat(fingerprint.filePath).isEqualTo("D:\\redmi3s-20201221-work\\Camera\\duplicate-2020-12-30.txt")

        fingerprint = fingerprints[1]
        assertThat(fingerprint.id).isEqualTo("5aa858befc56dee1effe7eae1c0f88bb")
        assertThat(fingerprint.filePath).isEqualTo("D:\\redmi3s-20201221-work\\Camera\\file_example_JPG_100kB.jpg")

        fingerprint = fingerprints[2]
        assertThat(fingerprint.id).isEqualTo("0c481e87f2774b1bd41a0a70d9b70d11")
        assertThat(fingerprint.filePath).isEqualTo("D:\\redmi3s-20201221-work\\Camera\\file_example_MP3_700KB — копия (2).mp3")
    }

    @Test
    fun shouldParseOnLinuxServerOk() {
        val osDetector = Mockito.mock(OsDetector::class.java)
        `when`(osDetector.isWindows()).then { false }
        val md5SumLinuxFingerprintParser = Md5SumLinuxFingerprintParser(osDetector)

        val fingerprints = md5SumLinuxFingerprintParser.parse(
                "test",
                this.javaClass.getResource("/duplicate-2020-12-30.txt").readBytes()
        )

        assertThat(fingerprints).hasSize(3)

        var fingerprint = fingerprints[0]
        assertThat(fingerprint.id).isEqualTo("d41d8cd98f00b204e9800998ecf8427e")
        assertThat(fingerprint.filePath).isEqualTo("/d/redmi3s-20201221-work/Camera/duplicate-2020-12-30.txt")

        fingerprint = fingerprints[1]
        assertThat(fingerprint.id).isEqualTo("5aa858befc56dee1effe7eae1c0f88bb")
        assertThat(fingerprint.filePath).isEqualTo("/d/redmi3s-20201221-work/Camera/file_example_JPG_100kB.jpg")

        fingerprint = fingerprints[2]
        assertThat(fingerprint.id).isEqualTo("0c481e87f2774b1bd41a0a70d9b70d11")
        assertThat(fingerprint.filePath).isEqualTo("/d/redmi3s-20201221-work/Camera/file_example_MP3_700KB — копия (2).mp3")
    }
}