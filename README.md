AdminPanel API Documentation
=
+ [Create User](#Create)
+ [Get User By ID](#GetId)
+ [Get Users](#GetUsers)
+ [Update User](#Update)
+ [Delete User](#Delete)

# <a name="Create"></a> Create User

`POST /admin/user`

## Path parameters

No parameters.

## Query parameters

No
parameters.

## Request fields

| Path            | Type    | Optional | Description                              |
| --------------- | ------- | -------- | ---------------------------------------- |
| firstName       | String  | false    |                                          |
| lastName        | String  | false    |                                          |
| birthday        | String  | false    | Must be in the past.                     |
| login           | String  | false    | Size must be between 4 and 20 inclusive. |
| password        | String  | false    | Size must be between 6 and 20 inclusive. |
| aboutUser       | String  | true     |                                          |
| address         | Object  | false    |                                          |
| address.country | String  | true     |                                          |
| address.city    | String  | false    |                                          |
| address.street  | String  | false    |                                          |
| address.house   | Integer | false    |                                          |
| address.build   | String  | true     |                                          |
| address.flat    | Integer | false    |                                          |
| address.zipCode | String  | true     |                                          |

## Response fields

| Path            | Type    | Optional | Description |
| --------------- | ------- | -------- | ----------- |
| userId          | String  | false    |             |
| firstName       | String  | false    |             |
| lastName        | String  | false    |             |
| birthday        | String  | false    |             |
| login           | String  | false    |             |
| aboutUser       | String  | true     |             |
| address         | Object  | false    |             |
| address.country | String  | false    |             |
| address.city    | String  | false    |             |
| address.street  | String  | false    |             |
| address.house   | Integer | false    |             |
| address.build   | String  | true     |             |
| address.flat    | Integer | false    |             |
| address.zipCode | String  | true     |             |
| role            | String  | false    |             |

## Example request

``` bash
$ curl 'http://localhost:8080/admin/user' -i -X POST \
    -H 'Content-Type: application/json' \
    -H 'Accept: application/json' \
    -d '{
  "firstName" : "Иван",
  "lastName" : "Иванов",
  "birthday" : "1990-05-05",
  "login" : "ivan123",
  "password" : "ivan123",
  "aboutUser" : "Новый юзер",
  "address" : {
    "country" : "Russia",
    "city" : "Москва",
    "street" : "Ленина",
    "house" : 15,
    "build" : null,
    "flat" : 3,
    "zipCode" : null
  }
}'
```

## Example response

``` http
HTTP/1.1 200 OK
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 411

{
  "userId" : "03529ea4-a5e6-469f-9cbf-443b0a243c97",
  "firstName" : "Иван",
  "lastName" : "Иванов",
  "birthday" : "1990-05-05",
  "login" : "ivan123",
  "aboutUser" : "Новый юзер",
  "address" : {
    "country" : "Russia",
    "city" : "Москва",
    "street" : "Ленина",
    "house" : 15,
    "build" : null,
    "flat" : 3,
    "zipCode" : null
  },
  "role" : "ROLE_USER"
}
```

# <a name="GetId"> </a> Get User By Id

`GET /admin/user/{userId}`

## Path parameters

| Parameter | Type   | Optional | Description |
| --------- | ------ | -------- | ----------- |
| userId    | String | false    |             |

## Query parameters

No parameters.

## Request fields

No request body.

## Response fields

| Path            | Type    | Optional | Description |
| --------------- | ------- | -------- | ----------- |
| userId          | String  | false    |             |
| firstName       | String  | false    |             |
| lastName        | String  | false    |             |
| birthday        | String  | false    |             |
| login           | String  | false    |             |
| aboutUser       | String  | true     |             |
| address         | Object  | false    |             |
| address.country | String  | false    |             |
| address.city    | String  | false    |             |
| address.street  | String  | false    |             |
| address.house   | Integer | false    |             |
| address.build   | String  | true     |             |
| address.flat    | Integer | false    |             |
| address.zipCode | String  | true     |             |
| role            | String  | false    |             |

## Example request

``` bash
$ curl 'http://localhost:8080/admin/user/925976d5-cdaf-41f8-a01d-8c2c61dc9b1e' -i -X GET \
    -H 'Content-Type: application/json'
```

## Example response

``` http
HTTP/1.1 200 OK
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 411

{
  "userId" : "6db667ec-0652-4e14-94d6-c76540b5759b",
  "firstName" : "Иван",
  "lastName" : "Иванов",
  "birthday" : "1990-05-05",
  "login" : "ivan123",
  "aboutUser" : "Новый юзер",
  "address" : {
    "country" : "Russia",
    "city" : "Москва",
    "street" : "Ленина",
    "house" : 15,
    "build" : null,
    "flat" : 3,
    "zipCode" : null
  },
  "role" : "ROLE_USER"
}
```

# <a name="GetUsers"></a> Get Users

`GET /admin/user`

## Path parameters

No parameters.

## Query parameters

Supports standard [paging](#overview-pagination) query
parameters.

| Parameter  | Type   | Optional | Description                                 |
| ---------- | ------ | -------- | ------------------------------------------- |
| login      | String | true     |                                             |
| firstName  | String | true     |                                             |
| lastName   | String | true     |                                             |
| role       | String | true     | Must be one of \[ROLE\_ADMIN, ROLE\_USER\]. |
| dateBefore | Object | true     |                                             |
| dateAfter  | Object | true     |                                             |
| country    | String | true     |                                             |
| city       | String | true     |                                             |

## Request fields

No request body.

## Response fields

Standard [paging](#overview-pagination) response where `content` field
is list of following objects:

| Path            | Type    | Optional | Description |
| --------------- | ------- | -------- | ----------- |
| userId          | String  | false    |             |
| firstName       | String  | false    |             |
| lastName        | String  | false    |             |
| birthday        | String  | false    |             |
| login           | String  | false    |             |
| aboutUser       | String  | true     |             |
| address         | Object  | false    |             |
| address.country | String  | false    |             |
| address.city    | String  | false    |             |
| address.street  | String  | false    |             |
| address.house   | Integer | false    |             |
| address.build   | String  | true     |             |
| address.flat    | Integer | false    |             |
| address.zipCode | String  | true     |             |
| role            | String  | false    |             |

## Example request

``` bash
$ curl 'http://localhost:8080/admin/user' -i -X GET \
    -H 'Content-Type: application/json'
```

## Example response

``` http
HTTP/1.1 200 OK
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 729

{
  "content" : [ {
    "userId" : "4153eb05-e805-41a6-a515-c4d720764c52",
    "firstName" : "Иван",
    "lastName" : "Иванов",
    "birthday" : "1990-05-05",
    "login" : "ivan123",
    "aboutUser" : "Новый юзер",
    "address" : {
      "country" : "Russia",
      "city" : "Москва",
      "street" : "Ленина",
      "house" : 15,
      "build" : null,
      "flat" : 3,
      "zipCode" : null
    },
    "role" : "ROLE_USER"
  } ],
  "pageable" : "INSTANCE",
  "last" : true,
  "totalPages" : 1,
  "totalElements" : 1,
  "sort" : {
    "sorted" : false,
    "unsorted" : true,
    "empty" : true
  },
  "first" : true,
  "numberOfElements" : 1,
  "size" : 1,
  "number" : 0,
  "empty" : false
}
```

# <a name="Update"></a> Update User By Id

`PUT /admin/user/{userId}`

## Path parameters

| Parameter | Type   | Optional | Description |
| --------- | ------ | -------- | ----------- |
| userId    | String | false    |             |

## Query parameters

No
parameters.

## Request fields

| Path            | Type    | Optional | Description                                 |
| --------------- | ------- | -------- | ------------------------------------------- |
| firstName       | String  | true     |                                             |
| lastName        | String  | true     |                                             |
| birthday        | String  | true     | Must be in the past.                        |
| password        | String  | true     | Size must be between 4 and 20 inclusive.    |
| aboutUser       | String  | true     |                                             |
| address         | Object  | true     |                                             |
| address.country | String  | true     |                                             |
| address.city    | String  | true     |                                             |
| address.street  | String  | true     |                                             |
| address.house   | Integer | true     |                                             |
| address.build   | String  | true     |                                             |
| address.flat    | Integer | true     |                                             |
| address.zipCode | String  | true     |                                             |
| role            | String  | true     | Must be one of \[ROLE\_ADMIN, ROLE\_USER\]. |

## Response fields

| Path            | Type    | Optional | Description |
| --------------- | ------- | -------- | ----------- |
| userId          | String  | false    |             |
| firstName       | String  | false    |             |
| lastName        | String  | false    |             |
| birthday        | String  | false    |             |
| login           | String  | false    |             |
| aboutUser       | String  | true     |             |
| address         | Object  | false    |             |
| address.country | String  | false    |             |
| address.city    | String  | false    |             |
| address.street  | String  | false    |             |
| address.house   | Integer | false    |             |
| address.build   | String  | true     |             |
| address.flat    | Integer | false    |             |
| address.zipCode | String  | true     |             |
| role            | String  | false    |             |

## Example request

``` bash
$ curl 'http://localhost:8080/admin/user/63156e88-e3f1-4532-8485-1f15f9943039' -i -X PUT \
    -H 'Content-Type: application/json' \
    -H 'Accept: application/json' \
    -d '{
  "firstName" : null,
  "lastName" : "Петров",
  "birthday" : null,
  "password" : null,
  "aboutUser" : null,
  "address" : null,
  "role" : null
}'
```

## Example response

``` http
HTTP/1.1 200 OK
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 411

{
  "userId" : "b1c7e18f-6c6d-4510-8cb4-5160598295f0",
  "firstName" : "Иван",
  "lastName" : "Петров",
  "birthday" : "1990-05-05",
  "login" : "ivan123",
  "aboutUser" : "Новый юзер",
  "address" : {
    "country" : "Russia",
    "city" : "Москва",
    "street" : "Ленина",
    "house" : 15,
    "build" : null,
    "flat" : 3,
    "zipCode" : null
  },
  "role" : "ROLE_USER"
}
```

# <a name="Delete"></a> Delete User By Id

`DELETE /admin/user/{userId}`

## Path parameters

| Parameter | Type   | Optional | Description |
| --------- | ------ | -------- | ----------- |
| userId    | String | false    |             |

## Query parameters

No parameters.

## Request fields

No request body.

## Response fields

No response
body.

## Example request

``` bash
$ curl 'http://localhost:8080/admin/user/5fe5b4d6-ab01-45b0-98cc-dd03d6f0ed44' -i -X DELETE \
    -H 'Content-Type: application/json' \
    -H 'Accept: application/json'
```

## Example response

``` http
HTTP/1.1 200 OK
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
```
