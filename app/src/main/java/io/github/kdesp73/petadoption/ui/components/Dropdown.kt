package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

class DropdownItem(
    val name: String,
    val action: () -> Unit
){

}

@Composable
fun Dropdown(defaultExpanded: Boolean = false, title: String, items: List<DropdownItem>){
    var expanded by remember { mutableStateOf(defaultExpanded) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ){
        HalfButton (
            text = title,
            icon = if(!expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp
        ){
            expanded = !expanded
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 20.dp, y = 5.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.name) },
                    onClick = { item.action() }
                )
            }

        }
    }
}

@Preview
@Composable
fun DropdownPreview(){
    val themes = listOf<DropdownItem>(
        DropdownItem("Light") {
        },
        DropdownItem("Dark") {
        },
        DropdownItem("Example") {
        },
    )
    Dropdown(defaultExpanded = true, title = "Theme", items = themes)
}