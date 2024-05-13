package io.github.kdesp73.petadoption.routes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter.State.Empty.painter
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.openURL
import io.github.kdesp73.petadoption.ui.components.IconButton

fun dpToPx(dp: Float, context: Context): Float {
    val density = context.resources.displayMetrics.density
    return dp * density
}
@Composable
fun About() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val sizeDp = 200.dp
        val size = dpToPx(sizeDp.value, LocalContext.current).toInt()
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(40.dp))
                .size(sizeDp),
            contentAlignment = Alignment.Center
        ) {
            val appIcon =
                LocalContext.current.packageManager.getApplicationIcon("io.github.kdesp73.petadoption")
            val appIconBitmap = appIcon.toBitmap(size, size)

            Image(
                painter = BitmapPainter(appIconBitmap.asImageBitmap()),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "App Icon",
                contentScale = ContentScale.FillBounds
            )
        }
        Text(text = stringResource(R.string.an_app_by))
        val myName = stringResource(R.string.konstantinos_despoinidis)
        val annotatedString = buildAnnotatedString { 
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontSize = 1.5.em)) {
                pushStringAnnotation(tag = myName, annotation = myName)
                append(myName)
            }
        }
        ClickableText(text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    if (span.item == myName){
                        openURL(context = context, url = "https://github.com/KDesp73")
                    }
                }
        }) 
        Text(text = stringResource(R.string.app_version))

        IconButton(
            colors = ButtonColors(
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
                disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
            ),
            text = "Source Code",
            imageVector = ImageVector.vectorResource(R.drawable.github)
        ) {
            openURL(context, "https://github.com/KDesp73/PawrfectMatch")
        }
    }
}
