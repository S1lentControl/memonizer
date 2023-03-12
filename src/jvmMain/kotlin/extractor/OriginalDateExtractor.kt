package extractor

import java.io.File
import java.time.Instant

interface OriginalDateExtractor {

    fun extractOriginalDate(file: File): Instant?
}
