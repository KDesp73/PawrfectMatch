package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.google.android.play.integrity.internal.i
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CheckBoxCollection(
    state: MutableStateFlow<MutableList<Boolean>> = MutableStateFlow(mutableListOf()),
    list: List<String>,
    maxHeight: Int = 5
){
    if (state.value.size != list.size) {
        return
    }

    FlowRow (

    ){
        for(index in list.indices){
            val item = list[index]
            val isChecked = remember { mutableStateOf(state.value[index]) }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = { newState ->
                        isChecked.value = newState
                        val currentState = state.value.toMutableList()
                        currentState[index] = newState
                        state.value = currentState
                    }
                )
                Text(text = item, color = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        }
    }
}
