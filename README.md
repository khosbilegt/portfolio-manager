# portfolio-manager

This project is the backend service being used for the website deployed at <https://khosbilegt.dev> (repository [here](https://github.com/khosbilegt/portfolio-web)), built using Quarkus.

This project is currently under development...

## Components
The project consists of the following components at the moment:
- Page Service: REST API for managing pages, tags and blocks. 
  - Tag: Tags are the metadata for the blogs and pages. They are used to categorize the blogs and pages.
  - Page: Pages are the main content of the website. They are used to display the main content of the website, and Tags
  are used to separate Projects, Blog posts and various other requirements.
  - Block: Encompasses any blocks of information that may be changed and may be freely fetched to populate 
  the front-end components. This has no innate connection to the UI and the components must be created manually and 
  only serves as the data.
- User Service: JWT-based User Registration and Authentication for RBAC for Admin APIs and commenting on blogs.

## Development

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/portfolio-manager-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
