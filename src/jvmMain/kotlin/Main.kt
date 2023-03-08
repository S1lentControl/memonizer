import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
    val text by remember { mutableStateOf("Select source folder") }
    var sourceDir by remember { mutableStateOf("") }

    MaterialTheme {
        Column {
            Button(
                onClick = {
                    val f = JFileChooser("/")
                        .apply {
                            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                            dialogTitle = "Selects source folder"
                            approveButtonText = "Select"
                            approveButtonToolTipText = "Selects source folder"
                        }
                    f.showOpenDialog(null)
                    sourceDir = f.selectedFile.absolutePath
                },
                modifier = Modifier.padding(20.dp)
            ) {
                Text(text)
            }
        }
        Column {
            Text(sourceDir)
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
