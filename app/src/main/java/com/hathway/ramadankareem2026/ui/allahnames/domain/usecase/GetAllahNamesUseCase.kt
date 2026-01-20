package com.hathway.ramadankareem2026.ui.allahnames.domain.usecase

import com.hathway.ramadankareem2026.ui.allahnames.data.repository.AllahNamesRepository
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName

class GetAllahNamesUseCase(
    private val repository: AllahNamesRepository
) {
    operator fun invoke(): List<AllahName> = repository.getAllahNames()
}
