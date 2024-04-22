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
import io.github.kdesp73.petadoption.ui.components.CheckBoxCollection
import io.github.kdesp73.petadoption.viewmodels.SearchFiltersViewModel

private const val TAG = "Search"

@Composable
fun Search(navController: NavController){
    val context = LocalContext.current
    val viewModel = SearchFiltersViewModel()
    viewModel.reset()

    val scrollState = rememberScrollState()

    Column (
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        Text(text = stringResource(R.string.search_filters), fontSize = 6.em)
        Spacer(modifier = Modifier.height(20.dp))
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

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ){
            val m = Modifier
                .height(200.dp)
                .width(LocalConfiguration.current.screenWidthDp.dp / 2 - 10.dp)
            CheckboxContainer(
                modifier = m,
                title = stringResource(id = R.string.age)
            ) {
                CheckBoxCollection(state = viewModel.ageState, list = petAgeLabelList)
            }
            CheckboxContainer(
                modifier = m,
                title = stringResource(id = R.string.gender)
            ) {
                CheckBoxCollection(state = viewModel.genderState, list = genderLabelList)
            }
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
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
                    Log.d(TAG, viewModel.serialize())
                    if(viewModel.validate().isSuccess){
                        navigateTo(Route.SearchResults.route + "?search_options=${viewModel.serialize()}", navController = navController)
                    } else {
                        Toast.makeText(context,
                            context.getString(R.string.select_at_least_one_of_each), Toast.LENGTH_LONG).show()
                    }
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
