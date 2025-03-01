package com.ub.todoapplication_jetpackcompose.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ub.todoapplication_jetpackcompose.data.models.Priorities
import com.ub.todoapplication_jetpackcompose.ui.theme.ICON_SIZE
import com.ub.todoapplication_jetpackcompose.ui.theme.PRIORITY_FONT_SIZE
import com.ub.todoapplication_jetpackcompose.ui.theme.SMALL_PADDING

@Composable
fun PriorityItem(priorities: Priorities){
    Row (verticalAlignment = Alignment.CenterVertically
    , modifier = Modifier.padding(horizontal = SMALL_PADDING).height(IntrinsicSize.Max)){
        Canvas(modifier = Modifier.size(ICON_SIZE)) {
            drawCircle(color = priorities.color)
        }
        Text(text = priorities.name,
            Modifier.padding(start = SMALL_PADDING),
            fontSize = PRIORITY_FONT_SIZE,
            fontWeight = FontWeight.Medium,
            style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
        )

    }
}


@Composable
@Preview
fun PreviewPriorityItem(){
    PriorityItem(priorities = Priorities.LOW)
}

