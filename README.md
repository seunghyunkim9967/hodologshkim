# inflearn-hodolog

게시글

POST /posts

GET /posts/{postId}

댓글
POST /comments?postId=1 OR
{
   postId : 1
}
POST /posts/{postId}/comments
{
   author: "seung",
   password: "1234",
   content: "ASDF"
}
## 댓글 -> 테이블 모델링 (comment) (=Comment Entity)

## 비공개, 공개 여부 (상태값) -> (Enum)

## 카테고리 -> DB(or Enum)

## 로그인 -> spring security

## 비밀번호 암호화 

1. 해시
2. 해시 방식
   1. SHA1
   2. SHA256
   3. MD5
   4. 왜 이 방식으로 비번 암호화 하면 안될까
3. BCrypt SCrypt, Argon2
   1. salt 값