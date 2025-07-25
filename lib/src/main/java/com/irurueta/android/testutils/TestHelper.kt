/*
 * Copyright (C) 2025 Alberto Irurueta Carro (alberto@irurueta.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.irurueta.android.testutils

import kotlin.apply
import kotlin.collections.find
import kotlin.collections.firstOrNull
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

/**
 * Calls any private function with the specified name on the receiver object,
 * optionally passing arguments, and returns the result as a nullable type.
 *
 * @param T the type of the receiver object.
 * @param R the type of the result expected from the private function.
 * @param name the name of the private function to call.
 * @param args optional arguments to pass to the private function.
 * @return the result of the private function call as a nullable type, or null if the function does
 * not exist or returns null.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any, R> T.callPrivateFuncWithResult(name: String, vararg args: Any?): R? =
    callPrivateFunc(name, *args) as? R

/**
 * Calls any private function not returning a value with the specified name on the receiver object,
 * optionally passing arguments.
 *
 * @param T the type of the receiver object.
 * @param name the name of the private function to call.
 * @param args optional arguments to pass to the private function.
 */
inline fun <reified T : Any> T.callPrivateFunc(name: String, vararg args: Any?): Any? =
    T::class.declaredMemberFunctions
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.call(this, *args)

/**
 * Retrieves the value of a private property with the specified name from the receiver object.
 *
 * @param T the type of the receiver object.
 * @param R the type of the property expected.
 * @param name the name of the private property to retrieve.
 * @return the value of the private property as a nullable type, or null if the property does not
 * exist.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any, R> T.getPrivateProperty(name: String): R? =
    T::class.memberProperties
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.get(this) as? R

/**
 * Sets the value of a private property with the specified name on the receiver object.
 *
 * @param T the type of the receiver object.
 * @param R the type of the value to set.
 * @param name the name of the private property to set.
 * @param value the value to set for the private property, which can be null.
 * @throws UnsupportedOperationException if the property is not mutable or does not exist.
 * @throws IllegalAccessException if the property cannot be accessed.
 * @throws IllegalArgumentException if the value is not compatible with the property type.
 * @throws SecurityException if the property cannot be accessed due to security restrictions.
 */
inline fun <reified T : Any, R> T.setPrivateProperty(name: String, value: R?) {
    val property = T::class.memberProperties.find { it.name == name }
    if (property is KMutableProperty<*>) {
        property.isAccessible = true
        property.setter.call(this, value)
    } else {
        property?.isAccessible = true
        property?.javaField?.isAccessible = true
        property?.javaField?.set(this, value)
    }
}
