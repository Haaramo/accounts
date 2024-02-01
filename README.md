# Bank demo

This is a demo of a bank application. It is a simple demo that allows you to create accounts and transfer money between them.
It demonstrates the use of the following technologies:

* Spring Boot 3
* Java 21
* Virtual threads
* Spring Data with JPA
* PostgreSQL
* Kafka
* Integration tests

PostgreSQL and Kafka are configured to run with integration tests only. Because of that the tests can be run but the actual
application cannot be run. PostgreSQL runs in a test container and Kafka is embedded. 