/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelab.basiclayouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basiclayouts.ui.theme.MySootheTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            MySootheApp(windowSizeClass)
        }
    }
}

// Step: Search bar - Modifiers
// SearchBar 컴포저블은 modifier 매개변수를 받아서 TextField에 전달
// -> 이 방식은 컴포즈 가이드라인에 따른 권장사항으로
// -> 이 방식을 사용하면 메서드의 호출자가 컴포저블의 디자인을 수정할 수 있어 유연성과 재사용성이 높아짐.
// modifier 역할 : 컴포저블 크기, 레이아웃 동작, 모양 변경, 접근성 라벨과 같은 정보 추가, 사용자 입력 처리, 요소 클릭, 스크롤, 드래그, 확대 축소 가능하게 하는 상호작용 추가
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        // heightIn : 최소 높이 지정(글꼴 크기 확대 시 크기 늘어남)
        // fillMaxWidth : 상위 요소의 전체 가로 공간 차지
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

// Step: Align your body - Alignment
// 이미지와 텍스트 요소를 세로로 배치하기 위해 Column 사용
@Composable
private fun AlignYourBodyElement(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Image 컴포저블
        // painter: painterResource()로 이미지 리소스 지정
        // contentDescription: 콘텐츠 설명
        // modifier.clip: 컴포저블의 모양 지정(원형 - CircleShape, 사각형 - RectangleShape)
        // contentScale: 이미지 크기 조정(Fit, FillBounds, Crop)
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        // Text 컴포저블
        // 상위 컨테이너 내부에서 컴포저블 정렬하려면 상위 컨테이너 alignment 설정 : 해당 텍스트 컴포저블 정렬 시 상위 컨테이너인 Column 컴포저블의 alignment 설정
        // + 하위 요소에서는 align 수정자를 추가하여 단일 요소 정렬 재정의 가능.
        // Column 정렬 : Start, CenterHorizontally, End
        // Row 정렬 : Top, CenterVertically, Bottom
        // Box 정렬(가로 및 세로 정렬 결합하여 사용) : TopStart, TopCenter, TopEnd, CenterStart, Center, CenterEnd, BottomStart, BottomCenter, BottomEnd
        Text(
            text = stringResource(text),
            // 텍스트 기준선 따라 패딩 적용
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            // 타이포그래피 적용
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// Step: Favorite collection card - Material Surface
// Surface는 다른 컴포저블들의 컨테이너로 다양한 컴포저블들을 배치할 수 있다.
// shape 속성 지정해 모서리 둥글게
@Composable
fun FavoriteCollectionCard(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(255.dp)
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

// Step: Align your body row - Arrangements
// LazyRow 하위에 컴포저블이 아닌 목록 항목을 나타내는 items 메서드 사용해 표시할 목록 지정
// 컨테이너 기본축(Row는 가로축, Column은 세로축)위에 하위 컴포저블을 배치할 방식을 지정
// LazyRow 배치 목록 : Equal Weight, Space Between, Space Around, Space Evenly, End, Center, Start
// LazyColumn 배치 목록 : Equal Weight, Space Between, Space Around, Space Evenly, Top, Center, Bottom
// 이외에도 Arrangement.spaceBy() 사용해 각 하위 컴포저블 사이에 고정 공간 추가 가능
// LazyRow 양 측면에 패딩 추가 시 modifier에 padding 적용하면 스크롤 시 해당 패딩 공간 만큼 항목이 잘리는 현상
// -> 동일한 패딩 유지하되, 상위 목록 경계 내에서 콘텐츠 자르지 않고 스크롤 하려면 LazyRow의 contentPadding을 설정!
@Composable
fun AlignYourBodyRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(alignYourBodyData) { item ->
            AlignYourBodyElement(item.drawable, item.text)
        }
    }
}

// Step: Favorite collections grid - LazyGrid
// 그리드 항목 구성 시 LazyRow와 각 항목이 두개의 Element 갖도록 하는 Column으로 구성 가능
// -> 항목-그리드 요소 매핑에 더 효율적인 LazyHorizontalGrid 사용
// LazyHorizontalGrid
// contentPadding: 전체 컨텐츠를 둘러싼 패딩 설정
// horizontalArrangement: 요소 간 가로 정렬 간격 설정
// verticalArrangement: 요소 간 세로 정렬 간격 설정
@Composable
fun FavoriteCollectionsGrid(
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.height(168.dp)
    ) {
        items(favoriteCollectionsData) { items ->
            FavoriteCollectionCard(items.drawable, items.text, Modifier.height(80.dp))
        }
    }
}

