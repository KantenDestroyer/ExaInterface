# Overview
This Project is Created for my school to grade my grade.
It's an Interface to support the Work as Employee.
Everything in the App is made to make the life as Employee easy.
## Features
- Password manager
- Ticket system
## TODO
für die Version v0.0.2\
[Key Tutorial0](https://www.baeldung.com/java-aes-encryption-decryption)\
[Key Tutorial1](https://dev.java/learn/security/intro/)\
[Key Tutorial2](https://de.wikipedia.org/wiki/Java_Cryptography_Extension)
## Projekt
### backend
- [ ] Make a Json Reader for Request with Json file [here is a Reference](https://www.geeksforgeeks.org/how-to-read-and-write-json-files-in-java/)
- [X] make it Webserver work
- [ ] Optimize Webserver, read file and work with redirect (think to use nginx for webserver and send json file to server socket).
- [X] build ServerSocket [Tutorial1](https://www.baeldung.com/a-guide-to-java-sockets) [Tutorial2](https://youtu.be/O7TuxKJXBII?si=5HrndzT0EYy9_8Ag)
- [ ] database class complete [Reference](https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html) [Reference](https://learn.microsoft.com/en-us/sql/connect/jdbc/step-3-proof-of-concept-connecting-to-sql-using-java?view=sql-server-ver16) [Reference](https://mariadb.com/docs/server/connect/programming-languages/java/connect/)
### frontend - Client
- [ ] Anwendung _(JavaFX)_
- [ ] Mobil version _(JavaFX)_
- [ ] WebApp _(HTML/CSS/PHP/JavaScript)_ -> Webserver in Package "org.kanten.server.Webserver" / Website "org.kanten.server.Website"
### gradle build
- [ ] Task build only Client
- [ ] Task build WebApp
- [ ] Task build only Server 
- [ ] Task build Master 
- [ ] Make a Runnable with arguments