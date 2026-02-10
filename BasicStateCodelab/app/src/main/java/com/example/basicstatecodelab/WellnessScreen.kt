package com.example.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

// viewModel()은 기존 ViewModel을 반환하거나 지정된 범위에서 새 ViewModel을 생성함
// ViewModel 인스턴스는 범위가 활성화되어 있는 동안 유지된다.
// 예를 들어 컴포저블이 activity에서 사용되는 경우 뷰모델 인스턴스는 activity가 완료되거나 프로세스가 종료될 때까지 동일한 인스턴스를 반환한다.

// ViewModel은 Navigiation graph의 activity나 fragment, destination에서 호출되는 루트 컴포저블에 가까운 screen 수준 컴포저블에서 사용하는 것이 좋다.
// ViewModel은 다른 컴포저블로 전달해선 안되고, 대신 필요한 데이터와 필수 로직을 실행하는 함수만 매개변수로 전달해야 한다.
@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter()

        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                wellnessViewModel.remove(task)
            },
        )
    }
}
