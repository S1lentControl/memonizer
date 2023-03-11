import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition.Aligned
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import blocks.FoldersSelectorBlock
import blocks.RunBlock
import service.FileService

@Composable
@Preview
fun App() {

    val appState by remember { mutableStateOf(AppState()) }

    MaterialTheme {
        Column {
            FoldersSelectorBlock(appState)
            RunBlock(appState)
        }
    }
}

val fileService = FileService()

fun main() = application {
    Window(
        title = "Memonizer",
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(size = DpSize(600.dp, 210.dp), position = Aligned(Alignment.Center))
    ) {
        App()
    }
}
