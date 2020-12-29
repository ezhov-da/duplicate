package ru.ezhov.duplicate.view

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import ru.ezhov.duplicate.domain.DuplicateSelectedService
import ru.ezhov.duplicate.domain.DuplicateService
import ru.ezhov.duplicate.domain.UploadService
import java.security.Principal
import java.time.format.DateTimeFormatter

@Controller
class UploadController(
        private val uploadService: UploadService,
        private val duplicateService: DuplicateService,
        private val duplicateSelectedService: DuplicateSelectedService,

        private val duplicateViewService: DuplicateViewService

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
            model: Model,
            principal: Principal
    ): String {
        return duplicateViewService.duplicates(
                model = model,
                uploadId = uploadId,
                page = page,
                username = principal.name
        )
    }

    @GetMapping("/uploads/{uploadId}/duplicates/selected")
    fun selected(
            @PathVariable uploadId: String,
            @RequestParam(defaultValue = "1") page: Int,
            model: Model,
            principal: Principal
    ): String {
        val sizeOnPage = 10

        uploadService
                .by(uploadId)
                ?.let {
                    var selected =
                            duplicateSelectedService
                                    .selected(uploadId)
                                    .map {
                                        SelectedPartView(
                                                id = it.id,
                                                path = it.file.absolutePath,
                                                name = it.name,
                                                link = "/data?id=${it.id}",
                                        )
                                    }
                    val pagination = PaginationService(page, 10, selected.size)
                            .calculate { page -> page?.let { "/uploads/$uploadId/duplicates/selected?page=$it" } }

                    val maxItem = sizeOnPage * page
                    selected = if (maxItem <= selected.size) {
                        selected.subList(maxItem - sizeOnPage, maxItem)
                    } else {
                        selected.subList(maxItem - sizeOnPage, selected.size)
                    }

                    model.addAttribute(
                            "page",
                            SelectedPage(
                                    username= principal.name,
                                    uploadId = uploadId,
                                    duplicatesLink = "/uploads/$uploadId/duplicates",
                                    parts = selected,
                                    pagination = pagination
                            )
                    )
                }

        return "selected"
    }
}