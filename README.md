## Java EE JMS JDBC Hello world application.

The application consists of 2 modules:

 * JMSMDB module provides REST api for send message to subscribers, using JMS and MDB
 * JDBC module for working with database

The JMSMDB module are collected in war.


-----------------------------
## Technology stack:
- #### Java 8
- #### JDBC
- #### JMS
- #### MDB
- #### Maven 3.5.4
- #### JAX-RS
- #### DerbyDB
- #### GlassFish

-----------------------------
## Curl For REST API
> Curl commands for the application deployed in application context `jms-mdb`. For Windows use `Git Bash`.

#### send message to topic and get message to rest
    curl -X POST -H "Content-Type: text/plain" --data "Arman" http://localhost:8080/jms-mdb/rest/hello
    