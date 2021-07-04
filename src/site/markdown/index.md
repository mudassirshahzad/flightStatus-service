# Flight status Service Rest Full API Service

This service will provide flight status service APIs.

## CI/CD pipeline with Apache Maven

### CI/CD#1 Build the app 

To build without generating javadocs and without activating static code analyses
`mvn clean install`

To build including javadocs generation and with static code analyses and instrumentation
`mvn -Dmaven.javadoc.skip=false -Djacoco.skip.instrument=false clean install`

### CI/CD#2 Generate maven site

1. Build the app including the generation of javadoc and code analyses report
`mvn -Dmaven.javadoc.skip=false -Djacoco.skip.instrument=false clean install`

2. Generate maven site
`mvn -Dmaven.javadoc.skip=false -Djacoco.skip.instrument=false site:site`

### CI/CD#3 Deploy source and javadoc to the local repository

To deploy source and javadoc to the local repository
`mvn clean -Dmaven.javadoc.skip=false javadoc:jar source:jar install`

### CI/CD#4 Run unit tests

`mvn surefire:test`

By default, the Surefire Plugin will automatically include all test classes with the following wildcard patterns:

"**/Test*.java" - includes all of its subdirectory and all java filenames that start with "Test".
"**/*Test.java" - includes all of its subdirectory and all java filenames that end with "Test".
"**/*TestCase.java" - includes all of its subdirectory and all java filenames that end with "TestCase".
If the test classes does not go with the naming convention, then configure Surefire Plugin and specify the tests you want to include.

### Skip unit tests

`mvn clean package -DskipTests`

### CI/CD#5 Run integration tests

`mvn failsafe:integration-test`

Integration tests have a different naming convention. They are named as “name of class + IT”. IT stands for Integration Test.

### Skip integration tests

`mvn clean install -DskipITs`

## Keep up to date with Apache Maven

### Update the versions of dependencies

To check all the plugins and reports used in your project and display a list of those plugins with newer versions available
`mvn versions:display-plugin-updates`

To check all the dependencies used in your project and display a list of those dependencies with newer versions available
`mvn versions:display-dependency-updates`

## CI/CD#6 Run the app with Apache Maven profiles
### dev

`mvn clean install`
It bundles up runnable JAR. All http traffic information are logged.

### qa

`mvn clean install -P qa`
It bundles up runnable JAR. All http traffic information are logged.

### prod

`mvn clean install -P prod`
It bundles up runnable JAR. No http traffic information is logged.

## Access to Swagger

### Url
`http://localhost:8899/swagger-ui.html`

### Logs
Logging performs through AOP, using spring cloud sleuth + spring filters.
Due adding new log to application, should be in agreement to logging contract in confluence.

### Configuration

# requirements
should contain:
1) server.jks in classpath  - to secure communication with configuration server
2) ConfigurationHolder & ConfigurationRefresher to keep properties update 
3) default properties (middleware...) in bootstrap.yml

# command line argument examples 
1) `--spring.cloud.config.uri=http://localhost:9999` to redefine configuration server port in application (8888 by default)
2) `--server.port=7777` to redefine application port (8899 by default)
3) `--spring.profiles.active=prod` to redefine spring profile (dev by default)