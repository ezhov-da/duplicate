package ru.ezhov.duplicate.infrastructure

import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.AnalyseDuplicate
import ru.ezhov.duplicate.domain.AnalysePart
import ru.ezhov.duplicate.domain.DuplicateAnalyserService
import ru.ezhov.duplicate.domain.Fingerprint
import java.io.File

@Service
class SimpleDuplicateAnalyserService : DuplicateAnalyserService {
    override fun duplicates(fingerprints: List<Fingerprint>): List<AnalyseDuplicate> =
            fingerprints
                    .groupBy { it.id }
                    .filter { e -> e.value.size > 1 }
                    .map { e ->
                        val parts = e
                                .value
                                .mapIndexed { index, f ->
                                    val file = File(f.filePath)
                                    AnalysePart(
                                            id = "${f.id}-$index",
                                            name = file.name,
                                            file = file,
                                    )
                                }
                        AnalyseDuplicate(
                                id = e.key,
                                parts = parts
                        )
                    }
}