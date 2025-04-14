# Consents API
An API for managing user consents for data usage.

## Error handling
All errorInfos are handled using the `@ControllerAdvice` annotation. This allows for centralized exception handling across all controllers in the application. The error response format is consistent following  [RFC 9457](https://www.rfc-editor.org/rfc/rfc9457.html).

