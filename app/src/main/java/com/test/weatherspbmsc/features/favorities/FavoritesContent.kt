package com.test.weatherspbmsc.features.favorities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.*
import com.test.weatherspbmsc.R
import com.test.weatherspbmsc.data.storage.WeatherEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FavoritesLoading(
    viewModel: FavoritesCitiesViewModel = hiltViewModel(),
) {

    when (val state = viewModel.weather.collectAsState().value) {
        is FavoritesCitiesViewModel.Event.Error -> TODO()
        is FavoritesCitiesViewModel.Event.LoadedSuccess ->
            FavoritesSuccessLoaded(state.value)
        FavoritesCitiesViewModel.Event.Loading -> {

        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FavoritesSuccessLoaded(
    weatherMap: Map<String, List<WeatherEntity>>
) {
    val pageCount = weatherMap.map { it.key }.size
    val pagerState = rememberPagerState(pageCount = pageCount)


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

        Tabs(pagerState = pagerState, weatherMap = weatherMap)
        TabsContent(pagerState = pagerState, weatherMap = weatherMap)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(
    pagerState: PagerState,
    weatherMap: Map<String, List<WeatherEntity>>
) {
    val colorActiveTab = Color.Black
    val colorInactiveTab = Color.LightGray
    val colorBackGround = Color.White


    val tabList = weatherMap.map { it.key }

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
        tabList.forEachIndexed { index, _ ->
            Tab(
                text = {
                    val color = if (pagerState.currentPage == index) colorActiveTab else colorInactiveTab
                    Text(text = tabList[index], color = color)
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
fun TabsContent(
    pagerState: PagerState,
    weatherMap: Map<String, List<WeatherEntity>>

) {
    val keys = weatherMap.map { it.key }.toHashSet()
    val test = weatherMap.toList().map { it.second }.flatten()
    HorizontalPager(state = pagerState) { page ->

        val key = keys.toList()[page]

        TabContentScreen(entityList = test.filter { it.cityName == key })
    }
}

@Composable
fun TabContentScreen(entityList: List<WeatherEntity>) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = entityList.size) { index ->
            MySimpleListItem(entity = entityList[index])
        }
    }
}

@Composable
fun MySimpleListItem(entity: WeatherEntity) {
    Row(modifier = Modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {

        WeatherIndicator(
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp),
            temperature = "${entity.dayTemperature} \u2103",
            imageHeight = 60.dp,
            weatherIcon = entity.icon
        )
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            val formattedDate = SimpleDateFormat("dd MMMM").format(Date(entity.date * 1000L))
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 16.dp),
                text = formattedDate)
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
    weatherIcon: String = "",
    temperature: String
) {
    Row (
        modifier = modifier,

        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {

        val model = "https://openweathermap.org/img/w/${weatherIcon}.png"
        AsyncImage(
            model = model,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.sizeIn(maxHeight = imageHeight)
        )

        Text(
            text = temperature,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}