package com.example.kneediary.ui.screens.start_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kneediary.ui.theme.KneeDiaryTheme

@Composable
fun StartScreen(modifier: Modifier = Modifier) {
    Greeting(name = "ゆうまさま", modifier = modifier)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello! $name", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KneeDiaryTheme {
        Greeting("ゆうまさま")
    }
}
