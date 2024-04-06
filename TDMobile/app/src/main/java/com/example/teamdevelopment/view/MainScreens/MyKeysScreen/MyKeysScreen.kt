package com.example.teamdevelopment.view.MainScreens.MyKeysScreen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdevelopment.R
import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.domain.entities.enums.KeyStatusEnum
import com.example.teamdevelopment.ui.theme.MyTypography
import com.example.teamdevelopment.ui.theme.TeamDevelopmentTheme
import com.example.teamdevelopment.ui.theme.interFamily
import com.example.teamdevelopment.view.MainScreens.MyKeysScreen.composables.MyKeyCard
import com.example.teamdevelopment.view.MainScreens.MyKeysScreen.composables.MyKeysWithEmptyListBox
import com.example.teamdevelopment.view.MainScreens.MyKeysScreen.composables.MyRequestCard
import com.example.teamdevelopment.view.MainScreens.MyKeysScreen.composables.MyRequestsWithEmptyListBox
import com.example.teamdevelopment.view.authScreens.LoginScreen.Composables.LoginHeader
import com.example.teamdevelopment.view.base.SIDE_EFFECTS_KEY
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

@Composable
fun MyKeysScreen(
    state: MyKeysScreenContract.State,
    onEventSent: (event: MyKeysScreenContract.Event) -> Unit,
    effectFlow: Flow<MyKeysScreenContract.Effect>?,
    onNavigationRequested: (navigationEffect: MyKeysScreenContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is MyKeysScreenContract.Effect.Navigation.Back -> onNavigationRequested(
                    effect
                )

                is MyKeysScreenContract.Effect.Navigation.ToMainScreen -> onNavigationRequested(
                    effect
                )
            }
        }?.collect()
    }

    TeamDevelopmentTheme {
//        val refreshState = rememberPullRefreshState(
//            refreshing = state.isRefreshing,
//            onRefresh = {
//                onEventSent(ProfileScreenContract.Event.RefreshScreen)
//            }
//        )
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .pullRefresh(refreshState)
        ) {

            when {
                state.isLoaded -> {
//                    MyKeysScreenInner(state, onEventSent)
                    HorizontalPager(state, onEventSent)
                }

                state.isError -> {
//                    NetworkErrorScreen {
//                        onEventSent(ProfileScreenContract.Event.RefreshScreen)
//                    }
                }

                else -> {

                }
            }

//            PullRefreshIndicator(
//                refreshing = state.isRefreshing,
//                state = refreshState,
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPager(
    state: MyKeysScreenContract.State,
    onEventSent: (event: MyKeysScreenContract.Event) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = {
        2
    })
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier
//                .padding(vertical = 20.dp)
                .fillMaxWidth()
//                .height(24.dp)
        ) {
            var isClickable by remember { mutableStateOf(true) }

            IconButton(
                onClick = {
                    if (isClickable) {
                        isClickable = false
                        onEventSent(MyKeysScreenContract.Event.Back)
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000L)
                            isClickable = true
                        }
                    }
                },
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterStart),
                content = {
                    Icon(
                        painter = painterResource(R.drawable.back_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .height(12.dp)
                            .width(12.dp)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
            customHorizontalSwitcher(
                items = listOf("мои ключи", "мои запросы"),
                onTouchEvent = { index ->
                    CoroutineScope(Dispatchers.Main).launch {
                        pagerState.scrollToPage(index)
                    }
                },
                pagerState = pagerState
                )
        }

        HorizontalPager(
            pagerState
        ) { pageIndex ->
            Box(
                modifier = Modifier
                    .padding(top = 28.dp)
                    .fillMaxSize()
            ) {

                if (pageIndex == 0) {
                    MyKeysScreenInner(state, onEventSent)
                } else {
                    MyRequestsScreenInner(state, onEventSent)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun customHorizontalSwitcher(
    items: List<String>,
    onTouchEvent: (index: Int) -> Unit,
    pagerState: PagerState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(color = (MaterialTheme.colorScheme.surfaceVariant).copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, item ->
                Card(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        onTouchEvent(index)
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = if (pagerState.currentPage == index) {
                            MaterialTheme.colorScheme.onBackground
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer
                        },
                        contentColor = if (pagerState.currentPage == index)
                            Color(0xFF2D3843)
                        else
                            Color(0xFFE1E2E8)
                    ),
                    shape = RoundedCornerShape(7.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MyRequestsScreenInner(
    state: MyKeysScreenContract.State,
    onEventSent: (event: MyKeysScreenContract.Event) -> Unit,
) {
    val functionExecuted = remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        if (!functionExecuted.value) {
            onEventSent(MyKeysScreenContract.Event.LoadMyRequests)
            functionExecuted.value = true
        }
    }
    Box(Modifier.padding(top = 20.dp)) {
        if (state.myRequestsList.isEmpty()) {
            MyRequestsWithEmptyListBox(onSelectKeysEvent = { onEventSent(MyKeysScreenContract.Event.OnSelectKeyNavigationRequested) })
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(state.myRequestsList) { req ->
                    MyRequestCard(
                        req,
                        onReturnKeyEvent = {

                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ShowQRCodeDialog(qrContent: String, onClose: () -> Unit) {
    val bitmap = generateQRBitmap(qrContent)

    AlertDialog(
        onDismissRequest = onClose,
        text = {
            Box(
                modifier = Modifier
                    .size(270.dp)
            ) {
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onClose
                ) {
                    Text("Закрыть")
                }
            }
        }
    )
}

private fun generateQRBitmap(content: String): android.graphics.Bitmap? {
    return try {
        val barcodeEncoder = BarcodeEncoder()
        barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 400, 400)
    } catch (e: Exception) {
        null
    }
}

@Composable
fun MyKeysScreenInner(
    state: MyKeysScreenContract.State,
    onEventSent: (event: MyKeysScreenContract.Event) -> Unit
) {
    val functionExecuted = remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        if (!functionExecuted.value) {
            onEventSent(MyKeysScreenContract.Event.LoadMyKeysEvent)
            functionExecuted.value = true
        }
    }

    var showDialog by remember { mutableStateOf(false) }
    var qrContent by remember { mutableStateOf("Your QR Code Content") }

    if (showDialog) {
        ShowQRCodeDialog(qrContent = qrContent) {
            showDialog = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (state.myKeysList.isEmpty()) {
            MyKeysWithEmptyListBox(onSelectKeysEvent = { onEventSent(MyKeysScreenContract.Event.OnSelectKeyNavigationRequested) })
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(state.myKeysList) { key ->
                    MyKeyCard(
                        key,
                        onReturnKeyEvent = {
                            onEventSent(
                                MyKeysScreenContract.Event.ReturnKeyEvent(key.id)
                            )
                        },
                        onTransferKeyEvent = {
                            qrContent = getRandPassword(500)
                            Log.e("X", qrContent)
                            showDialog = true
                            onEventSent(MyKeysScreenContract.Event.CreateQR(key.id, qrContent))
                        }
                    )
                }
            }
        }
    }
}


fun getRandPassword(n: Int): String {
    val characterSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    val random = Random(System.nanoTime())
    val password = StringBuilder()

    for (i in 0 until n) {
        val rIndex = random.nextInt(characterSet.length)
        password.append(characterSet[rIndex])
    }

    return password.toString()
}

@Preview(showBackground = true)
@Composable
fun HorizontalPagerPreview() {
    HorizontalPager(
        state = MyKeysScreenContract.State(
            isLoaded = false,
            isError = false,
            myKeysList = listOf(
                KeyModel(
                    number = 224,
                    id = "",
                    building = 2,
                    currentUser = null,
                    currentUserId = null,
                    deanId = ""
                ),
                KeyModel(
                    number = 224,
                    id = "",
                    building = 2,
                    currentUser = null,
                    currentUserId = null,
                    deanId = ""
                ),
                KeyModel(
                    number = 224,
                    id = "",
                    building = 2,
                    currentUser = null,
                    currentUserId = null,
                    deanId = ""
                ),
                KeyModel(
                    number = 224,
                    id = "",
                    building = 2,
                    currentUser = null,
                    currentUserId = null,
                    deanId = ""
                ),
            ),
            myRequestsList = listOf()
        ),
        onEventSent = { },
    )
}