# ![RealWorld Example App](kvision-logo.png)

[![RealWorld Frontend](https://img.shields.io/badge/realworld-frontend-%23783578.svg)](http://realworld.io)

> ### [KVision](https://github.com/rjaros/kvision) codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.

### [Demo](https://github.com/gothinkster/realworld)&nbsp;&nbsp;&nbsp;&nbsp;[RealWorld](https://github.com/gothinkster/realworld)

This codebase was created to demonstrate a fully fledged fullstack application built with [KVision](https://github.com/rjaros/kvision) including CRUD operations, authentication, routing, pagination, and more.

We've gone to great lengths to adhere to the [KVision](https://github.com/rjaros/kvision) community styleguides & best practices.

For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.

# How it works

Created with [KVision](https://github.com/rjaros/kvision) and written in pure Kotlin/JS.

# Getting started

Make sure you have JDK 8 or higher installed.

Run Gradle build with `./gradlew` / `gradlew.bat` command.

## Gradle Tasks

### Running
* `./gradlew -t run` - Starts a webpack dev server on port 3000. Open http://localhost:3000 in a browser.

### Packaging
* `./gradlew -Pprod=true zip` - Packages a minified production version in a zip archive with all required files into `build/libs/*.zip`.

### Testing
* `./gradlew test` - Run unit tests defined in `src/test/kotlin` source files. Test reports are generated into `build/reports/tests/test`.  
