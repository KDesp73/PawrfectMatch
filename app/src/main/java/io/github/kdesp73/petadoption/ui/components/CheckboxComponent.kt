package io.github.kdesp73.petadoption.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import io.github.kdesp73.petadoption.R
import kotlinx.coroutines.flow.MutableStateFlow

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
fun CheckboxComponent(
    value: String,
    state: MutableStateFlow<Boolean> = MutableStateFlow(false),
    onTextSelected: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val checked by state.collectAsState()
        Checkbox(checked = checked,
            onCheckedChange = {
                state.value = !state.value
                onCheckedChange.invoke(it)
            })

        ClickableTextComponent(value = value, onTextSelected)
    }
}