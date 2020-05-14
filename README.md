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

### 2. 웹 페이지 파일 만들기
+ WebContent : 웹 페이지 파일 저장하는 폴더 <br>
  + WebContent > New > JSP File > 파일명.jsp

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
