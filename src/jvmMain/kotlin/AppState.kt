import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.concurrent.atomic.AtomicLong

class AppState {
    var sourceDir: String? by mutableStateOf(null)
    var destDir: String? by mutableStateOf(null)
    var totalImages: Long? by mutableStateOf(null)
    var totalVideos: Long? by mutableStateOf(null)
    var isPrepared: Boolean by mutableStateOf(false)
    val processedFiles: AtomicLong by mutableStateOf(AtomicLong(0))
}
