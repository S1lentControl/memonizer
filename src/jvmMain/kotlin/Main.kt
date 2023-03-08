import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import javax.swing.JFileChooser

@Composable
@Preview
fun App() {
    var sourceDir by remember { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("Select source folder") }

    MaterialTheme {
        Column {
            Button(
                onClick = {
                    val f = JFileChooser("/")
                        .apply {
                            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                            dialogTitle = buttonText
                            approveButtonText = "Select"
                        }
                    f.showOpenDialog(null)
                    sourceDir = f.selectedFile.absolutePath
                    buttonText = "Change source folder"
                },
                modifier = Modifier.padding(20.dp)
            ) {
                Text(buttonText)
            }

            if (sourceDir.isNotEmpty()) {
                Card(
                    content = {
                        Text(
                            text = sourceDir,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp)
                )
            }
        }

    }
}

fun main() = application {
    Window(
        title = "Memonizer",
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
