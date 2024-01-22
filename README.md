# springboot-api

### Login

- Content-Type : “application/json;charset=utf-8”

| Method | URL | Description |
| --- | --- | --- |
| POST | /api/v1/login | 로그인 엔드포인트 |

### Account

- Content-Type : “application/json;charset=utf-8”

| Method | URL | Description |
| --- | --- | --- |
| POST | /api/v1/account | 계정 생성 |
| PUT | /api/v1/account/username | username 수정 |
| PUT | /api/v1/account/password | password 수정 |
| DELETE | /api/v1/account/{username} | 계정 삭제 |

### Board

- Content-Type : “application/json;charset=utf-8”

| Method | URL | Description |
| --- | --- | --- |
| GET | /api/v1/board/{boardId} | 게시판 단일 조회 |
| GET | /api/v1/board/list | 전체 게시판 조회 (페이징 없음) |
| GET | /api/v1/board/list/{pageIndex} | 20페이지씩 끊어서 게시판 조회 (내림차순) |
| GET | /api/v1/board/list/covering/{pageIndex} | 20페이지씩 끊어서 게시판 조회 (내림차순) - 커버링 인덱스 적용 |
| GET | /api/v1/board/search/content | 내용 기반 게시판 검색 |
| GET | /api/v1/board/search/writer | 작성자 기반 게시판 검색 |
| GET | /api/v1/board/search/title | 제목 기반 게시판 검색 |
| POST | /api/v1/board | 게시판 생성 |
| PUT | /api/v1/board/{boardId} | 게시판 수정 |
| DELETE | /api/v1/board/{boardId} | 게시판 삭제 |

### BoardLike

- Content-Type : “application/json;charset=utf-8”

| Method | URL | Description |
| --- | --- | --- |
| POST | /api/v1/board/like | 좋아요 추가 |
| DELETE | /api/v1/board/like | 좋아요 삭제 |

### Reply

- Content-Type : “application/json;charset=utf-8”

| Method | URL | Description |
| --- | --- | --- |
| GET | /api/v1/reply/list/{boardId} | 해당 게시판의 댓글 모두 읽어오기 |
| GET | /api/v1/reply/{replyId} | 댓글 하나 읽어오기 |
| POST | /api/v1/reply/{boardId} | 댓글 추가 |
| PUT | /api/v1/reply/{replyId} | 댓글 수정 |
| DELETE | /api/v1/reply/{replyId} | 댓글 삭제 |