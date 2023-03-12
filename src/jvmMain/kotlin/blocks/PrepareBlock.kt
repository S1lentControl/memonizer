package blocks

import AppState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrepareBlock(appState: AppState) {

    if (appState.totalVideos != null && appState.totalImages != null)
        Column(modifier = Modifier.padding(10.dp)) {
            Row {
                Text("Number of image files: ${appState.totalImages}")
            }
            Row {
                Text("Number of video files: ${appState.totalVideos}")
            }
        }
}
