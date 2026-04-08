package com.example.compose.rally

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    // Compose는 테스트에 사용할 compose ui content를 선택하여 격리 테스트가 가능하다.
    // ComposeTestRule의 setContent 메서드로 실행되며 어디에서나 호출할 수 있다(단 한번만).
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts,
            )
        }

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() { // 현재 탭의 라벨이 대문자로 표시되는지 확인
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

        composeTestRule
//            .onNodeWithText(RallyScreen.Accounts.name.uppercase())
//            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                hasParent(
                    hasContentDescription(RallyScreen.Accounts.name)
                ),
                useUnmergedTree = true
                // 매우 격리된 테스트이므로 일치자에 상위요소(hasParent)를 추가하지 않아도 됨.
                // 하지만 text의 다른 인스턴스 발견할 때 더 큰 테스트에서 실패할 수 있는 광범위한 파인더(hasText)를 단독으로 사용하지 않는 것이 좋음.
            )
            .assertExists()
    }
}