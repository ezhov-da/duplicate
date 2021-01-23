package ru.ezhov.duplicate.view

import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.ezhov.duplicate.domain.DuplicateUploadService
import ru.ezhov.duplicate.domain.PartSelectedRepository
import ru.ezhov.duplicate.domain.PartService
import javax.servlet.http.HttpServletResponse

@RestController
class DuplicateApi(
        private val partSelectedRepository: PartSelectedRepository,
        private val duplicateUploadService: DuplicateUploadService,
        private val partService: PartService
) {
    @GetMapping("/data")
    fun data(@RequestParam id: String): ResponseEntity<InputStreamResource> =
            partService.data(id).let {
                ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_TYPE, it.mimeType)
                        .header(HttpHeaders.CACHE_CONTROL, "max-age=31536000")
                        .body(InputStreamResource(it.data))
            }

    @PutMapping("/uploads/{uploadId}/duplicates/part/{id}/select")
    fun select(@PathVariable uploadId: String, @PathVariable id: String): ResponseEntity<SelectResult> {
        return ResponseEntity
                .ok()
                .body(SelectResult(select = partSelectedRepository.select(uploadId, id)))
    }

    @PostMapping("/upload/file")
    fun upload(@RequestParam("file") file: MultipartFile, response: HttpServletResponse) {
        if (file.size > 0) {
            duplicateUploadService.upload(file.originalFilename ?: "name-empty", file.bytes)
        }

        response.sendRedirect("/")
    }
}