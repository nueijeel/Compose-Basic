package com.example.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// 목록 기본값이었던 getWellnessTasks() 결과값이 Screen 수준(WellnessScreen)으로 끌어올려지기 때문에
// 새 람다 함수 매개변수인 onCloseTask 추가(삭제될 WellnessTask 수신)
@Composable
fun WellnessTasksList(
    list: List<WellnessTask>,
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        // 기본적으로 각 항목의 상태는 list에 있는 항목의 위치를 기준으로 키가 지정됨.
        // mutableList에서는 데이터 세트가 변경될 때 위치가 변경되는 항목은 기억된 상태를 잃는 문제가 발생
        // 각 WellnessTaskItem의 id를 각 항목의 키로 사용해 문제 해결
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { checked -> onCheckedTask(task, checked) },
                onClose = { onCloseTask(task) }
            )
        }
    }
}
// LazyColumn에 있는 항목의 경우 스크롤하면서 항목을 지나치면 항목이 컴포지션을
// 완전히 종료하므로 더 이상 항목이 표시되지 않는다.
// 따라서 checkedState가 변경되면 WellnessTaskItem의 해당 인스턴스만 재구성되며
// LazyColumn의 모든 WellnessTaskItem 인스턴스가 재구성되는 것은 아니므로
// 스크롤하며 재구성된 항목을 지나치면 항목이 컴포지션을 완전히 종료하므로 더 이상 항목이 표시되지 않는다.
// -> rememberSaveable 사용해 저장된 인스턴스 상태 메커니즘으로 activity, process 재생성 후에도 상태 유지.
