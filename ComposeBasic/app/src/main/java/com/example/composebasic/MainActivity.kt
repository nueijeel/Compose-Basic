package com.example.composebasic

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebasic.ui.theme.ComposeBasicTheme

/** Compose 앱은 Composable 함수로 구성되어 있다.
 * @Composable 어노테이션이 표시된 일반 함수이고, 다른 Composable 함수를 호출할 수 있다.
 * 어노테이션은 지속적으로 UI를 업데이트하고 유지관리하기 위해 Compose에 알려주는 역할을 한다.
 *
 * Compose에서는 AndroidManifest.xml에 지정된 activity가 앱의 진입점이다.
 * setContent를 사용해 레이아웃 정의 -> 기존 뷰 시스템처럼 xml 파일을 사용하는 대신 이 함수에서 composable 함수를 호출함
 * setContent 내부의 앱 테마는 프로젝트 이름에 맞게 지정됨 -> Composable 함수의 스타일을 지정하는 방법
 *
 * Android studio의 미리보기를 사용하기 위해 Composable 함수에 @Review 어노테이션을 표시하고 빌드한다.
 * 동일한 파일에 미리보기를 여러개 만들고 이름을 지정할 수 있다.
 * ex) @Preview(showBackground = true, name = "Text preview")
 *
 * 배경 색상 지정 시 Text Composable을 Surface로 매핑해 배경 지정이 가능하다.
 *
 * 대부분의 Compose UI 요소는 modifier 매개변수를 선택적으로 허용한다.
 * modifier는 상위 요소 레이아웃 내에서 UI 요소가 배치, 표시되고 동작하는 방식을 UI 요소에 알려준다.
 * Greeting Composable에서는 기본 수정자가 있고, 이는 Text Composable에 전달된다.
 * -> modifier가 데코하는 요소 주변 공간을 나타내는 padding 지정
 * -> 체이닝을 통해 여러 수정자를 추가할 수 있음
 * ex) Modifier.fillMaxWidth().padding(24.dp)
 *
 * UI에 사용되는 구성요소가 많을수록 생성되는 중첩 레벨이 많아지면 함수가 거대해지고 가독성이 떨어짐 -> 재사용 가능한 작은 구성요소로 해결
 *
 * Compose 기본 레이아웃 표준 요소는 column, row, box 3가지이다.
 * 위 레이아웃 요소는 Composable 콘텐츠를 사용하는 Composable 함수이므로 내부에 항목을 배치할 수 있다.
 * 또 Composable 함수는 Kotlin의 다른 함수처럼 사용이 가능하다.
 * ex) for 루프를 사용해 column에 요소 추가
 *      fun MyApp(
 *          modifier: Modifier = Modifier,
 *          names: List<String> = listOf("World", "Compose")
 *      ) {
 *          Column(modifier) {
 *              for (name in names) {
 *                  Greeting(name = name)
 *              }
 *          }
 *      }
 *
 * Button은 material3 패키지에서 제공하는 Composable로, Composable을 마지막 인수로 사용한다.
 * 배치를 위해서 사용하는 weight는 요소를 유연하게 만들기 위해 가중치가 없는 다른 요소를 밀어내 요소의 사용 가능한 모든 공간을 채운다.
 *
 * Compose에서는 리컴포지션을 위해 새 데이터를 사용해 composable 함수를 다시 호출한다.
 * -> 전체 UI 트리를 재구성하는 작업은 컴퓨팅 성능 및 배터리 수명 사용 측면에서 고비용 소모
 * 리컴포지션은 입력이 변경될 때 composable 함수를 다시 호출하는 프로세스이다. compose는 새 입력을 기반으로 재구성할 때 변경되었을 수 있는 함수 또는 람다만 호출하고
 * 나머지(매개변수가 없는 함수나 람다 등)는 건너뜀으로써 효율적인 과정을 거친다.
 * * composable 함수는 자주 실행될 수 있고 순서와 관계없이 실행될 수 있으므로 코드가 실행되는 순서 또는 이 함수가 다시 구성되는 횟수에 의존해서는 안됨!
 * 따라서, composable 내부에 상태를 추가하려면 mutableStateOf 함수를 사용하면 된다.
 * -> State 및 MutableState는 어떤 값을 보유하고 그 값이 변경될 때 마다 UI 업데이트(리컴포지션)을 트리거하는 인터페이스이다.
 * 하지만 composable 내에 mutableStateOf를 할당하기만 하면 composable 재호출시 중복된 리컴포지션이 발생된다.
 * -> 여러 리컴포지션 간 상태 유지를 위해 remember 함수 사용하여 변경 가능한 상태를 기억해야함.
 *
 * - 상태 호이스팅
 * composable 함수에서 여러 함수가 읽고 수정하는 상태는 공통의 상위 항목에 위치 해야함
 * 이 프로세스를 상태 호이스팅 이라고함 (호이스팅 : 들어 올린다. 끌어올린다)
 * 상태를 호이스팅할 수 있게 만들면 상태가 중복되지 않고 버그가 발생하는 것을 방지할 수 있고, composable을 재사용하고 쉽게 테스트할 수 있다는 장점이 있음
 *
 * - 리스트 만들기
 * 스크롤이 가능한 열 LazyColumn, 행 LazyRow -> 화면에 보이는 항목만 렌더링하므로 항목이 많은 리스트 렌더링 시 성능 향상
 * -> LazyColumn은 RecyclerView 같이 하위 요소는 재활용하지 않는다. composable을 방출하는 것이 view를 인스턴스화 하는 방식보다 상대적으로
 *      비용이 적게 들기 때문에 LazyColumn은 스크롤 시 새 composable을 방출하고 계속 성능을 유지한다.
 *
 * - 상태 유지
 * remember 함수는 composable이 컴포지션에 유지되는 동안만 작동한다. -> 기기 회전 시 전체 활동이 재시작되어 상태 손실
 * => rememberSaveable을 사용하면 구성 변경(회전)이나 프로세스 중단에도 각 상태를 저장한다.
 * 리스트 항목의 상태에도 rememberSaveable을 적용하면 상태 유지가 가능하다.
 *
 * - 간단한 애니메이션
 * animateDpAsState composable을 사용해 애니메이션이 완료될 때 까지 애니메이션에 의해 객체의 value가 계속 업데이트 되는 상태 객체를 반환한다.
 * 애니메이션 맞춤 설정을 위한 animationSpec 속성 사용.
 *
 * **/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeBasicTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
) {
    // = 대신 by 키워드 사용 : 매번 .value를 사용할 필요가 없도록 해주는 위임
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
//    Column(modifier = modifier.padding(vertical = 4.dp)) {
//        for (name in names) {
//            Greeting(name = name)
//        }
//    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(100) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
//    var expanded by rememberSaveable { mutableStateOf(false) }
//
//    val extraPadding by animateDpAsState(
//        if (expanded) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
//
//    Surface(
//        color = MaterialTheme.colorScheme.primary,
//        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//    ) {
//        Row(modifier = Modifier.padding(24.dp)) {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
//            ) {
//                Text("Hello")
//                // copy 함수를 이용해 미리 정의된 스타일을 수정
//                Text(
//                    text = name,
//                    style = MaterialTheme.typography.headlineMedium.copy(
//                        fontWeight = FontWeight.ExtraBold
//                    )
//                )
//            }
//            ElevatedButton(
//                onClick = { expanded = !expanded }
//            ) {
//                Text(if (expanded) "Show less" else "Show more")
//            }
//        }
//    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                        "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(id = R.string.show_less)
                } else {
                    stringResource(id = R.string.show_more)
                }
            )
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Welcome to the Basics Codelab!")
        // onClick 발생 시 상태가 아닌 함수를 직접 전달받아 컴포저블의 재사용성을 높임
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text(text = "Continue")
        }
    }
}

@Preview()
@Composable
fun MyAppPreview() {
    ComposeBasicTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

// 다크모드 미리보기 설정
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    ComposeBasicTheme {
        Greetings()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ComposeBasicTheme {
        // onContinueClicked 를 빈 람다 표현식에 할당하는 것은 아무 작업도 하지 않음을 의미
        OnboardingScreen(onContinueClicked = {})
    }
}