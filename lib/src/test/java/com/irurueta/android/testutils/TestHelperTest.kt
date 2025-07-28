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

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

@Suppress("unused")
class TestHelperTest {

    @After
    fun tearDown() {
        TestObject.reset()
    }

    @Test
    fun callPrivateFuncWithResult_whenNoExtensionAndNoParameters_returnsExpectedResult() {
        val testClass = TestClass()

        assertFalse(testClass.executed)

        val result: String? = callPrivateFuncWithResult(
            TestClass::class,
            testClass,
            "privateMethodWithoutParametersAndWithResult"
        )
        assertEquals(RESULT, result)

        assertTrue(testClass.executed)
    }

    @Test
    fun callPrivateFuncWithResult_whenExtensionAndNoParameters_returnsExpectedResult() {
        val testClass = TestClass()

        assertFalse(testClass.executed)

        val result: String? = testClass.callPrivateFuncWithResult(
            "privateMethodWithoutParametersAndWithResult"
        )
        assertEquals(RESULT, result)

        assertTrue(testClass.executed)
    }

    @Test
    fun callPrivateFuncWithResult_whenNoExtensionAndParameter_returnsExpectedResult() {
        val testClass = TestClass()

        assertFalse(testClass.executed)
        assertNull(testClass.parameter)

        val result: String? = callPrivateFuncWithResult(
            TestClass::class,
            testClass,
            "privateMethodWithParameterAndWithResult", PARAMETER
        )

        assertEquals(RESULT, result)
        assertTrue(testClass.executed)
        assertEquals(PARAMETER, testClass.parameter)
    }

    @Test
    fun callPrivateFuncWithResult_whenExtensionAndParameter_returnsExpectedResult() {
        val testClass = TestClass()

        assertFalse(testClass.executed)
        assertNull(testClass.parameter)

        val result: String? = testClass.callPrivateFuncWithResult(
            "privateMethodWithParameterAndWithResult", PARAMETER
        )

        assertEquals(RESULT, result)
        assertTrue(testClass.executed)
        assertEquals(PARAMETER, testClass.parameter)
    }

    @Test
    fun callPrivateFunc_whenNoExtensionNoParameters_executesPrivateMethod() {
        val testClass = TestClass()

        assertFalse(testClass.executed)

        callPrivateFunc(TestClass::class, testClass, "privateMethodWithoutParameters")

        assertTrue(testClass.executed)
    }

    @Test
    fun callPrivateFunc_whenExtensionAndNoParameters_executesPrivateMethod() {
        val testClass = TestClass()

        assertFalse(testClass.executed)

        testClass.callPrivateFunc("privateMethodWithoutParameters")

        assertTrue(testClass.executed)
    }

    @Test
    fun callPrivateFunc_whenNoExtensionParameter_executesPrivateMethod() {
        val testClass = TestClass()

        assertFalse(testClass.executed)
        assertNull(testClass.parameter)

        callPrivateFunc(TestClass::class, testClass, "privateMethodWithParameter", PARAMETER)

        assertTrue(testClass.executed)
        assertEquals(PARAMETER, testClass.parameter)
    }

    @Test
    fun callPrivateFunc_whenExtensionParameter_executesPrivateMethod() {
        val testClass = TestClass()

        assertFalse(testClass.executed)
        assertNull(testClass.parameter)

        testClass.callPrivateFunc("privateMethodWithParameter", PARAMETER)

        assertTrue(testClass.executed)
        assertEquals(PARAMETER, testClass.parameter)
    }

    @Test
    fun callPrivateStaticFuncWithResult_whenNoExtensionAndNoParameters_returnsExpectedResult() {
        val testClass = TestClass()

        assertFalse(testClass.executed)

        val result: String? = callPrivateStaticFuncWithResult(
            TestClass::class, testClass, "privateMethodWithoutParametersAndWithResult"
        )
        assertEquals(RESULT, result)

        assertTrue(testClass.executed)
    }

