## Credit Limit Application ##

Identity number, name-surname, monthly income and telephone information are obtained from the user.
The credit score service, which is assumed to have been written before, is entered with the number of the person concerned.
Credit score is taken and credit result is shown to the user according to the following rules.
(There may be two options as Approval or Rejection.)

#### Notice:

- You should use Lombok plugin on your IDE, to build the project

#### SWAGGER URL

    http://localhost:8080/swagger-ui.html


#### Api Documentation

* Create Customer
    ```
    POST http://localhost:8080/customers
        {
           "fistName": "string",
           "id": 0,
           "lastName": "string",
           "phoneNumber": "string",
           "salary": 0
        }
    ```

* Get all Customers
    ```
    GET http://localhost:8080/customers
    ```

* Get Customer By id
    ```
    GET http://localhost:8080/customers/{{id}}
    ```
*  Delete Customer By id
    ```
    DELETE http://localhost:8080/customers/{{id}}
    ```

* Update Customer
    ```
    PUT http://localhost:8080/customers
    ```
  
* Get Customer Count
    ```
    GET http://localhost:8080/customers/count
    ```

* Get Check Credit Limit
    ```
    POST http://localhost:8080/customers/checkCreditLimit
    ```

* Credit Application Result
    ```
    GET http://localhost:8080/credit-application-result/{{id}}
    ```
