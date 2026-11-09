package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.theme.EmeraldDark
import com.hathway.ramadankareem2026.ui.theme.Gold

data class SponsoredAdItem(
    val title: String,
    val description: String,
    val ctaText: String? = null,
    val actionUrlOrRoute: String? = null,
    val badgeText: String = "Sponsored"
)

@Composable
fun HomeSponsoredAdCard(
    adItem: SponsoredAdItem?,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onAdClick: (String) -> Unit = {}
) {
    // Hide the entire ad component when no valid ad content is available, loading, error, or missing title/description
    if (adItem == null || adItem.title.isBlank() || adItem.description.isBlank() || isLoading || !errorMessage.isNullOrEmpty()) {
        return
    }

    val hasValidDestination = !adItem.actionUrlOrRoute.isNullOrEmpty()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Clear "Sponsored" label with subtle gold accent
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Gold.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = adItem.badgeText,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Gold,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = adItem.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = adItem.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (hasValidDestination && !adItem.ctaText.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { adItem.actionUrlOrRoute?.let { onAdClick(it) } },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldDark,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.semantics { role = Role.Button }
                ) {
                    Text(
                        text = adItem.ctaText,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
