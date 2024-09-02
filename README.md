# Prueba-tecnica

## Descripcion
Se elaboran dos microservicios:
mcs.customer: Es un proyecto java de tipo microservicio desarrollado con el proposito de procesar informacion de clientes.

mcs.account.management: Es un proyecto java de tipo microservicio desarrollado con el proposito de procesar informacion sobre cuentas y movimientos, ademas se comunica con el mcs.customer.


## Configuración del proyecto 
Se utiliza un archivo docker-compose.yml que se encuentra en la raíz del repositorio
```
./docker-compose.yml
```

## Endpoints

## CUSTOMER

- **POST Create Cliente**
  - `http://localhost:8080/clientes`
```
 {
  "name": "Jose lema",
  "gender": "MASCULINO",
  "age": 18,
  "id": "1234567812345",
  "address": "Otavalo sn y principal",
  "phone": "098254785",
  "password":"1234",
  "status":true
}
```

- **GET Find all**
  - `http://localhost:8080/clientes`

- **GET Find by CustomerId**
  - `http://localhost:8080/clientes/{id}`

- **PUT Update**
  - `http://localhost:8080/clientes`

- **PATCH Patch**
  - `http://localhost:8080/clientes/1`

- **DELETE Delete**
  - `http://localhost:8080/clientes/1`

## Account

- **POST Create account**
  - `http://localhost:8081/cuentas`
```
{
  "account_number": "478758",
  "account_type": "AHORROS",
  "init_balance": 2000,
  "status":true,
  "customer" : {
      "customer_id":"1"
  }
}
```
- **GET Find all**
  - `http://localhost:8081/cuentas`

- **GET Find By Id**
  - `http://localhost:8081/cuentas/{id}`

- **PUT Update**
  - `http://localhost:8081/cuentas`
```
{
  "id":4,
  "account_number": "478758",
  "account_type": "AHORROS",
  "init_balance": 15000,
  "status":true,
   "customer" : {
      "customer_id":"2"
  }
}  
```
- **PATCH Patch**
  - `http://localhost:8081/cuentas/1`

- **DELETE Delete**
  - `http://localhost:8081/cuentas/2`

## Transactions

- **POST Create**
  - `http://localhost:8081/movimientos`
```
{
  "transaction_type": "CREDITO",
  "value": 2200,
  "account" : {
      "account_number":"478758"
  }
}
```
- **GET Find all**
  - `http://localhost:8081/movimientos`

- **GET Find by Id**
  - `http://localhost:8081/movimientos/1`

- **PUT Update**
  - `http://localhost:8081/movimientos`
``` 
{
  "id":2,
  "transaction_type": "CREDITO",
  "value": 2000,
  "account" : {
      "account_number":"478758"
  }
}
```
- **DELETE Delete**
  - `http://localhost:8081/movimientos/1`

## Report

- **GET Generate report by cliente id**
  - `http://localhost:8081/reportes?customer_id=1&starting_date=2024-08-23&end_date=2024-09-02`

  Query Params:
    - customer_id: 1
    - starting_date: 2024-08-23
    - end_date: 2024-09-02

