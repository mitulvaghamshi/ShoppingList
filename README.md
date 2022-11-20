# ShoppingList

KMM: Full Stack Web App (Shopping List example)
Frontend: js(react) - kotlin, Backend: jvm - kotlin

## HTTP requests (curl)

### Get All

```shell
curl -i http://localhost:9090/shoppingList
```

### Get by ID

```shell
curl -i http://localhost:9090/shoppingList/<item-id>
```

### Delete by ID

```shell
curl -i -X DELETE http://localhost:9090/shoppingList/<item-id>
```

### Add new item

```shell
curl -i -X POST \
http://localhost:9090/shoppingList/add \
-H 'Content-Type: application/json' \
-d '{"desc": "Hot Pappers 🌶️", "priority": 10}'
```

## HTTP scripts

### Add item

```shell
POST http://localhost:9090/shoppingList
Content-Type: application/json
{"desc": "Peppers 🌶","priority": 5}
```

### Delete item

```shell
DELETE http://localhost:9090/shoppingList/<item-id>
```
