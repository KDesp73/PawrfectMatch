package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.viewmodels.ToyFormViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ToyForm(modifier: Modifier = Modifier, viewModel: ToyFormViewModel, submitAction : () -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        val imageModifier = Modifier.fillMaxSize()
        SelectImage(null) { action, uri ->
            val imagePainter = rememberAsyncImagePainter(uri)
            CircularImage(
                modifier = imageModifier.clickable { action() },
                painter = imagePainter,
                contentDescription = "Pet image",
                size = 200.dp
            )
            viewModel.imageState.value = uri
        }
        // TODO: change icon
        TextFieldComponent(
            viewModel.nameState,
            labelValue = stringResource(R.string.name),
            icon = Icons.Rounded.Face,
            type = TextFieldType.OUTLINED
        )
        TextFieldComponent(
            viewModel.locationState,
            labelValue = stringResource(id = R.string.location),
            icon = Icons.Rounded.LocationOn,
            type = TextFieldType.OUTLINED
        )
        DecimalFieldComponent(
            viewModel.priceState,
            labelValue = "Price",
            icon = Icons.Filled.ShoppingCart,
            type = TextFieldType.OUTLINED
        )

        CircularIconButton(
            icon = Icons.Filled.Check,
            description = "Add Pet",
            bg = MaterialTheme.colorScheme.surface,
            size = 60.dp
        ) {
            submitAction()
        }
    }
}