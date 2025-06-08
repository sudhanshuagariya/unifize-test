Tech stack used (Prerequisites):
1) Java 17
2) Springboot 3.5.0
3) Mockito
4) JUnit
5) Gradle
6) Lombok
7) Used PostgreSQL for backend through JPA

 Build instructions :
 Use command: ./gradlew build to build project

 Test execution instructions :
 Similar how we run the tests in springboot project.

 Documentation:
 Using Java API doc for services and repository
   
Key Assumptions made while working on this Assignment : 
1) Customer with tier = 'BUDGET' can only have category and brand discounts
2) Customer voucher and Payment discount could be applied to any customer
3) Discounts would keep on getting applied from brand level -> category level -> customer voucher level -> payment level if exists.

We have 5 tables in Postgres DB :
1) Brand -> storing brand name and its discount information
2) Category -> storing category name and its discount information
3) Customer -> storing customer information
4) Payment_info -> storing payment information
5) product -> storing product information.


I have used tests to run the methods of DiscountServiceImpl instead of creating controller to test them.



