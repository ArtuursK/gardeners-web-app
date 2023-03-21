# gardeners-web-app


### Tools, libraries, frameworks used:
* spring boot - spring5
* java 17
* thymeleaf 3
* bootstrap 5
* mysql 8
* bcrypt 4
* 


### How to setup and run:
1) add the following environment variables based on your database configuration:
   ```${GARDENERS_DB_URL}```
   ```${GARDENERS_DB_USER}```
   ```${GARDENERS_DB_PASSWORD}```
2) run:
   ```mvn build```
3) run:
   ```mvn deploy```

### Some example endpoints:

1) Get all posts with information about their author
   Request:
   ```HTTP GET localhost:8080/get-all-posts```
   Response:
```
[
   {
      "post_id":1,
      "authorName":"grdnr1",
      "description":"abc",
      "datetime":"2023-01-23T11:50:19.000+00:00"
   },
   {
      "post_id":2,
      "authorName":"grdnr1",
      "description":"def",
      "datetime":"2023-01-23T11:51:22.000+00:00"
   },
   {
      "post_id":3,
      "authorName":"grdnrdd2",
      "description":"desc for gardener 2 #1 post edit",
      "datetime":"2023-01-30T10:53:48.000+00:00"
   }
]
```


### Test retrieving all posts from database in user interface
Request:
```HTTP GET <server ip>:8080/allposts```
Response example:
![](/assets/allposts.jpg)



