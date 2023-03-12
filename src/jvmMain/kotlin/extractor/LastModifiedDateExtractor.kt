package extractor

import java.io.File
import java.time.Instant

class LastModifiedDateExtractor : OriginalDateExtractor {

    override fun extractOriginalDate(file: File): Instant? =
        Instant.ofEpochMilli(file.lastModified())
}
