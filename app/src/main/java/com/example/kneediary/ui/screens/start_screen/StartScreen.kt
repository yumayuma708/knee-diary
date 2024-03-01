package com.example.kneediary.ui.screens.start_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kneediary.data.repositories.AuthRepository
import com.example.kneediary.ui.theme.KneeDiaryTheme

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    ) {
    val authRepository = remember { AuthRepository() }
    val viewModel: StartScreenViewModel = viewModel(factory = StartScreenViewModelFactory(authRepository))
    Greeting(modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(modifier: Modifier = Modifier) {

    val viewModel: StartScreenViewModel = viewModel()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "ひざ日記にログイン",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "ログインすることで、毎日のひざの状態を保存できます。",
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(30.dp))
            var email by remember { mutableStateOf("") }
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("メールアドレス") },
                leadingIcon = {
                    Icon(Icons.Filled.Email, contentDescription = "メール")
                }
            )
            Spacer(modifier = Modifier.height(16.dp)) // フィールド間のスペース
            var password by remember { mutableStateOf("") }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("パスワード") },
                visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = {
                    Icon(Icons.Default.VpnKey , contentDescription = "パスワード")
                }
            )
            Spacer(modifier = Modifier.height(15.dp)) // TextFieldと次のコンテンツ間のスペース

            Button(
                onClick = {
                    viewModel.signInWithEmailAndPassword(email, password,
                        onSuccess = {
                            // ログイン成功時の処理
                        },
                        onError = {
                            // ログイン失敗時の処理、例えばエラーメッセージの表示
                        }
                    )
                },
            ) {
                Text("ログイン")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(text ="まだログインしていない場合",
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                    onClick = {
                        // ボタンのクリックイベントをここに記述
                    }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.PersonAdd,
                            contentDescription = "新規登録",
                            modifier = Modifier.padding(end = 8.dp) // アイコンとテキストの間隔
                        )
                        Text("新規登録")
                    }
            }

            Spacer(modifier = Modifier.height(30.dp))
            val annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append("このアプリの利用を開始することで、")
                }
                pushStringAnnotation(tag = "TERMS", annotation = "利用規約ページへ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                    append("利用規約")
                }
                pop()
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append("及び")
                }
                pushStringAnnotation(tag = "PRIVACY", annotation = "プライバシーポリシーページへ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                    append("プライバシーポリシー")
                }
                pop()
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append("に同意したものとみなします。")
                }
            }

            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                        .firstOrNull()?.let {
                            // ここで利用規約のリンク処理を行う
                        }
                    annotatedString.getStringAnnotations(tag = "PRIVACY", start = offset, end = offset)
                        .firstOrNull()?.let {
                            // ここでプライバシーポリシーのリンク処理を行う
                        }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { /* ボタンのクリック
イベントをここに記述 */
                },
            ) {
                Text("ホーム画面へ")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KneeDiaryTheme {
        Greeting()
    }
}
