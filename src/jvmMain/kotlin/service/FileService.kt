package service

import java.nio.file.Files
import java.nio.file.Paths

class FileService {

    fun countFilesInFolder(path: String): Long =
        Files.walk(Paths.get(path))
            .parallel()
            .filter { !it.toFile().isDirectory }
            .count()
}
