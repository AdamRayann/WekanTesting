# Use OpenJDK 23 with Maven
FROM maven:3.9-eclipse-temurin-23

# Set working directory
WORKDIR /tests

# Copy test files & dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline  # Cache dependencies

# Copy the test source files
COPY src ./src

# Run tests when container starts
CMD ["mvn", "clean", "test", "-Dwebdriver.chrome.driver=/usr/local/bin/chromedriver", "-Dwebdriver.chrome.args=--headless,--no-sandbox,--disable-dev-shm-usage"]





## Use an OpenJDK image with Maven
#FROM maven:3.9-eclipse-temurin-17
#
## Set working directory
#WORKDIR /tests
#
## Copy test files & dependencies
#COPY pom.xml ./
#RUN mvn dependency:resolve
#
## Copy the test source files
#COPY src ./src
#
## Define the command to run the tests
#CMD ["mvn", "clean", "test"]
