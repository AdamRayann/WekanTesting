# Use an OpenJDK image with Maven
FROM maven:3.9-eclipse-temurin-17

# Set working directory
WORKDIR /tests

# Copy test files & dependencies
COPY pom.xml ./
RUN mvn dependency:resolve

# Copy the test source files
COPY src ./src

# Define the command to run the tests
CMD ["mvn", "clean", "test"]
