package com.example.basicstatecodelab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// 생성자에서 기본값이 false인 initialChecked 변수를 수신하도록 하면
// mutableStateOf()로 checked 변수를 초기화하여 initialChecked를 기본값으로 사용할 수 있습니다.
class WellnessTask(
    val id: Int,
    val label: String,
    initialChecked: Boolean = false,
) {
    var checked by mutableStateOf(initialChecked)
}
