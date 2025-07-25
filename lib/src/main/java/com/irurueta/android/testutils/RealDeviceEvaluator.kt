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

/**
 * Determines whether the instrumentation test application is being executed on a real device or an
 * emulator.
 */
interface RealDeviceEvaluator {

    /**
     * Indicates whether the test application is being executed on an android emulator.
     *
     * @return true if instrumentation test application is being executed on an emulator,
     * false otherwise.
     */
    fun isEmulator(): Boolean

    /**
     * Indicates whether the test application is being executed on a real device.
     *
     * @return true if instrumentation test application is being executed on a real device,
     * false otherwise.
     */
    fun isRealDevice(): Boolean {
        return !isEmulator()
    }
}