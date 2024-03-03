# Velog

<br/>

## 프로젝트 기간 
2023-01 ~ 2023-03

<br/>

## 개요 
개발자를 위한 블로그 서비스

<br/>

## 기술 구성
언어 및 프레임워크: Java/Spring Boot, Spring Data JPA, MySQL, Junit5, Redis, Spring-Event, Querydsl
<br/>

<br/>

## ERD
<img width="2186" alt="velogerd" src="https://github.com/yoonezi/velog_BE/assets/101792929/d372e4b0-92ee-4db2-b2f9-e5091fa08554">

<br/>

## 프로젝트 구현과정 및 고려사항
  - 피드 조회 성능 개선
      - 문제 : 쿼리를 통해 작성한 피드를 팔로워들의 뉴스 피드 공간에 피드 데이터를 생성해주는 과정은 DB의 잦은 read 연산에 따라 비효율적인 과정이라고 판단
      - 해결 : Redis을 활용한 피드 발행 (Fan Out On Write) API 개발, Message Queue를 이용한 실시간성 데이터를 기반으로 피드를 전달하도록 구현.
      
  - Spring Event를 이용하여 서비스 간의 강한 의존성을 낮춤
      - 문제 : 피드 발행 기능이 팔로우, 댓글, 좋아요 서비스에 직접 구현되어 있을 경우 강한 의존성이 생김
      - 해결 :  Spring Event를 이용하여 강한 의존성을 줄이고, 이벤트로 분리된 부분을 비동기 방식으로 처리하게 되어 전체 프로세스가 끝나는 시간이 짧아지는 성능 측면 개선
        
  - Test
      - @SpringBootTest를 이용한 통합테스트
      - @DataJpaTest, @WebMvcTest를 이용한 슬라이드 테스트 
