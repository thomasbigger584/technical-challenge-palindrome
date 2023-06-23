# Palindrome Technical Challenge

## Installation

- Application requires Java 17 installed on your machine. Maven is provided via the maven wrapper.

- The following command will clean, test and run the application

```
./mvnw clean test spring-boot:run
```

- Run tests on their own

```
./mvnw test
```

- Run application on its own

```
./mvnw spring-boot:run
```

- Quit out of running application:

```
CTRL+C
```

## User Guide

- Once the application starts up it will create an embedded Tomcat HTTP server with a single GET endpoint
- If you provide a valid palindrome then there will be `true` in the response
- If you provide an invalid palindrome then it will return `false` in the response
- If you do not provide any potential palindrome (blank or null string) then it will also return `true`. This is an
  assumption as it is not stated in the requirements. Nothing forwards is equaled to nothing backwards.

## Internals

- When the application starts up it will populate the cache with the palindromes stored in the datastore.
- Both the DataStore and Cache are based on abstractions to be able to change implementation to another technology.
- The cache currently only stores palindromes if they pass validation. Invalid input is not saved into the cache. This
  can change based on feedback.
- The datastore currently uses the file system, writing the file to the users home directory, in a thread-safe manor.
  This could be changed to use the database instead via the extensibility of the architecture. The file location can be
  overridden with the property `${app.fileSystem.fileLocation}`.
- When the HTTP Get request gets called it first does validation on the provided string and throws an exception if the
  string is invalid. The validation is extensible to add new types of validation by creating a new `@Service` which
  extends `ValidationService`.
- Next it checks the cache if the value exists. If it does exist then we can just return it without doing any additional
  processing. This uses Spring Cache but is extensible to use another cache like an external Redis database.
- Then we will do the processing of calculating if the provided string is a palindrome or not.
- Once we get a result we can put this to the cache and also asynchronously save it to the datastore.

## Testing

- In your terminal use curl to execute a GET endpoint:

```shell
curl  'http://localhost:8080/palindrome?value=madam'
```

- Will return:

```json
{
  "value": "madam",
  "palindrome": true
}
```

--- 

```shell
curl  'http://localhost:8080/palindrome?value=techchallenge'
```

- Will return:

```json
{
  "value": "techchallenge",
  "palindrome": false
}
```

--- 

```shell
curl  'http://localhost:8080/palindrome?value=madam123'
```

- Will return:

```json
{
  "message": "Palindrome value should not contain a number: 1"
}
```

--- 

```shell
curl  'http://localhost:8080/palindrome?value=mad%20am'
```

- Will return:

```json
{
  "message": "Palindrome value should not contain any spaces"
}
```
