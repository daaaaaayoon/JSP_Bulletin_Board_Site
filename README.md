# JSP_Bulletin_Board_System
:clipboard: JSP로 간단한 게시판 웹 사이트 만들기

## JSP (Java Server Pages)
  + server programming 언어로써, 홈페이지에 게시판이나 로그인 회원가입 등의 기능을 넣어야할 때 사용
  + jsp는 다른 서버 프로그래밍 언어와 다르게 Java언어를 사용한다.

### 0. 다운로드 및 개발환경
  + [JDK Download](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
  + [환경변수 설정](https://docs.oracle.com/javase/7/docs/technotes/tools/windows/classpath.html)
  + [Apache Tomcat Download (Window Service Installer)](https://tomcat.apache.org/download-80.cgi)
  + [Eclipse Download](https://www.eclipse.org/downloads/)
  + [MySQL Community Downloads](https://dev.mysql.com/downloads/mysql/)

### 1. 프로젝트 생성 및 서버 연결
+ Eclipse IDE => Create a Dynamic Web project
  + Project Name: BBS
  + Target runtime에서 New Runtime 클릭 후 Apache Tomcat 8.5v 선택 후 Next
  + Tomcat Installation directory에 설치한 apache-tomcat-8.5.14 경로 입력

### 2. 웹 페이지 파일 만들기 & 부트스트랩 
+ WebContent : 웹 페이지 파일 저장하는 폴더 <br>
  + WebContent > New > JSP File > 파일명.jsp
+ [Bootstrap 설치](https://getbootstrap.com/docs/4.4/getting-started/download/)
  + 압축풀어 부트스트랩 폴더 안에있는 js, css, fonts 폴더 복사해서 WEB-INF 안에 붙여넣음
  ```
  <!--반응형 및 부트스트랩 적용 -->
    <meta name="viewport" content="width-device-width", initial-scale="1">
    <link rel="stylesheet" href="css/bootstrap.css">
  <!-- script -->
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	  <script src="js/bootstrap.js"></script>
  ```

### 3. 데이터베이스 구축
+ 시작 > MySQL Command Line Client > MySQL 설치했을 때 설정한 비밀번호 입력
+ 회원 테이블 생성 및 확인하기
  ![jsp](https://user-images.githubusercontent.com/53184797/81903937-8347b280-95fd-11ea-95cf-993dffd96a53.png)
+ 회원 데이터 삽입
  ![jsp2](https://user-images.githubusercontent.com/53184797/81903942-8478df80-95fd-11ea-85f2-fb6c8c373ae5.png)
+ commit; 명령어로 저장

### 4. Java Beans 만들기
+ __Java Beans__: 데이터베이스의 데이터를 jsp 서버에서 관리하고 처리할 수 있도록 하는 것
+ src > New Java Package > user (패키지명)
+ user (생성된 패키지) > New Class > User(클래스 이름 - 맨앞글자 대문자)
+ User.java(생성된 파일) 수정: 데이터베이스 테이블의 정보와 동일한 이름으로 변수 입력
  ![userjava](https://user-images.githubusercontent.com/53184797/81905570-d884c380-95ff-11ea-8388-2b6427173bea.png)
+ 변수들을 JSP 서버에서 사용할 수 있는 형태로 만들기
  + 오른쪽 마우스 > Source > Generate Getters and Setters 클릭

### 5. DAO (데이터베이스 접근 객체)만들기
+ jsp에서 데이터베이스에 접근하여 데이터를 가져오거나 넣고자 할 때 사용
+ user (패키지) > New Class >> UserDAO(클래스이름DAO)
+ [UserDAO.java](./src/user/UserDAO.java) 작성

### 6. 데이터처리 페이지
+ 사용자의 로그인 시도를 처리하는 [loginAction.jsp](./WebContent/loginAction.jsp) 작성
  + UserDAO 클래스 가져오기, js문장을 작성하기 위한 코드도 작성
    ```
    <%@ page import="user.UserDAO" %>
    <%@ page import="java.io.PrintWriter" %>

    ```
 + 한명의 회원 정보를 담는 User라는 클래스를 Java Beans로써 사용
   + scope : 현재 페이지 안에서만 Java Beans가 사용됨
   + userID, userPassword : login.jsp에서 받은 userID, userPassword
    ```
    <jsp:useBean id="user" class="user.User" scope="page" />
    <jsp:setProperty name="user" property="userID"/>
    <jsp:setProperty name="user" property="userPassword"/>
    ```

### 7. MySQL jdbc 연결 Driver 설치 및 라이브러리 적용
+ [mysql jdbc connector](https://dev.mysql.com/downloads/connector/j/5.1.html)
+ 진행중인 프로젝트의 WEB-INF > lib 폴더 안으로 mysql-connector-java-5.1.49-bin.jar 파일 복사해서 넣어줌
+ 프로젝트폴더 우클릭 > Property > Java Build Path > Libraries > Add JARS.. > lib안에 있는 방금 복사해준 파일 선택하여 추가 > Apply and Close

### 8. 실행 및 확인
+ __Server Run__ => 로그인 페이지 (login.js) <br>
![loginpage](https://user-images.githubusercontent.com/53184797/81998297-1f6cca80-968d-11ea-8f02-0a825fae9acd.png)
+ __데이터베이스에 존재하지 않는 아이디로 로그인한 경우__ => alert
![wrongid](https://user-images.githubusercontent.com/53184797/81998311-2c89b980-968d-11ea-87f9-5013295a4d13.png)
+ __데이터베이스에 존재하는 아이디, 패스워드로 로그인한 경우__ => main.jsp
![loginsuccess](https://user-images.githubusercontent.com/53184797/81998327-314e6d80-968d-11ea-8ce0-32c976810666.png)

## 배포하기
+ 웹 호스팅 서비스 이용 [Cafe24: Tomcat JSP 호스팅](https://www.cafe24.com/?controller=product_page&type=special&page=tomcat)

+ MySQL Web Admin 접속
  + DataBase에 Table 생성

+ DB에 접속하는 부분을 변경 (웹 호스팅 설정에 따라 BbsDAO, UserDAO 의 dbURL, dbID, dbPassword 변경)
+ jdk 버전 DownGrade : Project 속성 > Project Facets > Java Version Downgrade
+ 작성 파일들 모두 실제 웹서버에 올리기
  + Project 속성 > Resource > Loaction 경로 복사해서 해당 폴더로 이동 (프로젝트 폴더)
  + 폴더에 있는 파일들을 웹 서버에 업로드 (웹 서버 접속도구 이용: 파일질라 등)
   + www폴더 안에 BBS폴더 생성 후 프로젝트의 META-INF, WEB-INF를 제외한 모든 파일 업로드
   + class 파일 업로드
		+ 프로젝트 폴더 BBS > build > classes 폴더 안에 있는 모든 클래스 파일을
		+ 웹서버 www폴더 안에 WEB-INF 폴더 > classes 폴더에 업로드

### class 파일 컴파일 오류
+ 웹 서버 재시작 : PUTTY 사용
