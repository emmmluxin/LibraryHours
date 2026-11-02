package com.anyuan.library.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anyuan.library.data.CardStyle

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    style: CardStyle = CardStyle.ELEVATED,
    highlighted: Boolean = false,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    val scheme = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(16.dp)

    val colors = when (style) {
        CardStyle.ELEVATED -> CardDefaults.cardColors(containerColor = scheme.surfaceContainerLow)
        CardStyle.OUTLINED -> CardDefaults.cardColors(containerColor = scheme.surface)
    }
    val elevation = when (style) {
        CardStyle.ELEVATED -> CardDefaults.cardElevation(defaultElevation = 2.dp)
        CardStyle.OUTLINED -> CardDefaults.cardElevation(defaultElevation = 0.dp)
    }
    val border = when {
        highlighted -> BorderStroke(1.5.dp, scheme.primary)
        style == CardStyle.OUTLINED -> BorderStroke(1.dp, scheme.outlineVariant)
        else -> null
    }

    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
        ) {
            Column(modifier = Modifier.padding(contentPadding), content = content)
        }
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
        ) {
            Column(modifier = Modifier.padding(contentPadding), content = content)
        }
    }
}
