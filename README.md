# Implementation by William Mangoni

## Starting the Application

Run the `BackendInterviewProjectApplication` class

Go to:

* http://localhost:8080/device/1
* http://localhost:8080/jobservice/1
* http://localhost:8080/customer/1
* http://localhost:8080/customer-device
* http://localhost:8080/customer/1/calculate-price

see all endpoints in "RMM Collection.postman_collection.json" file in root directory to test with postman

You should see results for both of these. The application is working and connected to the H2 database.

## H2 Console

In order to see and interact with your db, access the h2 console in your browser. After running the application, go to:

http://localhost:8080/h2-console

Enter the information for the url, username, and password in the application.yml:

```yml
url: jdbc:h2:mem:localdb
username: sa
password: password
```

You should be able to see a db console now that has the Sample Repository in it.

Type:

```sql
SELECT * FROM DEVICE;
````

Click `Run`, you should see 3 rows