    @Test
    fun callPrivateStaticFuncWithResult_whenExtensionAndNoParameters_returnsExpectedResult() {
        assertFalse(TestObject.executed)

        val result: String? = TestObject.callPrivateStaticFuncWithResult(
            "privateMethodWithoutParametersAndWithResult"
        )
        assertEquals(RESULT, result)

        assertTrue(TestObject.executed)
    }

    @Test
    fun callPrivateStaticFuncWithResult_whenNoExtensionAndParameter_returnsExpectedResult() {
        val testClass = TestClass()

        assertFalse(testClass.executed)
        assertNull(testClass.parameter)

        val result: String? = callPrivateStaticFuncWithResult(
            TestClass::class, testClass, "privateMethodWithParameterAndWithResult", PARAMETER
        )

        assertEquals(RESULT, result)
        assertTrue(testClass.executed)
        assertEquals(PARAMETER, testClass.parameter)
    }

    @Test
    fun callPrivateStaticFuncWithResult_whenExtensionAndParameter_returnsExpectedResult() {
        assertFalse(TestObject.executed)
        assertNull(TestObject.parameter)

        val result: String? = TestObject.callPrivateStaticFuncWithResult(
            "privateMethodWithParameterAndWithResult", PARAMETER
        )

        assertEquals(RESULT, result)
        assertTrue(TestObject.executed)
        assertEquals(PARAMETER, TestObject.parameter)
    }

    @Test
    fun callPrivateStaticFunc_whenNoExtensionAndNoParameters_executesPrivateMethod() {
        val testClass = TestClass()

        assertFalse(testClass.executed)

        callPrivateStaticFunc(TestClass::class, testClass, "privateMethodWithoutParameters")

        assertTrue(testClass.executed)
    }

    @Test
    fun callPrivateStaticFunc_whenExtensionAndNoParameters_executesPrivateMethod() {
        assertFalse(TestObject.executed)

        TestObject.callPrivateStaticFunc("privateMethodWithoutParameters")

        assertTrue(TestObject.executed)
    }

    @Test
    fun callPrivateStaticFunc_whenNoExtensionAndParameter_executesPrivateMethod() {
        val testClass = TestClass()

        assertFalse(testClass.executed)
        assertNull(testClass.parameter)

        callPrivateStaticFunc(TestClass::class, testClass, "privateMethodWithParameter", PARAMETER)

        assertTrue(testClass.executed)
        assertEquals(PARAMETER, testClass.parameter)
    }

    @Test
    fun callPrivateStaticFunc_whenExtensionAndParameter_executesPrivateMethod() {
        assertFalse(TestObject.executed)
        assertNull(TestObject.parameter)

        TestObject.callPrivateStaticFunc("privateMethodWithParameter", PARAMETER)

        assertTrue(TestObject.executed)
        assertEquals(PARAMETER, TestObject.parameter)
    }

    @Test
    fun getPrivateProperty_whenNoExtension_returnsExpectedValue() {
        val testClass = TestClass()

        val result: String? = getPrivateProperty(TestClass::class, testClass, "privateProperty")
        assertEquals(PRIVATE_PROPERTY_VALUE, result)
    }

    @Test
    fun getPrivateProperty_whenExtension_returnsExpectedValue() {
        val testClass = TestClass()

        val result: String? = testClass.getPrivateProperty("privateProperty")
        assertEquals(PRIVATE_PROPERTY_VALUE, result)
    }

    @Test
    fun getPrivateStaticProperty_whenNoExtension_returnsExpectedValue() {
        val testClass = TestClass()

        val result: String? =
            getPrivateStaticProperty(TestClass::class, testClass, "privateProperty")
        assertEquals(PRIVATE_PROPERTY_VALUE, result)
    }

    @Test
    fun getPrivateStaticProperty_whenExtension_returnsExpectedValue() {
        val result: String? = TestObject.getPrivateStaticProperty("privateProperty")
        assertEquals(PRIVATE_PROPERTY_VALUE, result)
    }

