# Use OpenJDK 23 with Maven
FROM maven:3.9-eclipse-temurin-23

# Install required dependencies
RUN apt-get update && apt-get install -y \
    wget unzip curl gnupg && \
    curl -fsSL https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" | tee /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && apt-get install -y google-chrome-stable && \
    wget -q "https://chromedriver.storage.googleapis.com/$(curl -sS https://chromedriver.storage.googleapis.com/LATEST_RELEASE)/chromedriver_linux64.zip" -O /tmp/chromedriver.zip && \
    unzip /tmp/chromedriver.zip -d /usr/local/bin/ && \
    chmod +x /usr/local/bin/chromedriver && \
    apt-get clean

# Verify Chrome & ChromeDriver installation
RUN google-chrome --version && chromedriver --version

# Set working directory
WORKDIR /tests

# Copy test files & dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline  # Cache dependencies

# Copy the test source files
COPY src ./src

# Set environment variables for Chrome & ChromeDriver
ENV CHROME_BIN="/usr/bin/google-chrome"
ENV CHROMEDRIVER_BIN="/usr/local/bin/chromedriver"

# Run tests when container starts in headless mode
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
