# Pet's Care
반려인 1000만 시대, 지친 여러분들과 함께 반려동물을 돌봐줄 돌봄이
<details open>
<summary>Skills</summary>
	
 - ### Backend
   - <img src="https://img.shields.io/badge/Java-007396?style=flat-square&amp;logo=Java&amp;logoColor=white">
   - <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&amp;logo=spring&amp;logoColor=white"> <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&amp;logo=springboot&amp;logoColor=white"> <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&amp;logo=springsecurity&amp;logoColor=white"> <img src="https://img.shields.io/badge/Junit5-6DB33F?style=flat-square&amp;logo=junit5&amp;logoColor=white">
   - <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=flat-square&amp;logo=java&amp;logoColor=white"> <img src="https://img.shields.io/badge/QueryDSL-007396?style=flat-square&amp;logo=Java&amp;logoColor=white">
   - <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&amp;logo=MySQL&amp;logoColor=white"> <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&amp;logo=Redis&amp;logoColor=white"> <img src="https://img.shields.io/badge/H2-007396?style=flat-square&amp;logo=Java&amp;logoColor=white">
 - ### Frontend
   - <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&amp;logo=JavaScript&amp;logoColor=white"> <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&amp;logo=HTML5&amp;logoColor=white"> <img src="https://img.shields.io/badge/css3-1572B6?style=flat-square&amp;logo=css3&amp;logoColor=white">
   - <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&amp;logo=Thymeleaf&amp;logoColor=white"> <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=flat-square&amp;logo=HTML5&amp;logoColor=white">
- ### Etc
   - <img src="https://img.shields.io/badge/Intellij_IDEA-000000?style=flat-square&amp;logo=intellijidea&amp;logoColor=white">
   - <img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&amp;logo=GitHub&amp;logoColor=white"> <img src="https://img.shields.io/badge/Sourcetree-0052CC?style=flat-square&amp;logo=Sourcetree&amp;logoColor=white">
</details>

<details open>
  <summary>Config</summary>
  
- #### properties 또는 yml 파일에 설정해야 할 필수 내용, 본 설명은 yml 기준으로 작성됨
  <details open>
    <summary>DB</summary>
    
    - MySQL(현재는 적용X)

      ```
      spring:
        datasource:
          url: jdbc:mysql://localhost:3306/생성한 DB명?serverTimezone=UTC&characterEncoding=UTF-8
          username: 이름
          password: 암호
          driver-class-name: com.mysql.cj.jdbc.Driver
      ```
    - H2

      ```
      spring:
        datasource:
          url: jdbc:h2:tcp://localhost/~/생성한 DB명;
          username: 이름
          password: 암호
          driver-class-name: org.h2.Driver
      ```
    - Redis

      ```
      spring:
        redis:
          host: localhost
          port: 6379
      ```
  </details>
  <details open>
    <summary>Spring Data JPA</summary><br />

      jpa:
        hibernate:
          ddl-auto: create (운영 단계에서는 설정에 따른 옵션 변경)
        properties:
          hibernate:
            format_sql: true (로그에 나오는 쿼리문이 좀 더 보기 좋게 출력)
  </details>
  <details open>
    <summary>Session</summary><br />

      spring:
        session:
          timeout: 30m (기본 30분)
          store-type: redis (세션 저장 방식 redis로 변경)
          redis:
            flush-mode: on_save (호출될 때만 저장)
  </details>
  <details open>
    <summary>Messages, Errors</summary><br />

      spring:
          messages:
            basename: messages, errors (참조할 설정 파일의 기본 이름을 설정)
  </details>
  <details open>
    <summary>Spring Security OAuth2</summary>
    
    - 해당 기능 아용 전, 각 사이트에서 개발자 등록 후 이용 가능
    - 네이버 : <https://developers.naver.com/docs/login/api/api.md/>
    - 카카오 : <https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api>

      ```
      spring:
        security:
          oauth2:
            client:
              registration:
                naver:
                  client-name: naver
                  client-id: 클라이언트 ID
                  client-secret: 클라이언트 Secret
                  redirect-uri: http://localhost:8080/login/oauth2/code/naver
                  authorization-grant-type: authorization_code
                  scope: nickname, profile_image
                google:  (아직 지원하지 않음)
                  client-name: google
                  client-id: 클라이언트 ID
                  client-secret: 클라이언트 Secret
                  redirect-uri: http://localhost:8080/login/oauth2/code/google
                  authorization-grant-type: authorization_code
                  scope: profile, email
                kakao:
                  client-name: kakao
                  client-id: 앱 키
                  redirect-uri: http://localhost:8080/login/oauth2/code/kakao
                  authorization-grant-type: authorization_code
                  scope: profile_nickname, profile_image
      
              provider: (google, facebook 등의 글로벌 사이트 같은 경우 Spring에서 자동으로 제공해주지만 국내 기업은 그렇지 않음)
                naver:
                  authorization-uri: https://nid.naver.com/oauth2.0/authorize
                  token-uri: https://nid.naver.com/oauth2.0/token
                  user-info-uri: https://openapi.naver.com/v1/nid/me
                  user-name-attribute: response
                kakao:
                  authorization_uri: https://kauth.kakao.com/oauth/authorize
                  token_uri: https://kauth.kakao.com/oauth/token
                  user-info-uri: https://kapi.kakao.com/v2/user/me
                  user_name_attribute: id
      ```
  </details>
  <details open>
    <summary>Log</summary><br />

      logging:
        level:
          org.hibernate.SQL: debug (로그 레벨을 디버그로 설정)
  </details>
</details>
