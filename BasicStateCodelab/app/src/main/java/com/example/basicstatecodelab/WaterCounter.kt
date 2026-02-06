package com.example.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

/**
 * Composition : Jetpack Compose가 Composable을 실행할 때 빌드한 UI에 관한 설명
 * 초기 Composition : Composable을 처음 실행해 Composition을 만드는 것
 * ReComposition : 데이터가 변경될 때 Composition을 업데이트 하기 위해 Composable을 재실행하는 것
 *
 * Compose는 state의 value 속성을 읽는 각 Composable을 추적하고 그 value가 변경되면 Recomposition을 트리거 한다.
 * 이때 mutableStateOf를 이용하면 전체 UI가 아닌, 상태가 변경된 Composable 함수만 Recomposition할 수 있게 된다.
 * mutableStateOf 함수를 사용하여 observable한 MutableState를 만들 수 있다. 이 함수는 초깃값을 State 객체에 래핑된 매개변수로 수신해 value 값을 observable한 상태로 만든다.
*/

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
//        val count: MutableState<Int> = mutableStateOf(0)
//        버튼 이벤트에 의해 recomposition 발생하면 count 변수가 다시 0으로 초기화되므로 recomposition간 원래 값을 유지할 방법이 필요!
        // 이를 위해 composable inline 함수인 remember를 사용 -> remember로 계산된 값은 초기 컴포지션 중에 컴포지션에 저장되고 저장된 값은 리포지션 간 유지됨

//        val count: MutableState<Int> = remember { mutableStateOf(0) }
//        또는 kotlin의 by 키워드 사용하여 count를 var로 정의하고, 위임된 속성을 통해 MutableState의 value 속성을 매번 명시적으로 참조하지 않고도 count를 간접적으로 읽고 변경 가능

        // count > 0 인 조건 내에서 showTask의 값이 유지되기 때문에
        // Button2에 의해서 count 값이 0으로 초기화되면 리컴포지션이 발생
        // 이때 count를 표시하는 Text와 WellnessTaskItem과 관련된 모든 코드가 호출되지 않고 컴포지션을 종료.
//        var count by remember { mutableStateOf(0) }
//        if (count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
//                WellnessTaskItem(
//                    onClose = { showTask = false },
//                    taskName = "Have you taken your 15 minute walk today?",
//                )
//            }
//            Text(
//                text = "You've had $count glasses."
//            )
//        }
//
//        Row(Modifier.padding(top = 8.dp)) {
//            Button(
//                onClick = { count++ },
//                enabled = count < 10 // 10 이전까지만 버튼 사용 가능
//            ) {
//                Text("Add one")
//            }
//            Button(
//                onClick = { count = 0 },
//                Modifier.padding(start = 8.dp)
//            ) {
//                Text("Clear water count")
//            }
//        }

        var count by remember { mutableStateOf(0) }
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

//@Composable
//fun StatefulCounter(modifier: Modifier = Modifier) {
//    var waterCount by rememberSaveable { mutableStateOf(0) }
//    var juiceCount by rememberSaveable { mutableStateOf(0) }
//    StatelessCounter(waterCount, { waterCount++ }, modifier)
//    StatelessCounter(juiceCount, { juiceCount++ }, modifier)
//}
// 사용자 클릭 이벤트가 발생해 juiceCount가 증가하면 StatefulCounter가 재구성되고
// juiceCount를 읽는 StatelessCounter로 재구성됨. 하지만 waterCount를 읽은 StatelessCounter는 재구성되지 않는다.

@Composable
fun StatefulCounter() {
    var count by remember { mutableStateOf(0) }

    StatelessCounter(count, { count++ })
//    AnotherStatelessMethod(count, { count *= 2 })
}
// 이 경우 개수가 StatelessCounter 또는 AnotherStatelessMethod에 의해 업데이트 되면 모든 항목이 재구성됨.