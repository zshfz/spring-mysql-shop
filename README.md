#프로젝트/웹사이트_Thymeleaf_Spring_MySQL
# 웹사이트 (Thymeleaf + Spring + MySQL)
## 1. 프로젝트 개요
* Thymeleaf + Spring + MySQL을 웹사이트를 개발함으로써 Spring MVC 구조에 대한 감각을 기르고 로그인/회원가입, 게시글 CRUD, 댓글 등 웹 서비스의 핵심 기능을 구현
* 배포 url : ~[http://zshfzwebsite-env.eba-xdz9qrxw.ap-northeast-2.elasticbeanstalk.com/](http://zshfzwebsite-env.eba-xdz9qrxw.ap-northeast-2.elasticbeanstalk.com/)~

## 2. 개발환경
* 프론트엔드 : Thymeleaf, CSS
* 백엔드 : Spring Boot
* DB : MySQL (Azure Cloud DB)
* 배포 환경 : AWS Elastic Beanstalk

## 3. 사용 기술
### 3.1. 프론트엔드
* Thymeleaf
  * `implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'` ⇒ HTML에 백엔드 데이터를 동적으로 바인딩
  * navbar, footer를 재사용 가능하게 만들어 전체 페이지에 공통 UI 유지
  * `implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.1.RELEASE'` ⇒ HTML에서 로그인 여부에 따라 HTML 랜더링 제어
* CSS
  * Nesting 사용

### 3.2. 백엔드
* Spring Boot
  * 메인 프레임워크
  * Controller, Service, Repository 등 계층 분리를 통한 MVC 구조
* Spring Security
  * `implementation 'org.springframework.boot:spring-boot-starter-security'` ⇒ 로그인 인증 및 URL 접근 권한 제어를 위한 보안 프레임워크
  * `CustomUser`를 통해 사용자 정보 확장 저장
  * `@PreAuthorize` 및 `SecurityConfig`를 통해 요청별 인증 처리 및 리다이렉션 구성
* Spring Session JDBC
  * `implementation 'org.springframework.session:spring-session-jdbc'` ⇒ 로그인 세션 정보를 메모리에 저장하는것이 아니라 MySQL DB에 저장
* Spring Data JPA
  * `implementation 'org.springframework.boot:spring-boot-starter-data-jpa'`
  * JpaRepository 기반의 CRUD 메소드 제공
* MySQL
  * `implementation 'mysql:mysql-connector-java:8.0.33'`
  * Azure 클라우드 기반 MySQL 인스턴스 사용
  * @OneToMany, @ManyToOne 과 같은 양뱡향 연관관계를 통한 게시글-댓글-회원 구조 설계
* AWS
  * `implementation 'io.awspring.cloud:spring-cloud-aws-starter-s3:3.1.1'` ⇒ AWS S3 presigned-url 기반 이미지 업로드 구현
  * 게시글 이미지 및 프로필 이미지를 S3에 저장
  * `S3Presigner` 을 통해 presigned URL 생성하고 그 URL에 PUT 요청으로 이미지 업로드
* 입력값 검증
  * `implementation 'org.springframework.boot:spring-boot-starter-validation'` ⇒ `@Valid`, `@NotBlank`, `@Size`을 사용하여 서버 사이드 유효성 검사 수행
  * `BindingResult`를 통해 오류메시지를 화면에 출력
* Lombok
  * `compileOnly 'org.projectlombok:`
  * `lombok'annotationProcessor 'org.projectlombok:lombok'`
  * getter, setter, 생성자 등 반복되는 코드를 줄이기 위해 사용

## 4. 주요 기능
* 회원가입, 로그인, 로그아웃
* 게시글 CRUD
* 댓글
* 회원 프로필 보기 및 수정
* 이미지 업로드
* 검색
* 페이지네이션

## 5. 폴더 구조
```
src
└── main
    ├── java
    │   └── com.example.website
    │       ├── advice 
    │       │   ├── GlobalControllerAdvice //로그인된 사용자 정보 전역 주입         
    │       │   └── GlobalExceptionHandler //공통 예외 처리        
    │       ├── controller
    │       │   ├── CommentController //댓글 작성 처리              
    │       │   ├── MemberController //로그인, 회원가입, 프로필 수정              
    │       │   ├── PostController //게시글 CRUD, 검색, 페이지네이션                 
    │       │   └── S3Controller //presigned-url 요청 처리                   
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
    │       │   ├── CustomUser //User 클래스 상속받아 확장된 CustomUser                     
    │       │   ├── MyUserDetailsService //사용자 인증 처리         
    │       │   └── SecurityConfig //Spring Security 설정                 
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
* 📁controller : 각 주요 도메인의 웹 요청 처리
* 📁service : 비즈니스 로직 담당
* 📁repository : JPA 기반 데이터 액세스 계층
* 📁dto : 직접 엔티티에 접근하는것은 보안상 좋지 않으므로 데이터 전달 객체 사용
* 📁entity : DB 테이블에 매핑되는 JPA 엔티티 클래스
* 📁security : Spring Security 관련 설정
* 📁advice : @ControllerAdvice 을 사용한 전역 설정
* 📁resources : Tyymeleaf 템플릿, 이미지, 설정파일

## 6. API 명세서
| 구분 | 기능 | HTTP METHOD | API URL | 요청 | 응답 | 에러 |
|------|------|-------------|---------|------|------|------|
| [회원] | 회원가입 | GET | `/register` |  | 회원가입 화면 반환 |  |
|  |  | POST | `/register` | `displayName`,<br />`username`,<br />`password`,<br />`profileImageUrl` | 메인페이지로 리다이렉트 | - 아이디 중복 시 400<br />- 유효성 검사 실패 시 HTML에 오류 표시 |
|  | 로그인 | GET | `/login` |  | 로그인 화면 반환 |  |
|  |  | POST | `/login` | `username`,<br />`password` | 메인페이지로 리다이렉트 | - 아이디 or 비밀번호 불일치 → 로그인 화면에 에러 메시지 |
|  | 로그아웃 | GET | `/logout` |  | 메인페이지로 리다이렉트 |  |
|  | 내 프로필 보기 | GET | `/profile` | 로그인된 사용자 세션 정보 | 프로필 화면 반환 | - 비로그인으로 접근 시 403<br />- 회원정보를 찾을 수 없는 경우 400 |
|  | 프로필 수정 | GET | `/edit-profile/{id}` | 로그인된 사용자 세션 정보,<br />사용자 id | 프로필 수정 화면 반환 | - 비로그인이거나 본인 외 접근 시 403<br />- 존재하지 않는 회원 ID일 경우 400 |
|  |  | POST | `/edit-profile/{id}` | 로그인된 사용자 세션 정보,<br />사용자 id,<br />`displayName`,<br />`profileImageUrl` | 프로필 화면으로 리다이렉트 | - 비로그인이거나 본인 외 접근 시 403<br />- 이미 사용 중인 닉네임으로 수정할 경우 400<br />- 유효성 검사 실패 시 HTML에 오류 표시 |
| [게시글] | 전체 글 목록 조회 | GET | `/` |  | 게시글 목록 화면 반환 |  |
|  | 페이지네이션 | GET | `/board/page/{id}` | 현재 보고싶은 페이지 번호(id) | 해당 페이지의 게시글 목록 반환 |  |
|  | 글쓰기 화면 불러오기 | GET | `/write` |  | 글쓰기 화면 반환 | - 비로그인으로 접근 시 403 |
|  | 글쓰기 | POST | `/write` | `title`,<br />`content`,<br />`postImageUrl`,<br />로그인된 사용자 세션 정보 | 메인화면으로 리다이렉트 | - 비로그인으로 접근 시 403<br />- 작성자 정보 찾을 수 없을 경우 400<br />- 유효성 검사 실패 시 HTML에 오류 표시 |
|  | 글 상세 불러오기 | GET | `/post/{id}` | 게시글 id | 게시글 상세 화면 반환 | - 존재하지 않는 게시글일 경우 400 |
|  | 글 수정화면 불러오기 | GET | `/edit/{id}` | 게시글 id,<br />로그인된 사용자 세션 정보 | 게시글 수정 화면 반환 | - 비로그인이거나 본인 외 접근 시 403 |
|  | 글 수정 | POST | `/edit/{id}` | `title`,<br />`content`,<br />`postImageUrl`,<br />게시글 id | 게시글 상세 화면으로 리다이렉트 | - 비로그인이거나 본인 외 접근 시 403<br />- 유효성 검사 실패 시 HTML에 오류 표시 |
|  | 글 삭제 | POST | `/delete/{id}` | 게시글 id,<br />로그인된 사용자 세션 정보 | 메인화면으로 리다이렉트 | - 비로그인이거나 본인 외 접근 시 403<br />- 게시글이 존재하지 않을 경우 400 |
|  | 검색 | POST | `/search` | `searchText` | 게시글 목록 화면 반환 |  |
| [댓글] | 댓글 작성 | POST | `/comment/{id}` | 게시글 id,<br />`content`,<br />로그인된 사용자 세션 정보 | 게시글 상세 화면으로 리다이렉트 | - 비로그인 시 403<br />- 유효성 검사 실패 시 HTML에 오류 표시<br />- 게시글이 존재하지 않거나 작성자 정보를 찾을 수 없으면 400 |
| [이미지] | presigned-url 발급 | GET | `/presigned-url` | `filename` | presigned URL 문자열 | - `filename` 누락 시 400<br />- AWS 연결 실패 시 500 |

## 7. ERD 다이어그램
![Image](https://github.com/user-attachments/assets/c740abc4-07d9-4478-8c2e-57a227224bc4)
| 테이블명           | 설명                                                           |
|----------------|--------------------------------------------------------------|
| member         | - 회원 테이블<br>- member 테이블은 post, comment 테이블과 각각 1:N 관계       |
| post           | - 게시글 테이블<br>- member_id 컬럼은 member 테이블의 id 컬럼과 연결<br>- post 테이블은 comment 테이블과 1:N 관계 |
| comment        | - 댓글 테이블<br>- comment 테이블은 member, post 테이블과 각각 N:1 관계<br>- member_id, post_id는 각각 member 테이블의 id, post 테이블의 id와 연결 |
| spring_session | - 로그인 사용자 세션 정보를 저장<br>- Spring Session JDBC가 자동 생성          |

## 8. 화면별 기능


## 9. 개선 목표
* RESTapi로 개발해보기
* JWT, OAuth2 사용해서 소셜 로그인 구현해보기
* 아이디/비밀번호 찾기, 이메일 인증, 대댓글, 좋아요, 실시간 인기 게시글, 쪽지, 실시간 채팅 등의 일반적인 웹 사이트 기능을 모두 갖춘 사이트 만들어보기
