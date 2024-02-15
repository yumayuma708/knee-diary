package com.example.kneediary.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import com.example.kneediary.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


private val DarkColorScheme = darkColorScheme(
    primary = Cyan80,
    secondary = CyanGrey80,
    tertiary = Cyan80,
    background = Color(0xFF1C1B1F),
)

private val LightColorScheme = lightColorScheme(
    primary = Cyan40,
    secondary = CyanGrey40,
    tertiary = Cyan40,
    background = Color(0xFFFFFBFE),
)

val KiwiMaru = FontFamily(Font(R.font.kiwi_maru_regular))

// Typographyの定義を追加
private val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = KiwiMaru,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = KiwiMaru,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    // 必要に応じて他のテキストスタイルも定義
)

@Composable
fun KneeDiaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography
    ) {
        content()
    }
}