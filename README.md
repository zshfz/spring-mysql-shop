# 웹사이트 (Thymeleaf + Spring + MySQL)

## 1. 프로젝트 개요

- Thymeleaf + Spring + MySQL을 웹사이트를 개발함으로써 Spring MVC 구조에 대한 감각을 기르고 로그인/회원가입, 게시글 CRUD, 댓글 등 웹 서비스의 핵심 기능을 구현
- 배포 url : [http://zshfzwebsite-env.eba-xdz9qrxw.ap-northeast-2.elasticbeanstalk.com/](http://zshfzwebsite-env.eba-xdz9qrxw.ap-northeast-2.elasticbeanstalk.com/)

## 2. 개발 환경

- 프론트엔드 : Thymeleaf, CSS
- 백엔드 : Spring Boot
- DB : MySQL (Azure Cloud DB)
- 배포 환경 : AWS Elastic Beanstalk

## 3. 사용 기술

### 프론트엔드

- Thymeleaf
    - `implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'` ⇒ HTML에 백엔드 데이터를 동적으로 바인딩
    - navbar, footer를 재사용 가능하게 만들어 전체 페이지에 공통 UI 유지
    - `implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.1.RELEASE'` ⇒ HTML에서 로그인 여부에 따라 HTML 랜더링 제어
- CSS
    - Nesting 사용

### 백엔드

- Spring Boot
    - 메인 프레임워크
    - Controller, Service, Repository 등 계층 분리를 통한 MVC 구조
- Spring Security
    - `implementation 'org.springframework.boot:spring-boot-starter-security'` ⇒ 로그인 인증 및 URL 접근 권한 제어를 위한 보안 프레임워크
    - `CustomUser`를 통해 사용자 정보 확장 저장
    - `@PreAuthorize` 및 `SecurityConfig`를 통해 요청별 인증 처리 및 리다이렉션 구성
- Spring Session JDBC
    - `implementation 'org.springframework.session:spring-session-jdbc'` ⇒ 로그인 세션 정보를 메모리에 저장하는것이 아니라 MySQL DB에 저장
- Spring Data JPA
    - `implementation 'org.springframework.boot:spring-boot-starter-data-jpa'`
    - JpaRepository 기반의 CRUD 메소드 제공
- MySQL
    - `implementation 'mysql:mysql-connector-java:8.0.33'`
    - Azure 클라우드 기반 MySQL 인스턴스 사용
    - `@OneToMany`, `@ManyToOne` 과 같은 양뱡향 연관관계를 통한 게시글-댓글-회원 구조 설계
- AWS
    - `implementation 'io.awspring.cloud:spring-cloud-aws-starter-s3:3.1.1'` ⇒ AWS S3 presigned-url 기반 이미지 업로드 구현
    - 게시글 이미지 및 프로필 이미지를 S3에 저장
    - `S3Presigner` 을 통해 presigned URL 생성하고 그 URL에 PUT 요청으로 이미지 업로드
- 입력값 검증
    - `implementation 'org.springframework.boot:spring-boot-starter-validation'` ⇒ `@Valid`, `@NotBlank`, `@Size`을 사용하여 서버 사이드 유효성 검사 수행
    - `BindingResult`를 통해 오류메시지를 화면에 출력
- Lombok
    - `compileOnly 'org.projectlombok:`
    - `lombok'annotationProcessor 'org.projectlombok:lombok'`
    - getter, setter, 생성자 등 반복되는 코드를 줄이기 위해 사용

## 4. 주요 기능

[제목 없음](%E1%84%8B%E1%85%B0%E1%86%B8%E1%84%89%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%90%E1%85%B3%20(Thymeleaf%20+%20Spring%20+%20MySQL)%2020dd41c2a13b8056840ec40e3d66f7b8/%E1%84%8C%E1%85%A6%E1%84%86%E1%85%A9%E1%86%A8%20%E1%84%8B%E1%85%A5%E1%86%B9%E1%84%8B%E1%85%B3%E1%86%B7%2020dd41c2a13b80829f1af8afdd89b3c4.csv)

- 회원가입, 로그인, 로그아웃
- 게시글 CRUD
- 댓글
- 회원 프로필 보기 및 수정
- 이미지 업로드
- 검색
- 페이지네이션

## 5. 폴더 구조

```jsx
src
└── main
    ├── java
    │   └── com.example.website
    │       ├── advice 
    │       │   ├── GlobalControllerAdvice     //로그인된 사용자 정보 전역 주입         
    │       │   └── GlobalExceptionHandler     //공통 예외 처리        
    │       ├── controller
    │       │   ├── CommentController          //댓글 작성 처리              
    │       │   ├── MemberController           //로그인, 회원가입, 프로필 수정              
    │       │   ├── PostController             //게시글 CRUD, 검색, 페이지네이션                 
    │       │   └── S3Controller               //presigned-url 요청 처리                   
    │       ├── dto
    │       │   ├── CommentRequest
    │       │   ├── EditProfileRequest
    │       │   ├── PostRequest
    │       │   └── RegisterRequest
    │       ├── entity
    │       │   ├── Comment
    │       │   ├── Member
    │       │   └── Post
    │       ├── repository
    │       │   ├── CommentRepository
    │       │   ├── MemberRepository
    │       │   └── PostRepository
    │       ├── security
    │       │   ├── CustomUser                 //User 클래스 상속받아 확장된 CustomUser                     
    │       │   ├── MyUserDetailsService       //사용자 인증 처리         
    │       │   └── SecurityConfig             //Spring Security 설정                 
    │       ├── service
    │       │   ├── CommentService
    │       │   ├── MemberService
    │       │   ├── PostService
    │       │   └── S3Service
    │       └── WebsiteApplication                 
    └── resources
        ├── static                                
        ├── templates                              
        ├── application.properties                

```

- 📁controller : 각 주요 도메인의 웹 요청 처리
- 📁service : 비즈니스 로직 담당
- 📁repository : JPA 기반 데이터 액세스 계층
- 📁dto : 직접 엔티티에 접근하는것은 보안상 좋지 않으므로 데이터 전달 객체 사용
- 📁entity : DB 테이블에 매핑되는 JPA 엔티티 클래스
- 📁security : Spring Security 관련 설정
- 📁advice : `@ControllerAdvice` 을 사용한 전역 설정
- 📁resources : Tyymeleaf 템플릿, 이미지, 설정파일
