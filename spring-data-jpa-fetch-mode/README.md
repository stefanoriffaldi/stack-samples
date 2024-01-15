# "N+1 selects problem" with JpaRepository method despite using FetchMode.JOIN

[StackOverflow question](https://stackoverflow.com/q/77806343/11289119)

I encountered "N+1 selects problem" despite using @Fetch(FetchMode.JOIN), the behavior always generates the “N+1 selects problem”. I've a very large dataset in the main table, in my case N is about 10/15k, executing JOIN is mandatory.
The expectation is to see 1 and only 1 query that (left outer) join University and Student, but executing the test log show 3 select: 1 on University, 2 on Student (there are 2 universities and 5 student for each university) despite FetchMode.JOIN usage. I tried every passible combination of FetchMode / FetchType, but nothin works.

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
- Run unit test org.example.test.FetchModeTest, it creates sql log 'target/junit/sql.log' that show 3 queries: 1 select for University, 2 select for Student  

Notes:
- If you use IntelliJ: Remember to manually set JDK 1.8under "Project Settings"."Project"."SDK" or else it should not work properly
- DB schema is autogenerate, db data is loaded via 'src/test/resources/data.sql' file
