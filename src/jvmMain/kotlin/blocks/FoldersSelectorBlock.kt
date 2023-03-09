package blocks

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
fun FoldersSelectorBlock() {

    Row(modifier = Modifier.background(Color.LightGray)) {
        Column(modifier = Modifier.fillMaxWidth(0.5f)) {
            var sourceDir by remember { mutableStateOf("") }
            var sourceButtonText by remember { mutableStateOf("Select source folder") }
            Button(
                onClick = {
                    val f = JFileChooser("/")
                        .apply {
                            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                            dialogTitle = sourceButtonText
                            approveButtonText = "Select"
                        }
                    f.showOpenDialog(null)
                    sourceDir = f.selectedFile.absolutePath
                    sourceButtonText = "Change source folder"
                },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(sourceButtonText)
            }

            if (sourceDir.isNotEmpty()) {
                Card(
                    content = {
                        Text(
                            text = "Source folder: $sourceDir",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    },
                    modifier = Modifier.padding(top = 0.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                )
            }
        }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            var destDir by remember { mutableStateOf("") }
            var sourceButtonText by remember { mutableStateOf("Select destination folder") }
            Button(
                onClick = {
                    val f = JFileChooser("/")
                        .apply {
                            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                            dialogTitle = sourceButtonText
                            approveButtonText = "Select"
                        }
                    f.showOpenDialog(null)
                    destDir = f.selectedFile.absolutePath
                    sourceButtonText = "Change destination folder"
                },

                modifier = Modifier.padding(10.dp)
            ) {
                Text(sourceButtonText)
            }

            if (destDir.isNotEmpty()) {
                Card(
                    content = {
                        Text(
                            text = "Destination folder: $destDir",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    },
                    modifier = Modifier.padding(top = 0.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                )
            }
        }

    }
}
