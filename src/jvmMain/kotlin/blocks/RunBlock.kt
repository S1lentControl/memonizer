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
            if (!appState.isPrepared) {
                Button(
                    onClick = {
                        val countResult = fileService.countFilesInFolder(appState.sourceDir!!)
                        appState.totalImages = countResult.images
                        appState.totalVideos = countResult.videos
                        appState.isPrepared = true
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BLACK_GREEN, contentColor = Color.White),
                    enabled = appState.sourceDir != null && appState.destDir != null
                ) {
                    Text("Next")
                }
            } else {
                Button(
                    onClick = {
                        progressFloat.value = 0f
                        progressStarted.value = false

                        progressStarted.value = true
                        fileService.copyFiles(appState)
                        val totalMediaFiles = appState.totalImages!! + appState.totalVideos!!
                        coroutineScope.launch {
                            progressFloat.value = appState.processedFiles.get().toFloat() / totalMediaFiles
                            while (totalMediaFiles != appState.processedFiles.get()) {
                                progressFloat.value = appState.processedFiles.get().toFloat() / totalMediaFiles
                                delay(100)
                                println(1)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BLACK_GREEN, contentColor = Color.White),
                    enabled = appState.sourceDir != null && appState.destDir != null
                ) {
                    Text("Organize")
                }
            }
        }
    }
}
