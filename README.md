# ▶️[Spring 6기] 아웃소싱 프로젝트


------

## ▶️프로젝트 소개

* 팀 이름: OverCoding

* 개발 기간 : 2025.04.22 ~ 2025.04.29

* 프로젝트 설명 : **배달 어플리케이션 개발에 대한 아웃소싱 프로젝트**

* 팀장: 진형국

* 팀원: 김윤범, 이종현, 류경선, 김준영

------

## ▶️목적

**배달 어플리케이션 개발에 대한 아웃소싱 프로젝트**

 1. 회원가입/로그인

 2. 가게

 3. 메뉴

 4. 주문

 5. 리뷰

------

##Stacks

Environoment

 <img src="https://img.shields.io/badge/Intellij-000000?style=for-the-badge&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white">

Development

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=&logoColor=white"> 

Frmaework

<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 

DB

<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/Redis-FF4438?style=for-the-badge&logo=redis&logoColor=white"> 

Communication

<img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"> <img src="https://img.shields.io/badge/notion-333333?style=for-the-badge&logo=notion&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> 

------

## ▶️ERD

![image](https://github.com/user-attachments/assets/b533db31-d644-4fea-94dc-c07836926734)

-------

## ▶️API 명세서

## Auth API

| **Method** | **Endpoint**  | **Description**                            | **Parameters** | **Request Body**                                                                                                                                         | **Response**                                  | **Status Code** |
|------------|---------------|--------------------------------------------|----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------|-----------------|
| `POST`     | `/signup`     | 회원가입 <br> - 정보 입력 및 확인 <br> - 비밀번호 암호화 <br> - 정보 저장 | 없음             | `{ "email": "string", "password": "string", "passwordCheck": "string", "userRole": "string", "name": "string", "nickname": "string", "lotNumberAddress": "string", "detailAddress": "string" }` | `{ "bearerToken": "string" }`                  | `201 Created`   |
| `POST`     | `/login`      | 로그인 <br> - 정보 입력 및 유효성 검사 <br> - 토큰 발급 | 없음             | `{ "email": "string", "password": "string" }`                                                                                                              | `{ "bearerToken": "string" }`                  | `200 OK`        |
| `POST`     | `/logout`     | 로그아웃 <br> - 토큰 블랙리스트 저장       | 없음             | 없음                                                                                                                                                     | 없음                                           | `200 OK`        |
| `POST`     | `/refresh`    | 토큰 재발급 <br> - 리프레시 토큰 이용        | 없음             | 없음                                                                                                                                                     | `{ "bearerToken": "string" }`                  | `200 OK`        |

## Member API

| **Method** | **Endpoint**         | **Description**                        | **Parameters** | **Request Body** | **Response** | **Status Code** |
|------------|----------------------|----------------------------------------|----------------|------------------|--------------|-----------------|
| `GET`      | `/members/me`         | 내 정보 조회 <br> - 로그인 유저 정보 반환 | 없음             | 없음             | `{ "id": long, "userRole": "string", "name": "string", "nickname": "string", "birth": "string", "defaultAddress": "string", "createTime": "string", "modifiedTime": "string" }` | `200 OK` |
| `GET`      | `/members/{userId}`   | 타인 정보 조회 <br> - 매니저 권한 확인 및 정보 반환 | **Path:** <br> - `userId`: 유저 식별 번호 (Long) | 없음             | `{ "id": "string", "nickname": "string", "address": "string", "createTime": "string", "memberStoreOrderCountDtoList": [ { "storeName": "string", "orderCount": int } ] }` | `200 OK` |
| `PATCH`    | `/members/{id}`        | 내 정보 수정 <br> - 비밀번호 재인증 및 정보 수정 | **Path:** <br> - `id`: 유저 식별 번호 (Long) | `{ "oldPassword": "string", "newPassword": "string", "newPasswordCheck": "string", "nickname": "string", "birth": "string" }` | `{ "id": long, "userRole": "string", "name": "string", "nickname": "string", "birth": "string", "defaultAddress": "string", "createTime": "string", "modifiedTime": "string" }` | `200 OK` |
| `DELETE`   | `/members`             | 회원 탈퇴 <br> - 회원 상태 비활성화 및 로그아웃 처리 | 없음             | 없음             | 없음 | `200 OK` |

## Address API

| **Method** | **Endpoint**         | **Description**                             | **Parameters** | **Request Body** | **Response** | **Status Code** |
|------------|----------------------|---------------------------------------------|----------------|------------------|--------------|-----------------|
| `POST`     | `/addresses`          | 주소 저장 <br> - 입력 정보 확인 및 주소 타입을 `NORMAL`로 저장 | 없음             | `{ "lotNumberAddress": "string", "detailAddress": "string" }` | `[ { "id": long, "email": "string", "fullAddress": "string", "addressType": "string" } ]` | `201 Created` |
| `GET`      | `/addresses`          | 주소 조회 <br> - 로그인된 사용자의 주소 리스트 조회 | 없음             | 없음             | `[ { "id": long, "email": "string", "fullAddress": "string", "addressType": "string" } ]` | `200 OK` |
| `PATCH`    | `/addresses/{id}`     | 대표 주소 변경 <br> - 전체 주소를 `NORMAL`로 변경 후, 선택한 주소를 대표 주소로 변경 | **Path:** <br> - `id`: 주소 식별 번호 (Long) | 없음             | `[ { "id": long, "email": "string", "fullAddress": "string", "addressType": "string" } ]` | `200 OK` |
| `GET`      | `/addresses/{id}`     | 주소 삭제 <br> - 선택한 식별번호의 주소 삭제 | **Path:** <br> - `id`: 주소 식별 번호 (Long) | 없음             | 없음 | `200 OK` |

## Store API

| **Method** | **Endpoint**            | **Description**                           | **Parameters** | **Request Body** | **Response** | **Status Code** |
|------------|---------------------------|-------------------------------------------|----------------|------------------|--------------|-----------------|
| `POST`     | `/stores`                 | 가게 등록 <br> - 가게 정보 저장             | 없음            | `{ "storeName": "string", "storeContent": "string", "openTime": "string", "closeTime": "string", "minimumOrderAmount": "string" }` | `{ "id": long, "storeName": "string", "storeContent": "string", "openTime": "string", "closeTime": "string", "minimumOrderAmount": int, "creatTime": "string", "modifiedTime": "string", "storeStatus": "string", "menuName": "string or null", "countStore": int }` | `200 OK` |
| `PATCH`    | `/stores/{storeId}`        | 가게 수정 <br> - 기존 가게 정보 수정         | **Path:** <br> - `storeId`: 가게 식별 번호 (Long) | `{ "storeName": "string", "storeContent": "string", "openTime": "string", "closeTime": "string", "minimumOrderAmount": "string", "storeStatus": "OPEN" }` | `{ "id": long, "storeName": "string", "storeContent": "string", "openTime": "string", "closeTime": "string", "minimumOrderAmount": int, "creatTime": "string", "modifiedTime": "string", "storeStatus": "string", "menuName": "string or null" }` | `200 OK` |
| `DELETE`   | `/stores/{storeId}`        | 가게 삭제 <br> - 가게 삭제 처리             | **Path:** <br> - `storeId`: 가게 식별 번호 (Long) | 없음             | "가게 삭제가 완료되었습니다." | `200 OK` |
| `GET`      | `/store/stores?keyword=`   | 가게 목록 조회 (메뉴 제외) <br> - 키워드 검색 및 페이징 | **Query:** <br> - `keyword`: 검색 키워드 (String) | 없음             | `{ "content": [ { "id": long, "storeName": "string", "storeContent": "string", "openTime": "string", "closeTime": "string", "minimumOrderAmount": int, "creatTime": "string", "modifiedTime": "string", "storeStatus": "string" } ], "pageable": { ... }, "numberOfElements": int, "first": boolean, "last": boolean, "size": int, "number": int, "sort": { ... }, "empty": boolean }` | `200 OK` |
| `GET`      | `/stores/{storeId}`        | 특정 가게 상세 조회 (메뉴 포함)               | **Path:** <br> - `storeId`: 가게 식별 번호 (Long) | 없음             | `{ "storeName": "string", "storeContent": "string", "openTime": "string", "closeTime": "string", "creatTime": "string" }` | `200 OK` |

## Menu API

| **Method** | **Endpoint**                         | **Description**                | **Parameters** | **Request Body** | **Response** | **Status Code** |
|------------|--------------------------------------|---------------------------------|----------------|------------------|--------------|-----------------|
| `POST`     | `/stores/{storeId}/menus`             | 메뉴 등록 <br> - 특정 가게에 메뉴 추가 | **Path:** <br> - `storeId`: 가게 식별 번호 (Long) | `{ "name": "string", "price": "string", "description": "string" }` | `{ "id": long, "name": "string", "price": int, "description": "string", "storeName": "string", "menuStatus": "string" }` | `200 OK` |
| `DELETE`   | `/stores/{storeId}/menus/{menuId}`    | 메뉴 삭제 <br> - 특정 메뉴 삭제   | **Path:** <br> - `storeId`: 가게 식별 번호 (Long) <br> - `menuId`: 메뉴 식별 번호 (Long) | 없음             | "메뉴가 삭제 완료되었습니다!" | `200 OK` |
| `PATCH`    | `/stores/{storeId}/menus/{menuId}`    | 메뉴 수정 <br> - 특정 메뉴 수정   | **Path:** <br> - `storeId`: 가게 식별 번호 (Long) <br> - `menuId`: 메뉴 식별 번호 (Long) | `{ "name": "string", "price": "string", "description": "string", "menuStatus": "string" }` | `{ "id": long, "name": "string", "price": int, "description": "string", "storeName": "string", "menuStatus": "string" }` | `200 OK` |

## Order API

| **Method** | **Endpoint**                  | **Description**                                | **Parameters** | **Request Body** | **Response** | **Status Code** |
|------------|---------------------------------|------------------------------------------------|----------------|------------------|--------------|-----------------|
| `POST`     | `/orders`                      | 주문 생성 <br> - 최소 주문 금액, 영업시간 체크 | 없음             | `{ "storeId": 1, "menuId": 5, "quantity": 2 }` | `{ "orderId": 10, "storeId": 1, "totalPrice": 16000, "orderStatus": "WAITING", "orderTime": "2025-04-22T12:00:00" }` | `201 Created` |
| `PATCH`    | `/orders/{orderId}/status`      | 주문 상태 변경 <br> - 상태 순서대로 변경, 사장님 권한 필요 | **Path:** <br> - `orderId`: 주문 식별 번호 (Long) | `{ "orderStatus": "WAITING" }` | `{ "orderId": 10, "orderStatus": "CONFIRM", "newStatusTime": "2025-04-22T12:00:00" }` | `200 OK` |
| `GET`      | `/orders/{orderId}`             | 주문 상세 조회 | **Path:** <br> - `orderId`: 주문 식별 번호 (Long) | 없음             | `{ "orderId": 10, "storeId": 1, "menuName": "치킨", "quantity": 2, "totalPrice": 16000, "orderStatus": "COOKING", "orderTime": "2025-04-22T12:00:00" }` | `200 OK` |
| `DELETE`   | `/orders/{orderId}`             | 주문 삭제 <br> - 주문자(본인)만 삭제 가능 | **Path:** <br> - `orderId`: 주문 식별 번호 (Long) | 없음             | 없음 | `200 OK` |

## Review API

| **Method** | **Endpoint**          | **Description**                   | **Parameters** | **Request Body** | **Response** | **Status Code** |
|------------|------------------------|------------------------------------|----------------|------------------|--------------|-----------------|
| `POST`     | `/reviews`              | 리뷰 등록                          | 없음             | `{ "contents": "string", "rating": int }` | `"리뷰 등록이 완료되었습니다."` | `201 Created` |
| `GET`      | `/reviews`              | 리뷰 전체 조회                     | 없음             | 없음             | `[ { "reviewId": long, "contents": "string", "rating": int, "memberId": long, "menuId": long, "storeId": long, "orderId": long }, ... ]` | `200 OK` |
| `GET`      | `/reviews/{storeId}`     | 상점별 리뷰 조회                    | **Path:** <br> - `storeId`: 상점 식별 번호 (Long) | 없음             | `[ { "reviewId": long, "contents": "string", "rating": int, "memberId": long, "menuId": long, "storeId": long, "orderId": long }, ... ]` | `200 OK` |
| `GET`      | `/reviews/search`        | 리뷰 별점 검색 전체 조회             | 없음             | `{ "min": int, "max": int }` | `[ { "reviewId": long, "contents": "string", "rating": int, "memberId": long, "menuId": long, "storeId": long, "orderId": long }, ... ]` | `200 OK` |
| `PATCH`    | `/reviews/{id}`           | 리뷰 수정 (단건)                    | **Path:** <br> - `id`: 리뷰 식별 번호 (Long) | `{ "contents": "string", "rating": int }` | `{ "reviewId": long, "contents": "string", "rating": int }` | `200 OK` |
| `DELETE`   | `/reviews/{id}`           | 리뷰 삭제 (단건)                    | **Path:** <br> - `id`: 리뷰 식별 번호 (Long) | 없음             | `1` | `200 OK` |






## 테스트 코드(커버리지)

![image](https://github.com/user-attachments/assets/0631bf47-cf06-40b3-b721-8e1125fc3733)
