package io.github.kdesp73.petadoption.viewmodels

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.github.kdesp73.petadoption.enums.genderLabelList
import io.github.kdesp73.petadoption.enums.genderValueList
import io.github.kdesp73.petadoption.enums.petAgeLabelList
import io.github.kdesp73.petadoption.enums.petAgeValueList
import io.github.kdesp73.petadoption.enums.petSizeLabelList
import io.github.kdesp73.petadoption.enums.petSizeValueList
import io.github.kdesp73.petadoption.enums.petTypeLabelList
import io.github.kdesp73.petadoption.enums.petTypeValueList
import kotlinx.coroutines.flow.MutableStateFlow

data class SearchOptions(
    val type: Map<String, Boolean>,
    val gender: Map<String, Boolean>,
    val size: Map<String, Boolean>,
    val age: Map<String, Boolean>
)
class SearchFiltersViewModel () : ViewModel(){
    var genderState = MutableStateFlow(mutableListOf<Boolean>())
    var typeState = MutableStateFlow(mutableListOf<Boolean>())
    var sizeState = MutableStateFlow(mutableListOf<Boolean>())
    var ageState = MutableStateFlow(mutableListOf<Boolean>())

    fun reset(){
        initType()
        initGender()
        initAge()
        initSize()
    }

    fun selectAll(){
        initType(true)
        initGender(true)
        initAge(true)
        initSize(true)
    }

    fun checkEmpty(){
        if(genderState.value.all { !it }){
            initGender(true)
        }

        if(typeState.value.all { !it }){
            initType(true)
        }

        if(ageState.value.all { !it }){
            initAge(true)
        }

        if(sizeState.value.all { !it }) {
            initSize(true)
        }
    }


    private fun initType(value: Boolean = false){
        typeState.value = MutableList(petTypeLabelList.size) { value }
    }
    private fun initAge(value: Boolean = false){
        ageState.value = MutableList(petAgeLabelList.size) { value }
    }
    private fun initSize(value: Boolean = false){
        sizeState.value = MutableList(petSizeLabelList.size) { value }
    }
    private fun initGender(value: Boolean = false) {
        genderState.value = MutableList(genderLabelList.size - 1) { value }
    }

    private fun exportType() : MutableMap<String, Boolean> {
        val hashMap = mutableMapOf<String, Boolean>()

        for(i in petTypeValueList.indices){
            hashMap[petTypeValueList[i]] = typeState.value[i]
        }

        return hashMap
    }

    private fun exportAge() : MutableMap<String, Boolean> {
        val hashMap = mutableMapOf<String, Boolean>()

        for(i in petAgeValueList.indices){
            hashMap[petAgeValueList[i]] = ageState.value[i]
        }

        return hashMap
    }

    private fun exportSize() : MutableMap<String, Boolean> {
        val hashMap = mutableMapOf<String, Boolean>()

        for(i in petSizeValueList.indices){
            hashMap[petSizeValueList[i]] = sizeState.value[i]
        }

        return hashMap
    }

    private fun exportGender() : MutableMap<String, Boolean> {
        val hashMap = mutableMapOf<String, Boolean>()

        for(i in genderValueList.dropLast(1).indices){
            hashMap[genderValueList[i]] = genderState.value[i]
        }

        return hashMap
    }

    fun validate() = runCatching {
        require(typeState.value.any { it })
        require(ageState.value.any { it })
        require(sizeState.value.any { it })
        require(genderState.value.any { it })
    }

    fun serialize(): String{
        val gson = Gson()
        val data = SearchOptions(exportType(), exportGender(), exportSize(), exportAge())
        return gson.toJson(data)
    }

    fun deserialize(json: String): SearchOptions{
        val gson = Gson()
        return gson.fromJson(json, SearchOptions::class.java)
    }
}