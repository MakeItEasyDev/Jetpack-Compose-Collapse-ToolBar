package com.jetpack.collapsetoolbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.jetpack.collapsetoolbar.ui.theme.CollapseToolbarTheme
import com.jetpack.collapsetoolbar.ui.theme.GradientOne
import com.jetpack.collapsetoolbar.ui.theme.GradientTwo
import com.jetpack.collapsetoolbar.ui.theme.Shapes
import com.jetpack.collapsetoolbar.utils.AppBarCollapsedHeight
import com.jetpack.collapsetoolbar.utils.AppBarExpendedHeight
import kotlin.math.max
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollapseToolbarTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CollapseToolBar()
                }
            }
        }
    }
}

@Composable
fun CollapseToolBar() {
    val scrollState = rememberLazyListState()

    Box {
        AccountInfo(scrollState)
        ProfileToolBar(scrollState)
    }
}

@Composable
fun ProfileToolBar(scrollState: LazyListState) {
    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight

    val maxOffset = with(LocalDensity.current) {
        imageHeight.roundToPx()
    } - LocalWindowInsets.current.systemBars.layoutInsets.top

    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)

    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = Color.White,
        modifier = Modifier
            .height(
                AppBarExpendedHeight
            )
            .offset {
                IntOffset(x = 0, y = -offset)
            },
        elevation = if (offset == maxOffset) 4.dp else 0.dp
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .graphicsLayer {
                        alpha = 1f - offsetProgress
                    }
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                GradientOne, GradientTwo
                            )
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.makeiteasy),
                            contentDescription = "Profile",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "@Make it Easy",
                            fontSize = 25.sp,
                            color = Color.White,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "makeiteasy@abc.com",
                            fontSize = 15.sp,
                            color = Color.White,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppBarCollapsedHeight),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Contact Info",
                    fontSize = 25.sp,
                    color = Color.Black,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = (16 + 28 * offsetProgress).dp)
                        .scale(1f - 0.25f * offsetProgress)
                )
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(AppBarCollapsedHeight)
            .padding(horizontal = 16.dp)
    ) {
        Button(
            onClick = {  },
            contentPadding = PaddingValues(),
            shape = Shapes.small,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Gray),
            elevation = ButtonDefaults.elevation(),
            modifier = Modifier
                .width(38.dp)
                .height(38.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back Arrow",
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp),
                tint = Color.Black
            )
        }
    }
}

@Composable
fun AccountInfo(scrollState: LazyListState) {
    LazyColumn(contentPadding = PaddingValues(top = AppBarExpendedHeight), state = scrollState) {
        item {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                for (i in 0..24) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                            .height(75.dp),
                        elevation = 5.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.profile_default),
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    text = "Developer",
                                    color = Color.Black,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "+91-1234567890",
                                    color = Color.Black,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}


























