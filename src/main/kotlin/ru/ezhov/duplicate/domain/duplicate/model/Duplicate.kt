package ru.ezhov.duplicate.domain.duplicate.model

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "DUPLICATES")
data class Duplicate(
    @Id
    val id: String,

    @Column(name = "UPLOAD_ID")
    val uploadId: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "DUPLICATE_ID")
    val parts: List<Part>
)
