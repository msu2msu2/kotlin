/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.descriptors.commonizer.cir

import org.jetbrains.kotlin.descriptors.commonizer.utils.hashCode
import org.jetbrains.kotlin.descriptors.commonizer.utils.Interner
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

class CirName private constructor(val name: String) {
    override fun equals(other: Any?): Boolean = (other as? CirName)?.name == name
    override fun hashCode(): Int = name.hashCode()
    override fun toString(): String = "CirName(name=$name)"

    companion object {
        fun create(name: String): CirName = interner.intern(CirName(name))
        fun create(name: Name): CirName = create(name.asString())

        private val interner = Interner<CirName>()
    }
}

class CirPackageName private constructor(val segments: Array<CirName>) {
    override fun equals(other: Any?): Boolean = (other as? CirPackageName)?.segments?.contentEquals(segments) == true
    override fun hashCode(): Int = hashCode(segments)
    override fun toString(): String = "CirPackageName(segments=${asString()})"

    fun asString(): String = segments.joinToString(".") { it.name }
    fun isRoot(): Boolean = segments.isEmpty()

    companion object {
        val ROOT = create(emptyArray())

        fun create(packageName: String): CirPackageName {
            if (packageName.isEmpty()) return ROOT

            val rawSegments = packageName.split(".")
            val segments = Array(rawSegments.size) { index -> CirName.create(rawSegments[index]) }

            return create(segments)
        }

        fun create(packageName: FqName): CirPackageName {
            if (packageName.isRoot) return ROOT

            val rawSegments = packageName.pathSegments()
            val segments = Array(rawSegments.size) { index -> CirName.create(rawSegments[index]) }

            return create(segments)
        }

        @Suppress("NOTHING_TO_INLINE")
        private inline fun create(segments: Array<CirName>) = interner.intern(CirPackageName(segments))

        private val interner = Interner<CirPackageName>()
    }
}
