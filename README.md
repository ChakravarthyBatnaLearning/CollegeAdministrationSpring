# CollegeAdministrationSpring

## CRUD Operations for Students

This project facilitates CRUD (Create, Read, Update, Delete) operations for managing student records.
## Runnig the Program
### 1. Login : 
- give the credentials (username and password) as query string to this url : `http://localhost:8044/CollegeAdministrationSpring/api/login?username=?&password=?&`. this request goes to com.college.student.login.LoginServlet
- **LoginServlet** will Authenticate username and password and generate a random cookie having name **my_auth_cookie** and sent to browser and also utilized HttpSession to maintain user session
- Now we can Access All the APIs through the url `http://localhost:8044/CollegeAdministrationSpring/api/${API}`
- **com.college.student.login.AuthenticationFilter** will do authenticate for all the API requests so that only authorized user can access the APIs

# Libraries Used : 
#### Event Handling
1. Utilizes ApplicationEventPublisher for event handling
### Logging
1.  **Logging** : used log4j2 framework for logging and slf4 as abstraction layer for loose coupling blw the classes and logging framework
### LRU Cache Implementation :
#### Custom Implementations
- **LRUCache with Custom DoublyLinkedList:**
  - Utilizes a custom DoublyLinkedList to manage cache entries.
- **LRUCache with java. util.Deque Support:**
  - Relies on the java. util.Deque interface for managing cache entries.
>Both implementations utilize a HashMap to store and retrieve cached data efficiently.
> refer : com.college.student.cache
### Exception Handling : 
- Handled Exceptions using **ControllerAdvice**
- the possible exceptions occurred at Repository layers are handled by **ControllerAdvice**
## Database Schema

The SQL script for creating the necessary tables can be found in `CollegeAdministrationSpring/src/main/resources/administration.sql`.

### Tables

- **user_password:** Handles user authentication for login.
- **Admission:** Stores admission details. Refer to `com.college.student.pojo.Admission`.
- **Address:** Manages address information. Refer to `com.college.student.pojo.Address`.
- **Student:** Stores student data. Refer to `com.college.student.pojo.Student`.

