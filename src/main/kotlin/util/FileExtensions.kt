package util

import java.io.File

inline fun <reified T> readPackageLocale(name: String) =
    File(
        "src/main/kotlin/" +
                T::class.java.packageName.replace(".", "/"),
        name
    )
        .readLines()
