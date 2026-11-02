package com.anyuan.library.data

import android.content.Context

enum class CardStyle { ELEVATED, OUTLINED }

enum class AccentPalette(val label: String) {
    OCEAN("海蓝"),
    BLUSH("胭脂"),
}

class Settings(context: Context) {

    private val sp = context.applicationContext
        .getSharedPreferences("library_settings", Context.MODE_PRIVATE)

    var cardStyle: CardStyle
        get() = runCatching {
            CardStyle.valueOf(sp.getString(KEY_STYLE, null) ?: CardStyle.ELEVATED.name)
        }.getOrDefault(CardStyle.ELEVATED)
        set(value) = sp.edit().putString(KEY_STYLE, value.name).apply()

    var accentPalette: AccentPalette
        get() = runCatching {
            AccentPalette.valueOf(sp.getString(KEY_PALETTE, null) ?: AccentPalette.OCEAN.name)
        }.getOrDefault(AccentPalette.OCEAN)
        set(value) = sp.edit().putString(KEY_PALETTE, value.name).apply()

    private companion object {
        const val KEY_STYLE = "card_style"
        const val KEY_PALETTE = "accent_palette"
    }
}
