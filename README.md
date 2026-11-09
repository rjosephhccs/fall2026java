Fall 2026 - Houston City College Capstone Project for Java Spring Boot.

The code was made using the Eclipse IDE. To use this project, run the UserApplication.java file in order to activate Spring Boot. To run APIs, we recommend using postman. In postman, use the provided JSON data to test the POST APIs.

```Json Examples along with a template at the bottom
{ (Delete this: For POST http://localhost:8080/api/users
  "username": "testing",
  "passwordHash": "hashed?",
  "role": "SUPER_USER",
  "firstName": "him",
  "lastName": "ag",
  "email": "mailer",
  "isActive": true
}

{ (Delete this: For POST http://localhost:8080/api/users/authenticate
  "username": "testing",
  "password": "hashed?"
}

{ (Delete this: For POST http://localhost:8080/api/users/(id)
  "userId": "",
  "username": "testing",
  "role": "SUPER_USER",
  "firstName": "him",
  "lastName": "ag",
  "email": "mailer",
  "isActive": true,
  "createdAt": "",
  "updatedAt": ""
}

{ (Delete this: Blank template for POST http://localhost:8080/api/users
  "username": "",
  "passwordHash": "",
  "role": "SUPER_USER",
  "firstName": "",
  "lastName": "",
  "email": "",
  "isActive": true
}
```
