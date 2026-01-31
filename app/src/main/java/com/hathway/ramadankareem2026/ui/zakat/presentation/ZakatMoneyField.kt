package com.hathway.ramadankareem2026.ui.zakat.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ZakatMoneyField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    currencySymbol: String = ""
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { 
            Text(
                text = if (currencySymbol.isNotEmpty()) "$label ($currencySymbol)" else label
            ) 
        },
        placeholder = if (currencySymbol.isNotEmpty()) {
            { Text("0.00 $currencySymbol") }
        } else null,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ZakatMoneyFieldPreview() {
    ZakatMoneyField(
        label = "Gold Value",
        value = "10000",
        onChange = {},
        keyboardType = KeyboardType.Number
    )
}

