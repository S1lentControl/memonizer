import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AppState {
    var sourceDir by mutableStateOf("")
    var destDir by mutableStateOf("")
}
