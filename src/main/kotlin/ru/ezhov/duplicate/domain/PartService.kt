package ru.ezhov.duplicate.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PartService(
        private val partRepository: PartRepository
) {
    fun all() = partRepository.all()

    fun data(id: String): ByteArray {
        val part = partRepository.by(id)
        val file = part?.file
        return if (file?.exists() == true) {
            file.readBytes()
        } else {
            this.javaClass.getResource("/static/not-found.jpg").readBytes()
        }
    }

    fun by(partId: String): Part? = partRepository.by(partId)
}