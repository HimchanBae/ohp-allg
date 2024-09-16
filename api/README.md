# AllergyIntolerance API
## Host a Development Environment of the API
### Prerequisites
- Linux
- Docker
- Maven
- an IDE

### Windows New Environment Setup
- Install Docker Desktop on Windows
```
https://docs.docker.com/desktop/install/windows-install/
```

- Install Linux on Windows (Ubuntu)
```
https://learn.microsoft.com/en-us/windows/wsl/install
```

- Change Directory to project (via mnt folder of the root)
```
 ../ohp-allg/api
```

- Install Maven via Linux Terminal
```
sudo apt-get install software-properties-common
sudo apt-add-repository universe
sudo apt-get update
sudo apt-get install maven
```

- Proceed to Step 1 as follows

- Note: [mvn liberty:dev] may require minutes to complete, please wait until the server fully starts.

### 1. Compile project
```
mvn compile war:war
```

### 2. Pull and run FHIR server via Docker
First terminal:
```
sudo docker run -p 9443:9443 -e BOOTSTRAP_DB=true ghcr.io/linuxforhealth/fhir-server
```

### 3. Run the API (2 options)
Second terminal:
1. Running API directly without docker (Recommended)
```
mvn liberty:dev
```
Using this method will automatically re-compile the API when there are any code changes.

OR

2. Running API via Docker
```
sudo docker build . -t ohp_api:develop --no-cache
sudo docker run -p 9080:9080 -it ohp_api:develop
```

You will have to re-compile and build the image again when there are any code changes.

### Running the API again after stopping containers
First terminal:
```
docker start -ai <fhir-server-container-name/ID>
```
Second terminal:
```
mvn liberty:dev
```
OR
```
docker start -ai <ohp_api-container-name/ID>
```

## Testing the API
- API base - http://localhost:9080/allg/api
- Username - `fhiruser`
- Password - `change-password`
### Using Postman (Recommended)
Install the Postman Desktop Agent and use Postman on the browser to test the API.

### Using openapi
Test basic API with this url: http://localhost:9080/openapi/ui

### Using curl (Not recommended)
#### curl options:
- Musts:
  - `-k` - Allow insecure server connections when using SSL
- Optional:
  - `-i` - Include protocol response headers in the output
  - `-d <data>` - Used for POST request
  - `-H <header>` - Pass custom header(s) to server

More: https://gist.github.com/eneko/dc2d8edd9a4b25c5b0725dd123f98b10

## docker commands:
- `docker start <container-name/ID>` - Start an existing container
- `docker attach <container-name/ID>` - Attach a running container to the current terminal
- `docker start -ai <container-name/ID>` - Start an existing container and attach it to the current terminal
- `docker stop <container-name/ID>` - Stop a running container
- `docker rename <container-name> <new-name>` - Rename a container

## Debugging the code:
- Configure Liberty Server for Debugging:

Open the Liberty server configuration file, typically named server.xml.
Add a <featureManager> section to enable the debug-1.0 feature:

```
<featureManager>
    <feature>debug-1.0</feature>
    <!-- other features -->
</featureManager>
```

- Specify Debug Port:
Still in the server.xml, add a debug entry within the server element to specify the debug port and other settings. For example:
```
<server description="Debug Server">
    <featureManager>
        <feature>debug-1.0</feature>
        <!-- other features -->
    </featureManager>
    <httpEndpoint id="defaultHttpEndpoint" host="*" httpPort="9080" httpsPort="9443" />
    <debug address="localhost" port="7777" />
    <!-- other server settings -->
</server>
```
In this example, the debug port is set to 7777.

- Start the Liberty Server in Debug Mode:
```
server start myServer
```

- Connect the Debugger:
Connect the Java debugger to the Liberty server using the specified debug port. Common IDEs like Eclipse, IntelliJ IDEA, and Visual Studio Code support remote debugging. Here's how to do it in Eclipse, for example:
In Eclipse, go to "Run" -> "Debug Configurations."
Create a new "Remote Java Application" configuration.
Provide a name for the configuration.
Set the host to "localhost" (or the address where the Liberty server is running).
Set the port to the debug port specified in the server.xml (7777 in the example above).
Click "Apply" and then "Debug."

- Set Breakpoints and Debug:
Set breakpoints in code as usual. When execution hits a breakpoint, it can inspect variables, step through code, and perform other debugging tasks.

- Remember to remove or comment out the debug configuration and debug-1.0 feature in the server.xml when deploying the application to production, as running the server in debug mode can introduce security risks.

- These are the general steps for debugging a Java project deployed on an IBM WebSphere Liberty server. The exact process may vary slightly

## JUnit Tests
JUnit tests are located in the tests folder and can be run using:
```
mvn test
```

Please do not rename the folder as it makes the test unable to read local test files.

## Helpful Links:

LinuxForHealth FHIR Server repository: https://github.com/LinuxForHealth/FHIR/blob/main/README.md

Online FHIR API for testing: http://hapi.fhir.org/home?encoding=null&pretty=true

HL7 FHIR: https://hl7.org/fhir/

HL7 FHIR RESTful API Specification: https://hl7.org/fhir/http.html

HL7 FHIR AllergyIntolerance Resource Specification: https://hl7.org/fhir/allergyintolerance.html
