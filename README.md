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
   ```shell
   gradle clean build
   ```
6. At this point, the project is compiled. In order to run the project, there are two ways.  

   a) Run it in-place at project root using Gradle task by typing:
   ```shell
   gradle bootRun
   ```
   
   b) Run the executable war file generated during the build task. Go to build/libs/ folder. You should see a
   gerrymander-measure-0.1.0.war file. Type the following command to start the server:
   ```shell
   java -jar gerrymander-measure-0.1.0.war
   ```
7. Once the app is running, go to localhost:8080 in your browser and you should be able to view the homepage.  


###Data Base Setup:
These steps will instruct you on how to move the database to another mysql 5.7 server.\

1. Download and install MySqlWorkbench, [here](https://www.mysql.com/products/workbench/).
2. From the main page of MySqlWorkbench add a new connection by clicking on the plus sign on the top right.  
a) Choose connection type as TCP/IP.  
b) Enter your hostname and port number to your database server.  
c) Enter your username associated with the database.  
3. Once connected create a new schema using the side panel on the left. 

###Importing Data into the Data Base:
Included is a folder containing 18 sql dump files.

1. In each sql dump file you must change the host and database fields to match your database host and database schema name.
    ```shell
    eg: Host: oldDatabase.com   Database: laconic (schema name)
        to 
        Host: newDatabase.com   Database: ResearchGA
    ```
2. Once all the sql dump files headers are changed to the new database go back to MySqlWorkbench.
3. Connect to your database that was setup above.
4. Click 'Import/Restore' in the Managerment Tab on the left side.
5. Choose 'Import from Dump Project Folder' and open the folder containing the sql dump files.
6. On the bottom left, you should see the name of the schema you wrote in the sql dump headers.
7. Click start importing.
8. Now you should see that all the tables and data have been imported into your database schema.
    

