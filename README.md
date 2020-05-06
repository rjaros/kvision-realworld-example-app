# <img src="https://raw.githubusercontent.com/rjaros/kvision-realworld-example-app/master/kvision-logo.png" width="130" height="100"> Realworld - KVision example app

[![RealWorld Frontend](https://img.shields.io/badge/realworld-frontend-%23783578.svg)](http://realworld.io)

> ### [KVision](https://github.com/rjaros/kvision) codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.

### [Demo](https://github.com/gothinkster/realworld)&nbsp;&nbsp;&nbsp;&nbsp;[RealWorld](https://github.com/gothinkster/realworld)

This codebase was created to demonstrate a fully fledged fullstack application built with [KVision](https://github.com/rjaros/kvision) including CRUD operations, authentication, routing, pagination, and more.

We've gone to great lengths to adhere to the [KVision](https://github.com/rjaros/kvision) community styleguides & best practices.

For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.


# How it works

> Describe the general architecture of your app here

# Getting started

## Gradle Tasks
Whenever you want to produce a minified "production" version of your code pass in `-Pproduction=true` or `-Pprod=true` to your build command.
### Resource Processing
* generatePotFile - Generates a `src/main/resources/i18n/messages.pot` translation template file.
### Running
* run - Starts a webpack dev server on port 3000.
### Packaging
* browserWebpack - Bundles the compiled js files into `build/distributions`
* zip - Packages a zip archive with all required files into `build/libs/*.zip`