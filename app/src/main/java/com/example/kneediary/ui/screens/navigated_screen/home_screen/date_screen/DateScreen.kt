@file:Suppress("IMPLICIT_CAST_TO_ANY", "DEPRECATION")

package com.example.kneediary.ui.screens.navigated_screen.home_screen.date_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kneediary.ui.components.KneeRecordListItem
import com.github.yumayuma708.apps.model.KneeRecord

@Composable
fun DateScreen(
    modifier: Modifier = Modifier,
    viewModel: DateScreenViewModel,
    toEdit: (Long) -> Unit,
) {
    val items = viewModel.items.collectAsState(initial = emptyList())
    DateScreen(
        modifier = modifier,
        kneeRecordList = items.value,
        toEdit = toEdit,
    )
}

@Composable
private fun DateScreen(
    modifier: Modifier = Modifier,
    kneeRecordList: List<KneeRecord>,
    toEdit: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(count = kneeRecordList.size,
            key = { index -> kneeRecordList[index].id }, //一意のkeyを指定する
            itemContent = {
                KneeRecordListItem(
                    kneeRecord = kneeRecordList[it],
                    onClick = {
                        toEdit(kneeRecordList[it].id)
                    },
                )
            })
    }
}

@Preview
@Composable
fun PreviewDateScreen() {
    DateScreen(
        kneeRecordList = emptyList(),
        toEdit = { _ -> },
    )
}