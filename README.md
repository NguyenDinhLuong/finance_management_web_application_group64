## List all used libraries and their versions

# Here are the libraries and their respective versions used in the frontend (ReactJS)
1. **@emotion/react**: ^11.10.4
2. **@emotion/styled**: ^11.10.4
3. **@fullcalendar/core**: ^5.11.3
4. **@fullcalendar/daygrid**: ^5.11.3
5. **@fullcalendar/interaction**: ^5.11.3
6. **@fullcalendar/list**: ^5.11.3
7. **@fullcalendar/react**: ^5.11.2
8. **@fullcalendar/timegrid**: ^5.11.3
9. **@mui/icons-material**: ^5.10.3
10. **@mui/joy**: ^5.0.0-beta.9
11. **@mui/material**: ^5.10.5
12. **@mui/x-data-grid**: ^5.17.26
13. **@nivo/bar**: ^0.80.0
14. **@nivo/core**: ^0.79.0
15. **@nivo/geo**: ^0.80.0
16. **@nivo/line**: ^0.79.1
17. **@nivo/pie**: ^0.80.0
18. **@reduxjs/toolkit**: ^1.8.5
19. **@testing-library/jest-dom**: ^5.16.5
20. **@testing-library/react**: ^13.3.0
21. **@testing-library/user-event**: ^13.5.0
22. **axios**: ^1.5.1
23. **chart.js**: ^3.9.1
24. **formik**: ^2.2.9
25. **jwt-decode**: ^3.1.2
26. **react**: ^18.2.0
27. **react-chartjs-2**: ^4.3.1
28. **react-dom**: ^18.2.0
29. **react-pro-sidebar**: ^0.7.1
30. **react-redux**: ^8.0.2
31. **react-router-dom**: ^6.3.0
32. **react-scripts**: 5.0.1
33. **react-toastify**: ^9.1.3
34. **web-vitals**: ^2.1.4
35. **yup**: ^0.32.11

# Here are the libraries (dependencies) and their respective versions used in backend (Spring Boot)

1. **org.springframework.boot:spring-boot-starter-parent**: 3.1.3
2. **org.springframework.boot:spring-boot-starter-data-jpa**: Inherited from parent (version 3.1.3)
3. **org.springframework.boot:spring-boot-starter-web**: Inherited from parent (version 3.1.3)
4. **com.mysql:mysql-connector-j**: Inherited from parent (runtime scope)
5. **org.springframework.boot:spring-boot-starter-validation**: Inherited from parent (version 3.1.3)
6. **org.springframework.boot:spring-boot-starter-test**: Inherited from parent (test scope, version 3.1.3)
7. **org.projectlombok:lombok**: Inherited from parent
8. **io.jsonwebtoken:jjwt-api**: 0.11.5
9. **io.jsonwebtoken:jjwt-impl**: 0.11.5
10. **io.jsonwebtoken:jjwt-jackson**: 0.11.5
11. **org.springframework.boot:spring-boot-starter-security**: Inherited from parent (version 3.1.3)
12. **com.vaadin.external.google:android-json**: 0.0.20131108.vaadin1

## List all working functionalities of the project

1. ** User Authentication and Authorization**
   - Sign in using unique username and password with validity checks.
   - Hashing of users’ credentials before saving into the database.
   - Use of JSON Web Tokens for session management and token refreshing.

2. **Account Summary**
   - Dashboard access for logged-in users.
   - Display of user financial activities, including income, expenses, net tax calculations, and a bar chart representation of the financial statement.
   - Ability to check all current valid financial entries in the Expense, Income, and Investment pages.

3. **Income and Expense Tracking**
   - Recording of financial records including details about income and expenses.
   - Ability to view recorded transactions and relevant details.

4. **Investment Portfolio Management**
   - Ability to add and track investment portfolio details such as net value, source, type, duration, and liquidity.
   - Automatic tracking of users’ recorded investment portfolios.

5. **Tax Calculation and Planning**
   - Calculation of payable tax based on user’s income and portfolio.
   - Provision of personalized tax insights including potential tax deductions and tax thresholds.

6. **Data Export**
   - Export of financial data within a customizable timeframe into csv format.

7. **Currency Conversion**
   - Ability to switch and view the application in different currencies.
   - Display of a list of supported currencies.

8. **Bill and Recurring Expense Tracking**
   - Ability to add and track bills and recurring expenses.
   - Setting due dates and repeating time periods for the bills.

9. **Expense Reminders**
   - Setting up of recurring expenses.
   - Scheduling of reminders for these expenses.
   - Sending of notifications through various channels based on user's preference.

10. **Financial Goal Setting and Tracking**
   - Setting up of financial goals related to saving, expenses, and investment.
   - Tracking and notification of progress towards these financial goals.

11. **Visualization Charts**
   - **Bar Chart**: Displays data using bars for income, expenses, and investments.
   - **Pie Chart**: Visual breakdown of expenses, income sources, and investment types.
   - **Line Chart**: Provides historical data for income, expenses, and investments.
     
## A quick guide on how to run your application

# Prerequisites:

1. **Node.js & npm**: Ensure you have [Node.js](https://nodejs.org/) and npm installed for React.
2. **Java JDK**: Install a suitable version for Spring Boot (typically Java 8 or 11).
3. **Maven**: For building the Spring Boot application.
4. **MySQL Workbench**: Installed and running.

# Steps:

1 Set Up MySQL:
1.1 Open MySQL Workbench and log in to your MySQL server instance.
1.2 Create a new database/schema for your application.
1.3 Update the `application.properties` or `application.yml` file in your Spring Boot application to match your MySQL configuration (username, password, database name).

2. Run Spring Boot Application:
2.1 Navigate to the root directory of your Spring Boot project.
2.2 Run the following command to build and start your application: mvn spring-boot:run
2.3 Once started, your backend API should be running, usually on `http://localhost:8080` unless configured differently.

3. Run React Frontend:
3.1 Navigate to the root directory of your React project.
3.2 Install the required npm packages (if you haven’t already) with: npm install
3.3 Start the React development server with: npm start
3.4 The React app should now be running, typically on `http://localhost:3000`. It should communicate with the backend on port 8080 or whichever port you've configured.

4. Access the Application:
Open a browser and go to `http://localhost:3000` (or the port you've set for React). You should now see your frontend application and be able to interact with the backend.

# Note:

1. Ensure that the React application has the correct proxy settings (usually in `package.json`) to communicate with the backend. For instance: "proxy": "http://localhost:8080"

2. If you have enabled CORS in Spring Boot, ensure the React server's address (`http://localhost:3000`) is allowed.

3. Make sure both MySQL and the backend server are running before you start the React frontend to ensure full functionality.

4. Always remember to check the console logs (both backend and frontend) for any errors or issues. It will help you debug faster.
