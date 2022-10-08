package ru.ezhov.duplicate.domain.duplicate.model

import java.io.File
import javax.persistence.AttributeConverter
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Converter
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


@Entity
@Table(name = "PARTS")
data class Part private constructor(
    @Id
    val id: String,

    @Column(name = "DUPLICATE_ID")
    val duplicateId: String,

    @Max(1000)
    @NotNull
    @NotBlank
    val name: String,

    @Max(1000)
    @NotNull
    @NotBlank
    val file: File,

    @NotNull
    @Column(name = "IS_SELECTED")
    var selected: Boolean = false,

    @NotNull
    @Column(name = "IS_DELETED")
    val deleted: Boolean = false,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "ID", referencedColumnName = "PART_ID")
    val partDeletedInfo: PartDeletedInfo? = null
) {
    fun fileType(): String = file.extension.lowercase().ifEmpty { "other" }

    companion object {
        fun create(
            id: String,
            duplicateId: String,
            name: String,
            file: File,
        ) = Part(
            id = id,
            duplicateId = duplicateId,
            name = name,
            file = file,
        )
    }
}

@Converter(autoApply = true)
class FileAttributeConverter : AttributeConverter<File, String> {
    override fun convertToDatabaseColumn(attribute: File): String = attribute.absolutePath

    override fun convertToEntityAttribute(dbData: String): File = File(dbData)
}
