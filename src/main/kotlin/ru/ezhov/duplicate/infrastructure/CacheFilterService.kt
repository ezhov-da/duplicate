package ru.ezhov.duplicate.infrastructure

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.duplicate.FilterService
import java.util.concurrent.ConcurrentHashMap

@Service
@Primary
class CacheFilterService(
    private val defaultFilterService: DefaultFilterService,
) : FilterService {
    private val fileTypes = ConcurrentHashMap<String, List<String>>()

    override fun availableFileTypes(uploadId: String): List<String> {
        if (!fileTypes.containsKey(uploadId)) {
            fileTypes[uploadId] = defaultFilterService.availableFileTypes(uploadId)
        }
        return fileTypes[uploadId] ?: emptyList()
    }
}
