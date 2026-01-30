/*
 * Copyright 2020 Google LLC
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

package com.google.samples.apps.sunflower.plantdetail

import android.content.res.Configuration
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.theme.SunflowerTheme
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

@Composable
fun PlantDetailDescription(planDetailViewModel: PlantDetailViewModel) {
    val plant by planDetailViewModel.plant.observeAsState()

    plant?.let {
        PlantDetailContent(it)
    }
//    Surface {
//        Text("Hello Compose")
//    }
}

@Composable
fun PlantDetailContent(plant: Plant) {
    Surface {
        Column(Modifier.padding(dimensionResource(R.dimen.margin_normal))) {
            PlantName(plant.name)
            PlantWatering(plant.wateringInterval)
            PlantDescription(plant.description)
        }
    }
}

@Preview
@Composable
private fun PlantDetailContentPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    SunflowerTheme {
        PlantDetailContent(plant)
    }
}

@Composable
private fun PlantName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
            .fillMaxWidth() // xml의 layout width 속성 match_parent
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            // marginStart, marginEnd를 한번에 horizontal로 가로 패딩 값 설정.
            // Compose는 dimensionResource(id)와 stringResource(id) 메서드를 제공해 dimens.xml및 strings.xml 파일 값을 가져올 수 있도록 함
            .wrapContentWidth(Alignment.CenterHorizontally) // xml의 gravity가 center_horizontal인 것과 유사. 텍스트가 가로로 화면 가운데 표시되도록 함
    )
}

@Preview
@Composable
private fun PlantNamePrivew() {
    SunflowerTheme {
        PlantName("Apple")
    }
}

// Compose 1.2.1에서 pluralStringResource를 사용하기 위해 옵션 삽입. 향후 컴포즈 버전에서는 불필요
@OptIn(ExperimentalComposeApi::class)
@Composable
private fun PlantWatering(wateringInterval: Int) {
    Column(Modifier.fillMaxWidth()) {
        // 가로 패딩과 alignment modifier는 두개의 text composable에서 공유하므로 로컬 변수로 할당해 재사용
        val centerWithPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)

        val normalPadding = dimensionResource(R.dimen.margin_normal)

        Text(
            text = stringResource(R.string.watering_needs_prefix),
            color = MaterialTheme.colorScheme.primaryContainer,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier.padding(top = normalPadding),
        )

        val wateringIntervalText = pluralStringResource(
            R.plurals.watering_needs_suffix, wateringInterval, wateringInterval
        )
        Text(
            text = wateringIntervalText,
            modifier = centerWithPaddingModifier.padding(bottom = normalPadding)
        )
    }
}

@Preview
@Composable
private fun PlantWateringPreview() {
    SunflowerTheme {
        PlantWatering(7)
    }
}

// Compose는 현재 Spanned 클래스를 지원하지 않으며 HTML 형식 텍스트도 표시하지 않음.
// HTML 코드 표시하려면 AndroidView의 TextView 사용해야 함
@Composable
private fun PlantDescription(description: String) {
    // htmlDescription은 매개변수로 전달된 description의 HTML 설명을 기억.
    // description 매개변수가 변경되면 remember 내 htmlDescription 코드가 다시 실행됨
    // 따라서 htmlDescription이 변경되면 AndroidView 업데이트 콜백이 재구성됨
    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    AndroidView(
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = {
            it.text = htmlDescription
        }
    )
}

@Preview
@Composable
private fun PlantDescriptionPreview() {
    SunflowerTheme {
        PlantDescription("HTML<br><br>description")
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlantDetailContentDarkPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    SunflowerTheme {
        PlantDetailContent(plant)
    }
}
