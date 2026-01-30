import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.File
import java.util.concurrent.atomic.AtomicLong

class AppState {
    var sourceDir: String? by mutableStateOf(null)
    var destDir: String? by mutableStateOf(null)
    var totalImages: Long? by mutableStateOf(null)
    var totalVideos: Long? by mutableStateOf(null)
    var isPrepared: Boolean by mutableStateOf(false)
    val processedFiles: AtomicLong by mutableStateOf(AtomicLong(0))
    var errorMessage: String? by mutableStateOf(null)
    
    fun isSourceDirValid(): Boolean {
        val dir = sourceDir ?: return false
        val file = File(dir)
        return file.exists() && file.isDirectory && file.canRead()
    }
    
    fun isDestDirValid(): Boolean {
        val dir = destDir ?: return false
        val file = File(dir)
        return file.exists() && file.isDirectory && file.canWrite()
    }
    
    fun reset() {
        totalImages = null
        totalVideos = null
        isPrepared = false
        processedFiles.set(0)
        errorMessage = null
    }
}
