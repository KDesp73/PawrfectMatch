package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import io.github.kdesp73.petadoption.enums.genderFromValue
import io.github.kdesp73.petadoption.enums.genderLabelList
import io.github.kdesp73.petadoption.enums.genderValueList
import io.github.kdesp73.petadoption.enums.petAgeFromValue
import io.github.kdesp73.petadoption.enums.petAgeLabelList
import io.github.kdesp73.petadoption.enums.petAgeValueList
import io.github.kdesp73.petadoption.enums.petSizeFromValue
import io.github.kdesp73.petadoption.enums.petSizeLabelList
import io.github.kdesp73.petadoption.enums.petSizeValueList
import io.github.kdesp73.petadoption.enums.petTypeFromValue
import io.github.kdesp73.petadoption.enums.petTypeLabelList
import io.github.kdesp73.petadoption.enums.petTypeValueList
import io.github.kdesp73.petadoption.firebase.FirestorePet
import io.github.kdesp73.petadoption.firebase.FirestoreToy
import io.github.kdesp73.petadoption.firebase.PetManager
import io.github.kdesp73.petadoption.firebase.ToyManager
import io.github.kdesp73.petadoption.isLoggedIn
import io.github.kdesp73.petadoption.isNotLoggedIn
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.CheckBoxCollection
import io.github.kdesp73.petadoption.ui.components.IconButton
import io.github.kdesp73.petadoption.ui.components.LoadingAnimation
import io.github.kdesp73.petadoption.ui.components.MyCard
import io.github.kdesp73.petadoption.ui.components.PetCard
import io.github.kdesp73.petadoption.ui.components.PleaseLogin
import io.github.kdesp73.petadoption.ui.components.ToyCard
import io.github.kdesp73.petadoption.viewmodels.SearchFiltersViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

private const val TAG = "Search"

@Composable
fun Search(room: AppDatabase, navController: NavController){
    var index by remember { mutableIntStateOf(0) }

    val tabs = listOf(stringResource(R.string.pets), stringResource(R.string.toys))

    val email: String? = room.userDao().getEmail()
    if(isNotLoggedIn(room)){
        PleaseLogin(email = email, navController = navController)
        return
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = index) {
            tabs.forEachIndexed { i, title ->
                Tab(text = { Text(title) },
                    selected = index == i,
                    onClick = { index = i }
                )
            }
        }
        when (index) {
            0 -> SearchPets(navController)
            1 -> SearchToys(navController)
            else -> Text("No index")
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun SearchToys(navController: NavController){
    val toyManager= ToyManager()

    var list by remember { mutableStateOf<List<FirestoreToy>?>(null) }

    LaunchedEffect(key1 = list) {
        val deferredResult = GlobalScope.async {
            toyManager.getToys()
        }

        list = deferredResult.await()
    }

    if(list == null){
        Center(modifier = Modifier.fillMaxSize()) {
            LoadingAnimation(64.dp)
        }
    } else if(list?.isEmpty() == true){
        Row (
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Text(text = stringResource(R.string.no_results), fontSize = 6.em)
        }
    } else {
        LazyColumn (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(list!!){ item ->
                ToyCard(toy = item, id = item.id, navController = navController)
            }
        }
    }

}

@Composable
fun SearchPets(navController: NavController){
    val context = LocalContext.current
    val viewModel = SearchFiltersViewModel()
    viewModel.reset()


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
            MyCard (
                modifier = modifier
            ){
                Column (
                    modifier = Modifier.padding(10.dp)
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
