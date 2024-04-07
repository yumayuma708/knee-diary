package com.example.kneediary.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val KneeDiaryTypography = Typography(
    displayLarge = TextStyle(
        fontSize = 57.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.25).sp,
        lineHeight = (64.0).sp // 1.12 * fontSize
    ),
    displayMedium = TextStyle(
        fontSize = 45.sp,
        fontWeight = FontWeight.W400,
        lineHeight = (52.2).sp // 1.16 * fontSize
    ),
    displaySmall = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.W400,
        lineHeight = (43.92).sp // 1.22 * fontSize
    ),
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.W400,
        lineHeight = (40.0).sp // 1.25 * fontSize
    ),
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.W400,
        lineHeight = (36.12).sp // 1.29 * fontSize
    ),
    headlineSmall = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.W400,
        lineHeight = (31.92).sp // 1.33 * fontSize
    ),
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.W400,
        lineHeight = (27.94).sp // 1.27 * fontSize
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.15.sp,
        lineHeight = (24.0).sp // 1.5 * fontSize
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.1.sp,
        lineHeight = (20.02).sp // 1.43 * fontSize
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.1.sp,
        lineHeight = (20.02).sp // 1.43 * fontSize
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.5.sp,
        lineHeight = (15.96).sp // 1.33 * fontSize
    ),
    labelSmall = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.5.sp,
        lineHeight = (15.95).sp // 1.45 * fontSize
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.5.sp,
        lineHeight = (24.0).sp // 1.5 * fontSize
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.25.sp,
        lineHeight = (20.02).sp // 1.43 * fontSize
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.4.sp,
        lineHeight = (15.96).sp // 1.33 * fontSize
    )
)
