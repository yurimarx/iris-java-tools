# Hibernate 6 Dialect for InterSystems
This is the Hibernate 6 Dialect for InterSystems IRIS. 

# Source code for any contributions
To contribute to this dialect, go to the dialect folder

# Pre reqs
1. Use Java 11 or later
2. Hibernate 6 or later

# How to install this dialect
To install this dialect:

- For Maven:

```
<dependency>
    <groupId>io.github.yurimarx</groupId>
    <artifactId>hibernateirisdialect</artifactId>
    <version>1.1.0</version>
</dependency>
```

- Gradle:

```
dependencies {
	implementation 'io.github.yurimarx:hibernateirisdialect:1.1.0'
}
```

# How to use this dialect

To use this dialect:

- For Spring properties file (application.properties):

```
spring.datasource.username=_SYSTEM
spring.datasource.url=jdbc:IRIS://localhost:1972/USER
spring.datasource.password=SYS
spring.datasource.driver-class-name=com.intersystems.jdbc.IRISDriver
spring.jpa.database-platform=io.github.yurimarx.hibernateirisdialect.InterSystemsIRISDialect
```

- for Spring yml file (application.yml):
```
spring:
    datasource:
        url: jdbc:IRIS://localhost:1972/USER
        password: SYS
        username: _SYSTEM
    jpa:
        properties:
            hibernate:
                dialect: io.github.yurimarx.hibernateirisdialect.InterSystemsIRISDialect
        
```

- For Hibernate file (hibernate.properties):

```
hibernate.connection.url=jdbc:IRIS://localhost:1972/USER
hibernate.connection.driver_class=com.intersystems.jdbc.IRISDriver
hibernate.connection.username=_SYSTEM
hibernate.connection.password=SYS
hibernate.dialect=io.github.yurimarx.hibernateirisdialect.InterSystemsIRISDialect
```

- For Hibernate xml file (hibernate.cfg.xml):

```
<hibernate-configuration>      
  <session-factory>
    <property name="connection.url">jdbc:IRIS://localhost:1972/USER</property>
    <property name="hibernate.connection.driver_class">com.intersystems.jdbc.IRISDriver</property>
    <property name="connection.username">_SYSTEM</property>
    <property name="connection.password">SYS</property>
    <property name="dialect">io.github.yurimarx.hibernateirisdialect.InterSystemsIRISDialect</property>
    <property name="hibernate.hbm2ddl.auto">create-drop</property>
  </session-factory>
</hibernate-configuration>
```

# Unit test for this dialect 
To execute unit the dialect unit tests or get a java hibernate sample, go to the folder tests-java-sample


# To learn more: 
- Tutorial: https://community.intersystems.com/post/using-new-intersystems-iris-hibernate-6-dialect-springboot-project
- Video: https://youtu.be/-8ureaF7wZQ
