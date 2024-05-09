package com.example.kneediary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.yumayuma708.apps.model.KneeNote
import java.util.Date


@Composable
fun KneeNoteListItem(
    kneeNote: KneeNote,
    onClick: () -> Unit,
) {
    val title = kneeNote.title
    val description = kneeNote.description.ifEmpty {
        "詳細なし"
    }

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

    val date = kneeNote.date
    val time = kneeNote.time
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        ListItem(
            headlineContent = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "$definedMonth $definedDay $definedDate",
                            style = MaterialTheme.typography.titleLarge
                        )
//                        Box(modifier = Modifier.padding(4.dp))
//                        Icon(
//                            imageVector = weatherIconId,
//                            contentDescription = "weather",
//                            modifier = Modifier.size(22.dp),
//                        )
                        Box(modifier = Modifier.padding(4.dp))
                        Text(
                            text = definedTime,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            },
//            leadingContent = {
//                Box(
//                    modifier = Modifier.fillMaxHeight(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        imageVector = painIcon,
//                        contentDescription = "Localized description",
//                        tint = painIconColor,
//                    )
//                }
//            },
//            supportingContent = {
//                Text(
//                    "$isRight：$note",
//                    style = MaterialTheme.typography.titleMedium,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//            },
            trailingContent = {
                Row(
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            onClick()
                        }
                    ) {
                        Icon(imageVector = Icons.Rounded.Edit, contentDescription = "編集")
                    }
                }
            }
        )
    }
}