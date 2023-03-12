# jwtUserRegistration
Spring Boot App with JWT Token authorization. Spring Boot, Spring Security, JWT, JPA/Hibernate, Validation, and PostgreSQL

The project is a Spring Boot application that uses JWT authentication to secure the endpoints. The following endpoints are available:

NOTE: Make sure to modify application.properties before running the project.

POST /register - Register a new user. The request body should include the user details, including the username, password, first name, last name, email, phone, and roles. The roles should be specified as an array of role objects, with each role object containing a roleId and roleName field. If the user is created successfully, the response will include the user details and a JWT token that can be used for authentication.
POST /login - Authenticate a user and generate a JWT token. The request body should include the username and password. If the credentials are valid, the response will include the user details and a JWT token.
GET /current-user - Retrieve the details of the authenticated user. The request should include a valid JWT token in the Authorization header.
GET /hello - A simple "Hello, World!" endpoint that is not secured by authentication.
POST /create-role - Create a new role. The request body should include the roleName. If the role is created successfully, the response will include the role details.
GET /all-roles - Retrieve all roles.
GET /{id} - Retrieve the details of a specific role by ID.
DELETE /delete/{id} - Delete a role by ID.
PUT /update-role/{id} - Update the details of a role by ID. The request body should include the roleName. If the role is updated successfully, the response will include the updated role details.

The following endpoints do not require authentication:

/register: Allows users to register and create a new account.
/login: Allows users to login and receive a JWT token.
The following endpoints require authentication:

/current-user: Returns the current user details.
/hello: A sample endpoint to check if the server is up and running.
/create-role: Allows authenticated users with "ROLE_ADMIN" authority to create a new role.
/all-roles: Allows authenticated users with "ROLE_ADMIN" authority to retrieve all roles.
/{id}: Allows authenticated users with "ROLE_ADMIN" authority to retrieve a role by ID.
/delete/{id}: Allows authenticated users with "ROLE_ADMIN" authority to delete a role by ID.
/update-role/{id}: Allows authenticated users with "ROLE_ADMIN" authority to update a role by ID.
Please note that the endpoints do not use the convention of api/v1 and use the direct endpoint names as follows:

/register: Create a new user account.
/current-user: Retrieve current user details.
/login: Obtain a JWT token.
/hello: Test endpoint to check server availability.
/create-role: Create a new role.
/all-roles: Retrieve all roles.
/{id}: Retrieve a role by ID.
/delete/{id}: Delete a role by ID.
/update-role/{id}: Update a role by ID.

Make sure to authenticate and authorize the user with proper authorities before accessing the endpoints that require authentication.

