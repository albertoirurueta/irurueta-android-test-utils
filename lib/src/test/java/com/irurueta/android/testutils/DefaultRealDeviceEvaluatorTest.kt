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

import android.os.Build
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@Suppress("SameParameterValue")
class DefaultRealDeviceEvaluatorTest {

    private val evaluator = DefaultRealDeviceEvaluator()

    private val customEvaluator = object : RealDeviceEvaluator {
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

    @Test
    fun isEmulator_returnsFalse() {
        assertFalse(evaluator.isEmulator())
        assertFalse(customEvaluator.isEmulator())
    }

    @Test
    fun isRealDevice_returnsTrue() {
        assertTrue(evaluator.isRealDevice())
        assertTrue(customEvaluator.isRealDevice())
    }
}