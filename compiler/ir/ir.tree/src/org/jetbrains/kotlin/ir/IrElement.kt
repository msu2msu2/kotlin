/*
 * Copyright 2010-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.ir

import org.jetbrains.kotlin.ir.visitors.IrElementTransformer
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor

interface IrElement {
    val startOffset: Int
    val endOffset: Int

    fun <R, D> accept(visitor: IrElementVisitor<R, D>, data: D): R

    fun <D> acceptChildren(visitor: IrElementVisitor<Unit, D>, data: D): Unit

    fun <D> transform(transformer: IrElementTransformer<D>, data: D): IrElement =
        accept(transformer, data)

    fun <D> transformChildren(transformer: IrElementTransformer<D>, data: D): Unit
}

interface IrStatement : IrElement

fun IrStatement.transformStatement(transformer: IrElementTransformerVoid): IrStatement =
    transform(transformer, null) as IrStatement

inline fun <reified T : IrElement> IrElement.assertCast(): T =
    if (this is T) this else throw AssertionError("Expected ${T::class.simpleName}: $this")
