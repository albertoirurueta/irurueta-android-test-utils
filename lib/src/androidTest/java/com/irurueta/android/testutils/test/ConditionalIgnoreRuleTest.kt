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

import androidx.test.ext.junit.rules.activityScenarioRule
import com.irurueta.android.testutils.ConditionalIgnore
import com.irurueta.android.testutils.ConditionalIgnoreRule
import com.irurueta.android.testutils.IgnoreCondition
import com.irurueta.android.testutils.RequiresEmulator
import com.irurueta.android.testutils.RequiresRealDevice
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConditionalIgnoreRuleTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<ConditionalIgnoreRuleActivity>()

    @get:Rule
    val conditionalIgnoreRule = ConditionalIgnoreRule()

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
}