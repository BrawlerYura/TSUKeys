# Project of KeyKeeper for HITs
 

## Our team:
 

*   Alexander Sochugov
     
*   Artyom Gorst
     
*   Yuri Sitdikov
     
*   Ivan Arsenyev
     
*   Miron Pontak
     
*   Andrey Makarov
     
    
    ## Links
     
    Swagger: [http://92.125.32.68:5284/swagger/index.html](http://92.125.32.68:5284/swagger/index.html) 
     
    Admin panel (PhpMyAdmin): [http://92.125.32.68/openserver/phpmyadmin/index.php](http://92.125.32.68/openserver/phpmyadmin/index.php) 
     
    
    *   Login: root
         
    *   Password: `ask a team member`
         
    
    Trello: [https://trello.com/invite/b/kUYGmfaI/ATTI20de4a92496b2779ad802430311ec5eb9C7BB91E/командная-разработка](https://trello.com/invite/b/kUYGmfaI/ATTI20de4a92496b2779ad802430311ec5eb9C7BB91E/командная-разработка) 
     
    Hosted admin panel: [https://tsu-hits-key.ru/openserver/phpmyadmin/index.php](https://tsu-hits-key.ru/openserver/phpmyadmin/index.php) 
     
    Backend: [http://92.125.32.68:5284](http://92.125.32.68:5284) 
     
    
    ## Server
     
    The server is divided into two parts: a docker container with the backend and a docker container with a Python script for direct interaction with the database. These measures are taken to protect the database against SQL injections and other types of attacks.
     
    The backend part of the docker container cannot work directly with the database itself, so it communicates with a Python script that performs all necessary actions. An admin panel has also been installed on the server, which allows users to interact with the database directly.
     
    ![image](https://github.com/IvanArsenev/team-development/blob/a680c0358d16d474829bd4a8722ff81f4c86b7aa/src/teamDev.drawio.png?raw=true)
