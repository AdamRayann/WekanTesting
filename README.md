
# **Automated Testing for Wekan**
🔗 **[View Project Slider](https://www.canva.com/design/DAGcopMyqhw/z9_hz2oMJMCBA8rW1cyOQg/view?utm_content=DAGcopMyqhw&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h1e4dfc1088)**  
[![Screenshot of WeKan ®][screenshot_wekan]][roadmap_wekan]

## **Overview**
This project implements automated testing for the **Wekan** platform, a collaborative task management web application. The tests cover various aspects of the application, including UI testing, responsiveness, and API testing. The goal is to ensure the application's functionality, reliability, and performance meet user expectations.

---

## **Testing Strategy and Objectives**

### **Objectives**
- Validate key functionalities of Wekan, including boards, lists, and cards.
- Test the responsiveness of the application across different device types (PC, Tablet, and Phone).
- Verify the API endpoints' functionality for user authentication and board management.
- Provide detailed execution and error reporting through tools like Allure and Lighthouse.

### **Scope**
- **Functional Testing**: Validate user flows like creating, deleting, and modifying boards, lists, and cards.
- **Non-Functional Testing**: Ensure responsiveness and accessibility across various devices.
- **Smoke Tests**: Run critical tests to verify the application's stability for key functionality.
- **API Testing**: Validate key API endpoints using REST-Assured.

---

## **Test Plan Summary**

### **What is Tested**
- **Boards**: Creating, moving, deleting, and marking boards as favorites.
- **Lists**: Adding lists to boards, reordering them, and deleting them.
- **Cards**: Adding cards to lists, moving them across lists, and deleting them.
- **API**: User authentication, token generation, and board management.

### **Tools and Frameworks**
- **Selenium WebDriver**: For automated UI testing.
- **REST-Assured**: For API testing.
- **Maven**: Build automation tool.
- **Allure**: For generating test execution reports.
- **Lighthouse**: For performance and accessibility audits.
- **GitHub Actions**: For CI/CD pipeline automation.

---

## **Setting Up the Testing Environment**

### **Prerequisites**
1. **Java Development Kit (JDK)**: Version 23 or higher.
2. **Maven**: For managing dependencies and building the project.
3. **Docker**: To run the Wekan application .
4. **Browser Drivers**: ChromeDriver and GeckoDriver (Firefox) as per your browser versions.


### **Setting Up Wekan Locally**
1. Pull the Wekan Docker image:
   ```bash
   docker pull wekanteam/wekan:latest
   ```
2. Run Wekan:
   ```bash
   docker-compose up -d
   ```

### **Setting Up Selenium Grid**
1. Pull the standalone Chrome container:
   ```bash
   docker pull selenium/standalone-chrome:latest
   ```
2. Run the Selenium Grid:
   ```bash
   docker run --name selenium-grid -d -p 4444:4444 --shm-size=2gb selenium/standalone-chrome:latest
   ```

---

## **Executing the Tests**

### **1. Clone the Repository**
```bash
git clone https://github.com/AdamRayann/WekanTesting.git
cd WekanTesting
```

### **2. Run the Tests**

#### **UI Tests**
Run all tests for boards, lists, and cards:
```bash
mvn test -Dbase_url=http://localhost:5000
```

#### **Smoke Tests**
Run specific smoke tests:
```bash
mvn test -Dbase_url=http://localhost:5000 -Dtest=WekanBoardsTests#creatingBoardTest
```

#### **API Tests**
Execute API tests:
```bash
mvn test -Dbase_url=http://localhost:5000 -Dtest=APIFunctionality.*
```

---

## **Generate Reports**

### **Lighthouse Reports**
1. Install Lighthouse globally:
   ```bash
   npm install -g lighthouse
   ```
2. Run Lighthouse for performance and accessibility testing:
   ```bash
   lighthouse http://localhost:5000 --output html --output-path ./lighthouse-report.html
   ```

---

## **Key Features**
- Automated tests for UI and API functionalities.
- Responsive design validation across different devices.
- Smoke tests for quick validation of critical functionality.

For more information or contributions, feel free to create an issue in the repository.
