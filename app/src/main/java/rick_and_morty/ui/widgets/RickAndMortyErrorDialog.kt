package rick_and_morty.ui.widgets

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun RickAndMortyErrorDialog(error: String) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {openDialog.value = false})
                { Text(text = "OK") }
            },
            title = { Text(text = "Error") },
            text = { Text(text = "Opps, something went wrong, $error") },
            containerColor = Color.Red
        )
    }
}