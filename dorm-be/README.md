# dorm-be
***

## To run 🚀

### 1. Build image 🏗️
```shell
docker build -t dorm-backend:dev .
```

### 2. Run compose 🔌
```shell
docker-compose up -d
```

### 3. Test e2e 🧪

```shell
docker-compose -f http/docker-compose.yml up
```

⚠️ remeber to wait for appication to spin up ⚠️

## To work with app 💻

 - To navigate endpoints use swagger 📖
####
 - To requests to app use ijhttp or other favorite http tool like postman ✉️
####
 - Applications requres logging for details see login.http 🔑

## To Update ♻️

```shell
docker ps -f name="dorm" -q | args docker rm -f && \
docker volume prune
git checkout main && git pull && \
docker build -t dorm-backend:dev . && \
docker-compose up -d
```

## To Clean-up 🧹
```shell
docker ps -f name="dorm" -q | xargs docker rm -f && \
docker volume prune -f
```

## To run locally (for debug)

<details>

#### 1. Install java - JDK 17

https://adoptium.net/temurin/releases/

#### 2. Install db - Postgres 15

https://www.postgresql.org/download/

or use docker

```shell
docker run -d --name dorm-db \
--restart=always \
-p 5432:5432 \
-e POSTGRES_USER=dorm \
-e POSTGRES_PASSWORD=dorm \
postgres:15-alpine
```

#### 3. Replace db credentials

```shell
sed -i '' 's/${POSTGRES_USER}/dorm/;s/${POSTGRES_PASSWORD}/dorm/' \
src/main/resources/application.properties
```

#### 4. Run app using gradle
```shell
./gradlew bootRun
```

</details>
