# 위준우

## 요구사항

- Java 1.8
- Spring-boot 2.7.8
- h2 database 2.1.214

## 프로젝트 설치

### MacOS

```
git clone https://github.com/wijoonwu/community.git
cd community
./gradlew bootRun
```

### Windows

```
git clone https://github.com/wijoonwu/community.git
cd community
gradlew bootRun
```

## H2 Database 설치

### MacOS or Windows

- https://atoz-develop.tistory.com/entry/H2-Database-%EC%84%A4%EC%B9%98-%EC%84%9C%EB%B2%84-%EC%8B%A4%ED%96%89-%EC%A0%91%EC%86%8D-%EB%B0%A9%EB%B2%95

> database의 이름은 반드시 'community'로 설정합니다.
> (ex. url: jdbc:h2:tcp://localhost/~/community)

## 실행 방법

Postman 혹은 Talend API 등의 프로그램을 이용해서 아래와 같이 테스트 합니다.

### 게시글 작성

- Method: POST
- Url: http://localhost:8080/article/new

#### Header

- Content-Type : application/json
- Authentication : Realtor 1

#### Body

```
{
    "title" : "안녕하세요",
    "content" : "하하하하하"
}
```

#### Response

```
{
    "status": "OK",
    "message": "게시글 등록에 성공했습니다.",
    "data": {
        "id": 3,
        "writer": "김양(공인중개사)",
        "title": "안녕하세요",
        "content": "하하하하하",
        "thumbsUpCount": 0,
        "thumbsUpStatus": false,
        "created": "2023/02/15 03:43",
        "modified": "2023/02/15 03:43"
    }
}
```

### 게시글 단건 조회

- Method: GET
- Url: http://localhost:8080/article/{id}

#### Header

- Content-Type : application/json
- Authentication : Realtor 1

#### Response

```
{
    "id": 1,
    "writer": "김양(공인중개사)",
    "title": "첫 게시글 입니다!",
    "content": "만나서 반갑습니다",
    "thumbsUpCount": 0,
    "thumbsUpStatus": false,
    "created": "2023-02-15 03:43",
    "modified": "2023-02-15 03:43"
}
```

### 게시글 전체 조회

- Method: GET
- Url: http://localhost:8080/article/list

#### Header

- Content-Type : application/json
- Authentication : Realtor 1

#### Response

```
[
    {
        "id": 1,
        "writer": "김양(공인중개사)",
        "title": "첫 게시글 입니다!",
        "content": "만나서 반갑습니다",
        "thumbsUpCount": 0,
        "thumbsUpStatus": false,
        "created": "2023-02-15 03:43",
        "modified": "2023-02-15 03:43"
    },
    {
        "id": 2,
        "writer": "김양(공인중개사)",
        "title": "두번째 게시글 입니다!",
        "content": "잘 부탁드립니다.",
        "thumbsUpCount": 0,
        "thumbsUpStatus": false,
        "created": "2023-02-15 03:43",
        "modified": "2023-02-15 03:43"
    },
    {
        "id": 3,
        "writer": "김양(공인중개사)",
        "title": "안녕하세요",
        "content": "하하하하하",
        "thumbsUpCount": 0,
        "thumbsUpStatus": false,
        "created": "2023/02/15 03:43",
        "modified": "2023/02/15 03:43"
    }
]
```

### 게시글 좋아요

- Method: POST
- Url: http://localhost:8080/article/{id}/thumbsUp

#### Header

- Content-Type : application/json
- Authentication : Realtor 1

#### Response

```
{
    "status": "OK",
    "message": "좋아요를 눌렀습니다.",
    "data": null
}
```

### 게시글 좋아요 조회

- Method: GET
- Url: http://localhost:8080/article/thumbsUps

#### Header

- Content-Type : application/json
- Authentication : Realtor 1

#### Response

```
[
    {
        "id": 2,
        "writer": "김양(공인중개사)",
        "title": "두번째 게시글 입니다!",
        "content": "잘 부탁드립니다.",
        "thumbsUpCount": 1,
        "thumbsUpStatus": true,
        "created": "2023-02-15 03:43",
        "modified": "2023/02/15 03:46"
    },
    {
        "id": 1,
        "writer": "김양(공인중개사)",
        "title": "첫 게시글 입니다!",
        "content": "만나서 반갑습니다",
        "thumbsUpCount": 1,
        "thumbsUpStatus": true,
        "created": "2023-02-15 03:43",
        "modified": "2023/02/15 03:46"
    }
]
```

### 게시글 좋아요 취소

- Method: POST
- Url: http://localhost:8080/article/{id}/thumbsUp

#### Header

- Content-Type : application/json
- Authentication : Realtor 1

#### Response

```
{
    "status": "OK",
    "message": "좋아요가 취소되었습니다.",
    "data": null
}
```

### 게시글 수정

- Method: PUT
- Url: http://localhost:8080/article/{id}/update

#### Header

- Content-Type : application/json
- Authentication : Realtor 1

#### Body

```
{
    "title" : "제목을 수정하겠습니다.",
    "content" : "내용도 수정하겠습니다."
}
```

#### Response

```
{
    "status": "OK",
    "message": "게시글을 수정 했습니다.",
    "data": {
        "id": 1,
        "writer": "김양(공인중개사)",
        "title": "제목을 수정하겠습니다.",
        "content": "내용도 수정하겠습니다.",
        "thumbsUpCount": 1,
        "thumbsUpStatus": true,
        "created": "2023-02-15 03:43",
        "modified": "2023/02/15 03:46"
    }
}
```

### 게시글 삭제

- Method: DELETE
- Url: http://localhost:8080/article/{id}/delete

#### Header

- Content-Type : application/json
- Authentication : Realtor 1

#### Response

```
{
    "status": "OK",
    "message": "게시글을 삭제 했습니다",
    "data": null
}
```
