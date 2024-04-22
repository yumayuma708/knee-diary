package com.example.kneediary.ui.screens.navigated_screen.home_screen.date_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.yumayuma708.apps.model.KneeRecord

@Composable
fun DateScreen(
    modifier: Modifier = Modifier,
    viewModel: DateScreenViewModel,
) {
    val items = viewModel.items.collectAsState(initial = emptyList())
    DateScreen(
        modifier = modifier,
        kneeRecordList = items.value
    )
}

@Composable
private fun DateScreen(
    modifier: Modifier = Modifier,
    kneeRecordList: List<KneeRecord>
) {
    LazyColumn (
        modifier = modifier,
    ){
        items(
            count = kneeRecordList.size,
            key = { index -> kneeRecordList[index].id }, //一意のkeyを指定する
            itemContent = {
                KneeRecordListItem(
                    kneeRecord = kneeRecordList[it]
                )
            }
        )
    }
}

@Composable
fun KneeRecordListItem(kneeRecord: KneeRecord) {
    ListItem(headlineContent = {
        Text(kneeRecord.isRight.toString())
        HorizontalDivider()
    })
}

@Preview
@Composable
fun PreviewDateScreen() {
    DateScreen(
        kneeRecordList = emptyList(),
    )
}