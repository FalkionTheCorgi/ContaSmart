package com.example.accountspayable.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    primaryVariant = BgFloatingActionButtonDark,
    secondary = TextColorDark,
    secondaryVariant = SecondaryContainerDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    error = BackGroundButtonDismissDark,
    onPrimary = OnPrimaryDark,
    onSecondary = TextColorButtonDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceVariantDark,
    onError = OnErrorDark
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = PrimaryLight,
    primaryVariant = BgFloatingActionButtonLight,
    secondary = TextColorLight,
    secondaryVariant = SecondaryContainerLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    error = BackGroundButtonDismissLight,
    onPrimary = OnPrimaryLight,
    onSecondary = TextColorButtonLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceVariantLight,
    onError = OnErrorLight
)

@Composable
fun AccountsPayableTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}