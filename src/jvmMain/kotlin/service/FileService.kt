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
import java.util.concurrent.atomic.AtomicLong


class FileService {

    fun countFilesInFolder(path: String): CountResult {
        val images = AtomicLong(0)
        val videos = AtomicLong(0)
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
        return CountResult(
            images = images.get(),
            videos = videos.get()
        )
    }

    fun copyFiles(appState: AppState) {
        val srcDir = requireNotNull(appState.sourceDir)
        val dstDir = Paths.get(requireNotNull(appState.destDir))
        Files.walk(Paths.get(srcDir))
            .parallel()
            .map { it.toFile() }
            .filter { !it.isDirectory }
            .forEach {
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
            }
    }

    private fun File.copyImageTo(dstDir: Path) {
        val filename = calculateImageFilename(this)
        println(filename)
        val dst = Paths.get(dstDir.toString(), IMAGES_FOLDER_NAME, filename)
        Files.createDirectories(dst.parent)
        Files.copy(
            this.toPath(),
            dst,
            StandardCopyOption.REPLACE_EXISTING
        )
    }

    private fun File.copyVideoTo(dstDir: Path) {
        val filename = calculateVideoFilename(this)
        println(filename)
        val dst = Paths.get(dstDir.toString(), VIDEOS_FOLDER_NAME, filename)
        Files.createDirectories(dst.parent)
        Files.copy(
            this.toPath(),
            dst,
            StandardCopyOption.REPLACE_EXISTING
        )
    }

    private fun calculateImageFilename(file: File): String {
        val size = file.length()
        val date = metadataService.extractOriginalDate(file)
        return "${date}_${size}.${file.extension}"
    }

    private fun calculateVideoFilename(file: File): String {
        val size = file.length()
        val date = metadataService.extractOriginalDate(file)
        return "${date}_${size}.${file.extension}"
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
