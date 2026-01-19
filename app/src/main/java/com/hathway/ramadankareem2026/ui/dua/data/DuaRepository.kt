package com.hathway.ramadankareem2026.ui.dua.data

import com.hathway.ramadankareem2026.ui.dua.model.DuaCategory
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem

class DuaRepository {

    fun getCategories(): List<DuaCategory> =
        DuaDataSource.categories

    fun getRamadanDuas(): List<DuaItem> =
        DuaDataSource.duas.filter { it.categoryId == "ramadan" }

    fun getDuasByCategory(categoryId: String): List<DuaItem> =
        DuaDataSource.duas.filter { it.categoryId == categoryId }

    fun getDuaById(id: String): DuaItem =
        DuaDataSource.duas.first { it.id == id }

}
