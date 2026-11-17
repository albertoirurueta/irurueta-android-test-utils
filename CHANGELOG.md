# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.3.2] - 2025-12-27

### Changed

- Minimum supported Android SDK raised from 24 to 26.
- Runtime dependency `kotlin-reflect` updated from 2.1.21 to 2.2.0.
- Build tooling updated: Kotlin 2.3.0, Android Gradle Plugin 8.13.2, Dokka 2.1.0, SonarQube plugin 7.2.2 and Maven
  publish plugin 0.35.0; MockK test dependency updated to 1.14.7.

## [1.3.1] - 2025-09-21

### Changed

- Runtime dependency `androidx.test.espresso:espresso-core` updated from 3.6.1 to 3.7.0.
- Build tooling and test dependencies updated: Kotlin 2.2.20, Android Gradle Plugin 8.13.0, SonarQube plugin 6.3.1,
  AndroidX Test core-ktx 1.7.0, AndroidX JUnit 1.3.0, Material 1.13.0 and Robolectric 4.16. No library source changes.

## [1.3.0] - 2025-07-28

### Added

- Non-extension overloads in `TestHelper.kt` taking an explicit `KClass` and instance (`c: KClass<T>, obj: T`) for
  `callPrivateFunc`, `callPrivateFuncWithResult`, `callPrivateStaticFunc`, `callPrivateStaticFuncWithResult`,
  `getPrivateProperty`, `setPrivateProperty`, `getPrivateStaticProperty` and `setPrivateStaticProperty`, so that
  private members declared in a superclass (or a class other than the receiver's reified type) can be reached.

### Changed

- The existing extension functions in `TestHelper.kt` now delegate to the new class-based overloads.

## [1.2.0] - 2025-07-27

### Added

- `InstrumentationTestHelper.tap(View)` to generate a tap touch event at the center of a view.

## [1.1.0] - 2025-07-27

### Added

- Reflection helpers for static-like members of objects and companion objects in `TestHelper.kt`:
  `callPrivateStaticFunc`, `callPrivateStaticFuncWithResult`, `getPrivateStaticProperty` and
  `setPrivateStaticProperty`.

## [1.0.0] - 2025-07-25

### Added

- Initial release of the Android test utilities library.
- `ConditionalIgnoreRule` JUnit rule with the `ConditionalIgnore` annotation and `IgnoreCondition` interface to skip
  tests conditionally.
- `RequiresEmulator` and `RequiresRealDevice` annotations to run tests only on an emulator or only on a real device,
  backed by the `RealDeviceEvaluator` interface and its `DefaultRealDeviceEvaluator` implementation.
- `InstrumentationTestHelper` with `pinch`, `drag` and `doubleTap(View)` gesture helpers for instrumentation tests.
- Reflection helpers in `TestHelper.kt` to call private functions (`callPrivateFunc`, `callPrivateFuncWithResult`) and
  read or write private properties (`getPrivateProperty`, `setPrivateProperty`).

[Unreleased]: https://github.com/albertoirurueta/irurueta-android-test-utils/compare/1.3.2...HEAD
[1.3.2]: https://github.com/albertoirurueta/irurueta-android-test-utils/compare/1.3.1...1.3.2
[1.3.1]: https://github.com/albertoirurueta/irurueta-android-test-utils/compare/1.3.0...1.3.1
[1.3.0]: https://github.com/albertoirurueta/irurueta-android-test-utils/compare/1.2.0...1.3.0
[1.2.0]: https://github.com/albertoirurueta/irurueta-android-test-utils/compare/1.1.0...1.2.0
[1.1.0]: https://github.com/albertoirurueta/irurueta-android-test-utils/compare/1.0.0...1.1.0
[1.0.0]: https://github.com/albertoirurueta/irurueta-android-test-utils/releases/tag/1.0.0
