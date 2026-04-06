Web aplikacija za upravljanje kandidatima i njihovim veštinama.  
Aplikacija omogućava dodavanje, izmenu, brisanje i pretragu kandidata, kao i rad sa njihovim veštinama.


### Struktura projekta


## `praksaProjekat` – backend (Spring Boot, IntelliJ okruženje)

 Podesiti `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/praksa
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
server.port=8080
```



## `praksaprojekatfront` – frontend (React)
Pokretanje aplikacije: npm install, npm run dev


## `database/praksa.sql` – skripta za bazu
U MySQL Workbench-u kreirati bazu i importovati fajl:
```text
database/praksa.sql
```


 Testovi: JUnit, Mockito, MockMvc


Dokumentacija: 
Swagger (OpenAPI) je dostupan na:
```text
http://localhost:8080/swagger-ui/index.html
```




## Najzahtevniji / Najzanimljiviji deo

Najzahtevniji deo implementacije za mene bio je rad sa React frontendom i pisanje testova, jer se ranije nisam susretala sa tim tehnologijama.

Tokom rada na frontend delu koristila sam različite online resurse i tutorijale kako bih savladala osnovne koncepte React-a i način rada sa HTTP zahtevima.

Pisanje testova mi je takođe predstavljalo izazov, jer sam prvi put radila sa JUnit-om i Mockito bibliotekom. Bilo je potrebno da razumem kako da testiram servisni sloj izolovano, kao i kako da simuliram ponašanje baze podataka. Kroz ovaj proces sam stekla bolje razumevanje testiranja i njegove važnosti u razvoju aplikacija.

Najzanimljiviji deo projekta za mene bio je backend, gde sam radila sa Spring Boot-om, bazom podataka i implementacijom poslovne logike.

