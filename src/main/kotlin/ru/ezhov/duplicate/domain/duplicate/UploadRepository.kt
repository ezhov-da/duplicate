package ru.ezhov.duplicate.domain.duplicate

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.ezhov.duplicate.domain.duplicate.model.Upload

@Repository
interface UploadRepository: JpaRepository<Upload, String>
