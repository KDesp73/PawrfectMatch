package io.github.kdesp73.petadoption.ui.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.R

@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    pic: Int,
    size: Dp
) {
    Surface(
        modifier = Modifier
            .size(154.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = pic),
            contentDescription = "profile image",
            modifier = modifier
                .size(size),
            contentScale = contentScale
        )
    }
}


@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    painter: Painter?,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop,
    size: Dp,
    placeholderId: Int = R.drawable.profile_pic_placeholder
) {
    Surface(
        modifier = Modifier
            .size(size)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
    ) {
        if (painter != null) {
            Box(
                modifier = Modifier
                    .size(size)
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = contentScale
                )
            }
        } else {
            // Placeholder or loading indicator when painter is null
            Box(
                modifier = Modifier
                    .size(size)
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = placeholderId),
                    contentDescription = "profile image",
                    contentScale = contentScale
                )
            }
        }
    }
}

@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    uri: Uri?,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop,
    size: Dp,
    placeholderId: Int = R.drawable.profile_pic_placeholder
) {
    Surface(
        modifier = Modifier
            .size(size)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        val p = rememberAsyncImagePainter(model = uri)
        if (uri != null) {
            Image(
                painter = p,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        } else {
            // Placeholder or loading indicator when painter is null
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = placeholderId),
                    contentDescription = "profile image",
                    modifier = modifier
                        .size(size),
                    contentScale = contentScale
                )
            }
        }
    }
}
