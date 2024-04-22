package io.github.kdesp73.petadoption.routes

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.genderLabelList
import io.github.kdesp73.petadoption.enums.genderValueList
import io.github.kdesp73.petadoption.enums.petAgeLabelList
import io.github.kdesp73.petadoption.enums.petAgeValueList
import io.github.kdesp73.petadoption.enums.petSizeLabelList
import io.github.kdesp73.petadoption.enums.petSizeValueList
import io.github.kdesp73.petadoption.enums.petTypeLabelList
import io.github.kdesp73.petadoption.enums.petTypeValueList
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.CheckBoxCollection
import io.github.kdesp73.petadoption.ui.components.IconButton
import io.github.kdesp73.petadoption.viewmodels.SearchFiltersViewModel

private const val TAG = "Search"

@Composable
fun Search(room: AppDatabase, navController: NavController){
    val context = LocalContext.current
    val viewModel = SearchFiltersViewModel()
    viewModel.reset()

    if(room.userDao().getEmail().isEmpty()){
        Center(modifier = Modifier.fillMaxSize()) {
            Text(text = "Please login first to use this feature")
            IconButton(text = "Login", imageVector = Icons.Filled.AccountCircle) {
                navigateTo(Route.Login.route, navController)
            }
        }
        return
    }

    val scrollState = rememberScrollState()

    Column (
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(scrollState)
    ){
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = stringResource(R.string.search_filters),
            fontSize = 6.em
        )
        @Composable
        fun CheckboxContainer(
            title: String,
            modifier: Modifier = Modifier,
            content: @Composable () -> Unit
        ){
            Card (
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = CardDefaults.cardColors().disabledContainerColor,
                    disabledContentColor = CardDefaults.cardColors().disabledContentColor
                ),
                modifier = modifier
            ){
                Column (
                    modifier = Modifier.padding(8.dp)
                ){
                    Text(text = title, fontSize = 5.em)
                    Spacer(modifier = Modifier.height(10.dp))
                    content()
                }
            }
        }
        val bigModifier = Modifier
            .height(150.dp)
            .fillMaxWidth()

        CheckboxContainer(
            modifier = bigModifier,
            title = stringResource(id = R.string.type)
        ) {
            CheckBoxCollection(state = viewModel.typeState, list = petTypeLabelList)
        }
        CheckboxContainer(
            modifier = bigModifier,
            title = stringResource(id = R.string.size)
        ) {
            CheckBoxCollection(state = viewModel.sizeState, list = petSizeLabelList)
        }
        CheckboxContainer(
            modifier = bigModifier,
            title = stringResource(id = R.string.age)
        ) {
            CheckBoxCollection(state = viewModel.ageState, list = petAgeLabelList)
        }
        CheckboxContainer(
            modifier = bigModifier,
            title = stringResource(id = R.string.gender)
        ) {
            val genders = genderLabelList.dropLast(1)
            Log.d(TAG, genders.toString())
            CheckBoxCollection(state = viewModel.genderState, list = genders)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
//            val iconSize = 50.dp
//            CircularIconButton(
//                icon = Icons.Filled.Add,
//                description = "Select All",
//                bg = MaterialTheme.colorScheme.background,
//                size = iconSize
//            ) {
//                viewModel.selectAll()
//            }
            Button(
                onClick = {
                    viewModel.checkEmpty()
                    Log.d(TAG, viewModel.serialize())
                    navigateTo(
                        Route.SearchResults.route + "?search_options=${viewModel.serialize()}",
                        navController = navController,
                        popUpToStartDestination = false,
                        launchAsSingleTop = false
                    )
                }
            ) {
                Text(text = stringResource(id = R.string.route_search))
            }
//            CircularIconButton(
//                icon = Icons.Filled.Clear,
//                description = "Clear All",
//                bg = MaterialTheme.colorScheme.background,
//                size = iconSize
//            ) {
//                viewModel.reset()
//            }
        }
    }
}
