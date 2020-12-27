package ru.ezhov.duplicate.infrastructure

import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.*

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
                                    AnalysePart(
                                            id = "${f.id}-$index",
                                            name = f.file.name,
                                            file = f.file,
                                    )
                                }
                        AnalyseDuplicate(
                                id = e.key,
                                parts = parts
                        )
                    }
}