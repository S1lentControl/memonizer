package extractor

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifSubIFDDirectory
import java.io.File
import java.time.Instant

class ExifOriginalDateExtractor : OriginalDateExtractor {

    override fun extractOriginalDate(file: File): Instant? {
        return try {
            val metadata = ImageMetadataReader.readMetadata(file)
            metadata.getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)
                ?.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)
                ?.toInstant()
        } catch (e: Exception) {
            null
        }
    }
}
