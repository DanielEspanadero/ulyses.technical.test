![image](https://github.com/DanielEspanadero/ulyses.technical.test/blob/master/docs/septeo.png)

# Instructions

### What’s included in this repository?
This repository contains a Spring Boot application with the following structure:

- The domain includes three entities: **Brand**, **Vehicle**, and **Sale**.
   - A Brand can have multiple Vehicles.
   - A Sale is associated with a Vehicle, a Brand, and a date.
- The application exposes a REST API that allows basic **CRUD operations**.
- Each entity is handled through its own **Controller**, **Service**, and **Repository**.
- The application uses **Spring Data JPA** to interact with the database.
- It uses an **H2 in-memory database**, preloaded with sample data, and is configured to run on port `8080`.

---

### What do you need to implement?

1. **Implement Spring Security:**
   - All **GET** endpoints must remain public (no authentication required).
   - All **POST**, **PUT**, and **DELETE** endpoints must require authentication and authorization.

2. **Add the following endpoints to the `SalesController`:**
   - `GET /api/sales/brands/{brandId}` → Return all sales for a given brand.
   - `GET /api/sales/vehicles/{vehicleId}` → Return all sales for a given vehicle.
   - Modify the existing `GET /api/sales` endpoint and add pagination to it.
     - Each response must return **10 items per page**.
     - The **page number** must be accepted as an optional query parameter.
     - If the page number is not provided, the API should return the **first page by default**.

3. **Implement a new endpoint in `SalesController`:**
   - `GET /api/sales/vehicles/bestSelling` → Return the **top 5 best-selling vehicles**.
   - Allow optional filtering by **start date** and **end date**.
   - You must retrieve all sales from the database (as currently implemented).
      - **Do not use** SQL-level sorting, `Collections.sort()`, or any built-in sort utilities.
      - Implement your **own sorting logic** using Java code, considering performance (imagine millions of records).
      - You may create additional models or helper classes as needed.

4. **Create a custom filter or interceptor using Spring:**
   - It must **log all incoming API requests and outgoing responses** to a file.
   - The log must include:
      - Request date and time
      - HTTP method
      - Request URL
      - Response status code
      - Processing time (in milliseconds)

5. **Implement a caching system for the GET endpoints of Brand**
   - Check if the data exists in the cache and has not expired.
   - If it does not exist or has expired, call the supplier.
   - Update the cache with the new value.
   - Handle concurrency properly.

---

### Additional Information

- You must use **Java 21**.
- **Do not use any external libraries** — all code must be implemented by you.
- If there’s something you’re not sure how to do, don’t worry — just give it your best effort.
- We will carefully review your code and provide feedback as soon as possible.
 

---

# Application Endpoints

## Brand Endpoints

### Get all brands
- **GET** `/api/brands`
- No authentication required

### Get a brand by ID
- **GET** `/api/brands/1`
- No authentication required

### Create a new brand
- **POST** `/api/brands`
- Requires authentication
- Request body:
```json
{
  "name": "Toyota"
}
```

### Update an existing brand
- **PUT** `/api/brands/1`
- Requires authentication
- Request body:
```json
{
  "name": "Toyota Motors"
}
```

### Delete a brand
- **DELETE** `/api/brands/1`
- Requires authentication

## Vehicle Endpoints

### Get all vehicles
- **GET** `/api/vehicles`
- No authentication required

### Get a vehicle by ID
- **GET** `/api/vehicles/1`
- No authentication required

### Create a new vehicle
- **POST** `/api/vehicles`
- Requires authentication
- Request body:
```json
{
  "brand": { "id": 1 },
  "model": "Corolla",
  "year": "2022",
  "color": "Red"
}
```

### Update an existing vehicle
- **PUT** `/api/vehicles/1`
- Requires authentication
- Request body:
```json
{
  "brand": { "id": 1 },
  "model": "Corolla",
  "year": "2023",
  "color": "Blue"
}
```

### Delete a vehicle
- **DELETE** `/api/vehicles/1`
- Requires authentication

## Sale Endpoints

### Get all sales (paginated)
- **GET** `/api/sales?page=0`
- No authentication required
- Optional parameter: `page` (default is 0)

### Get a sale by ID
- **GET** `/api/sales/1`
- No authentication required

### Get sales by brand
- **GET** `/api/sales/brands/1`
- No authentication required

### Get sales by vehicle
- **GET** `/api/sales/vehicles/1`
- No authentication required

### Get top 5 best-selling vehicles
- **GET** `/api/sales/vehicles/bestSelling`
- **GET** `/api/sales/vehicles/bestSelling?startDate=2023-01-01&endDate=2023-12-31`
- No authentication required
- Optional parameters: `startDate`, `endDate`

### Create a new sale
- **POST** `/api/sales`
- Requires authentication
- Request body:
```json
{
  "vehicle": { "id": 1 },
  "brand": { "id": 1 },
  "saleDate": "2023-07-15"
}
```

### Update an existing sale
- **PUT** `/api/sales/1`
- Requires authentication
- Request body:
```json
{
  "vehicle": { "id": 2 },
  "brand": { "id": 1 },
  "saleDate": "2023-07-20"
}
```

### Delete a sale
- **DELETE** `/api/sales/1`
- Requires authentication