// Step: Home section - Slot APIs
// 각 섹션별로 달라지는 동적 컨텐츠 구성을 위해 슬롯 API 사용
// 슬롯 API : Compose가 Composable 위에 맞춤설정 레이어를 배치하기 위해 도입한 패턴
// 해당 방식을 통해 하위 Composable의 모든 구성 매개변수를 노출하지 않고 자체적으로 하위 요소를 구성할 수 있어 유연성이 확장됨
// -> content 매개변수로 후행 람다 사용해 콘텐츠 슬롯 채움
@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            modifier =Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

// Step: Home screen - Scrolling
// Spacer 사용 시 Column 내 요소 간 간격 설정 가능 -> Spacer 대신 Column 자체 패딩 적용 시 FavoriteCollectionsGrid에서와 같이 컨텐츠 잘림 현상
// * 목록에 포함된 요소가 많거나 로드해야 할 데이터 셋이 많아서 모든 항목을 동시에 내보내면 성능이 저하되는 경우 Lazy 레이아웃 사용
//   이외에는 간단한 Column이나 Row 사용하고 스크롤 동작 수동으로 추가
//   -> verticalScroll or horizontalScroll 수정자 사용, 스크롤 상태 저장을 위해 scrollState 사용
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(16.dp))
        SearchBar(Modifier.padding(horizontal = 16.dp))
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow()
        }
        HomeSection(title = R.string.favorite_collections) {
            FavoriteCollectionsGrid()
        }
        Spacer(Modifier.height(16.dp))
    }
}

// Step: Bottom navigation - Material
// Compose Material library의 NavigationBar Composable 사용 시 Bottom Navigation Bar 구현 가능
// Navigation Bar 내에서 하나 이상의 NavigatioinBarItem을 추가하면 Material library에 의해 자동으로 스타일이 지정됨
@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    NavigationBar(
        // containerColor 지정하여 navigationbar의 배경 색상 설정
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Spa,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.bottom_navigation_home)
                )
            },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.bottom_navigation_profile)
                )
            },
            selected = false,
            onClick = {}
        )
    }
}

// Step: MySoothe App - Scaffold
// Scaffold Composable은 Material Design을 구현하는 앱을 위한 '구성 가능한 최상위 수준 composable'을 제공한다.
// Scaffold에는 다양한 material개념 슬롯이 포함되어 있는데 이 중 하나가 BottomBar로 이에 앞서 만든 Bottom Navigation을 배치할 수 있다.
@Composable
fun MySootheAppPortrait() {
    MySootheTheme {
        Scaffold(
            bottomBar = { SootheBottomNavigation() }
        ) { padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
}

// Step: Bottom navigation - Material
// 앱의 가로모드에서 메뉴를 표시할 때는 NavigationRail Composable을 사용한다.
// Navigation Bar와 마찬가지로 NavigationRailItem을 추가해 메뉴 구성
@Composable
private fun SootheNavigationRail(modifier: Modifier = Modifier) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationRailItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Spa,
                        contentDescription = null
                    )
                },
                label = {
                    Text(stringResource(R.string.bottom_navigation_home))
                },
                selected = true,
                onClick = {}
            )

            Spacer(modifier = Modifier.padding(8.dp))

            NavigationRailItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null
                    )
                },
                label = {
                    Text(stringResource(R.string.bottom_navigation_profile))
                },
                selected = true,
                onClick = {}
            )
        }
    }
}

