package com.ub.todoapplication_jetpackcompose.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ub.todoapplication_jetpackcompose.data.models.Priorities
import com.ub.todoapplication_jetpackcompose.data.models.TodoModel

@Composable
fun TodoListItems(todoModel: TodoModel, maxLines: Int = 2, isCheckedVisible: Boolean = false, onItemClick:()-> Unit,
                  onItemChecked: (Boolean) -> Unit){
    Card(colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
    ), elevation = CardDefaults.elevatedCardElevation(2.dp), modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onItemClick()
        }
        .padding(horizontal = 10.dp, vertical = 7.dp)) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start = 12.dp, top = 8.dp)) {
            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 10.dp)) {
                Canvas(modifier = Modifier
                    .size(12.dp)
                    .weight(.3f)) {
                    drawCircle(color = todoModel.priority.color)
                }

                Spacer(modifier = Modifier.padding(end = 10.dp))

                val weight : Float = if(isCheckedVisible) 2.5f else 7f

                Text(text = todoModel.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .weight(weight)
                )


                val alpha: Float = if(isCheckedVisible) 1f else 0f
                CustomCheckBox(
                    Modifier
                        .weight(.7f)
                        .padding(end = 8.dp)
                        .alpha(alpha), todoModel.isChecked){ isChecked->
                    todoModel.isChecked = isChecked
                    onItemChecked(todoModel.isChecked)
                }
            }


            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(text = todoModel.description,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                maxLines = maxLines,
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .padding(bottom = 15.dp, end = 10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewItems(){
    val todoModel = TodoModel(id = 0, title = "Name of anything", description = "description of anything", isChecked = false, priority = Priorities.HIGH)
    TodoListItems(todoModel = todoModel, onItemClick = { /*TODO*/ }) {

    }
}