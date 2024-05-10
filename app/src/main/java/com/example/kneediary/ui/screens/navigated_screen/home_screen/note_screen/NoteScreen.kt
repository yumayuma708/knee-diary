package com.example.kneediary.ui.screens.navigated_screen.home_screen.note_screen

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kneediary.ui.components.KneeNoteListItem
import com.github.yumayuma708.apps.model.KneeNote

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteScreenViewModel,
    toEdit: (Long) -> Unit,
) {
    val items = viewModel.items.collectAsState(
        initial = emptyList()
    )
    NoteScreen(
        modifier = modifier,
        kneeNoteList = items.value,
        toEdit = toEdit,
    )
}

@Composable
private fun NoteScreen(
    modifier: Modifier = Modifier,
    kneeNoteList: List<KneeNote>,
    toEdit: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(count = kneeNoteList.size,
            key = { index -> kneeNoteList[index].id },
            itemContent = {
                Divider()
                KneeNoteListItem(
                    kneeNote = kneeNoteList[it],
                    onClick = {
                        Log.d("メッセージ", "onClickが呼び出されました")
                        toEdit(kneeNoteList[it].id)
                        Log.d("id", kneeNoteList[it].id.toString())
                    },
                )
            }
        )
    }
}

@Preview
@Composable
fun PreviewNoteScreen() {
    NoteScreen(
        kneeNoteList = emptyList(),
        toEdit = { _ -> },
    )
}