package com.hathway.ramadankareem2026.ui.zakat.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZakatMoneyField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    currencySymbol: String = "",
    imeAction: ImeAction = ImeAction.Next
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    OutlinedTextField(
        value = value, onValueChange = { input ->
            // Optional: allow only numbers + decimal
            if (input.matches(Regex("^\\d*(\\.\\d{0,2})?$"))) {
                onChange(input)
            }
        }, label = {
            Text(
                text = if (currencySymbol.isNotEmpty()) "$label ($currencySymbol)"
                else label
            )
        }, placeholder = {
            if (currencySymbol.isNotEmpty()) {
                Text(stringResource(R.string.zero_amount_with_currency, currencySymbol))
            }
        }, singleLine = true, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal, imeAction = imeAction
        ),

        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    scope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            })
}


@Preview(showBackground = true)
@Composable
fun ZakatMoneyFieldPreview() {
    ZakatMoneyField(
        label = stringResource(R.string.gold_value),
        value = "10000",
        onChange = {},
        )
}

