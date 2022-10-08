package ru.ezhov.duplicate.domain.analyse

import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.duplicate.UploadService
import ru.ezhov.duplicate.domain.duplicate.model.Duplicate
import ru.ezhov.duplicate.domain.duplicate.model.Part
import ru.ezhov.duplicate.domain.duplicate.model.Upload

@Service
class DuplicateUploadService(
    private val fingerprintParser: FingerprintParser,
    private val uploadService: UploadService,
    private val duplicateAnalyserService: DuplicateAnalyserService,
) {
    fun upload(username: String, name: String, data: ByteArray) {
        val fingerprints = fingerprintParser.parse(name, data)
        val analyseDuplicates = duplicateAnalyserService.duplicates(fingerprints)
        val uploadId = Upload.generateId()
        val duplicates = analyseDuplicates
            .map { a ->
                Duplicate(
                    id = a.id,
                    uploadId = uploadId,
                    parts = a.parts.map { ap ->
                        Part.create(
                            id = ap.id,
                            duplicateId = a.id,
                            name = ap.name,
                            file = ap.file,
                        )
                    }
                )
            }

        uploadService.save(
            Upload.create(id = uploadId, name = name, duplicates = duplicates, username = username)
        )
    }
}
