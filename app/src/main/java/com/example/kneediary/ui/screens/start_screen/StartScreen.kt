package com.example.kneediary.ui.screens.start_screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.compose.KneeDiaryTheme
import com.example.kneediary.MainActivity
import com.example.kneediary.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    onNavigateToHomeScreen: () -> Unit,
) {

    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                showNotification(context)
            }
        }

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
                    Icon(Icons.Default.VpnKey, contentDescription = "パスワード")
                }
            )
            Spacer(modifier = Modifier.height(30.dp)) // TextFieldと次のコンテンツ間のスペース

            Row {
                Button(
                    onClick = {
                        StartScreenViewModel().signInWithEmailAndPassword(email, password,
                            onSuccess = {
                                // ログイン成功時の処理
                            },
                            onError = { exception ->
                                // ログイン失敗時の処理、例えばエラーメッセージの表示
                            }
                        )
                    },
                ) {
                    Text("ログイン")
                }
                Spacer(modifier = Modifier.width(30.dp))
                Button(
                    onClick = { /* ボタンのクリック
イベントをここに記述 */
                    },
                ) {
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
                    annotatedString.getStringAnnotations(
                        tag = "TERMS",
                        start = offset,
                        end = offset
                    )
                        .firstOrNull()?.let {
                            // ここで利用規約のリンク処理を行う
                        }
                    annotatedString.getStringAnnotations(
                        tag = "PRIVACY",
                        start = offset,
                        end = offset
                    )
                        .firstOrNull()?.let {
                            // ここでプライバシーポリシーのリンク処理を行う
                        }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = (
                        onNavigateToHomeScreen
                        )
            ) {
                Text("ホーム画面へ")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            return@Button
                        }
                    }
                    showNotification(context)
                }
            ) {
                Text("通知を表示")
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun showNotification(context: android.content.Context) {
    val manager = NotificationManagerCompat.from(context)
    val channel = NotificationChannelCompat.Builder(
        "channel_id",
        NotificationManagerCompat.IMPORTANCE_HIGH
    )
        .setName("ひざの痛みを記録しましょう！")
        .build()
    manager.createNotificationChannel(channel)
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE)
    val notification = NotificationCompat.Builder(context, "channel_id")
        .setContentTitle("ひざの痛みを記録しましょう！")
        .setContentText("毎日のひざの状態を記録しましょう！")
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()
    manager.notify(1, notification)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KneeDiaryTheme {
        StartScreen(onNavigateToHomeScreen = {})
    }
}
