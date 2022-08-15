Recipe management API provides way to perform Create, Read, Update and delete operations on the user’s favorite recipes.

To access all API’s via Swagger 
http://localhost:8081/swagger-ui.html

Current Limitation
The current solution uses embedded Mongodb for data persistence. The persisted data’s will be available or having lifetime till application shutdown. Every time application data has to be created when application has been shutdown.

Steps to run the application from windows 
The application jar is stored in git hub public repository download the jar along with config folder and applicationStart.bat
https://github.com/sivagurunathbabu/recipe-management-2.0.git

	Start the application by executing the applicationStart.bat (Currently configured to start the application with config params updated in config location).
	Check the console for the application start as shown below. Also open the swagger API screen and see whether all API’s are listed.
http://localhost:8081/swagger-ui.html


