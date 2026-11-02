package com.anyuan.library.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Rectangle
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Rectangle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anyuan.library.data.AccentPalette
import com.anyuan.library.data.CardStyle
import com.anyuan.library.ui.components.AppCard
import com.anyuan.library.ui.theme.swatchColor

@Composable
fun StyleScreen(
    currentStyle: CardStyle,
    currentPalette: AccentPalette,
    onStyleChange: (CardStyle) -> Unit,
    onPaletteChange: (AccentPalette) -> Unit,
) {
    val scheme = MaterialTheme.colorScheme

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item("header") {
            Column {
                Text(
                    text = "样式",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = scheme.onSurface,
                )
                Text(
                    text = "卡片形态 · 配色 · Material 3",
                    style = MaterialTheme.typography.bodyMedium,
                    color = scheme.onSurfaceVariant,
                )
            }
        }

        item("accent") {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    text = "配色",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = scheme.onSurface,
                )
                AppCard(style = currentStyle) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        AccentPalette.entries.forEach { palette ->
                            AccentSwatch(
                                palette = palette,
                                selected = palette == currentPalette,
                                onClick = { onPaletteChange(palette) },
                            )
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    PalettePreviewBar(currentPalette = currentPalette)
                }
            }
        }

        item("cards") {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    text = "卡片样式",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = scheme.onSurface,
                )
                StyleOptionCard(
                    title = "立体",
                    subtitle = "阴影层级表达重要性",
                    icon = Icons.Outlined.Layers,
                    selected = currentStyle == CardStyle.ELEVATED,
                    style = CardStyle.ELEVATED,
                    onClick = { onStyleChange(CardStyle.ELEVATED) },
                )
                StyleOptionCard(
                    title = "描边",
                    subtitle = "描边无阴影，最安静",
                    icon = Icons.Outlined.Rectangle,
                    selected = currentStyle == CardStyle.OUTLINED,
                    style = CardStyle.OUTLINED,
                    onClick = { onStyleChange(CardStyle.OUTLINED) },
                )
                Text(
                    text = "两种形态遵循 Material 3 规范。",
                    style = MaterialTheme.typography.labelSmall,
                    color = scheme.outline,
                )
            }
        }

        item("about") {
            Text(
                text = "图书馆 v1.0",
                style = MaterialTheme.typography.labelSmall,
                color = scheme.outline,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun AccentSwatch(
    palette: AccentPalette,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val color = swatchColor(palette)
    val scheme = MaterialTheme.colorScheme
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            onClick = onClick,
            shape = CircleShape,
            color = color,
            border = BorderStroke(2.dp, if (selected) scheme.onSurface else Color.Transparent),
            modifier = Modifier.size(44.dp),
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = if (selected) 1f else 0f),
                    modifier = Modifier.size(20.dp),
                )
            }
        }
        Spacer(Modifier.width(4.dp))
        Text(
            text = palette.label,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) scheme.onSurface else scheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun PalettePreviewBar(currentPalette: AccentPalette) {
    val scheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        AccentPalette.entries.forEach { palette ->
            val selected = palette == currentPalette
            Box(
                Modifier
                    .weight(1f)
                    .height(if (selected) 11.dp else 7.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(swatchColor(palette))
                    .border(
                        1.5.dp,
                        if (selected) scheme.onSurface.copy(alpha = 0.5f) else Color.Transparent,
                        RoundedCornerShape(4.dp),
                    ),
            )
        }
    }
}

@Composable
private fun StyleOptionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    selected: Boolean,
    style: CardStyle,
    onClick: () -> Unit,
) {
    val scheme = MaterialTheme.colorScheme
    AppCard(
        style = style,
        highlighted = selected,
        onClick = onClick,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selected) scheme.primary else scheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp),
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (selected) scheme.primary else scheme.onSurface,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = scheme.onSurfaceVariant,
                )
            }
            Surface(
                shape = CircleShape,
                color = if (selected) scheme.primary else Color.Transparent,
                modifier = Modifier.size(26.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = scheme.onPrimary.copy(alpha = if (selected) 1f else 0f),
                    modifier = Modifier.padding(5.dp),
                )
            }
        }
    }
}
