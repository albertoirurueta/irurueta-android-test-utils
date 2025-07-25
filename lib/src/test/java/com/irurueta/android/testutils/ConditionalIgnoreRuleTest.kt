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

import com.irurueta.android.testutils.ConditionalIgnoreRule.IgnoreStatement
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.*
import org.junit.AssumptionViolatedException
import org.junit.Test
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement

class ConditionalIgnoreRuleTest {

    @After
    fun afterTest() {
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun apply_whenNoMethod_returnsBaseStatement() {
        val statement = mockk<Statement>()
        val rule = ConditionalIgnoreRule()
        val result = rule.apply(statement, null, null)

        assertSame(statement, result)
    }

    @Test
    fun apply_whenMethodHasNoAnnotation_returnsBaseStatement() {
        val statement = mockk<Statement>()
        val method = mockk<FrameworkMethod>()
        every { method.getAnnotation(eq(ConditionalIgnore::class.java)) }.returns(null)
        every { method.getAnnotation(eq(RequiresEmulator::class.java)) }.returns(null)
        every { method.getAnnotation(eq(RequiresRealDevice::class.java)) }.returns(null)

        val rule = ConditionalIgnoreRule()
        val result = rule.apply(statement, method, null)

        assertSame(statement, result)
    }

    @Test
    fun apply_whenMethodHasConditionalIgnoreAnnotationWithInnerClassIgnoreConditionNotInsideTarget_throwsIllegalArgumentException() {
        val statement = mockk<Statement>()
        val method = mockk<FrameworkMethod>()
        val conditionalIgnore =
            ConditionalIgnore(condition = SomeClass.CustomIgnoreCondition::class)
        every { method.getAnnotation(eq(ConditionalIgnore::class.java)) }.returns(conditionalIgnore)
        every { method.getAnnotation(eq(RequiresEmulator::class.java)) }.returns(null)
        every { method.getAnnotation(eq(RequiresRealDevice::class.java)) }.returns(null)

        val rule = ConditionalIgnoreRule()
        assertThrows(IllegalArgumentException::class.java) { rule.apply(statement, method, this) }
    }

    @Test
    fun apply_whenMethodHasConditionalIgnoreAnnotationWithInnerClassIgnoreConditionInsideTargetAndNotSatisfied_returnsBaseStatement() {
        val statement = mockk<Statement>()
        val method = mockk<FrameworkMethod>()
        val conditionalIgnore =
            ConditionalIgnore(condition = UnsatisfiedIgnoreCondition::class)
        every { method.getAnnotation(eq(ConditionalIgnore::class.java)) }.returns(conditionalIgnore)
        every { method.getAnnotation(eq(RequiresEmulator::class.java)) }.returns(null)
        every { method.getAnnotation(eq(RequiresRealDevice::class.java)) }.returns(null)

        val rule = ConditionalIgnoreRule()
        val result = rule.apply(statement, method, this)

        assertSame(statement, result)
    }

    @Test
    fun apply_whenMethodHasConditionalIgnoreAnnotationWithInnerClassIgnoreConditionInsideTargetAndSatisfied_returnsIgnoreStatement() {
        val statement = mockk<Statement>()
        val method = mockk<FrameworkMethod>()
        val conditionalIgnore =
            ConditionalIgnore(condition = SatisfiedIgnoreCondition::class)
        every { method.getAnnotation(eq(ConditionalIgnore::class.java)) }.returns(conditionalIgnore)
        every { method.getAnnotation(eq(RequiresEmulator::class.java)) }.returns(null)
        every { method.getAnnotation(eq(RequiresRealDevice::class.java)) }.returns(null)

        val rule = ConditionalIgnoreRule()
        val result = rule.apply(statement, method, this)

        assertNotSame(statement, result)
        assertTrue(result is IgnoreStatement)
        val ignoreStatement = result as IgnoreStatement
        val ignoreCondition = ignoreStatement.condition
        assertNotNull(ignoreCondition)
        assertTrue(ignoreCondition is SatisfiedIgnoreCondition)
    }

    @Test
    fun apply_whenMethodHasRequiresEmulatorAnnotationAndAndItIsSatisfied_returnsBaseStatement() {
        val statement = mockk<Statement>()
        val method = mockk<FrameworkMethod>()
        val requiresEmulator = RequiresEmulator()
        every { method.getAnnotation(eq(ConditionalIgnore::class.java)) }.returns(null)
        every { method.getAnnotation(eq(RequiresEmulator::class.java)) }.returns(requiresEmulator)
        every { method.getAnnotation(eq(RequiresRealDevice::class.java)) }.returns(null)

        // we assume that method is executed on emulator and test will be executed (no ignore
        // statement is returned)
        val realDeviceEvaluator = mockk<RealDeviceEvaluator>()
        every { realDeviceEvaluator.isRealDevice() }.returns(false)

        val rule = ConditionalIgnoreRule(realDeviceEvaluator)
        val result = rule.apply(statement, method, null)

        assertSame(statement, result)
    }

    @Test
    fun apply_whenMethodHasRequiresEmulatorAnnotationAndItIsNotSatisfied_returnsIgnoreStatement() {
        val statement = mockk<Statement>()
        val method = mockk<FrameworkMethod>()
        val requiresEmulator = RequiresEmulator()
        every { method.getAnnotation(eq(ConditionalIgnore::class.java)) }.returns(null)
        every { method.getAnnotation(eq(RequiresEmulator::class.java)) }.returns(requiresEmulator)
        every { method.getAnnotation(eq(RequiresRealDevice::class.java)) }.returns(null)

        // we assume that method is executed on real device and test will be ignored (ignore
        // statement is returned)
        val realDeviceEvaluator = mockk<RealDeviceEvaluator>()
        every { realDeviceEvaluator.isRealDevice() }.returns(true)

        val rule = ConditionalIgnoreRule(realDeviceEvaluator)
        val result = rule.apply(statement, method, null)

        assertNotSame(statement, result)
        assertTrue(result is IgnoreStatement)
        val ignoreStatement = result as IgnoreStatement
        val ignoreCondition = ignoreStatement.condition
        assertNotNull(ignoreCondition)
        assertTrue(ignoreCondition is ConditionalIgnoreRule.NotRunningOnEmulator)
    }

    @Test
    fun apply_whenMethodHasRequiresRealDeviceAnnotationAndItIsSatisfied_returnsBaseStatement() {
        val statement = mockk<Statement>()
        val method = mockk<FrameworkMethod>()
        val requiresRealDevice = RequiresRealDevice()
        every { method.getAnnotation(eq(ConditionalIgnore::class.java)) }.returns(null)
        every { method.getAnnotation(eq(RequiresEmulator::class.java)) }.returns(null)
        every { method.getAnnotation(eq(RequiresRealDevice::class.java)) }.returns(
            requiresRealDevice
        )

        // we assume that method is executed on real device and test will be executed (no ignore
        // statement is returned)
        val realDeviceEvaluator = mockk<RealDeviceEvaluator>()
        every { realDeviceEvaluator.isEmulator() }.returns(false)

        val rule = ConditionalIgnoreRule(realDeviceEvaluator)
        val result = rule.apply(statement, method, null)

        assertSame(statement, result)
    }

    @Test
    fun apply_whenMethodHasRequiresRealDeviceAnnotationAndItIsNotSatisfied_returnsIgnoreStatement() {
        val statement = mockk<Statement>()
        val method = mockk<FrameworkMethod>()
        val requiresRealDevice = RequiresRealDevice()
        every { method.getAnnotation(eq(ConditionalIgnore::class.java)) }.returns(null)
        every { method.getAnnotation(eq(RequiresEmulator::class.java)) }.returns(null)
        every { method.getAnnotation(eq(RequiresRealDevice::class.java)) }.returns(
            requiresRealDevice
        )

        // we assume that method is executed on emulator and test will be ignore (ignore
        // statement is returned)
        val realDeviceEvaluator = mockk<RealDeviceEvaluator>()
        every { realDeviceEvaluator.isEmulator() }.returns(true)

        val rule = ConditionalIgnoreRule(realDeviceEvaluator)
        val result = rule.apply(statement, method, null)

        assertNotSame(statement, result)
        assertTrue(result is IgnoreStatement)
        val ignoreStatement = result as IgnoreStatement
        val ignoreCondition = ignoreStatement.condition
        assertNotNull(ignoreCondition)
        assertTrue(ignoreCondition is ConditionalIgnoreRule.NotRunningOnRealDevice)
    }

    @Test
    fun evaluateIgnoreStatement_whenIgnoreConditionSatisfied_executesSuccessfully() {
        val condition = mockk<IgnoreCondition>()
        every { condition.isSatisfied }.returns(true)
        val ignoreStatement = IgnoreStatement(condition)
        ignoreStatement.evaluate()
    }

    @Test
    fun evaluateIgnoreStatement_whenIgnoreConditionNotSatisfied_throwsAssumptionViolatedException() {
        val condition = mockk<IgnoreCondition>()
        every { condition.isSatisfied }.returns(false)
        val ignoreStatement = IgnoreStatement(condition)

        assertThrows(AssumptionViolatedException::class.java) {
            ignoreStatement.evaluate()
        }
    }

    private class SatisfiedIgnoreCondition : IgnoreCondition {
        override val isSatisfied: Boolean
            get() = true // always satisfied for testing purposes
    }

    private class UnsatisfiedIgnoreCondition : IgnoreCondition {
        override val isSatisfied: Boolean
            get() = false // always not satisfied for testing purposes
    }

    private class SomeClass {
        inner class CustomIgnoreCondition : IgnoreCondition {
            override val isSatisfied: Boolean
                get() = true // always satisfied for testing purposes
        }
    }
}