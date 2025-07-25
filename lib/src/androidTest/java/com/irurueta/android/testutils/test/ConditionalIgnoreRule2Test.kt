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

package com.irurueta.android.testutils.test

import android.os.Build
import androidx.test.ext.junit.rules.activityScenarioRule
import com.irurueta.android.testutils.ConditionalIgnore
import com.irurueta.android.testutils.ConditionalIgnoreRule
import com.irurueta.android.testutils.IgnoreCondition
import com.irurueta.android.testutils.RealDeviceEvaluator
import com.irurueta.android.testutils.RequiresEmulator
import com.irurueta.android.testutils.RequiresRealDevice
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConditionalIgnoreRule2Test {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<ConditionalIgnoreRuleActivity>()

    // conditional rule with custom real device evaluator
    @get:Rule
    val conditionalIgnoreRule = ConditionalIgnoreRule(CustomRealDeviceEvaluator())

    private var activity: ConditionalIgnoreRuleActivity? = null

    @Before
    fun setUp() {
        activityScenarioRule.scenario.onActivity { activity ->
            this.activity = activity
        }
    }

    @After
    fun tearDown() {
        activity = null
    }

    @RequiresRealDevice
    @Test
    fun testRequiresRealDeviceAnnotation() {
        assertTrue("This test requires a real device to run", true)
    }

    @RequiresEmulator
    @Test
    fun testRequiresEmulatorAnnotation() {
        // This test is just a placeholder to demonstrate how to use the RequiresEmulator annotation
        // In a real scenario, you would implement the test logic here
        assertTrue("This test requires an emulator to run", true)
    }

    @ConditionalIgnore(condition = SatisfiedCondition::class)
    @Test
    fun testConditionalIgnore_whenConditionIsSatisfied_testIsIgnored() {
        // This test is just a placeholder to demonstrate how to use the ConditionalIgnore annotation
        // In a real scenario, you would implement the test logic here
        // Assert below will not be executed because the test is ignored, otherwise it would fail
        assertFalse("This test will be conditionally ignored", true)
    }

    @ConditionalIgnore(condition = UnsatisfiedCondition::class)
    @Test
    fun testConditionalIgnore_whenConditionIsNotSatisfied_testRuns() {
        // This test is just a placeholder to demonstrate how to use the ConditionalIgnore annotation
        // In a real scenario, you would implement the test logic here
        assertTrue("This test runs because the condition is not satisfied", true)
    }

    /**
     * Condition that is always satisfied, used for testing purposes.
     * When condition is satisfied, the test will be ignored.
     */
    inner class SatisfiedCondition : IgnoreCondition {
        override val isSatisfied: Boolean
            get() = true

    }

    /**
     * Condition that is never satisfied, used for testing purposes.
     * When condition is not satisfied, the test will run.
     */
    inner class UnsatisfiedCondition : IgnoreCondition {
        override val isSatisfied: Boolean
            get() = false
    }

    private class CustomRealDeviceEvaluator : RealDeviceEvaluator {
        override fun isEmulator(): Boolean {
            // test example to define a custom evaluator using different properties
            val brand: String? = Build.BRAND
            val device: String? = Build.DEVICE
            val fingerprint: String? = Build.FINGERPRINT
            val hardware: String? = Build.HARDWARE
            val model: String? = Build.MODEL
            val manufacturer: String? = Build.MANUFACTURER
            val product: String? = Build.PRODUCT

            return ((brand?.startsWith("generic") ?: false)
                    && (device?.startsWith("generic") ?: false)
                    || (fingerprint?.startsWith("generic") ?: false)
                    || (fingerprint?.startsWith("unknown") ?: false)
                    || (hardware?.contains("goldfish") ?: false)
                    || (hardware?.contains("ranchu") ?: false)
                    || (model?.contains("google_sdk") ?: false)
                    || (model?.contains("Emulator") ?: false)
                    || (model?.contains("Android SDK built for x86") ?: false)
                    || (manufacturer?.contains("Genymotion") ?: false)
                    || (product?.contains("sdk_google") ?: false)
                    || (product?.contains("google_sdk") ?: false)
                    || (product?.contains("sdk") ?: false)
                    || (product?.contains("sdk_x86") ?: false)
                    || (product?.contains("sdk_gphone64_arm64") ?: false)
                    || (product?.contains("vbox86p") ?: false)
                    || (product?.contains("emulator") ?: false)
                    || (product?.contains("simulator")) ?: false)
        }
    }
}