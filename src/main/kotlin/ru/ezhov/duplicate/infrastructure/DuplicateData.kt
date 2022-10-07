package ru.ezhov.duplicate.infrastructure

import ru.ezhov.duplicate.domain.duplicate.model.Duplicate

class DuplicateData {
    var id: String = ""
    var uploadId: String = ""
    var partIds: List<String> = emptyList()

    companion object {
        fun from(duplicate: Duplicate) = DuplicateData().apply {
            id = duplicate.id
            uploadId = duplicate.uploadId
            partIds = duplicate.partIds
        }
    }

    fun convert() = Duplicate(
            id = this.id,
            uploadId = this.uploadId,
            partIds = this.partIds,
    )
}