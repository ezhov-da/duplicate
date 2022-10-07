package ru.ezhov.duplicate.infrastructure

import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.analyse.AnalyseDuplicate
import ru.ezhov.duplicate.domain.analyse.AnalysePart
import ru.ezhov.duplicate.domain.analyse.DuplicateAnalyserService
import ru.ezhov.duplicate.domain.analyse.Fingerprint

@Service
class SimpleDuplicateAnalyserService : DuplicateAnalyserService {
    override fun duplicates(fingerprints: List<Fingerprint>): List<AnalyseDuplicate> =
            fingerprints
                    .groupBy { it.hash }
                    .filter { e -> e.value.size > 1 }
                    .map { e ->
                        val parts = e
                                .value
                                .mapIndexed { index, f ->
                                    AnalysePart(
                                            id = "${f.hash}-$index",
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