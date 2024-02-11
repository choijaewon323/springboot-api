# springboot-api

## DB Table
![DB table](https://github.com/choijaewon323/springboot-api/assets/46291115/70d6c855-06e4-4744-8d1d-d62c4ae3aa7f)


## 기능 요구사항
### Security

- [x]  Spring Security + JWT 를 이용한 인증 및 인가
- [x]  JWT 토큰 발급
- [x]  JWT 토큰 만료 체크
- [x]  로그인
    - [x]  비밀번호 encoding
    - [x]  이미 존재하는 username 검사
- [ ]  로그아웃
- [ ]  ACCOUNT ROLE 별 접근 가능 API 제한

### ACCOUNT

- [ ]  전체 account 리스트 가져오기 → 최근 생성순 (ADMIN)
- [ ]  전체 account 리스트 가져오기 → 오래된 순 (ADMIN)
- [x]  account 하나 가져오기
- [x]  account 생성
- [x]  username 수정
- [x]  password 수정
- [ ]  account 삭제 → 연관된 것 모두 삭제되어야함
- [ ]  username 및 password 수정 시 JWT 재발급

### BOARD

- [x]  게시글 작성
- [x]  게시글 수정
- [ ]  게시글 삭제 → 연관 reply, board_like 다 삭제되어야함
- [ ]  게시글 목록 전체 불러오기 → 최근 생성된 순으로
- [ ]  게시글 목록 전체 불러오기 → 생성된지 오래된 순으로
- [x]  게시글 하나 불러오기 → 조회수 ++
- [ ]  조회수 동시성 문제 해결
- [x]  게시글 검색 → 내용
- [x]  게시글 검색 → 제목
- [x]  게시글 검색 → 작성자
- [x]  게시글 페이징 → 1번에 20개씩
- [x]  검색 최적화
- [x]  페이징 최적화
- [ ]  게시글 목록 불러오기 → Redis 를 이용한 캐싱 적용 고려

### REPLY

- [x]  댓글 생성
- [x]  댓글 수정
- [x]  댓글 삭제
- [ ]  해당 board에 대한 댓글 전체 읽어오기 → 최근 생성순
- [ ]  해당 board에 대한 댓글 전체 읽어오기 → 오래된순

### BOARD_LIKE

- [x]  좋아요 추가
- [x]  좋아요 삭제
- [ ]  좋아요 추가 및 삭제 동시성 문제 해결

## API
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
