# Cheaper Tickets

Author: David Michael Nelson (davidmichael.nelson -at- gmail -dot- com)

Project meant to find the cheapest plane tickets in Brazil, looking for specific week days within a given period.


## Technology stack:

### Language
- Groovy - http://groovy.codehaus.org

### Build tool
- Gradle - http://www.gradle.org/

### Dependencies 
- HTTPBuilder - http://groovy.codehaus.org/modules/http-builder/
- Spock - http://code.google.com/p/spock/ _(Testing)_
- Commons Logging - http://commons.apache.org/logging/ _(Logging)_
- Log4J - http://logging.apache.org/log4j/ _(Logging - Optional)_ 
                                                     

## Setting up
1. Download and set up Groovy and Gradle
2. Checkout the code
3. Test if it is building by running ``gradle build`` on project root
4. Have fun!

## Running
Run ``gradle run`` from the command line

### Developing with Intellij IDEA
1. Run ``gradle idea`` on project root
2. Open the project on IDEA

#### Known issues after setting up on IDEA
- Project being created with wrong JDK version
 
 _Just change for the one on your machine :)._

- Project being created without referencing Groovy standard library causing ClassNotFoundException whem running scripts within IDEA

 _Right-click your project, click on ``Add framework support``, then select Groovy._ 

