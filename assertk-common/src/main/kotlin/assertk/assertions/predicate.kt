package assertk.assertions

import assertk.Assert
import assertk.assertions.support.expected

/**
 * Asserts if the values satisfies the predicate provided.
 *
 * ```
 * val divisibleBy5 : (Int) -> Boolean = { it % 5 == 0 }
 * assert(10).matchesPredicate(divisibleBy5)
 * ```
 */
fun <T> Assert<T>.matchesPredicate(f: (T) -> Boolean) {
    if (f(actual)) return
    expected("$actual to satisfy the predicate")
}
