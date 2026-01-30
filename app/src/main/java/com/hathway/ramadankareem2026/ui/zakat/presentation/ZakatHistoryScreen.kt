package com.hathway.ramadankareem2026.ui.zakat.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.data.local.database.entity.ZakatCalculationEntity
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZakatHistoryScreen(
    calculations: List<ZakatCalculationEntity>,
    onDeleteCalculation: (ZakatCalculationEntity) -> Unit,
    onDeleteAll: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "Zakat History (${calculations.size})",
                showBack = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Debug info
            Text(
                text = "Debug: Found ${calculations.size} calculations",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            // Header with delete all button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Previous Calculations",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = RamadanGold
                    )
                    Text(
                        text = "Total: ${calculations.size} items",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                if (calculations.isNotEmpty()) {
                    TextButton(
                        onClick = onDeleteAll,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color.Red
                        )
                    ) {
                        Text("Delete All")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (calculations.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No calculations yet",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Your zakat calculations will appear here",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                // Debug: Print calculation details
                println("DEBUG: Calculations list size: ${calculations.size}")
                calculations.forEachIndexed { index, calc ->
                    println("DEBUG: Calculation $index - ID: ${calc.id}, Date: ${calc.formattedDate}, Amount: ${calc.zakatPayable}")
                }
                
                // Calculations list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Take remaining space
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = calculations,
                        key = { it.id } // Add key to ensure proper recomposition
                    ) { calculation ->
                        println("DEBUG: Rendering calculation ID: ${calculation.id}")
                        ZakatCalculationItem(
                            calculation = calculation,
                            onDelete = { onDeleteCalculation(calculation) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ZakatCalculationItem(
    calculation: ZakatCalculationEntity,
    onDelete: () -> Unit
) {
    // Simple currency formatting function
    fun formatCurrency(amount: Double): String {
        return "${calculation.currencySymbol}${String.format("%.2f", amount)}"
    }
    
    println("DEBUG: ZakatCalculationItem called for ID: ${calculation.id}, Currency: ${calculation.currencyCode} (${calculation.currencySymbol})")
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with date, currency info and delete button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = calculation.formattedDate,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = RamadanGold
                    )
                    if (calculation.country.isNotEmpty()) {
                        Text(
                            text = "${calculation.currencySymbol} ${calculation.currencyName} • ${calculation.country}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Calculation details
            CalculationDetailRow(
                label = "Total Assets",
                value = formatCurrency(calculation.totalAssets)
            )
            
            CalculationDetailRow(
                label = "Total Liabilities",
                value = formatCurrency(calculation.totalLiabilities)
            )
            
            CalculationDetailRow(
                label = "Nisab Type",
                value = calculation.nisabType.replaceFirstChar { it.uppercase() }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Zakat payable - highlight
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = RamadanGold.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Zakat Payable",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RamadanGold
                    )
                    
                    Text(
                        text = formatCurrency(calculation.zakatPayable),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RamadanGold
                    )
                }
            }
        }
    }
}

@Composable
private fun CalculationDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
