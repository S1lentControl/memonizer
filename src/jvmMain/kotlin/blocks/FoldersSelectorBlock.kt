package blocks

import AppState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import javax.swing.JFileChooser

@Preview
@Composable
fun FoldersSelectorBlock(appState: AppState) {

    Row(modifier = Modifier.background(Color.LightGray)) {
        Column(modifier = Modifier.fillMaxWidth(0.5f)) {
            var sourceButtonText by remember { mutableStateOf("Select source folder") }
            Button(
                onClick = {
                    val f = JFileChooser("/")
                        .apply {
                            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                            dialogTitle = sourceButtonText
                            approveButtonText = "Select"
                        }
                    val result = f.showOpenDialog(null)
                    if (result == JFileChooser.APPROVE_OPTION && f.selectedFile != null) {
                        appState.sourceDir = f.selectedFile.absolutePath
                        sourceButtonText = "Change source folder"
                    }
                },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(sourceButtonText)
            }

            if (appState.sourceDir?.isNotEmpty() == true) {
                Card(
                    content = {
                        Text(
                            text = "Source folder: ${appState.sourceDir}",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    },
                    modifier = Modifier.padding(top = 0.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                )
            }
        }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            var destButtonText by remember { mutableStateOf("Select destination folder") }
            Button(
                onClick = {
                    val f = JFileChooser("/")
                        .apply {
                            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                            dialogTitle = destButtonText
                            approveButtonText = "Select"
                        }
                    val result = f.showOpenDialog(null)
                    if (result == JFileChooser.APPROVE_OPTION && f.selectedFile != null) {
                        appState.destDir = f.selectedFile.absolutePath
                        destButtonText = "Change destination folder"
                    }
                },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(destButtonText)
            }

            if (appState.destDir?.isNotEmpty() == true) {
                Card(
                    content = {
                        Text(
                            text = "Destination folder: ${appState.destDir}",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    },
                    modifier = Modifier.padding(top = 0.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                )
            }
        }
    }
}
