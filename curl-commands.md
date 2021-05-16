**CURL COMMANDS**

DISH

1. Get dishes by restaurant id
   curl -X GET --location "http://localhost:80/api/v1/voting/dishes/1" \
   -H "Content-Type: application/json" \
   --basic --user admin@i.c:admin
2. Update dish by dish id
   curl -X PUT --location "http://localhost:80/api/v1/voting/dishes/1" \
   -H "Content-Type: application/json" \
   -d "{
   \"name\": \"Shaurma\",
   \"price\": 450
   }" \
   --basic --user admin@i.c:admin
3. Delete dish by dish id
   curl -X DELETE --location "http://localhost:80/api/v1/voting/dishes/1"
   
RESTAURANT

1. Add new restaurant
   curl -X POST --location "http://localhost:80/api/v1/voting/restaurants" \
   -H "Content-Type: application/json" \
   -d "{
   \"name\": \"New Restaurant\"
   }" \
   --basic --user admin@i.c:admin
2. Add dish to restaurant
   curl -X POST --location "http://localhost:80/api/v1/voting/restaurants/2" \
   -H "Content-Type: application/json" \
   -d "{
   \"name\": \"Satsivi\",
   \"price\": 1500
   }" \
   --basic --user admin@i.c:admin
3. Add dishes to restaurant
   curl -X POST --location "http://localhost:80/api/v1/voting/restaurants/2/dishes" \
   -H "Content-Type: application/json" \
   -d "[
   {
   \"name\": \"Beefsteak\",
   \"price\": 2000
   },
   {
   \"name\": \"Green salad\",
   \"price\": 500
   }
   ]" \
   --basic --user admin@i.c:admin
4. Search restaurant by id
   curl -X GET --location "http://localhost:80/api/v1/voting/restaurants/search-by-id/3" \
   -H "Content-Type: application/json" \
   --basic --user admin@i.c:admin
5. Search restaurant by name
   curl -X GET --location "http://localhost:80/api/v1/voting/restaurants/search-by-name/Tanuki" \
   -H "Content-Type: application/json" \
   --basic --user user@i.c:user
6. Update restaurant by id
   curl -X PUT --location "http://localhost:80/api/v1/voting/restaurants/4" \
   -H "Content-Type: application/json" \
   -d "{
   \"name\": \"New Restaurant\"
   }" \
   --basic --user admin@i.c:admin
7. Delete restaurant by id
   DELETE http://localhost:80/api/v1/voting/restaurants/3
   Authorization: Basic admin@i.c admin
   
USER

1. Add new use
   curl -X POST --location "http://localhost:80/api/v1/voting/users" \
   -H "Content-Type: application/json" \
   -d "{
   \"email\": \"new@i.c\",
   \"password\": \"new\"
   }" \
   --basic --user admin@i.c:admin
2. Get user by email
   curl -X GET --location "http://localhost:80/api/v1/voting/users/new@i.c" \
   --basic --user admin@i.c:admin
3. Get first page of 5 users
   curl -X GET --location "http://localhost:80/api/v1/voting/users/0/5" \
   --basic --user admin@i.c:admin
4. Update user by email
   curl -X PUT --location "http://localhost:80/api/v1/voting/users/new@i.c" \
   -H "Content-Type: application/json" \
   -d "{
   \"email\": \"newest@i.c\",
   \"password\": \"password\"
   }" \
   --basic --user admin@i.c:admin
5. Toggle use activation status
   curl -X PUT --location "http://localhost:80/api/v1/voting/users/user2@i.c/toggle-status" \
   --basic --user admin@i.c:admin
6. curl -X DELETE --location "http://localhost:80/api/v1/voting/users/user2@i.c" \
   --basic --user admin@i.c:admin
   
VOTES

1. Vote for a restaurant by name
   curl -X POST --location "http://localhost:80/api/v1/voting/votes/" \
   -H "Content-Type: application/json" \
   -d "{
   \"restaurantName\": \"El Mediterraneo\"
   }" \
   --basic --user user3@i.c:user3
2. Get voting results by date
   curl -X GET --location "http://localhost:80/api/v1/voting/votes/results/17-05-2021" \
   --basic --user admin@i.c:admin
3. Get simple results by date
   curl -X GET --location "http://localhost:80/api/v1/voting/votes/simple-results/17-05-2021" \
   --basic --user user3@i.c:user3
4. Delete vote
   curl -X DELETE --location "http://localhost:80/api/v1/voting/votes/1" \
   --basic --user admin@i.c:admin


**Postman documentation:**
https://www.getpostman.com/collections/cfae617548dac8f68eea