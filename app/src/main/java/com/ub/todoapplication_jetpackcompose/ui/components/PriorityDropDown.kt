package com.ub.todoapplication_jetpackcompose.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.ub.todoapplication_jetpackcompose.R
import com.ub.todoapplication_jetpackcompose.data.models.Priorities
import com.ub.todoapplication_jetpackcompose.ui.theme.BORDER_WIDTH
import com.ub.todoapplication_jetpackcompose.ui.theme.ICON_SIZE
import com.ub.todoapplication_jetpackcompose.ui.theme.MEDIUM_PADDING
import com.ub.todoapplication_jetpackcompose.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import com.ub.todoapplication_jetpackcompose.ui.theme.PRIORITY_FONT_SIZE
import com.ub.todoapplication_jetpackcompose.ui.theme.SMALL_PADDING
import com.ub.todoapplication_jetpackcompose.ui.viewModels.TodoViewModel

@Composable
//@Preview
fun PriorityDropDown(viewModel: TodoViewModel, prioritiesList: Array<Priorities>, onPrioritySelected:(Priorities)-> Unit){

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val angle : Float by animateFloatAsState(targetValue = if(isExpanded) 180f else 0f, label = "")
    var parentSize by remember {
        mutableStateOf(IntSize.Zero)
    }


    Row(modifier = Modifier
        .fillMaxWidth().padding(horizontal = MEDIUM_PADDING)
        .onGloballyPositioned {
            parentSize = it.size
        }
        .background(MaterialTheme.colorScheme.background)
        .height(PRIORITY_DROP_DOWN_HEIGHT)
        .clickable {
            isExpanded = true
        }
        .border(
            width = BORDER_WIDTH,
            Color.LightGray,
            shape = RoundedCornerShape(size = SMALL_PADDING)
        ),
        verticalAlignment = Alignment.CenterVertically) {


        Canvas(
            Modifier
                .size(ICON_SIZE)
                .weight(1f)){
            drawCircle(color = viewModel.selectedPriority.color)
        }

        Text(text = viewModel.selectedPriority.name,
            fontSize = PRIORITY_FONT_SIZE,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(7f),
            style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
        )

        IconButton(onClick = { isExpanded = true }, modifier = Modifier
            .weight(1f)
            .rotate(angle)) {
            Icon(imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(R.string.drop_down_icon))
        }

        DropdownMenu(
            modifier = Modifier.width(with(LocalDensity.current) { parentSize.width.toDp() }),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            prioritiesList.slice(0..3).forEach { priority ->
                DropdownMenuItem(
                    text = {PriorityItem(priority)},
                    onClick = {
                        isExpanded = false
                        onPrioritySelected(priority)
                    },
                    contentPadding = PaddingValues(0.dp)
                )
            }
        }

    }
}