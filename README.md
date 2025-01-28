# projet13_OC

## Front

Go to frontend folder

> cd chatapp_front

Install your node_modules before starting (`npm install`).

### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.


## Back

Make sure you have a local instance of mysql80 running on port 3306

Setup your database username and password in the application.yml file with your MySql username and password strings.

For testing purposes, the database is emptied every time you run the app you dont have to run any sql script to build all tables, all is done by hibernate

    hibernate:
      ddl-auto: create-drop

One testing user is created on app startup with credentials :
Email:

    support@yourcaryourway.com
    customer1@exemple.com
    customer2@exemple.com

Password:

    password

Setup your database username and password in the following environment variables

    DB_USER
    DB_PASS

Go to backend folder

> cd chatappback

Build project
> mvn package

Run project
> mvn spring-boot:run


Project runs at
```localhost:8087```