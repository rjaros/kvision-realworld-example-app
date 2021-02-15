# ![RealWorld Example App](kvision-logo.png)

[![RealWorld Frontend](https://img.shields.io/badge/realworld-frontend-%23783578.svg)](http://realworld.io)
![CI](https://github.com/rjaros/kvision-realworld-example-app/workflows/CI/badge.svg)

> ### [KVision](https://kvision.io) codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.

### [Demo](https://rjaros.github.io/kvision-realworld-example-app/)&nbsp;&nbsp;&nbsp;&nbsp;[RealWorld](https://github.com/gothinkster/realworld)

This codebase was created to demonstrate a fully fledged fullstack application built with [KVision](https://kvision.io) including CRUD operations, authentication, routing, pagination, and more.

We've gone to great lengths to adhere to the [KVision](https://kvision.io) community styleguides & best practices.

For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.

# How it works

Created with [KVision](https://kvision.io) and written in pure Kotlin/JS.

This project is using [Redux Kotlin](https://reduxkotlin.org/) KVision module for state management.

All asynchronous API calls are wrapped with [Kotlin coroutines](https://github.com/Kotlin/kotlinx.coroutines) suspending 
functions and executed with KVision `RestClient` component. API calls are fully type-safe and automatic
serialization/deserialization is done with [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) library.

Every part of HTML layout is created with Kotlin language using KVision DSL.

External [marked](https://www.npmjs.com/package/marked) JS library is used for parsing markdown.

# Getting started

Make sure you have [JDK 8](https://openjdk.java.net/) or higher installed. Check other requirements of KVision [here](https://kvision.gitbook.io/kvision-guide/part-1-fundamentals/setting-up).

The project is build with Gradle Wrapper. Run Gradle build with `./gradlew` or `gradlew.bat` command.

### Running
* `./gradlew -t run` - Starts a webpack dev server on port 3000. Open http://localhost:3000 in a browser.

### Packaging
* `./gradlew zip` - Packages a minified production version in a zip archive with all required files into `build/libs/*.zip`.

### Testing
* `./gradlew test` - Run unit tests defined in `src/test/kotlin` source files. Test reports are generated into `build/reports/tests/test`.
