# Use an official Maven image with OpenJDK
FROM maven:3.9-eclipse-temurin-17 AS builder

# Set working directory inside the container
WORKDIR /tests

# Copy only the Maven project files to download dependencies first
COPY pom.xml ./
RUN mvn dependency:go-offline  # Pre-download dependencies for caching

# Copy the test source files after downloading dependencies
COPY src ./src

# Run tests when the container starts
CMD ["mvn", "clean", "test"]




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
