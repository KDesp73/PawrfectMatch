package io.github.kdesp73.petadoption.routes

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil.compose.AsyncImagePainter.State.Empty.painter
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.openURL
import io.github.kdesp73.petadoption.ui.components.IconButton

@Composable
private fun ClickableTextComponent(value: String, onTextSelected: (String) -> Unit) {
    val initialText = stringResource(R.string.by_continuing_you_accept_our)
    val privacyPolicyText = stringResource(R.string.privacy_policy)
    val andText = stringResource(R.string.and)
    val termsAndConditionsText = stringResource(R.string.terms_of_use)

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(initialText)
        }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(andText)
        }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            pushStringAnnotation(tag = termsAndConditionsText, annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }
    }

    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span ->
                Log.d("ClickableTextComponent", "{${span.item}}")

                if ((span.item == termsAndConditionsText) || (span.item == privacyPolicyText)) {
                    onTextSelected(span.item)
                }
            }
    })
}
@Composable
fun About() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp))
                .size(150.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.paw_solid), // TODO: change with app icon
                modifier = Modifier.fillMaxSize(),
                contentDescription = "App Icon",
                contentScale = ContentScale.Crop
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
            openURL(context, "https://github.com/KDesp73/PetAdoption")
        }
    }
}
