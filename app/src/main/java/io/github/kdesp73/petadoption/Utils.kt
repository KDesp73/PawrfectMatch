package io.github.kdesp73.petadoption

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import io.github.kdesp73.petadoption.room.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import java.security.MessageDigest

fun checkName(name: String): Boolean {
    return name.all { it.isLetter() } && name.isNotBlank() && name.isNotEmpty()
}

fun checkEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return email.matches(emailRegex.toRegex())
}

const val ERR_LEN = "Password must have at least eight characters!"
const val ERR_WHITESPACE = "Password must not contain whitespace!"
const val ERR_DIGIT = "Password must contain at least one digit!"
const val ERR_LOWER = "Password must have at least one lowercase letter!"
const val ERR_UPPER = "Password must have at least one uppercase letter!"
const val ERR_SPECIAL = "Password must have at least one special character, such as: _%-=+#@"

fun validatePassword(pwd: String) = runCatching {
    require(pwd.length >= 8) { ERR_LEN }
    require(pwd.none { it.isWhitespace() }) { ERR_WHITESPACE }
    require(pwd.any { it.isDigit() }) { ERR_DIGIT }
    require(pwd.any { it.isLowerCase() }) { ERR_LOWER }
    require(pwd.any { it.isUpperCase() }) { ERR_UPPER }
    require(pwd.any { !it.isLetterOrDigit() }) { ERR_SPECIAL }
}

fun hash(pass: String): String {
    val bytes = pass.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}

