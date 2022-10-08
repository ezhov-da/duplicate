package ru.ezhov.duplicate.domain.duplicate.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


@Entity
@Table(name = "PARTS_DELETED_INFO")
data class PartDeletedInfo(
    @Id
    @Column(name = "PART_ID")
    val id: String,

    @Max(50)
    @NotNull
    @NotBlank
    val username: String,

    @Column(name = "DELETED_DATE")
    val deletedDate: String,
)
