package studio.eyesthetics.tetaanimationcomposelesson.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import studio.eyesthetics.tetaanimationcomposelesson.R
import studio.eyesthetics.tetaanimationcomposelesson.extensions.noRippleClickable
import studio.eyesthetics.tetaanimationcomposelesson.ui.cards.CardsScreen
import studio.eyesthetics.tetaanimationcomposelesson.ui.switch.SwitchScreen
import studio.eyesthetics.tetaanimationcomposelesson.ui.theme.ColorGrayAthens
import studio.eyesthetics.tetaanimationcomposelesson.ui.theme.ColorGrayChateau
import studio.eyesthetics.tetaanimationcomposelesson.ui.theme.ColorRedMonza

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val tabTitles = stringArrayResource(id = R.array.tab_titles)

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            },
            contentColor = ColorRedMonza,
            divider = {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(ColorGrayAthens))
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.h6,
                            textAlign = TextAlign.Center,
                            color = if (pagerState.currentPage == index) ColorRedMonza else ColorGrayChateau,
                            modifier = Modifier
                                .fillMaxWidth()
                                .noRippleClickable {
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {  }
                )
            }
        }

        HorizontalPager(
            count = MainCompanion.PAGES_COUNT,
            state = pagerState
        ) { page ->
            when(page) {
                0 -> CardsScreen()
                1 -> SwitchScreen()
            }
        }
    }
}