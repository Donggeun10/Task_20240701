# Backend Server
- Spring Boot 기반의 RESTful API 서버 구현

## 1. Frameworks And Tools
- JDK 21
- spring-boot 3.2.8
- spring-boot-web
- spring-boot-data-jpa
- springdoc-openapi
- jackson-dataformat-csv
- h2database
- lombok

## 2. Api Spec 확인 주소
- http://localhost:8080/swagger-ui.html

## 3. 주요 Endpoints
- GET /api/employees
- GET /api/employees/{name}
- GET /api/employees/tel/{tel}
- POST /api/employees
- DELETE /api/employees/tel/{tel}

## 4. 데이터 규격
### 4.1. Employee
- name: String
- email: String
- tel: String (010-1234-5678)
- joined: Date (yyyy-MM-dd)

## 5. 테스트용 샘플 데이터
- csv 와 json 형식으로 제공, 그 중 하나를 선택하여 사용해야 함.

### 5.1. CSV
- csv 는 헤더 항목이 없어야 함.
```
김철수, charles@clovf.com, 010-7531-2468, 2018-03-07
박영희, matilda@clovi.com, 010-8765-4321, 2021-04-28
홍길동, kildong.hong@clovf.com, 010-1234-5678, 2015-08-15
```
### 5.2. JSON
```
[
{"name": "김클로", "email":"clo@clovf.com", "tel":"010-1111-2424", "joined":"2012-01-05"},
{"name": "박마블","email":"md@clovf.com", "tel":"010-3535-7979", "joined": "2013-07-01" },
{"name": "홍커넥", "email":"connect@clovf.com", "tel":"010-8531-7942","joined":"2019-12-05"}
]
```

## 6. Docker Image 생성 명령어
- 로컬 소스를 이용해서 빌드하는 경우
```
docker build -t demo-api:local .  && docker run -p 9090:8080  -e"SPRING_PROFILES_ACTIVE=local"  demo-api:local
```
- git clone 후 빌드하는 경우
```
docker build -t demo-api:local -f Dockerfile_git . && docker run -p 9090:8080  -e"SPRING_PROFILES_ACTIVE=local"  demo-api:local
```