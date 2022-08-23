# bank-account-statement
Bank Account Statement

Demo project to Filter Account Statement Report by Amount Filter and Date Filter with JWT token based Authentication


To run this project open up the CLI in the given project location and then run **mvn spring-boot:run**

Before running the project, We need to set the DB fully qualified path in application.properties
Example: spring.datasource.url=jdbc:ucanaccess:///C:/work/bank-account-statement/src/main/resources/db/accountsdb.accdb;ignoreCase=true

Sample CURL commands:
1.Authentication

  curl --location --request POST 'localhost:8080/authenticate' \
  --header 'Content-Type: application/json' \
  --data-raw '{
      "username" : "admin",
      "password": "admin"
  }'

2. Application Endpoint

  curl --location --request GET 'http://localhost:8080/api/v1/statement/3?fromAmount=700&toAmount=600&fromDate=10-02-2020&toDate=10-02-2022' \
  --header 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2MTI3NjE4NywiaWF0IjoxNjYxMjc1ODg1fQ.5gKKitkPFwH0NSp8Y801K7QHCYsieMP_S8BGwbW7gPxGobIjYMip0HS2NMHYA143LE6RDT-pc9ELua62k0tL1w'

