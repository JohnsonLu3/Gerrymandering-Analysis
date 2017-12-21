##Gerrymandering Analysis 
###Setup Instructions: 
1. Clone the project from the repository. 
2. From the project root path, open src/main/resources/application.properties
3. In application.properties, specify the following info in the fields enclosed by brackets <>:  
a) spring.datasource.url - The URL of the database server to be used. MySQL server has been verified to work. Any SQL
database that supports OpenGIS standard should also work, but Hibernate needs to support it in order to deserialize the
data into Java geospatial objects.  
b) spring.datasource.username - The user name to log into your database.  
c) spring.datasource.password - The password to be used to log into the database.  
d) spring.mail.host - User registration in our code requires an email server to be set up to send confirmations. Some
popular and free email servers are hosted by Gmail and AWS.  
e) spring.mail.username - The user to log into the email server.  
f) spring.mail.password - The password to log into the email server.  
4. Install Gradle following the instructions [here](https://gradle.org/install/).
5. Once Gradle is installed, go to project's root directory and type:
   ```
   gradle clean build
   ```
6. At this point, the project is compiled. In order to run the project, there are two ways.  
a) Run it in-place at project root using Gradle task by typing `gradle bootRun`.  
b) Run the executable war file generated during the build task. Go to build/libs/ folder. You should see a
gerrymander-measure-0.1.0.war file. Type the following command to start the server: `java -jar gerrymander-measure-0.1.0.war`.
7. Once the app is running, go to localhost:8080 in your browser and you should be able to view the homepage.


###Data Base Setup:
These steps will instruct you on how to move the database to another mysql 5.7 server.

1. Download and install MySqlWorkbench, [here](https://www.mysql.com/products/workbench/).
2. From the main page of MySqlWorkbench add a new connection by clicking on the plus sign on the top right.  
a) Choose connection type as TCP/IP.  
b) Enter your hostname and port number to your database server.  
c) Enter your username associated with the database.  
3. Once connected create a new schema using the side panel on the left. 

###Importing Data into the Data Base:
Included is a folder containing 18 sql dump files.

1. In each sql dump file you must change the host and database fields to match your database host and database schema name.
        eg: Host: oldDatabase.com   Database: laconic (schema name)
        to 
        Host: newDatabase.com   Database: ResearchGA
2. Once all the sql dump files headers are changed to the new database go back to MySqlWorkbench.
3. Connect to your database that was setup above.
4. Click 'Import/Restore' in the Managerment Tab on the left side.
5. Choose 'Import from Dump Project Folder' and open the folder containing the sql dump files.
6. On the bottom left, you should see the name of the schema you wrote in the sql dump headers.
7. Click start importing.
8. Now you should see that all the tables and data have been imported into your database schema.
    
###Project Structure:
####/dataParsers
This folder contains all the KML and CSV parsing as well as database import scripts. They are written in Python.
####/src/main/java
This is the Java application in the backend, it consists of the following packages`:  
#####gerrymandering.api -
This package contains wrapper class for returning HTTP response as a JSON.   
#####gerrymandering.bean -
This package contains some Spring beans used for authentication and authorization.
#####gerrymandering.common - 
This package contains global constants and enums used throughout the project.
#####gerrymandering.config - 
This package contains Spring related configurations such as Spring Security and URL mappings for static pages.
#####gerrymandering.controller - 
This package contains web controllers that receive incoming HttpServletRequests and process them.  
#####gerrymandering.measure - 
This package contains classes and interfaces for running HR3057 measures, the Three Test analysis from Princeton gerrymandering group,
and efficiency gap test. It also contains classes and interfaces for storing the results of these measures.  
#####gerrymandering.model - 
This package contains JPA entities that are used for database persistence.  
#####gerrymandering.repository - 
This package contains Spring interfaces that get scanned in runtime to generate database access objects for the models.   
#####gerrymandering.service - 
This package contains the application level logics that get called by the controller classes.
####/src/main/resources
This is where application.properties is located. This file is used to configure Spring Boot and Hibernate.
####/src/main/webapp
This is where the front-end and JSP code resides. Everything inside this folder gets packaged into an embedded Tomcat server
at runtime. It contains the following folders:  
#####/img - 
This contains all the images used for the webpages. To access files in this folder, use the prefix /resources/img as specified in gerrymandering.config.StaticResourceHandler.   
#####/js - 
This contains all Javascripts used for the webpages. The Google Map and D3 chart code is all in here. To access files in this folder, use the prefix /resources/img as specified in gerrymandering.config.StaticResourceHandler.  
#####/jsp - 
This contains JSP pages rendered in the front-end. To access files in this folder, refer to the corresponding web controller in gerrymandering.controller.   
#####/static - 
This contains the static pages rendered in the front-end. Note that Spring modified the URL mapping for this
 folder. To access files within, your webpage needs to refer to /www/\<filename\>.  
#####/style - 
This contains the CSS files used in the front-end. To access files in this folder, use the prefix /resources/style as specified in gerrymandering.config.StaticResourceHandler.