package com.hathway.ramadankareem2026.ui.tips.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.core.localization.LocalizationManager
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.tips.data.model.TipCategory
import com.hathway.ramadankareem2026.ui.tips.presentation.components.SimpleTipCard
import com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.TipsViewModel
import com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.TipsViewModelFactory
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen

@Composable
fun TipsScreen(
    onBack: () -> Unit,
    onTipClick: (Int) -> Unit,
    viewModel: TipsViewModel = viewModel(factory = TipsViewModelFactory())
) {
    val context = LocalContext.current
    val localizationManager = LocalizationManager(context)
    val currentLanguage = localizationManager.getCurrentLanguage()
    
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.ramadan_tip),
                showBack = true,
                onBackClick = onBack,
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Simple category chips
            item {
                CategoryChipsRow(
                    categories = uiState.categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        viewModel.selectCategory(category)
                    }
                )
            }
            
            // Tips list
            when {
                uiState.isLoading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = RamadanGold)
                        }
                    }
                }
                uiState.error != null -> {
                    item {
                        Text(
                            text = uiState.error!!,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                uiState.filteredTips.isEmpty() -> {
                    item {
                        Text(
                            text = "No tips found",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                else -> {
                    items(uiState.filteredTips) { tip ->
                        SimpleTipCard(
                            tip = tip,
                            onClick = { onTipClick(tip.id) },
                            language = currentLanguage
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryChipsRow(
    categories: List<TipCategory>,
    selectedCategory: TipCategory?,
    onCategorySelected: (TipCategory?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // "All" chip
        item {
            SimpleCategoryChip(
                text = "All",
                isSelected = selectedCategory == null,
                onClick = { onCategorySelected(null) }
            )
        }
        
        // Category chips
        items(categories) { category ->
            SimpleCategoryChip(
                text = category.displayName.getName("en"),
                isSelected = selectedCategory == category,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
private fun SimpleCategoryChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        color = if (isSelected) RamadanGreen else MaterialTheme.colorScheme.surfaceVariant,
        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}
