package com.hathway.ramadankareem2026.ui.allahnames.viewmodel

import org.junit.Test
import org.junit.Assert.*

class AllahNamesViewModelTest {

    @Test
    fun `should initialize with non-null names list`() {
        // Given
        val viewModel = AllahNamesViewModel()
        
        // When
        val names = viewModel.names
        
        // Then
        assertNotNull("Names list should not be null", names)
    }

    @Test
    fun `should return same names instance on multiple calls`() {
        // Given
        val viewModel = AllahNamesViewModel()
        
        // When
        val names1 = viewModel.names
        val names2 = viewModel.names
        
        // Then
        assertSame("Should return same instance", names1, names2)
    }

    @Test
    fun `should handle repository dependency injection`() {
        // Given
        val mockRepository = com.hathway.ramadankareem2026.ui.allahnames.data.repository.AllahNamesRepository()
        
        // When
        val viewModel = AllahNamesViewModel(mockRepository)
        
        // Then
        assertNotNull("ViewModel should initialize with custom repository", viewModel.names)
    }

    @Test
    fun `should handle default repository when none provided`() {
        // When
        val viewModel = AllahNamesViewModel()
        
        // Then
        assertNotNull("ViewModel should initialize with default repository", viewModel.names)
    }
}
