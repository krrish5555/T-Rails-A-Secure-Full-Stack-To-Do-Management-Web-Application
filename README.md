# T-Rails-A-Secure-Full-Stack-To-Do-Management-Web-Application
A responsive full-stack to-do list web app built with Spring Boot, Thymeleaf, and JavaScript featuring secure login, task management, and dynamic dashboard views.

T-Rails is a responsive, full-stack to-do list web application designed to help users manage their daily tasks efficiently while showcasing modern web development practices. The application is built using Spring Boot on the back end and HTML, CSS, and JavaScript on the front end, with Thymeleaf for dynamic HTML rendering and Spring Security for authentication and access control.

The system enables user registration, login, password change, task creation, editing, deletion, and completion tracking, all through a user-friendly interface. Tasks are organized by priority levels (HIGH, MEDIUM, LOW) and due dates, with a dashboard that highlights tasks due today, pending and completed task counts, and status color coding.

Key Features:
✅ User Authentication & Authorization
Spring Security integration to protect endpoints, allow role-based access, and secure form submissions.

📝 Task Management (CRUD)
Users can create, view, edit, delete, and mark tasks as complete, with tasks stored in a relational database using Spring Data JPA.

📅 Today's Task Highlighting
Displays tasks due on the current date at the top of the dashboard, grouped by priority.

📊 Completion Status Tracking
Visually indicates task status with "Yes" (green) or "No" (red) and dynamically counts completed vs. pending tasks.

🌐 Responsive Frontend Design
Custom styling using CSS and media queries for mobile-friendly layouts and enhanced UX.

🛡️ Form Validation
JavaScript-based client-side validation and backend checks to ensure data integrity and prevent duplicate registrations.

📁 Modular Architecture
Clean MVC structure with controllers, services, repositories, and entity classes for scalability and maintainability.

Technologies Used:
Back End: Spring Boot, Spring Security, Spring Data JPA, Hibernate

Front End: HTML5, CSS3, JavaScript, Thymeleaf

Database: H2 / MySQL (configurable)

Tools & IDEs: IntelliJ IDEA, Maven

Other: BCrypt password encoding, RESTful APIs, Responsive Design


