package com.hathway.ramadankareem2026.ui.dua.data

class DuaRepository {


    fun getRamadanDuas() = DuaDataSource.duas.filter { it.categoryId == "ramadan" }

    fun getDuasByCategory(categoryId: String) =
        DuaDataSource.duas.filter { it.categoryId == categoryId }

    fun getDuaById(id: String) = DuaDataSource.duas.first { it.id == id }

}
