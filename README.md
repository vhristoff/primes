# Prime Numbers Generator and Checker 

A very simple service with two endpoints:
- `/primes/check` - determines if the given number is a prime
- `/primes/next` - finds the smallest prime number greater than the provided number

### How to run
`./gradlew clean bootRun`

The application listens on port **8080** and the endpoints can be tested via SwaggerUI page on 
`/swagger-ui.html`

