// !DIAGNOSTICS: -UNUSED_PARAMETER

abstract class SubFunction : kotlin.Function0<Unit>

fun <T> takeIt(x: T, f: SubFunction) {}

fun cr() {}

fun test() {
    <!TYPE_INFERENCE_PARAMETER_CONSTRAINT_ERROR!>takeIt<!>(42, <!TYPE_MISMATCH!>::cr<!>)
    <!TYPE_INFERENCE_PARAMETER_CONSTRAINT_ERROR!>takeIt<!>(42, <!TYPE_MISMATCH!>{ }<!>)
}
