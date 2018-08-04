@file:JvmName("SupportJVMKt")
package assertk.assertions.support

import kotlin.reflect.KClass

internal actual fun displayPlatformSpecific(value: Any?): String {
    return when (value) {
        is Byte -> "0x%02X".format(value)
        is Float -> "${value}f"
        is Regex -> "/$value/"
        is Class<*> -> value.name
        is KClass<*> -> {
            val name = value.qualifiedName ?: value.simpleName
            if (name != null) "class $name" else value.toString()
        }
        else -> value.toString()
    }
}
