// DONT_TARGET_EXACT_BACKEND: JS JS_IR JS_IR_ES6 WASM NATIVE
//WITH_RUNTIME
// MODULE: lib
// FILE: JavaClass.java

public class JavaClass {

    public Double test() {
        return null;
    }

}

// MODULE: main(lib)
// FILE: b.kt

fun test(s: Double) {

}

fun box(): String {
    try {
        test(JavaClass().test())
    }
    catch (e: NullPointerException) {
        return "OK"
    }
    return "fail"
}