    @Test
    fun setPrivateProperty_whenNoExtensionAndMutableProperty_updatesProperty() {
        val testClass = TestClass()

        // check initial value
        val propertyName = "privateProperty"
        val result1: String? = getPrivateProperty(TestClass::class, testClass, propertyName)
        assertEquals(PRIVATE_PROPERTY_VALUE, result1)

        // set value
        setPrivateProperty(TestClass::class, testClass, propertyName, NEW_PRIVATE_PROPERTY_VALUE)

        // check
        val result2: String? = getPrivateProperty(TestClass::class, testClass, propertyName)
        assertEquals(NEW_PRIVATE_PROPERTY_VALUE, result2)

        // set null value
        setPrivateProperty(TestClass::class, testClass, propertyName, null)

        // check
        val result3: String? = getPrivateProperty(TestClass::class, testClass, propertyName)
        assertNull(result3)
    }

    @Test
    fun setPrivateProperty_whenExtensionAndMutableProperty_updatesProperty() {
        val testClass = TestClass()

        // check initial value
        val propertyName = "privateProperty"
        val result1: String? = testClass.getPrivateProperty(propertyName)
        assertEquals(PRIVATE_PROPERTY_VALUE, result1)

        // set value
        testClass.setPrivateProperty(propertyName, NEW_PRIVATE_PROPERTY_VALUE)

        // check
        val result2: String? = testClass.getPrivateProperty(propertyName)
        assertEquals(NEW_PRIVATE_PROPERTY_VALUE, result2)

        // set null value
        testClass.setPrivateProperty(propertyName, null)

        // check
        val result3: String? = testClass.getPrivateProperty(propertyName)
        assertNull(result3)
    }

    @Test
    fun setPrivateStaticProperty_whenNoExtensionAndMutableProperty_updatesProperty() {
        val testClass = TestClass()

        // check initial value
        val propertyName = "privateProperty"
        val result1: String? = getPrivateStaticProperty(TestClass::class, testClass, propertyName)
        assertEquals(PRIVATE_PROPERTY_VALUE, result1)

        // set value
        setPrivateStaticProperty(
            TestClass::class,
            testClass,
            propertyName,
            NEW_PRIVATE_PROPERTY_VALUE
        )

        // check
        val result2: String? = getPrivateStaticProperty(TestClass::class, testClass, propertyName)
        assertEquals(NEW_PRIVATE_PROPERTY_VALUE, result2)

        // set null value
        setPrivateStaticProperty(TestClass::class, testClass, propertyName, null)

        // check
        val result3: String? = getPrivateStaticProperty(TestClass::class, testClass, propertyName)
        assertNull(result3)
    }

    @Test
    fun setPrivateStaticProperty_whenExtensionAndMutableProperty_updatesProperty() {
        // check initial value
        val propertyName = "privateProperty"
        val result1: String? = TestObject.getPrivateStaticProperty(propertyName)
        assertEquals(PRIVATE_PROPERTY_VALUE, result1)

        // set value
        TestObject.setPrivateStaticProperty(propertyName, NEW_PRIVATE_PROPERTY_VALUE)

        // check
        val result2: String? = TestObject.getPrivateStaticProperty(propertyName)
        assertEquals(NEW_PRIVATE_PROPERTY_VALUE, result2)

        // set null value
        TestObject.setPrivateStaticProperty(propertyName, null)

        // check
        val result3: String? = TestObject.getPrivateStaticProperty(propertyName)
        assertNull(result3)
    }

    @Test
    fun setPrivateProperty_whenNoExtensionAndImmutableProperty_updatesProperty() {
        val testClass = TestClass()

        // check initial value
        val propertyName = "immutableProperty"
        val result1: String? = getPrivateProperty(TestClass::class, testClass, propertyName)
        assertEquals(PRIVATE_PROPERTY_VALUE, result1)

        // set value
        setPrivateProperty(TestClass::class, testClass, propertyName, NEW_PRIVATE_PROPERTY_VALUE)

        // check
        val result2: String? = getPrivateProperty(TestClass::class, testClass, propertyName)
        assertEquals(NEW_PRIVATE_PROPERTY_VALUE, result2)
    }

