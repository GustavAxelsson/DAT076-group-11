To run the application (on IntelliJ):
1. Have an apache derby server open on port 1527 with no user credentials.
2. Create a new data source with Apache derby(remote)
    in the database tab. Host: localhost, Port: 1527,
    URL: jdbc:derby://localhost:1527/database;create=true
4. Repeat step 3 but with url:jdbc:derby://localhost:1527/test;create=true
5. Click on test connection and make sure that you have connection to the database.
6. Run mvn clean package in the root of the directory.
7. Download payara and unzip payara full in preferred directory
8. Add a new payara config in the edit configurations menu.
9. Add the database-2.0.war as artifact under the deploy tab.
10. It is possible that you need to add connection pool and JNDI manually
    in the admin interface which is located on http://localhost:4848
    when the server is running.
10. Run.
12. When deployed navigate to http://localhost:8080/database-2.0
11. Enjoy.

Disclaimer it might be possible to run the application easier.
But this is the way we did it.

Structure of repo:
 Report: Is found in root directory.

 Frontend (Angular 11): is found under root/src/angular

 Backend Test: is found under root/src/test

 Backend resources i.e. persistence.xml, microprofile-config and glassfish-resource
    is found under src/main/resources

 Rest of the application is found under src/main/java/restApi
    




