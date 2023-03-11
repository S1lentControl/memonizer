package blocks

import AppState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import constants.BLACK_GREEN
import fileService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RunBlock(appState: AppState) {

    val progressFloat = remember { mutableStateOf(0f) }
    val progressStarted = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            if (progressStarted.value) {
                LinearProgressIndicator(
                    modifier = Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp).fillMaxWidth(),
                    progress = progressFloat.value
                )
            }
            Button(
                onClick = {
                    val count = fileService.countFilesInFolder(appState.sourceDir)
                    println(count)

                    progressFloat.value = 0f
                    progressStarted.value = false

                    progressStarted.value = true
                    coroutineScope.launch {
                        while (progressFloat.value < 1) {
                            progressFloat.value += 0.1f
                            delay(300)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = BLACK_GREEN, contentColor = Color.White),
            ) {
                Text("Organize")
            }
        }
    }
}
