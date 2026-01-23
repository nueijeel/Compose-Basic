package com.example.reply.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Compose는 Shapes 클래스에 확장 매개변수를 제공해 도형을 구현.
// Typo Scale과 마찬가지로 Shape 배율을 사용하면 다양한 크기의 도형 사용 가능
// extraSmall, small, medium, large, extraLarge
val shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp),
)
