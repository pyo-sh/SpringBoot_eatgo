# EatGo

“Fast Campus 올인원 패키지: Java 웹 개발 마스터” 실전 프로젝트 , 레스토랑  예약 사이트 만들기 

### 원문

![ahastudio:fastcampus-eatgo](https://github.com/ahastudio/fastcampus-eatgo)

### 개발 환경

id 'org.springframework.boot' version '2.2.10.RELEASE'

id 'io.spring.dependency-management' version '1.0.10.RELEASE'

id 'java'

* Tool : IntelliJ IDEA
* TDD : JUnit5 / Mockito
* Library : Lombok / h2database
* database : mariadb

## 프로젝트 관련 명령어

### Test all

TDD 실행 환경에서 gradle로 전체 Test를 실행하는 명령어

```shell
SPRING_PROFILES_ACTIVE=test ./gradlew cleanTest test
```

### Build JAR

API 들에 대해서 build를 실행 ('libs/Jar파일' 을 생성하기 위함)

API 를 실행할 수 있는 환경을 만들 수 있다

```shell
./gradlew bootJar
```

### Install dependencies for web

각 web에 대해서 npm install을 통해 라이브러리를 받아야 실행할 수 있다

명령어의 기준은 eatgo 메인 디렉토리다

```shell
cd eatgo-admin-web && npm install
cd eatgo-customer-web && npm install
cd eatgo-restaurant-web && npm install
```

### Run with Docker

Docker를 활용해 여러 API를 Container를 통해 한 번에 실행하는 환경이다.

```shell
# Docker용 환경 설정 파일을 복사 (.gitignore 처리 되어있음)
# .env 파일은 필요에 따라 수정해서 사용
cp .env.default .env
```

docker-compose.yml을 통해 IDE 에서 실행할 수 있다

또한 Terminal에서 아래의 명령어를 입력하면 실행 가능하다

```shell
# 컨테이너를 모두 실행
docker-compose up
```

명령어로 컨테이너를 실행했다면 다음과 같은 명령어로 확인할 수 있다

```shell
# 전체 상태를 확인할 수 있다
docker-compose ps

# 개별적으로 볼 수 있다
docker-compose logs [컨테이너 이름]
```

#### API 확인

접근할 수 있는 포트 번호는 다음과 같다

* Admin-Api : http://localhost:8002/
* Customer-Api : http://localhost:8003/
* Login-Api : http://localhost:8001/
* Restaurant-Api : http://localhost:8004/

#### 웹 프론트엔드 확인

접근할 수 있는 포트 번호는 다음과 같다

* Admin: http://localhost:8082/
* Customer: http://localhost:8083/
* Restaurant: http://localhost:8084/