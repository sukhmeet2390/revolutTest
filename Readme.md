# banking

Running the application
---
1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/banking-1.0-SNAPSHOT.jar server`

APIs
---
Note all responses are sample and might differ according to running parameters.


# Profile

## createProfile

User can create profiles by providing registration details. Profiles are uniquely identified by mobile and email. So, appropriate validation are made on these fields. UserId generation is incremental from 1(Left here for simplicity in testing/evaluation; Not to be used in production, as it results in predicting user's Ids)


**Request**
```
curl -X POST \
  http://localhost:8080/profiles \
  -H 'Content-Type: application/json' \
  -d '{
	"profile" : {
    "name" : {
        "firstName":"Sukhmeet",
        "lastName":"Singh"
    },
    "mobile":{
        "countryCode":"91",
        "number":"8004968195"
    },
    "email":"skhmtsngh@gmail.com"
	}
}'

```
**Response**

```
{
    "id": 1,
    "name": {
        "firstName": "Sukhmeet",
        "lastName": "Singh"
    },
    "email": "skhmtsngh@gmail.com",
    "mobile": {
        "countryCode": "91",
        "number": "9702655536",
        "isVerified": true
    }
}
```

# GetProfile
Get the profile of the created user specified from id which is returned in the response above. Ideally this should not take `id` instead an indentifier like `phone` or `email`.
This is kept to keep things simple.

`isVerified` is short circuited, but should be used to allow any transaction from/to this profile.


**Request**
```
curl -X GET \
  http://localhost:8080/profiles/{id}

```

**Response**

```
{
    "id": 1,
    "name": {
        "firstName": "Sukhmeet",
        "lastName": "Singh"
    },
    "email": "skhmtsngh@gmail.com",
    "mobile": {
        "countryCode": "91",
        "number": "9702655536",
        "isVerified": true
    }
}
```

# Account

## getBalance

Pass `id` of the profile returned after creating profile.
**Request**
```

curl -X GET \
  http://localhost:8080/accounts/{id}

```
**Response**
```
{
    "balance": 0
}

```

## getStatement

Get statement is a paged API, with 3 parameters: `limit`, `startTime`, `endTime`. Response contains a pointer to next page information.
The start-end time range is (inclusive, exclusive] in nature. `startTime` and `endTime` are computed using `System.nanoTime() / 1000`. In real world
this can be different according to business specification.

**Request**
```
curl -X GET \
  'http://localhost:8080/accounts/1/statement?limit=10&endTime={endTime}&startTime={startTime}'

```

**Response**
```
{
    "profile": {
        "id": 1,
        "name": {
            "firstName": "Sukhmeet",
            "lastName": "Singh"
        },
        "email": "skhmtsngh@gmail.com",
        "mobile": {
            "countryCode": "91",
            "number": "8004968195",
            "isVerified": true
        }
    },
    "generationDate": 1559980270658,
    "balance": {
        "balance": 0
    },
    "transactions": [
        {
             "id":"e26bd143-2596-4bc5-9028-7373a1d53a90",
             "from":null,
             "to":1,
             "date":1534954562432,
             "amount":"10.0",
             "transactionType":"CREDIT",
             "balance":10.859375
          }
    ],
    "nextPageEndTime":155954556835
}
```

# Transactions - Credit/Debit/Transfers
Transactions rely on `id` of profile for transfer. This is just for simplicity and in real world this is not the case.
Token is also added in API but is not used. `token` takes care of authorization.

## Add transaction

**Request**
```
curl -X POST \
  http://localhost:8080/transactions/add/ \
  -H 'Content-Type: application/json' \
  -d '{
    "userId": 1,
    "amount": 2.2,
    "token": "auth_token"
}'
```

**Response**
```
{
    "balance": 2.2
}
```


## Deduct transaction
**Request**
```
curl -X POST \
  http://localhost:8080/transactions/deduct/ \
  -H 'Content-Type: application/json' \
  -d '{
    "userId": 1,
    "amount": 2,
    "token": "auth_token"
}'
```
**Response**
```
{
    "balance": 0.2
}
```

## Transfer

**Request**
```
curl -X POST \
  http://localhost:8080/transactions/transfer/ \
  -H 'Content-Type: application/json' \
  -d '{
    "fromUserId": 2,
    "toUserId": 1,
    "amount": 10,
    "token": "auth_token"
}'
```
**Response**
```
{
    "balance": 0.1999999999999993
}
```