    @Test
    fun setPrivateProperty_whenExtensionAndImmutableProperty_updatesProperty() {
        val testClass = TestClass()

        // check initial value
        val propertyName = "immutableProperty"
        val result1: String? = testClass.getPrivateProperty(propertyName)
        assertEquals(PRIVATE_PROPERTY_VALUE, result1)

        // set value
        testClass.setPrivateProperty(propertyName, NEW_PRIVATE_PROPERTY_VALUE)

        // check
        val result2: String? = testClass.getPrivateProperty(propertyName)
        assertEquals(NEW_PRIVATE_PROPERTY_VALUE, result2)
    }

    @Test
    fun setPrivateStaticProperty_whenNoExtensionAndImmutableProperty_updatesProperty() {
        val testClass = TestClass()

        // check initial value
        val propertyName = "immutableProperty"
        val result1: String? = getPrivateStaticProperty(TestClass::class, testClass, propertyName)
        assertEquals(PRIVATE_PROPERTY_VALUE, result1)

        // set value
        setPrivateStaticProperty(TestClass::class, testClass, propertyName, NEW_PRIVATE_PROPERTY_VALUE)

        // check
        val result2: String? = getPrivateStaticProperty(TestClass::class, testClass, propertyName)
        assertEquals(NEW_PRIVATE_PROPERTY_VALUE, result2)
    }

    @Test
    fun setPrivateStaticProperty_whenExtensionAndImmutableProperty_throwsIllegalAccessException() {
        // check initial value
        val propertyName = "immutableProperty"
        val result1: String? = TestObject.getPrivateStaticProperty(propertyName)
        assertEquals(PRIVATE_PROPERTY_VALUE, result1)

        // set value
        assertThrows(IllegalAccessException::class.java) {
            TestObject.setPrivateStaticProperty(propertyName, NEW_PRIVATE_PROPERTY_VALUE)
        }
    }

    @Suppress("unused")
    private class TestClass {

        var executed = false

        var parameter: String? = null

        private fun privateMethodWithoutParameters() {
            executed = true
        }

        private fun privateMethodWithParameter(param: String) {
            parameter = param
            executed = true
        }

        private fun privateMethodWithoutParametersAndWithResult(): String {
            executed = true
            return RESULT
        }

        private fun privateMethodWithParameterAndWithResult(param: String): String {
            parameter = param
            executed = true
            return RESULT
        }

        private var privateProperty: String? = PRIVATE_PROPERTY_VALUE

        private val immutableProperty: String = PRIVATE_PROPERTY_VALUE
    }

    private object TestObject {

        var executed = false

        var parameter: String? = null

        private fun privateMethodWithoutParameters() {
            executed = true
        }

        private fun privateMethodWithParameter(param: String) {
            parameter = param
            executed = true
        }

        private fun privateMethodWithoutParametersAndWithResult(): String {
            executed = true
            return RESULT
        }

        private fun privateMethodWithParameterAndWithResult(param: String): String {
            parameter = param
            executed = true
            return RESULT
        }

        private var privateProperty: String? = PRIVATE_PROPERTY_VALUE

        private val immutableProperty: String = PRIVATE_PROPERTY_VALUE

        fun reset() {
            executed = false
            parameter = null
            privateProperty = PRIVATE_PROPERTY_VALUE
        }
    }

    companion object {
        const val PARAMETER = "parameter"

        const val RESULT = "Hello, World!"

        const val PRIVATE_PROPERTY_VALUE = "Private Property Value"

        const val NEW_PRIVATE_PROPERTY_VALUE = "New Private Property Value"
    }
}