package service

import dateTimeFormat
import extractor.ExifOriginalDateExtractor
import extractor.LastModifiedDateExtractor
import java.io.File


class MetadataService {

    private val originalDateExtractors = listOf(
        ExifOriginalDateExtractor(),
        LastModifiedDateExtractor()
    )

    fun extractOriginalDate(file: File): String =
        originalDateExtractors.firstNotNullOf { it.extractOriginalDate(file) }
            .let { dateTimeFormat.format(it) }
}
