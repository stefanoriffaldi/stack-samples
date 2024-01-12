# Problem
"N+1 selects problem" despite using @Fetch(FetchMode.JOIN). The problem occurs independently of the use of the "fetch = FetchType.EAGER" field of @OneToMany and university field in Student class

References:
* https://stackoverflow.com/questions/463349/jpa-eager-fetch-does-not-join
* https://www.baeldung.com/hibernate-lazy-eager-loading (Last updated: January 8, 2024)
* https://www.baeldung.com/hibernate-fetchmode (Last updated: January 8, 2024)

Dependencies:
- Java 8
- spring-boot:1.5.8 
- spring-boot-starter-data-jpa:1.5.8 
  - [spring-data-jpa:1.11.18](https://docs.spring.io/spring-data/jpa/docs/1.11.8.RELEASE/reference/html/)
  - hibernate-core:5.0.12
  - hibernate-entitymanager:5.0.12

To reproduce anomaly:
- Run unit test org.example.test.FetchTypeTest, it creates sql log 'target/junit/sql.log' that show 3 queries: 1 select for University, 2 select for Student  

Notes:
- If you use IntelliJ: Remember to manually set JDK 1.8under "Project Settings"."Project"."SDK" or else it should not work properly
- DB schema is autogenerate, db data is loaded via 'src/test/resources/data.sql' file