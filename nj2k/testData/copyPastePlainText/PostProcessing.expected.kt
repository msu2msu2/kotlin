package to

import java.io.File


class JavaClass {
    internal fun foo(file: File?, target: MutableList<String>?) {
        val list = ArrayList<String>()
        if (file != null) {
            list.add(file.name)
        }
        target?.addAll(list)
    }
}
