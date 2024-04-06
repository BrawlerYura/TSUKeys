package com.example.teamdevelopment.view.MainScreens.MainScreen.Composables

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.teamdevelopment.R
import com.example.teamdevelopment.domain.entities.enums.RoleEnum
import com.example.teamdevelopment.domain.entities.enums.StatusEnum
import com.example.teamdevelopment.ui.theme.interFamily
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

val ScanFunctionAmbient = staticCompositionLocalOf<(() -> Unit)?> { null }





@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyCard(
    myFullName: String, myRole: RoleEnum, isActive: Boolean, isDenied: Boolean, navigateToProfile: () -> Unit, navigateToMyKeys: () -> Unit
) {
    val scan = ScanFunctionAmbient.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(vertical = 15.dp, horizontal = 20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Row() {
                Text(
                    text = "Моя карточка",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(end = 15.dp)
                )
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateToProfile()
                }) {
                GlideImage(
                    model = R.drawable.avatar_pic,
                    contentDescription = null,
                    modifier = Modifier
                        .height(55.dp)
                        .width(55.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterStart),
                    contentScale = ContentScale.Crop,
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(start = 60.dp, end = 25.dp)
                ) {
                    if (myRole != RoleEnum.Admin) {
                        Row() {
                            Text(
                                text = if(isActive) {
                                    if (myRole == RoleEnum.Unconfirmed) "аккаунт не подтвержден" else {
                                        if (myRole == RoleEnum.Student) "подтвержденный студент" else if (myRole == RoleEnum.Teacher) "подтвержденный преподаватель" else "подтвержденный декан"
                                    }
                                } else {
                                       if(isDenied) {
                                           "заявка на подтверждение аккаунта отклонена"
                                       } else {
                                           "аккаунт не подтвержден"
                                       }
                                       },
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 10.sp,
                                    color =
                                    if (myRole == RoleEnum.Unconfirmed || isDenied || !isActive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(end = 3.dp)
                            )
                        }
                    } else {
                        Text(
                            text = "адмэн",
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            textAlign = TextAlign.Left,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                    Text(
                        text = myFullName,
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                IconButton(onClick = {
                    navigateToProfile()
                }, modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterEnd), content = {
                    Icon(
                        painter = painterResource(R.drawable.arrow_right),
                        contentDescription = null,
                        modifier = Modifier
                            .height(18.dp)
                            .width(10.dp)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                })
            }

            Spacer(modifier = Modifier.height(1.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color(0xFFFFFFFF).copy(alpha = 0.15f))
            )

            Spacer(modifier = Modifier.height(3.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            navigateToMyKeys()
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.keys_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .height(18.dp)
                            .width(18.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Мои ключи", style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ), textAlign = TextAlign.Left, color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            scan?.invoke()
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.qr_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .height(18.dp)
                            .width(18.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Взять ключ у товарища", style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ), textAlign = TextAlign.Left, color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyCardPreview() {
    MyCard(
        navigateToProfile = {},
        myFullName = "Иванов Иван Иванович",
        myRole = RoleEnum.Student,
        navigateToMyKeys = {},
        isActive = true,
        isDenied = false
    )
}
