### Java Spring File Upload
Upload images using Java Spring MVC

### Overview
In this simple application I am using Spring MVC and JPA (Hibernate) to persist User's information to the database including their profile photo/image. 

### Requirements
 - MySQL database needs to be installed (https://dev.mysql.com/downloads/)
 - Internet connection to download the dependencies
 - Knowledge of Java and Spring framework

### Setup
 - Clone the project to your local machine
 - Import the project into Spring Tool Suite/Eclipse/IntellijIDEA
 - Locate the Database folder and change the database name, username and password
 - Open the ```modisefileupload.java.controller.UserController``` and 
 modify the image path (for development purposes you will need to point the path to you local computer, 
 I have commented out the actual `rootDirect` to be used in the Server).
 - Right click on the project and run on server
 - Enjoy!


### License
```
The MIT License (MIT)

Copyright (c) 2016 Mr Modise

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.```