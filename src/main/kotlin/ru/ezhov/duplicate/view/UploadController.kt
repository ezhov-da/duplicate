package ru.ezhov.duplicate.view

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import ru.ezhov.duplicate.configuration.domain.ConfigurationRepository
import ru.ezhov.duplicate.configuration.domain.PropertyName
import ru.ezhov.duplicate.domain.DuplicateSelectedService
import ru.ezhov.duplicate.domain.DuplicateService
import ru.ezhov.duplicate.domain.FilterService
import ru.ezhov.duplicate.domain.UploadService
import ru.ezhov.duplicate.view.filter.FilterParams
import ru.ezhov.duplicate.view.filter.FilterView
import java.security.Principal
import java.time.format.DateTimeFormatter

@Controller
class UploadController(
        private val uploadService: UploadService,
        private val duplicateService: DuplicateService,
        private val duplicateSelectedService: DuplicateSelectedService,
        private val duplicateViewService: DuplicateViewService,
        private val fileService: FileService,
        private val filterService: FilterService,
        private val configurationRepository: ConfigurationRepository,
) {
    @GetMapping("/")
    fun index(model: Model, principal: Principal): String {
        val uploads = uploadService
                .all()
                .sortedByDescending { it.date }
                .map {
                    val uploadId = it.id

                    val count = duplicateSelectedService.selected(uploadId).size

                    UploadView(
                            id = it.id,
                            name = it.name,
                            date = it.date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")),
                            duplicatesLink = "/uploads/${it.id}/duplicates",
                            selectedLink = count.takeIf { c -> c > 0 }?.let { "/uploads/$uploadId/duplicates/selected" },
                            selectedCount = count,
                            duplicatesCount = duplicateService.by(it.id).size
                    )
                }

        model.addAttribute(
                "page",
                IndexPage(
                        principal.name,
                        uploads = uploads
                )
        )
        return "index"
    }

    @GetMapping("/uploads/{uploadId}/duplicates")
    fun duplicates(
            @PathVariable uploadId: String,
            @RequestParam(defaultValue = "1") page: Int,
            @RequestParam(required = false) fileType: String?,
            model: Model,
            principal: Principal,
    ): String {
        return duplicateViewService.duplicates(
                uploadId = uploadId,
                page = page,
                fileType = fileType,
                countOnPage = configurationRepository.by(PropertyName.COUNT_ON_PAGE, "10").toInt(),
                model = model,
                username = principal.name,
        )
    }

    @GetMapping("/uploads/{uploadId}/duplicates/selected")
    fun selected(
            @PathVariable uploadId: String,
            @RequestParam(defaultValue = "1") page: Int,
            @RequestParam(required = false) fileType: String?,
            model: Model,
            principal: Principal
    ): String {
        val sizeOnPage = configurationRepository.by(PropertyName.COUNT_ON_PAGE, "10").toInt()

        uploadService
                .by(uploadId)
                ?.let {
                    var selected =
                            duplicateSelectedService
                                    .selected(uploadId)
                                    .mapNotNull { p ->
                                        p.takeIf { fileType == null || fileType == p.fileType }
                                                ?.let {
                                                    SelectedPartView(
                                                            id = it.id,
                                                            path = it.file.absolutePath,
                                                            name = it.name,
                                                            link = "/data?id=${it.id}",
                                                            fileType = fileService.fileType(it.file.absolutePath).name
                                                    )
                                                }
                                    }
                    val pagination = PaginationService(page, sizeOnPage, selected.size)
                            .calculate { page ->
                                page?.let { "/uploads/$uploadId/duplicates/selected${FilterParams.create().add("page", page).add("fileType", fileType).query()}" }
                            }

                    val filtersView = mutableListOf(FilterView(name = "Все", link = "/uploads/$uploadId/duplicates"))
                    filtersView
                            .addAll(filterService
                                    .availableFileTypes(uploadId)
                                    .map {
                                        FilterView(name = it, link = "/uploads/$uploadId/duplicates/selected?fileType=$it")
                                    }
                            )

                    val maxItem = sizeOnPage * page
                    selected = if (maxItem <= selected.size) {
                        selected.subList(maxItem - sizeOnPage, maxItem)
                    } else {
                        selected.subList(maxItem - sizeOnPage, selected.size)
                    }

                    model.addAttribute(
                            "page",
                            SelectedPage(
                                    username = principal.name,
                                    uploadId = uploadId,
                                    filters = filtersView,
                                    duplicatesLink = "/uploads/$uploadId/duplicates",
                                    parts = selected,
                                    pagination = pagination
                            )
                    )
                }

        return "selected"
    }
}