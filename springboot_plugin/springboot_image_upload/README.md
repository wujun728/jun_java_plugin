# spring-boot-jpa-summernote-image-upload-example
Spring boot 와 JPA를 이용하여, 간단한 summernote의 이미지 업로드 예제 입니다.

mvn package 명령어를 통해 패키지를 생성해주시고, target 폴더에서

command 창에서 java -jar Summernote_image_upload-0.0.1-SNAPSHOT.war

입력하시면, 예제 소스를 구동할 수 있습니다.

또는

STS -> Import -> Maven -> Existing Mavan Projects에서 해당 프로젝트의 pom.xml 을 찾아 Import 하신 후
Boot Dashboard를 통해 실행해보실 수 있습니다.

기본 이미지 업로드 디렉토리( App.java 에서 변경 가능 )
d:/image/

H2 DB 데이터 확인
http://localhost:8080/console
url : jdbc:h2:mem:testdb