// Step: Landscape Mode
// 세로 모드에서는 Scaffold 이용해 하단바와 화면을 구성하였다면,
// 가로 모드에서는 Row에 레일바와 화면을 나란히 배치한다.
// NavigationRail의 색상 설정 위해 Row를 Surface로 감싸준다.
@Composable
fun MySootheAppLandscape(){
    MySootheTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Row {
                SootheNavigationRail()
                HomeScreen()
            }
        }
    }
}

// Step: MySoothe App
// 미리보기를 통해 가로모드, 세로모드 화면이 잘 표시되지만 기기를 통해 앱을 실행시켜 회전하면 가로모드 버전이 표시되지 않는다.
// 언제 어떤 구성을 표시해야 할지 앱에 알려줘야 함 -> calculateWindowSizeClass() 함수 사용해 창 크기 확인
// 창 크기 클래스 너비는 소형, 중형, 확장형 3가지
// 앱이 세로 모드인 경우 소형 너비(WindowWidthSizeClass.Compact)이고,
// 앱이 가로 모드인 경우 확장형 너비(WindowWidthSizeClass.Expanded)이다.
@Composable
fun MySootheApp(windowSize: WindowSizeClass) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            MySootheAppPortrait()
        }
        WindowWidthSizeClass.Expanded -> {
            MySootheAppLandscape()
        }
    }
}

private val alignYourBodyData = listOf(
    R.drawable.ab1_inversions to R.string.ab1_inversions,
    R.drawable.ab2_quick_yoga to R.string.ab2_quick_yoga,
    R.drawable.ab3_stretching to R.string.ab3_stretching,
    R.drawable.ab4_tabata to R.string.ab4_tabata,
    R.drawable.ab5_hiit to R.string.ab5_hiit,
    R.drawable.ab6_pre_natal_yoga to R.string.ab6_pre_natal_yoga
).map { DrawableStringPair(it.first, it.second) }

private val favoriteCollectionsData = listOf(
    R.drawable.fc1_short_mantras to R.string.fc1_short_mantras,
    R.drawable.fc2_nature_meditations to R.string.fc2_nature_meditations,
    R.drawable.fc3_stress_and_anxiety to R.string.fc3_stress_and_anxiety,
    R.drawable.fc4_self_massage to R.string.fc4_self_massage,
    R.drawable.fc5_overwhelmed to R.string.fc5_overwhelmed,
    R.drawable.fc6_nightly_wind_down to R.string.fc6_nightly_wind_down
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun SearchBarPreview() {
    MySootheTheme { SearchBar(Modifier.padding(8.dp)) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun AlignYourBodyElementPreview() {
    MySootheTheme {
        AlignYourBodyElement(
            text = R.string.ab1_inversions,
            drawable = R.drawable.ab1_inversions,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun FavoriteCollectionCardPreview() {
    MySootheTheme {
        FavoriteCollectionCard(
            text = R.string.fc2_nature_meditations,
            drawable = R.drawable.fc2_nature_meditations,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun FavoriteCollectionsGridPreview() {
    MySootheTheme { FavoriteCollectionsGrid() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun AlignYourBodyRowPreview() {
    MySootheTheme { AlignYourBodyRow() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun HomeSectionPreview() {
    MySootheTheme {
        HomeSection(R.string.align_your_body) {
            AlignYourBodyRow()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE, heightDp = 180)
@Composable
fun ScreenContentPreview() {
    MySootheTheme { HomeScreen() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun BottomNavigationPreview() {
    MySootheTheme { SootheBottomNavigation(Modifier.padding(top = 24.dp)) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun NavigationRailPreview() {
    MySootheTheme { SootheNavigationRail() }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MySoothePortraitPreview() {
    MySootheAppPortrait()
}

@Preview(widthDp = 640, heightDp = 360)
@Composable
fun MySootheLandscapePreview() {
    MySootheAppLandscape()
}
