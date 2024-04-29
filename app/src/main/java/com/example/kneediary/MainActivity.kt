package com.example.kneediary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.navigation.compose.rememberNavController
import com.example.compose.KneeDiaryTheme
import com.example.kneediary.navigation.KneeDiaryNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val provider = GoogleFont.Provider(
            providerAuthority = "com.google.android.gms.fonts",
            providerPackage = "com.google.android.gms",
            certificates = R.array.com_google_android_gms_fonts_certs
        )
        setContent {
            KneeDiaryTheme {
                val navController = rememberNavController()
                KneeDiaryNavHost(navController = navController)
            }
        }

    }
}
