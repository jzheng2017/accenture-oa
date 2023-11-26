# High level overview of application
The application is using Spring Boot to expose an `GET` endpoint`/countries` to fetch the following data:
- Sorted list of countries by population density in descending order
- Country in Asia containing the most bordering countries of a different region

The data fetching from `https://restcountries.com/v3.1` is cached. 
This is because the data of the countries change rarely. I have set the expiry date to 1 day, after that it will be evicted and needs to be retrieved again.

Also exposed health probes (e.g. liveness and readiness). When deployed in production you can periodically check these probes to see whether the Spring Boot application is in a healthy state. If not kill the pod and start a new one. This allows the application to be resistant to unrecoverable errors.

# How to use
1. Clone the repository
2. Open it in your desired IDE
3. Run the application
4. When the application has started you can request for the desired information at `localhost:8080/countries` with a GET request. The standard port is 8080 but can easily be changed in `application.properties` with `server.port={desired port}`