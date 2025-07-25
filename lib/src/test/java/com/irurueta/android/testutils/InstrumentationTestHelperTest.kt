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

import android.view.View
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
class InstrumentationTestHelperTest {

    @LooperMode(LooperMode.Mode.INSTRUMENTATION_TEST)
    @Test
    fun pinch_runsSuccessfully() {
        val startX1 = 100
        val startY1 = 100
        val startX2 = 500
        val startY2 = 500
        val endX1 = 200
        val endY1 = 200
        val endX2 = 400
        val endY2 = 400
        InstrumentationTestHelper.pinch(
            startX1, startY1, startX2, startY2,
            endX1, endY1, endX2, endY2
        )
    }

    @LooperMode(LooperMode.Mode.INSTRUMENTATION_TEST)
    @Test
    fun drag_runsSuccessfully() {
        val startX = 100
        val startY = 100
        val endX = 500
        val endY = 500
        InstrumentationTestHelper.drag(
            startX, startY, endX, endY
        )
    }

    @LooperMode(LooperMode.Mode.INSTRUMENTATION_TEST)
    @Test
    fun doubleTap_whenView_runsSuccessfully() {
        val view = mockk<View>()
        every { view.left }.returns(0)
        every { view.right }.returns(600)
        every { view.top }.returns(0)
        every { view.bottom }.returns(600)
        InstrumentationTestHelper.doubleTap(view)
    }
}