@file:Suppress("IMPLICIT_CAST_TO_ANY", "DEPRECATION")

package com.example.kneediary.ui.screens.navigated_screen.home_screen.date_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.AcUnit
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Help
import androidx.compose.material.icons.rounded.MoodBad
import androidx.compose.material.icons.rounded.SentimentDissatisfied
import androidx.compose.material.icons.rounded.SentimentNeutral
import androidx.compose.material.icons.rounded.SentimentSatisfied
import androidx.compose.material.icons.rounded.SentimentVeryDissatisfied
import androidx.compose.material.icons.rounded.Umbrella
import androidx.compose.material.icons.rounded.WbCloudy
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.yumayuma708.apps.model.KneeRecord
import java.util.Date

@Composable
fun DateScreen(
    modifier: Modifier = Modifier,
    viewModel: DateScreenViewModel,
) {
    val items = viewModel.items.collectAsState(initial = emptyList())
    DateScreen(
        modifier = modifier, kneeRecordList = items.value
    )
}

@Composable
private fun DateScreen(
    modifier: Modifier = Modifier, kneeRecordList: List<KneeRecord>
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(count = kneeRecordList.size,
            key = { index -> kneeRecordList[index].id }, //一意のkeyを指定する
            itemContent = {
                KneeRecordListItem(
                    kneeRecord = kneeRecordList[it]
                )
            })
    }
}

@Composable
fun KneeRecordListItem(kneeRecord: KneeRecord) {
    fun extractDateTime(datePlusTime: Long): List<String> {
        val datePlusTimeDate = Date(datePlusTime)
        val datePlusTimeString = datePlusTimeDate.toString()
        val parts = datePlusTimeString.split(" ")
        val date = parts[0]
        val month = parts[1]
        val day = parts[2]
        val time = parts[3].substringBeforeLast(':')
        return listOf(date, month, day, time)
    }

    val date = kneeRecord.date
    val time = kneeRecord.time
    val datePlusTime = (date + time)
    val dateInfo = extractDateTime(datePlusTime)
    val definedDate = when (dateInfo[0]) {
        "Mon" -> "(月)"
        "Tue" -> "(火)"
        "Wed" -> "(水)"
        "Thu" -> "(木)"
        "Fri" -> "(金)"
        "Sat" -> "(土)"
        "Sun" -> "(日)"
        else -> "不明"
    }
    val definedMonth = when (dateInfo[1]) {
        "Jan" -> "1月"
        "Feb" -> "2月"
        "Mar" -> "3月"
        "Apr" -> "4月"
        "May" -> "5月"
        "Jun" -> "6月"
        "Jul" -> "7月"
        "Aug" -> "8月"
        "Sep" -> "9月"
        "Oct" -> "10月"
        "Nov" -> "11月"
        "Dec" -> "12月"
        else -> "不明"
    }
    val definedTime = dateInfo[3]
    val definedDay = dateInfo[2] + "日"
    val weatherIconId: ImageVector = when (kneeRecord.weather) {
        "sunny" -> Icons.Rounded.WbSunny
        "cloudy" -> Icons.Rounded.WbCloudy
        "rainy" -> Icons.Rounded.Umbrella
        "snowy" -> Icons.Rounded.AcUnit
        else -> Icons.Rounded.Help
    }
    val painIcon = when (kneeRecord.painLevel) {
        1 -> Icons.Rounded.SentimentSatisfied
        2 -> Icons.Rounded.SentimentNeutral
        3 -> Icons.Rounded.SentimentDissatisfied
        4 -> Icons.Rounded.MoodBad
        5 -> Icons.Rounded.SentimentVeryDissatisfied
        else -> Icons.Rounded.Face
    }
    val isRight = when (kneeRecord.isRight) {
        true -> "右"
        false -> "左"
    }
    val note = kneeRecord.note.ifEmpty {
        "メモなし"
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        ListItem(
            headlineContent = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$definedMonth $definedDay $definedDate",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Box(modifier = Modifier.padding(4.dp))
                        Icon(
                            imageVector = weatherIconId,
                            contentDescription = "weather",
                            modifier = Modifier.size(22.dp),
                        )
                        Box(modifier = Modifier.padding(4.dp))
                        Text(
                            text = definedTime,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            },
            leadingContent = {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = painIcon,
                        contentDescription = "Localized description",
                    )
                }
            },
            supportingContent = {
                Text(
                    "$isRight：$note",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            trailingContent = {
                Row (
                    horizontalArrangement = Arrangement.End
                ){
                    IconButton(
                        onClick = {
                            //編集画面に遷移
                        }
                    ) {
                        Icon(imageVector =Icons.Rounded.Edit, contentDescription = "編集")
                    }

                    IconButton(
                        onClick = {
                            //削除の確認のポップアップを表示
                        }
                    ) {
                        Icon(imageVector =Icons.Rounded.Delete, contentDescription = "削除")
                    }
                }
            }
        )
    }
}
    @Preview
    @Composable
    fun PreviewDateScreen() {
        DateScreen(
            kneeRecordList = emptyList(),
        )
    }