package com.example.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

// Ui State, List를 ViewModel로 이전하고, 비즈니스 로직도 ViewModel로 추출
// ViewModel은 컴포지션의 일부가 아니기 때문에 메모리 누수가 발생할 수 있으므로 컴포저블에서 만든 state(remember 값)를 보유해서는 안됨.
class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
        _tasks.find { it.id == item.id }?.let { task ->
            task.checked = checked
        }
}

// fake data create method (실제 앱에서는 데이터 영역에 위치)
private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

