package com.ub.todoapplication_jetpackcompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ub.todoapplication_jetpackcompose.R

@Composable
fun TextWithDrawableLeft(
    text: String,
    click:()->Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            click()
        }
    ) {
        IconButton(onClick = { click() }) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back_button))
        }

        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
        )
    }
}
