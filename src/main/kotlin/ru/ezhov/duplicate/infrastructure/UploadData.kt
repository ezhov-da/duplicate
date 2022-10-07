package ru.ezhov.duplicate.infrastructure

import ru.ezhov.duplicate.domain.duplicate.model.Upload
import java.time.LocalDateTime

class UploadData {
    var id: String = ""
    var name: String = ""
    var date: LocalDateTime = LocalDateTime.now()

    companion object {
        fun from(upload: Upload): UploadData {
            val uploadData = UploadData()
            uploadData.id = upload.id
            uploadData.name = upload.name
            uploadData.date = upload.date
            return uploadData
        }
    }

    fun convert() = Upload.from(
            id = this.id,
            name = this.name,
            date = this.date
    )
}