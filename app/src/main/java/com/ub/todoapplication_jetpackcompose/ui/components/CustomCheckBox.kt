package com.ub.todoapplication_jetpackcompose.ui.components


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ub.todoapplication_jetpackcompose.R
import com.ub.todoapplication_jetpackcompose.utils.Utils

//@Preview(showSystemUi = true)
@Composable
fun CustomCheckBox(modifier: Modifier, checked: Boolean, onClick:(Boolean)-> Unit){
    var isChecked by remember {
        mutableStateOf(checked)
    }

    val alpha: Float by animateFloatAsState(if (isChecked) 1f else 0.5f, label = "alpha")

    val drawableResId = if(isChecked){
        R.drawable.checkbox_checked
    }else{
        R.drawable.checkbox_unchecked
    }

    Image(painter = painterResource(id = drawableResId), contentDescription = stringResource(id = R.string.checkbox_icon),
        contentScale = ContentScale.Crop,
        modifier = modifier.width(20.dp).height(20.dp).graphicsLayer(alpha = alpha).clickable {
            isChecked = !isChecked
            onClick(isChecked)
        })

}