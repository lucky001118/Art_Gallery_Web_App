# Art Gallery Web Application

Art Gallery is an e-commerce platform designed for selling and purchasing artwork. The application includes standard e-commerce features, a customer-seller chat functionality, and an admin role to manage the platform effectively. It is built using Spring Boot microservices architecture, ensuring scalability and maintainability.

---

## Features

### General Features
- User Registration and Login (JWT-based authentication)
- Role-based Access Control (Admin, Customer, Seller)
- Browse and search for artwork
- Secure payment integration
- Customer reviews and ratings for artwork

### Customer Features
- Add artwork to the cart and wishlist
- View order history
- Communicate with sellers through chat
- Track order status

### Seller Features
- Upload and manage artwork
- View sales statistics
- Communicate with customers via chat
- Manage inventory

### Admin Features
- Manage users (customers and sellers)
- Approve or reject seller registrations
- Monitor platform activities
- Generate reports

---

## Tech Stack

### Backend
- **Java**: Programming Language
- **Spring Boot**: Framework for building microservices
- **Hibernate**: ORM for database interaction
- **MySQL**: Relational database

### Frontend
- **HTML**, **CSS**, **JavaScript**: Core web technologies
- **Tailwind CSS**: Styling framework
- **React**: Frontend framework for UI development

### Microservices
- **Authentication Service**: Handles user authentication and authorization
- **Product Service**: Manages artwork listings
- **Order Service**: Processes customer orders
- **Chat Service**: Enables customer-seller communication
- **Admin Service**: Admin management tools
- **API Gateway**: Centralized API entry point
- **Eureka Server**: Service discovery and registry

---

## Key Functionalities

- **Authentication**:
  - JWT tokens for secure authentication.
  - Google and GitHub login integration using OAuth.

- **Security**:
  - Spring Security for role-based access.
  - Encryption and decryption of sensitive data.
  - OTP-based mobile number verification.

- **Notifications**:
  - Email verification and order updates via Google Mail API.
  - SMS notifications using Twilio.

- **Scalability**:
  - Built on Spring Boot microservices for modular development.
  - Independent services for handling specific tasks.

---

## Installation and Setup

### Prerequisites
- JDK 17 or later
- MySQL Server
- Maven
- Node.js
- React CLI

### Backend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/art-gallery.git
   ```
2. Navigate to the backend directory:
   ```bash
   cd art-gallery/backend
   ```
3. Configure the database connection in `application.properties` for each service.
4. Build and run the services:
   ```bash
   mvn clean install
   java -jar target/<service-name>.jar
   ```

### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd art-gallery/frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the React development server:
   ```bash
   npm start
   ```

---

## API Endpoints

### Authentication Service
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - User login

### Product Service
- `GET /api/products` - Get all products
- `POST /api/products` - Add a new product (Seller only)

### Order Service
- `POST /api/orders` - Create a new order
- `GET /api/orders/{id}` - Get order details

### Chat Service
- `POST /api/chat` - Send a message
- `GET /api/chat/{conversationId}` - Get chat history

### Admin Service
- `GET /api/admin/users` - Manage users
- `GET /api/admin/reports` - Generate reports

---

## Contribution Guidelines

We welcome contributions to improve the Art Gallery application! Here’s how you can contribute:

1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add your message here"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Create a pull request.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Contact

For any queries or support, feel free to contact:
- **Name**: Lucky Manikpur
- **Email**: your-email@example.com
- **GitHub**: [your-username](https://github.com/your-username)

---

Thank you for exploring the Art Gallery application! 🌟
