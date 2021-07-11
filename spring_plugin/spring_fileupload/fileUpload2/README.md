FileUpload
==========

Spring MVC (blueimp) jQuery File Upload. Based on blueimp's Grails plugin, just ported it back to spring mvc.

## Run
```maven
mvn tomcat7:run
```
The application is going to tell you its up, after it does go to [http://localhost:8080](http://localhost:8080) and check it out.

## Main tools
+ Spring MVC 3.2.1
+ Hibernate 4.1.9
+ H2 (embedded)
+ logback

## Details
To change the size of the uploads you can find that on [WebConfig.java](src/main/java/org/davidmendoza/fileUpload/config/WebConfig.java)
```java
    /**
     * Supports FileUploads.
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(5000000);
        return multipartResolver;
    }
```
Is set to < 5Mb by default but you can change it to what you need.

To change to a different database, you need to add the driver dependency on the [pom.xml](pom.xml) and change the database connection information in [fileUpload.properties](src/main/resources/fileUpload.properties).

It's configured by default to upload the file & a created thumbnail for that file to your machine's /tmp folder. To change that folder just change it in [fileUpload.properties](src/main/resources/fileUpload.properties).

If you have any issues with it please let me know. If you have any suggestions, great! Make a pull request.
