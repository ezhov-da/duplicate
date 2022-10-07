package ru.ezhov.duplicate.domain.duplicate.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Duplicate(
        @Id
        val id: String,
        val uploadId: String,
        val partIds: List<String>
)