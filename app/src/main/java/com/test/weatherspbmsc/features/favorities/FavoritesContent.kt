package com.test.weatherspbmsc.features.favorities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import com.test.weatherspbmsc.R
import kotlinx.coroutines.launch

@Preview
@Composable
fun FavoritesContentPreview() {
    Surface(color = MaterialTheme.colors.background) {
        FavoritesContent()
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FavoritesContent(
    viewModel: FavoritesCitiesViewModel = hiltViewModel()
) {

    val pagerState = rememberPagerState(pageCount = 3)

    Column(modifier = Modifier.fillMaxSize()) {
        WeatherIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            temperature = "some"
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "some",
            )
        }

        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState)
    }
}



@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val colorActiveTab = Color.Black
    val colorInactiveTab = Color.LightGray
    val colorBackGround = Color.White


    val list = listOf(
        "Home" ,
        "Shopping"
    )
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = colorBackGround,
        contentColor = colorActiveTab,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.Black
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    val color = if (pagerState.currentPage == index) colorActiveTab else colorInactiveTab
                    Text(text = list[index], color = color)
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> TabContentScreen(data = "Welcome to Home Screen")
            1 -> TabContentScreen(data = "Welcome to Shopping Screen")
            2 -> TabContentScreen(data = "Welcome to Settings Screen")
        }
    }
}

@Composable
fun TabContentScreen(data: String) {

    val testData = listOf("test0","test0","test0","test0","test0","test0",)
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = testData.size) { index ->
            MySimpleListItem(itemViewState = testData[index])
        }
    }
}

@Composable
fun MySimpleListItem(itemViewState: String) {
    Row(modifier = Modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {

        WeatherIndicator(
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp),
            temperature = "some",
            imageHeight = 60.dp
        )
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 16.dp),
                text = "date")
        }
    }
}

@Composable
fun WeatherIndicator(
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    imageHeight: Dp = 120.dp,
    textSize: Int = 0,
    temperature: String
) {
    Row (
        modifier = modifier,

        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {

        val image = painterResource(R.drawable.ic_launcher_background)
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.sizeIn(maxHeight = imageHeight)
        )

        Text(
            text = temperature,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}