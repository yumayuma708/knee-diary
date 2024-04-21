package com.example.kneediary.ui.screens.navigated_screen.home_screen.date_screen

import androidx.lifecycle.ViewModel
import com.repository.KneeRecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
//@Injectアノテーションをつけ、constructorを指定し、@HiltViewModelアノテーションをつけることで、ViewModelをDIする。
class DateScreenViewModel @Inject constructor(
    //KneeRecordを取ってくる必要があるので、プロパティとしてKneeRecordRepositoryを取ってくる処理を書く。
    private val repository: KneeRecordRepository
) : ViewModel(){
    //リポジトリのgetAll()を呼んで、KneeRecordのリストをFlowで受け取る。
    val items = repository.getAll()

}