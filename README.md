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
