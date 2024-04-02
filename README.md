# Velog
## 프로젝트 기간 
2023-01 ~ 2023-03

<br/>


## 🛠 기술 구성
- 언어 및 프레임워크: Java/Spring Boot, Spring Data JPA, MySQL, Junit5, Redis, Spring-Event, Querydsl

<br/>


# :bookmark: 개요 <a name = "outline"></a>
개발자를 위한 블로그 서비스


<br/>

## ERD
<img width="2186" alt="velogerd" src="https://github.com/yoonezi/velog_BE/assets/101792929/d372e4b0-92ee-4db2-b2f9-e5091fa08554">

<br/><br/>

## 프로젝트 구현과정 및 고려사항
  - 피드 조회 성능 개선
      - 문제 : 쿼리를 통해 작성한 피드를 팔로워들의 뉴스 피드 공간에 피드 데이터를 생성해주는 과정은 DB의 잦은 read 연산에 따라 비효율적인 과정이라고 판단
      - 해결 : Redis을 활용한 피드 발행 (Fan Out On Write) API 개발, Message Queue를 이용한 실시간성 데이터를 기반으로 피드를 전달하도록 구현.
      
  - Spring Event를 이용하여 서비스 간의 강한 의존성을 낮춤
      - 문제 : 피드 발행 기능이 팔로우, 댓글, 좋아요 서비스에 직접 구현되어 있을 경우 강한 의존성이 생김
      - 해결 :  Spring Event를 이용하여 강한 의존성을 줄이고, 이벤트로 분리된 부분을 비동기 방식으로 처리하게 되어 전체 프로세스가 끝나는 시간이 짧아지는 성능 측면 개선
   
  - n+1 문제
      - 문제 : 게시글 조회시 N+1 쿼리가 발생
      - 해결 :Fetch Join과 Batch Size 조절을 통해 API 조회 성능 0.7s > 0.3s로 2배 성능 향상
   
  - Test
      - @SpringBootTest를 이용한 통합테스트
      - @DataJpaTest, @WebMvcTest를 이용한 슬라이드 테스트

<br/><br/>

## ✨ Velog FE
화면 구현에 대한 자세한 설명은 아래 링크를 참고해주세요!
https://github.com/yoonezi/velog_FE

<br/><br/>

# 🖥️ 결과물 <a name = "result"></a>
## 홈
<img width="1000" alt="스크린샷 2024-04-02 오후 4 51 03" src="https://github.com/yoonezi/velog_FE/assets/101792929/17c4d134-43a4-4a3c-ab5e-6cd8930ab073">

### 홈 - 페이징
<img width="1439" alt="스크린샷 2024-04-02 오후 6 38 27" src="https://github.com/yoonezi/velog_FE/assets/101792929/bb7a8836-c296-4eb7-af27-1b9415840a58">


## 헤더
<p align="center">
<img width="756" alt="스크린샷 2024-04-02 오후 5 01 01" src="https://github.com/yoonezi/velog_FE/assets/101792929/f0e7cf05-15e2-4749-8ea2-9402ba39f0c1">
</p>


## 피드
<img width="1440" alt="스크린샷 2024-04-02 오후 6 18 05" src="https://github.com/yoonezi/velog_FE/assets/101792929/9fb5f7a4-5d58-4ced-92e2-484d8337e5f3">
<img width="1440" alt="스크린샷 2024-04-02 오후 5 52 03" src="https://github.com/yoonezi/velog_FE/assets/101792929/fa7ed7b3-c9ef-428e-b162-2b93ad6280d6">


## 포스트
<img width="1440" alt="스크린샷 2024-04-02 오후 5 02 16" src="https://github.com/yoonezi/velog_FE/assets/101792929/a22f42d6-afe7-4f96-a52f-431ad3ec324d">
<img width="1440" alt="스크린샷 2024-04-02 오후 5 02 29" src="https://github.com/yoonezi/velog_FE/assets/101792929/b94c5a28-1a61-4d7f-adc6-3dbf7d3b9f7d">


