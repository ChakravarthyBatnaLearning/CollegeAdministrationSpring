# CollegeAdministrationSpring

## CRUD Operations for Students

This project facilitates CRUD (Create, Read, Update, Delete) operations for managing student records.
## Database Schema

The SQL script for creating the necessary tables can be found in `CollegeAdministrationSpring/src/main/resources/administration.sql`.

### Tables

- **user_password:** Handles user authentication for login.
- **Admission:** Stores admission details. Refer to `com.college.student.pojo.Admission`.
- **Address:** Manages address information. Refer to `com.college.student.pojo.Address`.
- **Student:** Stores student data. Refer to `com.college.student.pojo.Student`.

## LRU Cache Implementation
### Custom Implementations
- **LRUCache with Custom DoublyLinkedList:**
  - Utilizes a custom DoublyLinkedList to manage cache entries.
- **LRUCache with java. util.Deque Support:**
  - Relies on the java. util.Deque interface for managing cache entries.
>Both implementations utilize a HashMap to store and retrieve cached data efficiently.
> refer : com.college.student.cache
#### Event Handling
1. Utilizes ApplicationEventPublisher for event handling

### Logging
1.  **Logging** : used log4j2 framework for logging and slf4 as abstraction layer for loose coupling blw the classes and logging framework 
