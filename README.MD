# Курсовая работа на тему _**Web-приложение Банка**_

## Переменные окружения

| Переменная окружения | Описание                                |
|----------------------|-----------------------------------------|
| `DB_URL`             | URL-адрес базы данных                   |
| `DB_USERNAME`        | Имя пользователя базы данных            |
| `DB_PASSWORD`        | Пароль от базы данных                   |
| `DB_NAME`            | Имя базы данных                         |
| `EMAIL_USERNAME`     | Адрес электронной почты                 |
| `EMAIL_PASSWORD`     | Пароль от электронной почты             |
| `JWT_SECRET`         | Секретный ключ JWT                      |
| `REDIS_HOST`         | Хост Redis                              |
| `REDIS_PORT`         | Порт Redis                              |
| `REDIS_USERNAME`     | Имя пользователя Redis (если требуется) |
| `REDIS_PASSWORD`     | Пароль от Redis (если требуется)        |

## API Endpoints

### GET /api/v1/me/

### *Получает информацию о текущем пользователе.*

### **Требуемые заголовки:**

* #### Authorization: Bearer {токен авторизации}

### **Ответ**

```json
{
  "user": {
    "id": 1,
    "username": "username",
    "first_name": "first_name",
    "last_name": "last_name",
    "full_name": "full_name",
    "email": "email@example.com",
    "created_at": "11.04.2024, 05:29:29",
    "is_active": true,
    "is_blocked": false,
    "roles": [
      "ROLE_USER"
    ]
  }
}
```

### GET /api/v1/me/accounts

### *Получает список счетов текущего пользователя.*

### **Требуемые заголовки:**

* #### Authorization: Bearer {токен авторизации}

### **Ответ**

```json
{
  "user": {
    "id": 1,
    "username": "username",
    "first_name": "first_name",
    "last_name": "last_name",
    "full_name": "full_name",
    "email": "email@example.com",
    "created_at": "created_at",
    "is_active": true,
    "is_blocked": false,
    "roles": [
      "ROLE_USER"
    ]
  },
  "accounts": [
    {
      "id": 1,
      "accountNumber": "2HL-Z5N-5SZ",
      "balance": 375.00,
      "currency": "USD",
      "balanceWithCurrency": "375.00 USD",
      "createdAt": "11.04.2024, 05:29:29",
      "card": {
        "type": "DEBIT",
        "name": "Дебетовая карта",
        "cardNumber": "4761-9720-3382-1910"
      }
    }
  ]
}
```

### POST /api/v1/auth/login

### *Аутентифицирует пользователя с помощью логина и пароля и возвращает токен авторизации.*

### **Требуемые параметры в теле запроса:**

* #### login: Логин пользователя
* #### password: Пароль пользователя

### **Ответ**

* #### Токен авторизации

### GET /api/v1/daily-curs

### *Возвращает список курса валют.*

### **Ответ**

* #### Список из курса валют

### GET /api/v1/transactions

### *Получает список транзакций пользователя.*

### **Требуемые заголовки:**

* #### Authorization: Bearer {токен авторизации}

### **Ответ**

```json
{
  "total": 2,
  "rows": [
    {
      "id": 1,
      "senderUsername": "senderUsername",
      "receiverUsername": "receiverUsername",
      "amount": 50.00,
      "currency": "USD",
      "amountWithCurrency": "50.00 USD",
      "type": "Перевод средств",
      "createdAt": "11.04.2024 05:35:32"
    },
    {
      "id": 2,
      "senderUsername": "senderUsername",
      "receiverUsername": "receiverUsername",
      "amount": 25.00,
      "currency": "RUB",
      "amountWithCurrency": "25.00 RUB",
      "type": "Перевод средств",
      "createdAt": "11.04.2024 05:36:39"
    }
  ]
}
```
