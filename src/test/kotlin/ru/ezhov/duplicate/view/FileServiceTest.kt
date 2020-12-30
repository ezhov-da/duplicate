package ru.ezhov.duplicate.view

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@Disabled
internal class FileServiceTest {
    @Test
    fun mimeType() {
        val mimeTypeService = FileService()
        var mimeType = mimeTypeService.mimeType("src/test/resource/file_example_MP3_700KB.mp3")
        assertThat(mimeType).isEqualTo("audio/mpeg")

        mimeType = mimeTypeService.mimeType("src/test/resource/file_example_MP4_480_1_5MG.mp4")
        assertThat(mimeType).isEqualTo("video/mp4")

        mimeType = mimeTypeService.mimeType("src/test/resource/file_example_JPG_100kB.jpg")
        assertThat(mimeType).isEqualTo("image/jpeg")

        mimeType = mimeTypeService.mimeType("src/test/resource/text.txt")
        assertThat(mimeType).isEqualTo("text/plain")

        assertThrows<NullPointerException> {
            mimeType = mimeTypeService.mimeType("src/test/resource/bla-bla-bla")
        }
    }

    @Test
    fun fileType() {
        val mimeTypeService = FileService()
        var fileType = mimeTypeService.fileType("src/test/resource/file_example_MP3_700KB.mp3")
        assertThat(fileType).isEqualTo(FileType.AUDIO)

        fileType = mimeTypeService.fileType("src/test/resource/file_example_MP4_480_1_5MG.mp4")
        assertThat(fileType).isEqualTo(FileType.VIDEO)

        fileType = mimeTypeService.fileType("src/test/resource/file_example_JPG_100kB.jpg")
        assertThat(fileType).isEqualTo(FileType.IMAGE)

        fileType = mimeTypeService.fileType("src/test/resource/text.txt")
        assertThat(fileType).isEqualTo(FileType.OTHER)

        fileType = mimeTypeService.fileType("src/test/resource/bla-bla-bla")
        assertThat(fileType).isEqualTo(FileType.OTHER)
    }
}