package io.github.kdesp73.petadoption.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.kdesp73.petadoption.enums.CustomAlignment


/**
 * |      |        |       |
 * |      |        |       |
 * |      |        |       |
 * | Left | Center | Right |
 * |      |        |       |
 * |      |        |       |
 * |      |        |       |
 */
@Composable
fun HorizontalScaffold(
    modifier: Modifier = Modifier,
    left: @Composable () -> Unit,
    right: @Composable () -> Unit,
    center: @Composable () -> Unit,
    leftAlignment: CustomAlignment = CustomAlignment.CENTER,
    centerAlignment: CustomAlignment = CustomAlignment.CENTER,
    rightAlignment: CustomAlignment = CustomAlignment.CENTER,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val left_a = when(leftAlignment) {
            CustomAlignment.TOP-> Alignment.TopCenter
            CustomAlignment.CENTER -> Alignment.Center
            CustomAlignment.BOTTOM -> Alignment.BottomCenter
            else -> throw IllegalArgumentException("Unsupported alignment: $leftAlignment")
        }

        val center_a = when(centerAlignment) {
            CustomAlignment.TOP-> Alignment.TopCenter
            CustomAlignment.CENTER -> Alignment.Center
            CustomAlignment.BOTTOM -> Alignment.BottomCenter
            else -> throw IllegalArgumentException("Unsupported alignment: $centerAlignment")
        }

        val right_a = when(rightAlignment) {
            CustomAlignment.TOP-> Alignment.TopCenter
            CustomAlignment.CENTER -> Alignment.Center
            CustomAlignment.BOTTOM -> Alignment.BottomCenter
            else -> throw IllegalArgumentException("Unsupported alignment: $rightAlignment")
        }

        Box(modifier = Modifier.align(left_a)){
            left()
        }

        Box(modifier = Modifier.align(center_a)){
            center()
        }

        Box(modifier = Modifier.align(right_a)){
            right()
        }
    }
}

