# HR Platform

Web aplikacija za upravljanje kandidatima i njihovim veštinama.  
Aplikacija omogućava dodavanje, izmenu, brisanje i pretragu kandidata, kao i rad sa njihovim veštinama.

## Tehnologije

- Backend: Java, Spring Boot, Spring Data JPA
- Baza podataka: MySQL
- Testovi: JUnit, Mockito, MockMvc
- Frontend: React (Vite)
- Dokumentacija: Swagger (OpenAPI)

## Struktura projekta

- `praksaProjekat` – backend (Spring Boot)
- `praksaprojekatfront` – frontend (React)
- `database/praksa.sql` – skripta za bazu

### Baza podataka

1. Otvoriti MySQL Workbench  
2. Importovati fajl `database/praksa.sql`

### Backend

Otvoriti projekat u IntelliJ okruženju  

Podesiti `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/praksa
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
server.port=8080
```


## Frontend
Otvoriti terminal u folderu:
```text
praksaprojekatfront
```


Pokrenuti komande:
```bash
npm install
npm run dev
```


## API

Swagger dokumentacija je dostupna na:
```
http://localhost:8080/swagger-ui/index.html
```



## Najzahtevniji / Najzanimljiviji deo

Najzahtevniji deo implementacije za mene bio je rad sa React frontendom i pisanje testova, jer se ranije nisam 
susretala sa tim tehnologijama.
Prilikom izrade frontend-a, bilo mi je izazovno da povežem React aplikaciju sa backend servisima i pravilno 
upravljam podacima koji dolaze sa servera. Kroz rad sam koristila različite online resurse i tutorijale kako
bih razumela osnovne koncepte React-a i način rada sa HTTP zahtevima.

Pisanje testova mi je takođe predstavljalo izazov, jer sam prvi put radila sa JUnit-om i Mockito bibliotekom.
Bilo je potrebno da razumem kako da testiram servisni sloj izolovano, kao i kako da simuliram ponašanje
baze podataka. Kroz ovaj proces sam stekla bolje razumevanje testiranja i njegove važnosti u razvoju aplikacija.

