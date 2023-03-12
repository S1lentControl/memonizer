package extensions

fun String.endsWithMulti(strings: Collection<String>): Boolean =
    strings.any { endsWith(it, true) }