#### 포스트 - 자신의 포스트
<img width="1440" alt="스크린샷 2024-04-02 오후 5 31 43" src="https://github.com/yoonezi/velog_FE/assets/101792929/4cb8a82a-93f2-44bd-a141-a157073782f9">


#### 포스트 - 다른 유저의 포스트
<img width="1440" alt="스크린샷 2024-04-02 오후 5 50 35" src="https://github.com/yoonezi/velog_FE/assets/101792929/a07d15e2-8763-4f98-a7f7-aee6bbddf50c">
<img width="1440" alt="스크린샷 2024-04-02 오후 6 34 10" src="https://github.com/yoonezi/velog_FE/assets/101792929/7638df7a-4cdd-4276-8a2b-d94566c04fe5">

#### 포스트 좋아요 & 댓글
<img width="1440" alt="스크린샷 2024-04-02 오후 6 34 39" src="https://github.com/yoonezi/velog_FE/assets/101792929/ef33d128-03c1-4112-acf9-74cb180ce4d7">

### 포스트 작성
<img width="1440" alt="스크린샷 2024-04-02 오후 5 10 11" src="https://github.com/yoonezi/velog_FE/assets/101792929/4a6c9531-8053-41ba-9043-24543728d87a">

#### 포스트 작성 - 카테고리
<img width="1440" alt="스크린샷 2024-04-02 오후 5 10 37" src="https://github.com/yoonezi/velog_FE/assets/101792929/24f73ba1-1243-445e-93fb-736e9fc6b812">

#### 포스트 작성 - 태그
<img width="1440" alt="스크린샷 2024-04-02 오후 5 10 45" src="https://github.com/yoonezi/velog_FE/assets/101792929/1a98067d-861a-4195-add7-2b82d2ee9e61">

#### 포스트 작성 - 이미지 업로드
<img width="1440" alt="스크린샷 2024-04-02 오후 5 11 32" src="https://github.com/yoonezi/velog_FE/assets/101792929/c3721e1d-5dab-4945-9f43-17d74900c89a">

### 포스트 수정
<img width="992" alt="image" src="https://github.com/yoonezi/velog_FE/assets/101792929/719f9428-f0e0-4718-b55a-2f025fd9dfbc">

### 임시 저장 글
<img width="1200" alt="image" src="https://github.com/yoonezi/velog_FE/assets/101792929/4cd3bbe1-2386-4faf-814d-a321a9bbdf2e">

### User Page
<img width="1440" alt="스크린샷 2024-04-02 오후 5 52 49" src="https://github.com/yoonezi/velog_FE/assets/101792929/80054b65-3d78-431e-ab90-e58a8dd8ca0e">

#### User Page - Follow Button
<img width="1440" alt="스크린샷 2024-04-02 오후 6 23 10" src="https://github.com/yoonezi/velog_FE/assets/101792929/6f40c82b-8109-468e-9e9d-a27acc1d4933">
<img width="1440" alt="스크린샷 2024-04-02 오후 6 23 07" src="https://github.com/yoonezi/velog_FE/assets/101792929/0eec1407-fa4e-4e13-87b1-273bff662dd0">

### Follow
#### Page - Following
<img width="1440" alt="스크린샷 2024-04-02 오후 6 21 00" src="https://github.com/yoonezi/velog_FE/assets/101792929/91ea0126-9544-469c-87c2-efc6e048c222">

#### Page- Follower
<img width="1440" alt="스크린샷 2024-04-02 오후 5 52 21" src="https://github.com/yoonezi/velog_FE/assets/101792929/78d94a34-6b99-4e53-adaa-0f82ddf45720">

### Login
<img width="1440" alt="스크린샷 2024-04-02 오후 5 07 13" src="https://github.com/yoonezi/velog_FE/assets/101792929/e510f1dd-c6e3-40ec-a3d2-ec94448056b2">

### Join
<img width="1440" alt="image" src="https://github.com/yoonezi/velog_FE/assets/101792929/bf2d7523-bed9-44e9-ad37-43e96b89aa18">
