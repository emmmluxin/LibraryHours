package com.anyuan.library.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.anyuan.library.data.Settings
import com.anyuan.library.ui.theme.LibraryTheme

@Composable
fun LibraryApp() {
    val context = LocalContext.current
    val settings = remember { Settings(context) }
    var tab by remember { mutableIntStateOf(0) }
    var cardStyle by remember { mutableStateOf(settings.cardStyle) }
    var palette by remember { mutableStateOf(settings.accentPalette) }

    LibraryTheme(palette = palette) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavItem(
                        selected = tab == 0,
                        label = "图书",
                        filled = Icons.Filled.LocalLibrary,
                        outlined = Icons.Outlined.LocalLibrary,
                    ) { tab = 0 }
                    NavItem(
                        selected = tab == 1,
                        label = "样式",
                        filled = Icons.Filled.Palette,
                        outlined = Icons.Outlined.Palette,
                    ) { tab = 1 }
                }
            },
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                when (tab) {
                    0 -> LibraryScreen(cardStyle = cardStyle)
                    1 -> StyleScreen(
                        currentStyle = cardStyle,
                        currentPalette = palette,
                        onStyleChange = {
                            cardStyle = it
                            settings.cardStyle = it
                        },
                        onPaletteChange = {
                            palette = it
                            settings.accentPalette = it
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.NavItem(
    selected: Boolean,
    label: String,
    filled: ImageVector,
    outlined: ImageVector,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(if (selected) filled else outlined, contentDescription = null) },
        label = { Text(label, maxLines = 1) },
    )
}
