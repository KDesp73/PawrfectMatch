package io.github.kdesp73.petadoption.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.kdesp73.petadoption.enums.CustomAlignment

/**
 * ---------------------
 * Top
 * - - - - - - - - - - -
 * 
 * 
 * Center
 * 
 * 
 * - - - - - - - - - - -
 * Bottom
 * ---------------------
 */
@Composable
fun VerticalScaffold(
    modifier: Modifier = Modifier,
    top: @Composable () -> Unit,
    bottom: @Composable () -> Unit,
    center: @Composable () -> Unit,
    topAlignment: CustomAlignment = CustomAlignment.CENTER,
    centerAlignment: CustomAlignment = CustomAlignment.CENTER,
    bottomAlignment: CustomAlignment = CustomAlignment.CENTER,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val top_a = when(topAlignment) {
            CustomAlignment.START -> Alignment.TopStart
            CustomAlignment.CENTER -> Alignment.TopCenter
            CustomAlignment.END -> Alignment.TopEnd
            else -> throw IllegalArgumentException("Unsupported alignment: $topAlignment")
        }

        val center_a = when(centerAlignment) {
            CustomAlignment.START -> Alignment.CenterStart
            CustomAlignment.CENTER -> Alignment.Center
            CustomAlignment.END -> Alignment.CenterEnd
            else -> throw IllegalArgumentException("Unsupported alignment: $centerAlignment")
        }

        val bottom_a = when(bottomAlignment) {
            CustomAlignment.START -> Alignment.BottomStart
            CustomAlignment.CENTER -> Alignment.BottomCenter
            CustomAlignment.END -> Alignment.BottomEnd
            else -> throw IllegalArgumentException("Unsupported alignment: $bottomAlignment")
        }

        Box(modifier = Modifier.align(top_a)){
            top()
        }

        Box(modifier = Modifier.align(center_a)){
            center()
        }
        
        Box(modifier = Modifier.align(bottom_a)){
            bottom()
        }
    }
}

