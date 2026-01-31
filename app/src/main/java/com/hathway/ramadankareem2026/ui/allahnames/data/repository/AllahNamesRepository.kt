package com.hathway.ramadankareem2026.ui.allahnames.data.repository

import com.hathway.ramadankareem2026.ui.allahnames.data.source.AllahNamesLocalData
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName

class AllahNamesRepository {
    fun getAllahNames(): List<AllahName> = AllahNamesLocalData.getAll()
}
