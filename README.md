# Guestbook (Jakarta Servlet + Jetty)

## Вимоги
- **JDK:** 21  
- **Maven:** 3.9+
- **Servlet API:** Jakarta Servlet 6.0  
- **Сервер:** Jetty 11 (jetty-maven-plugin)
- **База даних:** H2 (embedded)

---

## Запуск
mvn clean jetty:run
## Застосунок буде доступний за адресою:

http://localhost:8080/guestbook

## База даних

СУБД: H2

Тип: file-based (embedded)

JDBC URL:

jdbc:h2:file:./data/guestbook


## Файл БД:

./data/guestbook.mv.db


База створюється автоматично при першому додаванні коментаря.

## Ендпоїнти
Отримати всі коментарі
GET /guestbook/comments


Відповідь: 200 OK, application/json

[
  {
    "id": 1,
    "author": "Іван",
    "text": "Гарний сайт",
    "createdAt": "2025-12-01T18:42:11"
  }
]

## Додати коментар
POST /guestbook/comments


Content-Type:

application/x-www-form-urlencoded


## Параметри:

author — обовʼязковий, ≤ 64 символи

text — обовʼязковий, ≤ 1000 символів

## Відповіді:

204 No Content — успішно

400 Bad Request — невалідні дані

500 Internal Server Error — помилка БД

## Валідація

author і text не можуть бути порожніми

Максимальна довжина:

author: 64

text: 1000

## Логування

SLF4J + Logback

Логується POST-запит і створення нового коментаря

## Збірка
mvn clean package


## WAR-файл:

target/guestbook.war
