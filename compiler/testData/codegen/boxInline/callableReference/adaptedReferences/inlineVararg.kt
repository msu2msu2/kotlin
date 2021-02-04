// DONT_TARGET_EXACT_BACKEND: WASM
// WASM_MUTE_REASON: IGNORED_IN_JS
// !LANGUAGE: +NewInference
// WITH_RUNTIME
// KJS_WITH_FULL_RUNTIME

// FILE: 1.kt
package test

inline fun foo(mkString: (Char, Char) -> String): String =
        mkString('O','K')

inline fun bar (vararg xs: Char) =
        String(xs)

// FILE: 2.kt
import test.*

fun box(): String = foo(::bar)
// -> { a, b -> bar(a, b) }