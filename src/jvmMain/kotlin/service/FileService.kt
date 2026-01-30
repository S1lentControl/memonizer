package service

import AppState
import constants.ALL_IMAGE_EXTENSIONS
import constants.IMAGES_FOLDER_NAME
import constants.VIDEOS_FOLDER_NAME
import constants.VIDEO_EXTENSIONS
import extensions.endsWithMulti
import metadataService
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class FileService {

    private val fileCounters = ConcurrentHashMap<String, AtomicLong>()

    fun countFilesInFolder(path: String): CountResult {
        val images = AtomicLong(0)
        val videos = AtomicLong(0)
        try {
            Files.walk(Paths.get(path))
                .parallel()
                .map { it.toFile() }
                .filter { !it.isDirectory }
                .forEach {
                    when {
                        it.isImage() -> images.incrementAndGet()
                        it.isVideo() -> videos.incrementAndGet()
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return CountResult(
            images = images.get(),
            videos = videos.get()
        )
    }

    fun copyFiles(appState: AppState) {
        val srcDir = requireNotNull(appState.sourceDir)
        val dstDir = Paths.get(requireNotNull(appState.destDir))
        fileCounters.clear()
        
        try {
            Files.walk(Paths.get(srcDir))
                .parallel()
                .map { it.toFile() }
                .filter { !it.isDirectory }
                .forEach {
                    try {
                        when {
                            it.isImage() -> {
                                it.copyImageTo(dstDir)
                                appState.processedFiles.incrementAndGet()
                            }
                            it.isVideo() -> {
                                it.copyVideoTo(dstDir)
                                appState.processedFiles.incrementAndGet()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun File.copyImageTo(dstDir: Path) {
        val filename = calculateUniqueFilename(this, IMAGES_FOLDER_NAME)
        val dst = Paths.get(dstDir.toString(), IMAGES_FOLDER_NAME, filename)
        Files.createDirectories(dst.parent)
        Files.copy(
            this.toPath(),
            dst,
            StandardCopyOption.REPLACE_EXISTING
        )
    }

    private fun File.copyVideoTo(dstDir: Path) {
        val filename = calculateUniqueFilename(this, VIDEOS_FOLDER_NAME)
        val dst = Paths.get(dstDir.toString(), VIDEOS_FOLDER_NAME, filename)
        Files.createDirectories(dst.parent)
        Files.copy(
            this.toPath(),
            dst,
            StandardCopyOption.REPLACE_EXISTING
        )
    }

    private fun calculateUniqueFilename(file: File, folderType: String): String {
        val size = file.length()
        val date = metadataService.extractOriginalDate(file)
        val baseFilename = "${date}_${size}"
        val key = "$folderType/$baseFilename"
        
        val counter = fileCounters.computeIfAbsent(key) { AtomicLong(0) }
        val count = counter.getAndIncrement()
        
        return if (count == 0L) {
            "${baseFilename}.${file.extension}"
        } else {
            "${baseFilename}_${count}.${file.extension}"
        }
    }

    private fun File.isImage(): Boolean =
        name.endsWithMulti(ALL_IMAGE_EXTENSIONS)

    private fun File.isVideo(): Boolean =
        name.endsWithMulti(VIDEO_EXTENSIONS)

    data class CountResult(
        val images: Long,
        val videos: Long
    )
}
