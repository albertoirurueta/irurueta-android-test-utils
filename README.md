# irurueta-android-test-utils
Test utilities for Android

[![Build Status](https://github.com/albertoirurueta/irurueta-android-test-utils/actions/workflows/main.yml/badge.svg)](https://github.com/albertoirurueta/irurueta-android-test-utils/actions)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=code_smells)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-test-utils)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=coverage)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-test-utils)

[![Duplicated lines](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-test-utils)
[![Lines of code](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=ncloc)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-test-utils)

[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-test-utils)
[![Quality gate](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=alert_status)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-test-utils)
[![Reliability](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-test-utils)

[![Security](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=security_rating)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-test-utils)
[![Technical debt](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=sqale_index)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-test-utils)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-test-utils)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.irurueta/irurueta-android-test-utils/badge.svg)](https://search.maven.org/artifact/com.irurueta/irurueta-android-test-utils/1.0.0/aar)

[API Documentation](http://albertoirurueta.github.io/irurueta-android-test-utils)

## Overview

This library contains utilities to simplify testing in Android applications.

There are three main components:

- Inline functions defined in TestHelpers.kt to access private methods and properties using 
  reflection.
- ConditionalIgnoreRule to conditionally ignore tests based on custom conditions or whether the
  test is running on real devices or emulators.
- InstrumentationTestHelper to simplify instrumentation tests by providing methods to simulate some 
  touch gestures.

## Usage

Add the following test dependency to your project to une inline functions in unit tests:

```
testImplementation 'com.irurueta:irurueta-android-test-utils:1.2.0'
```

Add the following android test dependency to your project to une all utilities in instrumentation 
tests:

```
androidTestImplementation 'com.irurueta:irurueta-android-test-utils:1.2.0'
```

In unit or instrumentation tests, you can use the inline functions to access private methods and
properties of classes, for example:

```kotlin
class MyClassTest {

    @Test
    fun testPrivatePropertyAccess() {
        val myClass = MyClass()
        myClass.setPrivateProperty("privateField", "newValue")
        val value = myClass.getPrivateProperty<String>("privateField")
        assertNotNull(value)
        assertEquals("newValue", value)
    }
}
```

In instrumentation tests, you can use the `InstrumentationTestHelper` to simulate touch gestures,
for example:

```kotlin
class MyActivityTest : ActivityTestRule<MyActivity>(MyActivity::class.java) {
    @Test
    fun testSwipeGesture() {
        val view = activity.findViewById<View>(R.id.my_view)
        InstrumentationTestHelper.doubleTap(view)
    }
}
```

Finally, you can use the `ConditionalIgnoreRule` to conditionally ignore tests based on custom
conditions, for example:

```kotlin
class MyTest {
    @get:Rule
    val conditionalIgnoreRule = ConditionalIgnoreRule()

    @RequiresRealDevice
    @Test
    fun test1() {
        // This test will be ignored if running on an emulator
    }

    @RequiresEmulator
    @Test
    fun test2() {
        // This test will only run on an emulator
    }

    @ConditionalIgnore(condition = CustomCondition::class)
    @Test
    fun test3() {
        // This test will be ignored if CustomCondition is satisfied
    }

    /**
     * Implementation of a custom condition providing custom logic to determine if a test
     * should be ignored.
     * This condition is satisfied, the test will be be ignored.
     * Implementations IgnoreCondition must be accessible inner classes of the test class, or can
     * also be defined as top-level classes shared between multiple tests.
     */
    inner class CustomCondition : IgnoreCondition {
        override val isSatisfied: Boolean
            get() {
                // Custom logic to determine if the test should be ignored
                return false // Change this to true to ignore the test
            }
    }
}
```