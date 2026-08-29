> [!CAUTION]
> 17/08/2026 - While the library is still in development. Grave changes to the API should not be a concern any longer.

# A Matrix Client SDK for Java

A client-side Java library for the [Matrix](https://matrix.org) protocol.

## Purpose

This library aims to allow authors to build any kind of Matrix program such as a bot or a desktop client, the library
handles all matters of serialization, validation and authentication.

## Feature support

| Service              | Capabilities                                | Status              |
|----------------------|---------------------------------------------|---------------------|
| Rooms                | Banning, kicking, room summary, room search | Supported           |
| Events               | Sending and reading events, `/sync`         | Partial             |
| User Data            | Profile search, profile modification        | Supported           |
| Filtering            | Creating and retrieving filters             | Supported           |
| Auth                 | OpenIDConnect handshake                     | Partial             |
| Capability Awareness | Notify consumers about user capabilities    | Not yet implemented |

## Current project status

The baseline to reach v1.0 is to implement all endpoints that are required in the specification, this means:

- Support for OAuth 2.0 _and_ Legacy API Auth.
- Be able to send and receive events seamlessly without handling raw JSON.
- Get room and server information without losing any data field while deserializing.

## Current limitations & project scope

1. This project does not handle multimedia resources, it _does_ expose the necessary Objects to handle serialization
   (RoomMessage) but consumers are required to bring their own library to pass required metadata.
2. This project does not allow for the creation or hookup of custom event types, for setUpClientPrints:
   `org.custom.type`. It _does_ recognize these events with `UnknownEvent.java` but consumers will be required to cast
   to a proper Object type.
3. Resource serialization/deserialization depends heavily on Jackson 3.0.
4. There is a limit of tolerance to wrongly created events, but in general the library will not attempt to serialize
   badly created objects. See: https://github.com/matrix-org/matrix-spec-proposals/pull/2801 on why this is the case.

## Usage

Currently, the only way to use this library is to compile it on your own using ```mvn compile```.

Set-up and usage is described in the [examples directory](examples).

### Requirements

- Java 25+

## Testing

- Tests use [WireMock](https://wiremock.org/) for HTTP stubbing and JUnit 5 as the test framework.
- Each service component has its own test file; all tests must pass to ensure the library works as intended.

To run the test suite:

```bash
./mvnw test
```

> [!NOTE]
> When using IntelliJ, JUnit might throw `IllegalAccessError`,
> fix this by either checking "Do not use --module-path option" or configuring Maven as the
> test runner.

## Dependencies

- [Jackson](https://github.com/FasterXML/jackson) required for JSON handling.
- [java-http](https://github.com/FusionAuth/java-http) required for creating a small server for callbacks.
- [Jspecify](https://jspecify.dev/) allows handling null values with ease.
- [SLF4J](https://www.slf4j.org/) required for logging.

## License

See [LICENSE](/LICENSE)