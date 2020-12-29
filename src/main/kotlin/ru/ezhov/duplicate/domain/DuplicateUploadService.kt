package ru.ezhov.duplicate.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class DuplicateUploadService(
        private val fingerprintParser: FingerprintParser,
        private val uploadService: UploadService,
        private val duplicateAnalyserService: DuplicateAnalyserService,
        private val duplicateRepository: DuplicateRepository,
        private val partRepository: PartRepository
) {
    fun upload(name: String, data: ByteArray) {
        val fingerprints = fingerprintParser.parse(name, data)
        val analyseDuplicates = duplicateAnalyserService.duplicates(fingerprints)
        val uploadId = Upload.generateId()
        val pairs = analyseDuplicates
                .map { a ->
                    Pair(
                            Duplicate(
                                    id = a.id,
                                    uploadId = uploadId,
                                    partIds = a.parts.map { p -> p.id }
                            ),
                            a.parts.map { ap ->
                                Part(
                                        id = ap.id,
                                        duplicateId = a.id,
                                        name = ap.name,
                                        file = ap.file
                                )
                            }
                    )

                }

        duplicateRepository.save(pairs.map { p -> p.first })
        partRepository.save(pairs.flatMap { p -> p.second })
        uploadService.save(listOf(
                Upload.create(uploadId, name)
        ))
    }
}