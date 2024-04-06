package com.example.teamdevelopment.view.authScreens.IntroducingScreen.Composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teamdevelopment.R
import com.example.teamdevelopment.ui.theme.MyTypography

@Composable
fun ImageWithDescriptionBox() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MyTypography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Image(
            painter = painterResource(id = R.drawable.hand_with_key),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .padding(top = 40.dp, bottom = 40.dp)
        )

        Text(
            text = stringResource(R.string.app_description),
            style = MyTypography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 8.dp, bottom = 35.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageWithDescriptionPreview() {
    ImageWithDescriptionBox()
}