# irurueta-android-test-utils

Test utilities for Android: reflection helpers to reach private members, a JUnit 4 rule to conditionally ignore
tests, and helpers to simulate touch gestures in instrumented tests.

[![Release](https://github.com/albertoirurueta/irurueta-android-test-utils/actions/workflows/main.yml/badge.svg)](https://github.com/albertoirurueta/irurueta-android-test-utils/actions/workflows/main.yml)
[![Develop](https://github.com/albertoirurueta/irurueta-android-test-utils/actions/workflows/develop.yml/badge.svg?branch=develop)](https://github.com/albertoirurueta/irurueta-android-test-utils/actions/workflows/develop.yml)
[![Security](https://github.com/albertoirurueta/irurueta-android-test-utils/actions/workflows/security.yml/badge.svg)](https://github.com/albertoirurueta/irurueta-android-test-utils/actions/workflows/security.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=bugs)](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=coverage)](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-test-utils&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils)

[![Maven Central](https://img.shields.io/maven-central/v/com.irurueta/irurueta-android-test-utils)](https://central.sonatype.com/artifact/com.irurueta/irurueta-android-test-utils)

## Project Status

| Item | Value |
| --- | --- |
| Language | Kotlin 2.3.0 (JVM target 17) |
| Build tool | Gradle 8.14.3 (Kotlin DSL) |
| Current development version | `1.4.0-SNAPSHOT` |
| Latest release | `1.3.3` |
| Platform | Android |
| Min / compile SDK | `minSdk` 26 (Android 8.0) / `compileSdk` 36 |
| Kotlin / AGP | Kotlin 2.3.0 / Android Gradle Plugin 8.13.2 |
| License | Apache License 2.0 |
| CI | GitHub Actions: `develop` builds (tests, Lint, SonarCloud, docs, snapshot publish), releases (Maven Central publish and GitHub Pages docs), post-release sync back into `develop` and weekly security scans |
| Quality | SonarCloud, JaCoCo coverage, Android Lint |
| Distribution | Maven Central (`com.irurueta:irurueta-android-test-utils`) |

## Documentation

- [Documentation site](https://albertoirurueta.github.io/irurueta-android-test-utils/) (Antora): installation,
  conditional ignore, reflection helpers, gesture helpers, building instructions and reference.
- [API documentation](https://albertoirurueta.github.io/irurueta-android-test-utils/api/) (Dokka).
- [SonarCloud dashboard](https://sonarcloud.io/summary/new_code?id=albertoirurueta_irurueta-android-test-utils).
- [Changelog](CHANGELOG.md).

## Overview

This library contains utilities to simplify testing in Android applications.

There are three main components:

- Inline functions defined in `TestHelper.kt` to call private functions and read or write private properties using
  Kotlin reflection. They work in local unit tests and in instrumented tests.
- `ConditionalIgnoreRule`, a JUnit 4 rule to conditionally ignore tests based on custom conditions or on whether the
  test is running on a real device or an emulator.
- `InstrumentationTestHelper`, which simplifies instrumentation tests by providing methods to simulate touch
  gestures (tap, double tap, drag and pinch).

## Installation

The library is published to Maven Central. Make sure `mavenCentral()` is among your repositories and add the
dependency to the test source sets of your module's `build.gradle.kts`.

To use the inline reflection functions in unit tests, add the following test dependency:

Latest release:

```kotlin
dependencies {
    testImplementation("com.irurueta:irurueta-android-test-utils:1.3.3")
}
```

To use all utilities in instrumentation tests, add the following Android test dependency:

Latest release:

```kotlin
dependencies {
    androidTestImplementation("com.irurueta:irurueta-android-test-utils:1.3.3")
}
```

The Installation page of the [documentation site](https://albertoirurueta.github.io/irurueta-android-test-utils/)
also covers the Groovy DSL and version catalog forms.

## Usage

All public types and functions live in the `com.irurueta.android.testutils` package.

### Reflection helpers

In unit or instrumentation tests, you can use the inline functions to access private methods and properties of
classes, for example:

```kotlin
class MyClassTest {

    @Test
    fun testPrivatePropertyAccess() {
        val myClass = MyClass()
        myClass.setPrivateProperty("privateField", "newValue")
        val value: String? = myClass.getPrivateProperty("privateField")
        assertNotNull(value)
        assertEquals("newValue", value)
    }

    @Test
    fun testPrivateFunctionCall() {
        val myClass = MyClass()
        val result: String? = myClass.callPrivateFuncWithResult("privateFunction", 1)
        assertNotNull(result)
    }
}
```

Each helper also has an explicit form taking the class and the instance, e.g.
`getPrivateProperty(MyClass::class, myClass, "privateField")`, and static variants such as
`callPrivateStaticFunc` and `getPrivateStaticProperty`.

### Gesture helpers

In instrumentation tests, you can use `InstrumentationTestHelper` to simulate touch gestures, for example:

```kotlin
class MyGestureTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MyActivity>()

    @Test
    fun testDoubleTapGesture() {
        var view: View? = null
        activityScenarioRule.scenario.onActivity { activity ->
            view = activity.findViewById(R.id.my_view)
        }

        InstrumentationTestHelper.doubleTap(view!!)
    }
}
```

`InstrumentationTestHelper` also provides `tap(view)`, `drag(fromX, fromY, toX, toY)` and `pinch(...)` using screen
coordinates.

### Conditional ignore

Finally, you can use `ConditionalIgnoreRule` to conditionally ignore tests based on custom conditions, for example:

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
     * When this condition is satisfied, the test will be ignored.
     * Implementations of IgnoreCondition must be accessible inner classes of the test class, or can
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

`ConditionalIgnoreRule` uses `DefaultRealDeviceEvaluator` to detect emulators; pass your own `RealDeviceEvaluator`
to its constructor to change that logic.

## Additional details

This library is contained within the `lib` module in source code.

### Gradle tasks

To execute all unit tests, execute:

```
./gradlew lib:test
```

To execute all instrumentation tests, first make sure you have connected a device or emulator and execute:

```
./gradlew lib:connectedAndroidTest
```

To generate API documentation (Dokka), run:

```
./gradlew lib:dokkaGenerate
```

To build the documentation site, run:

```
cd docs && npx antora antora-playbook.yml
```

To execute SonarCloud analysis, run:

```
./gradlew :lib:sonar
```

To convert the unit tests coverage exec file into an XML report:

```
java -jar jacoco-0.8.13/lib/jacococli.jar report lib/build/outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec --classfiles lib/build/tmp/kotlin-classes/debug --sourcefiles lib/src/main/java --xml lib/build/reports/coverage/test/report.xml
```

## License

This project is licensed under the Apache License 2.0. See [LICENSE](LICENSE).
