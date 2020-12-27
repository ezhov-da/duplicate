package ru.ezhov.duplicate.view

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import ru.ezhov.duplicate.domain.*

@Service
class DuplicateViewService(
        @Autowired private val uploadService: UploadService,
        @Autowired private val duplicateService: DuplicateService,
        @Autowired private val duplicateSelectedService: DuplicateSelectedService,
        @Autowired private val partService: PartService,
) {
    private val commonPartService: CommonPartService = CommonPartService()

    fun duplicates(model: Model, sizeOnPage: Int = 10, uploadId: String, page: Int): String {
        uploadService
                .by(uploadId)
                ?.let {
                    val selected = duplicateSelectedService.selected(uploadId)
                    var duplicates =
                            duplicateService
                                    .by(uploadId)
                                    .map { duplicateView(uploadId, it, selected) }

                    val pagination = PaginationService(page, 10, duplicates.size)
                            .calculate { page -> page?.let { "/uploads/$uploadId/duplicates?page=$it" } }

                    val maxItem = sizeOnPage * page
                    duplicates = if (maxItem <= duplicates.size) {
                        duplicates.subList(maxItem - sizeOnPage, maxItem)
                    } else {
                        duplicates.subList(maxItem - sizeOnPage, duplicates.size)
                    }

                    model.addAttribute(
                            "page",
                            DuplicatePage(
                                    pagination = pagination,
                                    selectedPage = "/uploads/$uploadId/duplicates/selected",
                                    duplicates = DuplicatesView(
                                            uploadId = uploadId,
                                            duplicates = duplicates
                                    )
                            )
                    )
                }

        return "duplicates"
    }

    private fun duplicateView(uploadId: String, duplicate: Duplicate, selectedParts: List<Part>): DuplicateView {
        val parts =
                duplicate
                        .partIds
                        .mapNotNull { partId -> partService.by(partId) }

        val commonPart = commonPartService.commonPart(parts.map { it.file.absolutePath })

        val partsView = parts.map { p ->
            PartView(
                    id = p.id,
                    path = commonPart?.let { cp -> p.file.absolutePath.substring(cp.length) } ?: p.file.absolutePath,
                    name = p.name,
                    link = "/data?id=${p.id}",
                    selected = selectedParts.find { s -> s.id == p.id } !== null,
                    selectLink = "/uploads/$uploadId/duplicates/part/${p.id}/select",
            )
        }

        return DuplicateView(
                id = duplicate.id,
                commonNamePart = commonPart,
                parts = partsView
        )
    }
}