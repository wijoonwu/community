# 위준우

## 요구사항

- Java `1.8`
- Spring-boot `2.7.8`
- H2 database `2.1.214`

### 💡 H2 Database를 사용한 이유

> H2는 메모리 Database로, 가장 적은 용량을 차지하며 편리하게 데이터 저장이나 조작을 테스트 해볼 수 있어 사용 했습니다.

## 프로젝트 설치

### MacOS

```bash
git clone https://github.com/wijoonwu/community.git
cd community
./gradlew bootRun
```

### Windows

```bash
git clone https://github.com/wijoonwu/community.git
cd community
gradlew bootRun
```

## H2 Database 설치

### MacOS or Windows

- https://atoz-develop.tistory.com/entry/H2-Database-%EC%84%A4%EC%B9%98-%EC%84%9C%EB%B2%84-%EC%8B%A4%ED%96%89-%EC%A0%91%EC%86%8D-%EB%B0%A9%EB%B2%95

> database의 이름은 반드시 `community`로 설정합니다.
> (ex. url: `jdbc:h2:tcp://localhost/~/community`)

## 실행 방법

`Postman` 혹은 `Talend API` 등의 프로그램을 이용해서 아래와 같이 테스트 합니다.

### 게시글 작성

- Method: `POST`
- Url: http://localhost:8080/article/new

#### Header

- Content-Type : `application/json`
- Authentication : Realtor 1

#### Body

```bash
{
    "title" : "안녕하세요",
    "content" : "하하하하하"
}
```

#### Response

```bash
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
        "createdDate": "2023-02-15 03:53",
        "modifiedDate": "2023-02-15 03:53"
    }
}
```

### 💡 외부인 게시글 작성 불가

> `Header` 에 `Authentication` 값을 넣지 않는 경우, 외부 사용자로 인식되어 게시글 작성이 불가하며 아래와 같은 응답 값이 전달됩니다.

#### Response

```bash
{
    "status": 401,
    "message": "외부 사용자는 이용할 수 없는 서비스입니다."
}
```

### 게시글 단건 조회

- Method: `GET`
- Url: http://localhost:8080/article/{id}

#### Header

- Content-Type : `application/json`
- Authentication : Realtor 1

#### Response

```bash
{
    "id": 1,
    "writer": "김양(공인중개사)",
    "title": "첫 게시글 입니다!",
    "content": "만나서 반갑습니다",
    "thumbsUpCount": 0,
    "thumbsUpStatus": false,
    "createdDate": "2023-02-15 03:53",
    "modifiedDate": "2023-02-15 03:53"
}
```

### 게시글 전체 조회

- Method: `GET`
- Url: http://localhost:8080/article/list

#### Header

- Content-Type : `application/json`
- Authentication : Realtor 1

#### Response

```bash
[
    {
        "id": 1,
        "writer": "김양(공인중개사)",
        "title": "첫 게시글 입니다!",
        "content": "만나서 반갑습니다",
        "thumbsUpCount": 0,
        "thumbsUpStatus": false,
        "createdDate": "2023-02-15 03:53",
        "modifiedDate": "2023-02-15 03:53"
    },
    {
        "id": 2,
        "writer": "김양(공인중개사)",
        "title": "두번째 게시글 입니다!",
        "content": "잘 부탁드립니다.",
        "thumbsUpCount": 0,
        "thumbsUpStatus": false,
        "createdDate": "2023-02-15 03:53",
        "modifiedDate": "2023-02-15 03:53"
    },
    {
        "id": 3,
        "writer": "김양(공인중개사)",
        "title": "안녕하세요",
        "content": "하하하하하",
        "thumbsUpCount": 0,
        "thumbsUpStatus": false,
        "createdDate": "2023-02-15 03:53",
        "modifiedDate": "2023-02-15 03:53"
    }
]
```

### 💡 전체 사용자 게시판 이용 가능

> 게시글 조회는 외부 사용자도 가능하기에 `Header` 에 `Authentication` 값을 넣지 않아도 됩니다.
> 다만 외부 사용자는 좋아요 기능을 이용할 수 없기에, 게시글 조회 시 `thumbsUpStatus` 값은 항상 `false` 로 표시됩니다.

### 게시글 좋아요

- Method: `POST`
- Url: http://localhost:8080/article/{id}/thumbsUp

#### Header

- Content-Type : `application/json`
- Authentication : Realtor 1

#### Response

```bash
{
    "status": "OK",
    "message": "좋아요를 눌렀습니다.",
    "data": null
}
```

### 💡 좋아요 취소

> 이미 좋아요 한 게시글에 한번 더 좋아요를 요청하는 경우, 좋아요가 취소 되며 아래와 같은 응답 값이 전달됩니다.

#### Response

```bash
{
    "status": "OK",
    "message": "좋아요가 취소되었습니다.",
    "data": null
}
```

### 💡 외부인 좋아요 불가

> `Header` 에 `Authentication` 값을 넣지 않는 경우, 외부 사용자로 인식되어 좋아요 기능을 이용할 수 없습니다. 응답 값은 아래와 같이 전달 됩니다.

#### Response

```bash
{
    "status": 401,
    "message": "외부 사용자는 이용할 수 없는 서비스입니다."
}
```

### 게시글 좋아요 조회

- Method: `GET`
- Url: http://localhost:8080/article/thumbsUps

#### Header

- Content-Type : application/json
- Authentication : Realtor 1

#### Response

```bash
[
    {
        "id": 1,
        "writer": "김양(공인중개사)",
        "title": "첫 게시글 입니다!",
        "content": "만나서 반갑습니다",
        "thumbsUpCount": 1,
        "thumbsUpStatus": true,
        "createdDate": "2023-02-15 03:53",
        "modifiedDate": "2023-02-15 03:54"
    },
    {
        "id": 2,
        "writer": "김양(공인중개사)",
        "title": "두번째 게시글 입니다!",
        "content": "잘 부탁드립니다.",
        "thumbsUpCount": 1,
        "thumbsUpStatus": true,
        "createdDate": "2023-02-15 03:53",
        "modifiedDate": "2023-02-15 03:54"
    }
]
```

### 게시글 수정

- Method: `PUT`
- Url: http://localhost:8080/article/{id}/update

#### Header

- Content-Type : `application/json`
- Authentication : Realtor 1

#### Body

```bash
{
    "title" : "제목을 수정하겠습니다.",
    "content" : "내용도 수정하겠습니다."
}
```

#### Response

```bash
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
        "createdDate": "2023-02-15 03:53",
        "modifiedDate": "2023-02-15 03:54"
    }
}
```

### 게시글 삭제

- Method: `DELETE`
- Url: http://localhost:8080/article/{id}/delete

#### Header

- Content-Type : `application/json`
- Authentication : Realtor 1

#### Response

```bash
{
    "status": "OK",
    "message": "게시글을 삭제 했습니다",
    "data": null
}
```

### 💡 작성자 외 게시글 수정 및 삭제 불가

> `Header` 에 입력한 `Authentication` 값과 게시글 작성자의 `account_id`가 일치 하지 않는 경우
> 게시글 수정 및 삭제가 불가능하며 아래와 같은 응답 값이 전달됩니다.

#### Response

```bash
{
    "status": 401,
    "message": "잘못된 접근입니다."
}
```
