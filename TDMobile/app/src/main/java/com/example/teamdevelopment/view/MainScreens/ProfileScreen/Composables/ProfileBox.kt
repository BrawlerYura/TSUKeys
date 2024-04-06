package com.example.teamdevelopment.view.MainScreens.ProfileScreen.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.teamdevelopment.ui.theme.MyTypography
import com.example.teamdevelopment.ui.theme.interFamily
import com.example.teamdevelopment.view.MainScreens.ProfileScreen.ProfileScreenContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileBox(
    state: ProfileScreenContract.State,
    onNavigationRequested: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            IconButton(
                onClick = {
                        onNavigationRequested()
                    },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .size(20.dp)
                    .align(Alignment.TopStart),
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

            GlideImage(
                model = R.drawable.avatar_pic,
                contentDescription = null,
                modifier = Modifier
                    .height(88.dp)
                    .width(88.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.role != RoleEnum.Admin) {
                Row() {
                    Text(
                        text = if(state.isActive) {
                            if (state.role == RoleEnum.Unconfirmed) "аккаунт не подтвержден, подойтите к деканату" else {
                                if (state.role == RoleEnum.Student) "подтвержденный студент" else if (state.role == RoleEnum.Teacher) "подтвержденный преподаватель" else "подтвержденный декан"
                            }
                        } else {
                            if(state.isDenied) {
                                "заявка на подтверждение аккаунта отклонена"
                            } else {
                                "аккаунт не подтвержден, ваша заявка еще не рассмотрена"
                            }
                        },
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            color =
                            if (state.role == RoleEnum.Unconfirmed || state.isDenied || !state.isActive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
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
                text = (state.name + " " + state.surname + " " + (state.patronymic ?: "")),
                style = MyTypography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ProfileBoxPreview() {
    ProfileBox(
        state = profileStatePreview,
        onNavigationRequested = { }
    )
}