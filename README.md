# 위준우

## 요구사항

- Java `1.8`
- Spring-boot `2.7.8`

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
- Url: http://localhost:8080/article/{id}

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
- Url: http://localhost:8080/article/{id}

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

### 💡 삭제된 게시물의 ID로 조회하는 경우

> 삭제된 게시물을 조회하는 경우, 게시물의 제목과 내용은 확인이 불가하며 삭제된 시간을 표기해줍니다.

#### Response

```bash
{
    "id": 2,
    "message": "삭제된 게시글입니다.",
    "deletedDate": "2023-02-15 03:04"
}
```

### 💡 게시물 전체 조회 시 삭제 게시물 미표시

> 삭제된 게시물(id:2)은 전체 게시물 목록에서 조회가 되지 않습니다.

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
        "createdDate": "2023-02-15 03:04",
        "modifiedDate": "2023-02-15 03:04"
    }
]
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
