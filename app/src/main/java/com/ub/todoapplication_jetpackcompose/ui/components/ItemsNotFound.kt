package com.ub.todoapplication_jetpackcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ub.todoapplication_jetpackcompose.R

@Composable
@Preview(showSystemUi = true)
fun NoItemsAvailable(){

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        val preloaderLottieComposition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                R.raw.no_item_found
            )
        )

        val preloaderProgress by animateLottieCompositionAsState(
            preloaderLottieComposition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true
        )


        LottieAnimation(
            composition = preloaderLottieComposition,
            progress = preloaderProgress,
            modifier = Modifier.height(250.dp).width(250.dp)
        )

        Spacer(modifier = Modifier.padding(top = 25.dp))
        Text(text = stringResource(R.string.no_items_found),
            style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }


}