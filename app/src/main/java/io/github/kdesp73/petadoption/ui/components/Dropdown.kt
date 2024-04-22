package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import kotlinx.coroutines.flow.MutableStateFlow

class DropdownItem(
    val name: String,
    val action: () -> Unit
)

@Composable
fun Dropdown(
    modifier: Modifier = Modifier,
    state: MutableStateFlow<String> = MutableStateFlow(""),
    defaultExpanded: Boolean = false,
    title: String,
    showTitle: Boolean = true,
    fontSize: TextUnit = 4.em,
    items: List<String>
){
    val opt by state.collectAsState()
    var expanded by remember { mutableStateOf(defaultExpanded) }

    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(showTitle)
            Text(text = title)

        IconButton (
            modifier = modifier,
            text = opt,
            fontSize = fontSize,
            imageVector = if(!expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp
        ){
            expanded = !expanded
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 25.dp, y = 5.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        expanded = false
                        state.value = item
                    }
                )
            }

        }
    }
}
