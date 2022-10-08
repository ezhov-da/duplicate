package ru.ezhov.duplicate.domain.duplicate.model

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "UPLOADS")
class Upload private constructor(
    @Id
    val id: String,

    @Max(1000)
    @NotNull
    @NotBlank
    val name: String,

    @Max(50)
    @NotNull
    @NotBlank
    val username: String,

    @Column(name = "CREATION_DATE")
    val creationDate: LocalDateTime,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "UPLOAD_ID")
    val duplicates: List<Duplicate>
) {

    companion object {
        fun generateId() = UUID.randomUUID().toString()

        fun create(
            id: String,
            name: String,
            duplicates: List<Duplicate>,
            username: String,
        ) = Upload(
            id = id,
            name = name,
            creationDate = LocalDateTime.now(),
            duplicates = duplicates,
            username = username,
        )
    }
}
