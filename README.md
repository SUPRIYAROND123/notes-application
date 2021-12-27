# notes-application
Names app for DISQO

Java Engineer Assessment 
Using a Java framework of your choice, build a RESTful Microservice for a “notes” application. Use MySql  or Postgres as your database to store the notes. Use multilayered architecture and demonstrate concepts  of OO design and design patterns (i.e. dependency injection, DAOs/Repositories, Services, …). 
Duration: 3 hours 

Git: 
Please use Git as version control. Create a new project in your own GitHub account (if you don’t have an  account, create one at github.com) and push the code to it. It is extremely important to commit often so  we can see the history of the commits. We will evaluate the project by not only the end result, but also the  history of the commits. Projects with only 2-3 commits will be rejected. 

Domain objects: 
User: 
A user in the system. It contains the following fields: 
- Email: Non-blank, valid email address, unique 
- Password: Non-blank, at least 8 characters 
- Create Time 
- Last Update Time 

Note: 
A note in the system. Notes are associated with Users. A user can have many notes. A note has the  following fields: 
- Title: Non-blank, max 50 characters long 
- Note: max 1000 long 
- Create Time 
- Last Update Time 

RESTful API: 
Design and build a RESTful API to allow CRUD operations on notes. Support JSON for requests and  responses. Note access should be restricted to the owner of the note. 
No need to build a RESTful API for users. You can insert a few default users into the database using  SQL. 

Authentication: 
Use Basic HTTP Authentication. 

Demonstration: 
Provide cURL commands to demonstrate the APIs
