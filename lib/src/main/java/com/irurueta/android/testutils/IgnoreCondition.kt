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
 * Interface to be implemented by conditions provided
 * in [ConditionalIgnore] annotations.
 */
interface IgnoreCondition {

    /**
     * Indicates whether ignore condition is satisfied or not.
     * When ignore condition is satisfied on an annotated test method, such
     * method is ignored.
     *
     * @return true if test method must be ignored, false otherwise.
     */
    val isSatisfied: Boolean
}