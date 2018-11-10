package assertk.assertions

import assertk.Assert
import assertk.all
import assertk.assertAll
import assertk.assertions.support.expected
import assertk.assertions.support.show

/**
 * Asserts the iterable contains the expected element, using `in`.
 * @see [doesNotContain]
 */
fun <T : Iterable<*>> Assert<T>.contains(element: Any?) {
    if (element in actual) return
    expected("to contain:${show(element)} but was:${show(actual)}")
}

/**
 * Asserts the iterable does not contain the expected element, using `!in`.
 * @see [contains]
 */
fun <T : Iterable<*>> Assert<T>.doesNotContain(element: Any?) {
    if (element !in actual) return
    expected("to not contain:${show(element)} but was:${show(actual)}")
}

/**
 * Asserts on each item in the iterable. The given lambda will be run for each item.
 *
 * ```
 * assert(listOf("one", "two")).each {
 *   it.hasLength(3)
 * }
 * ```
 */
fun <E, T : Iterable<E>> Assert<T>.each(f: (Assert<E>) -> Unit) {
    assertAll {
        actual.forEachIndexed { index, item ->
            f(assert(item, "${name ?: ""}${show(index, "[]")}"))
        }
    }
}

/**
 * Asserts on each item in the iterable, passing if at least `times` items pass.
 * The given lambda will be run for each item.
 *
 * ```
 * assert(listOf(-1, 1, 2) as Iterable<Int>).atLeast(2) { it -> it.isPositive() }
 * ```
 */
fun <E, T : Iterable<E>> Assert<T>.atLeast(times: Int, f: (Assert<E>) -> Unit) {
    var count = 0
    all(message = "expected to pass at least $times times",
        body = { each { item -> count++; f(item) } },
        failIf = { count - it.size < times })
}
