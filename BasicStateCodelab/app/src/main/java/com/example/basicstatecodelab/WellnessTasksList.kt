package com.example.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// fake data create method (실제 앱에서는 데이터 영역에 위치)
fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask> = remember { getWellnessTasks() }
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(list) { task ->
            WellnessTaskItem(taskName = task.label)
        }
    }
}
// LazyColumn에 있는 항목의 경우 스크롤하면서 항목을 지나치면 항목이 컴포지션을
// 완전히 종료하므로 더 이상 항목이 표시되지 않는다.
// 따라서 checkedState가 변경되면 WellnessTaskItem의 해당 인스턴스만 재구성되며
// LazyColumn의 모든 WellnessTaskItem 인스턴스가 재구성되는 것은 아니므로
// 스크롤하며 재구성된 항목을 지나치면 항목이 컴포지션을 완전히 종료하므로 더 이상 항목이 표시되지 않는다.
// -> rememberSaveable 사용해 저장된 인스턴스 상태 메커니즘으로 activity, process 재생성 후에도 상태 유지.
