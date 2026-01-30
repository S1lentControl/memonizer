package service

import dateTimeFormat
import extractor.ExifOriginalDateExtractor
import extractor.LastModifiedDateExtractor
import java.io.File
import java.time.Instant

class MetadataService {

    private val originalDateExtractors = listOf(
        ExifOriginalDateExtractor(),
        LastModifiedDateExtractor()
    )

    fun extractOriginalDate(file: File): String {
        val instant = originalDateExtractors.firstNotNullOfOrNull { 
            it.extractOriginalDate(file) 
        } ?: Instant.now()
        
        return dateTimeFormat.format(instant)
    }
}
