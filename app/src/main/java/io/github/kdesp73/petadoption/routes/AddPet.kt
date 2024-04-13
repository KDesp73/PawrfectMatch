
package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.Pet
import io.github.kdesp73.petadoption.enums.Orientation
import io.github.kdesp73.petadoption.enums.PetAge
import io.github.kdesp73.petadoption.enums.PetSize
import io.github.kdesp73.petadoption.enums.PetType
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.ui.components.CircularImage
import io.github.kdesp73.petadoption.ui.components.Dropdown
import io.github.kdesp73.petadoption.ui.components.DropdownItem
import io.github.kdesp73.petadoption.ui.components.GradientButton
import io.github.kdesp73.petadoption.ui.components.HalfButton
import io.github.kdesp73.petadoption.ui.components.OptionPicker
import io.github.kdesp73.petadoption.ui.components.SelectImage
import io.github.kdesp73.petadoption.ui.components.TextFieldComponent
import io.github.kdesp73.petadoption.viewmodels.AddPetViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AddPet(){
    val scrollState = rememberScrollState()
    val viewModel = AddPetViewModel()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        val imageModifier = Modifier.fillMaxSize()
        SelectImage (viewModel.imageState){ action ->
            val imagePainter = rememberAsyncImagePainter(viewModel.imageState.value)
            CircularImage(
                modifier = imageModifier.clickable { action() },
                painter = imagePainter,
                contentDescription = "Pet image",
                size = 200.dp
            )
        }
        TextFieldComponent(viewModel.nameState, labelValue = "Name", icon = Icons.Rounded.Face, type = TextFieldType.OUTLINED)
        Row {
            Dropdown(
                state = viewModel.typeState,
                title = "Type",
                items = listOf(
                    PetType.DOG.label,
                    PetType.CAT.label,
                    PetType.BIRD.label,
                    PetType.FISH.label,
                    PetType.BUNNY.label,
                    PetType.SNAKE.label
                )
            )
            Dropdown(
                state = viewModel.ageState,
                title = "Age",
                items = listOf(
                    PetAge.BABY.label,
                    PetAge.ADULT.label,
                    PetAge.SENIOR.label
                )
            )
        }
        Dropdown(
            state = viewModel.sizeState,
            title = "Size",
            items = listOf(
                PetSize.SMALL.label,
                PetSize.MEDIUM.label,
                PetSize.LARGE.label
            )
        )
        
        HalfButton(
            icon = Icons.Filled.Add,
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Add Your Pet!",
        ) {
            // TODO: Apply query
        }


    }
}

@Preview
@Composable
fun AddPetPreview(){
    AddPet()
}