package ru.ezhov.duplicate.view

import org.springframework.stereotype.Service
import org.springframework.ui.Model
import ru.ezhov.duplicate.domain.*
import ru.ezhov.duplicate.view.filter.FilterParams
import ru.ezhov.duplicate.view.filter.FilterView

@Service
class DuplicateViewService(
        private val uploadService: UploadService,
        private val duplicateService: DuplicateService,
        private val duplicateSelectedService: DuplicateSelectedService,
        private val partService: PartService,
        private val fileService: FileService,
        private val filterService: FilterService,
) {
    private val commonPartService: CommonPartService = CommonPartService()

    fun duplicates(
            uploadId: String,
            page: Int,
            fileType: String?,
            countOnPage: Int,
            username: String,
            model: Model,
    ): String {
        uploadService
                .by(uploadId)
                ?.let {
                    val selected = duplicateSelectedService.selected(uploadId)
                    var duplicatesView =
                            duplicateService
                                    .by(uploadId)
                                    .mapNotNull { duplicateView(uploadId, it, selected, fileType) }

                    val pagination = PaginationService(page, countOnPage, duplicatesView.size)
                            .calculate { page ->
                                page?.let {
                                    "/uploads/$uploadId/duplicates${FilterParams.create().add("page", page).add("fileType", fileType).query()}"
                                }
                            }

                    val maxItem = countOnPage * page
                    duplicatesView = if (maxItem <= duplicatesView.size) {
                        duplicatesView.subList(maxItem - countOnPage, maxItem)
                    } else {
                        duplicatesView.subList(maxItem - countOnPage, duplicatesView.size)
                    }

                    val filtersView = mutableListOf(FilterView(name = "Все", link = "/uploads/$uploadId/duplicates"))
                    filtersView
                            .addAll(filterService
                                    .availableFileTypes(uploadId)
                                    .map {
                                        FilterView(name = it, link = "/uploads/$uploadId/duplicates?fileType=$it")
                                    }
                            )

                    model.addAttribute(
                            "page",
                            DuplicatePage(
                                    username = username,
                                    pagination = pagination,
                                    filters = filtersView,
                                    selectedPage = "/uploads/$uploadId/duplicates/selected",
                                    duplicates = DuplicatesView(
                                            uploadId = uploadId,
                                            duplicates = duplicatesView
                                    )
                            )
                    )
                }

        return "duplicates"
    }

    private fun duplicateView(
            uploadId: String,
            duplicate: Duplicate,
            selectedParts: List<Part>,
            fileType: String?
    ): DuplicateView? {
        val parts =
                duplicate
                        .partIds
                        .mapNotNull { partId ->
                            partService
                                    .by(partId)
                                    .takeIf { fileType == null || fileType == it?.fileType }
                        }
        return parts
                .takeIf { it.isNotEmpty() }
                ?.let {


                    val commonPart = commonPartService.commonPart(parts.map { it.file.absolutePath })

                    val partsView = parts.map { p ->
                        PartView(
                                id = p.id,
                                path = commonPart?.let { cp -> p.file.absolutePath.substring(cp.length) }
                                        ?: p.file.absolutePath,
                                name = p.name,
                                link = "/data?id=${p.id}",
                                selected = selectedParts.find { s -> s.id == p.id } !== null,
                                selectLink = "/uploads/$uploadId/duplicates/part/${p.id}/select",
                                fileType = fileService.fileType(p.file.absolutePath).name
                        )
                    }

                    DuplicateView(
                            id = duplicate.id,
                            commonNamePart = commonPart,
                            parts = partsView
                    )
                }
    }
}
