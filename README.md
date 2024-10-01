# Jakarta EE Notes

This document provides an overview of key concepts in Jakarta EE (formerly known as Java EE) and explains how to use Servlets, Maven, and web containers to build dynamic web applications.

## Key Concepts

### Maven
Maven is a project build tool used in Java development. It helps manage dependencies, run tests, compile code, and create executable files (JAR, WAR, EAR).

#### Project Build Tools:
- **Imperative**: Developer specifies each build step.
- **Declarative**: Developer declares the desired result, and the tool determines the steps (Maven is declarative).
- **POM (Project Object Model)**: A `pom.xml` file contains all the configuration details about project assembly.

#### Artifacts
Artifacts are the resulting executable files (JAR, WAR, EAR) from the build process.

### Web Server vs. Web Container

- **Web Server**: Serves static content (HTML, CSS, JavaScript). For example, it responds to a request for a file like `index.html` or `main.js`.
  
- **Web Container (Servlet Container)**: Serves dynamic content. Static web servers can't handle dynamic content, so web containers are used to handle requests dynamically using Servlets or JSP (JavaServer Pages).

# Servlets and Dynamic Content

A **Servlet** is a Java class that extends `HttpServlet` and is capable of serving dynamic content, such as generating HTML, XML, or JSON responses. Servlets must run inside a web container (e.g., Apache Tomcat).

### Annotations
```java
@WebServlet("/example")
```
The @WebServlet annotation maps a Servlet class to a specific URL path, allowing the web container to direct requests to the appropriate Servlet.

### Servlet Lifecycle
The client sends a request to the server.
The server is divided into two parts:
#### Web server: Serves static content.
#### Web container: Handles dynamic content (Servlets, JSP).
The request is routed to the web container (e.g., Tomcat).
The web container checks the deployment descriptor (web.xml) to find the corresponding Servlet class based on the request's URL path.
The Servlet handles the request, processes it, and generates a dynamic response (HTML, XML, JSON) that is sent back to the client.
Deployment Descriptor (web.xml)
The web.xml file specifies the Servlet class and URL mappings.

```java
<servlet>
  <servlet-name>exampleServlet</servlet-name>
  <servlet-class>com.example.MyServlet</servlet-class>
</servlet>

<servlet-mapping>
  <servlet-name>exampleServlet</servlet-name>
  <url-pattern>/example</url-pattern>
</servlet-mapping>
```
### HttpServlet
HttpServlet is a Java class that enables you to work with HTTP requests and responses.

#### Request: Data sent by the client to the server.
#### Response: Data sent by the server back to the client (HTML, JSON, XML).
# Evolution of Java EE (Jakarta EE)
#### January 1996: Java was released (Core Java).
#### December 1999: J2EE (Java 2 Platform, Enterprise Edition) was introduced for building enterprise-level applications.
#### May 2006: J2EE was renamed Java EE with the release of Java 5.
#### September 2017: Oracle transferred the enterprise specification rights to the Eclipse Foundation, renaming Java EE to Jakarta EE.
# Maven Project Structure
Archetype: A template that provides a basic project structure.
Dependencies: Add required libraries in the pom.xml file to manage project dependencies.
Example Maven Setup (pom.xml)
```java
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>myapp</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- Add your project dependencies here -->
    </dependencies>
</project>
```
# Basic Flow of a Servlet Application
#### 1-The client sends an HTTP request to the server.
#### 2-The web container routes the request to the appropriate Servlet based on the URL mapping defined in web.xml or through annotations.
#### 3-The Servlet processes the request and generates a response (dynamic content).
#### 4-The response is sent back to the client.
# Helpful Commands
## Maven:
Compile: mvn compile
Run tests: mvn test
Package (create JAR/WAR): mvn package
## Web Servers/Containers:
Examples of web containers that serve Servlets and JSP: Tomcat, JBoss, GlassFish.
