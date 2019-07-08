# abbyy-lingvo-example-downloader
The application allows to download thousands of examples of word or phrase usages from lingvolive.com **_fast_**. It uses Java concurrency API for that purpose. The application is based on the same API that is used by lingvolive.com. See demo here: https://drive.google.com/file/d/1Xn1orIAsvrBxeXrw3GWE19E-UtflIjQU/view?usp=sharing

The technology stack:
- Java (including concurrency API to make multiple requests simultaneously)
- Maven (including "exec-maven-plugin" to run Java app using Maven)

Java libraries:
- com.fasterxml.jackson package to parse JSON from lingvolive.com
- RestTemplate (from org.springframework.web.client package) to make HTTP requests. 
