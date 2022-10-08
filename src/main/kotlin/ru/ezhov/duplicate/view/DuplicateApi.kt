package ru.ezhov.duplicate.view

import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.ezhov.duplicate.domain.analyse.DuplicateUploadService
import ru.ezhov.duplicate.domain.duplicate.PartService
import ru.ezhov.duplicate.domain.duplicate.RemovePartsScriptBuilder
import ru.ezhov.duplicate.domain.duplicate.UploadService
import java.security.Principal
import javax.servlet.http.HttpServletResponse

@RestController
class DuplicateApi(
    private val uploadService: UploadService,
    private val duplicateUploadService: DuplicateUploadService,
    private val partService: PartService,
    private val removePartsScriptBuilder: RemovePartsScriptBuilder
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
            .body(SelectResult(select = uploadService.select(uploadId = uploadId, partId = id)))
    }

    @PostMapping("/upload/file")
    fun upload(@RequestParam("file") file: MultipartFile, principal: Principal, response: HttpServletResponse) {
        if (file.size > 0) {
            duplicateUploadService.upload(
                username = principal.name,
                name = file.originalFilename ?: "name-empty",
                data = file.bytes
            )
        }

        response.sendRedirect("/")
    }

    @GetMapping("/uploads/{uploadId}/duplicates/selected/remove_script.sh")
    fun upload(@PathVariable uploadId: String): ResponseEntity<String> {
        val script = removePartsScriptBuilder.buildScript(
            uploadService.by(uploadId)
                ?.duplicates
                ?.map {
                    it.parts.mapNotNull { part ->
                        part.takeIf { p -> p.selected }?.let { part.file.absolutePath }
                    }
                }
                .orEmpty()
                .flatten()
        )
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"remove_script_${uploadId}.sh\"")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
            .body(script)
    }
}
