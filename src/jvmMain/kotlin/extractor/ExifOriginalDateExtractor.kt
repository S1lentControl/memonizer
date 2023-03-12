package extractor

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifSubIFDDirectory
import java.io.File
import java.time.Instant

class ExifOriginalDateExtractor : OriginalDateExtractor {

    override fun extractOriginalDate(file: File): Instant? {
        val metadata = ImageMetadataReader.readMetadata(file)
        return metadata.getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)
            ?.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)
            ?.toInstant()
    }
}
