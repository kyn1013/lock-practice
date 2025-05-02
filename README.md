### 낙관적 락

- 충돌이 드물다고 가정하고 동시성을 관리하는 방법
- 버전 번호나 타임 스탬프를 사용하여 데이터가 수정되었는지 검사
- 장점
    - 충돌이 드문 경우 성능에 큰 부담 없이 동시성 제어
    - 락을 별도로 유지하지 않아서 데이터베이스 락 경합이 적다
- 단점
    - 충돌 발생 시에 이를 처리하기 위한 추가 로직 필요 (재시도 처리 같은)
- 사용 : JPA의 낙관적 락: `@Version` 어노테이션을 사용하여 엔티티의 버전을 관리.

만약 두 개의 트랜잭션이 같은 `productId`에 대해 동시에 데이터를 수정하려고 할 때, 첫 번째 트랜잭션이 완료된 후 두 번째 트랜잭션이 `OptimisticLockException`을 발생

### 트랜잭션 1:

1. 상품 `id = 1`의 데이터 조회 (버전 1)
2. 수량을 10 증가시킴 (버전 1에서 버전 2로 증가)
3. 트랜잭션 커밋

### 트랜잭션 2:

1. 상품 `id = 1`의 데이터 조회 (버전 1)
2. 수량을 5 증가시킴 (버전 1에서 버전 2로 증가 시도)
3. 트랜잭션 커밋 시 **버전 충돌** 발생 (첫 번째 트랜잭션의 버전 2와 두 번째 트랜잭션의 버전 1이 다르므로 `OptimisticLockException`이 발생)

-> 이런 흐름으로 적용되는 것

<details>

<summary>기능 흐름도</summary>

  <details>
  <summary>회원가입 및 로그인</summary>

  ![Image](https://github.com/user-attachments/assets/d1f5132b-a4a9-4f80-bad5-a10eb5892534)

  ![Image](https://github.com/user-attachments/assets/42e3b1c2-cace-41b3-927a-d3b5551161a4)
  </details>

<details>
  <summary>비밀번호, 의사 이미지 변경</summary>

![Image](https://github.com/user-attachments/assets/38a8d145-97ba-4112-91f3-d0e5d2913d3c)

![Image](https://github.com/user-attachments/assets/14a19b7c-7544-4a1c-8130-7a13a11a3705)

  </details>

<details>
  <summary>게시글 </summary>

![Image](https://github.com/user-attachments/assets/9f410a66-0cc2-4dc5-ae76-fa955baa7a95)

![Image](https://github.com/user-attachments/assets/4546128b-dbcd-4b0d-abc8-5973573eb872)

![Image](https://github.com/user-attachments/assets/abcd1e21-2847-46e2-b205-f1e74986c941)

![Image](https://github.com/user-attachments/assets/f292589e-174a-410a-aa26-76c22a3ca361)

![Image](https://github.com/user-attachments/assets/a115064e-de4a-4078-942f-932f9ceb5fdb)

  </details>

<details>
  <summary>채팅방 </summary>

![Image](https://github.com/user-attachments/assets/0dd05bd2-4923-432a-b967-e2b732b468bd)

![Image](https://github.com/user-attachments/assets/27c8c7f1-ca48-4e9f-ab1a-807a107f7b7c)

![Image](https://github.com/user-attachments/assets/92da760e-aa1f-46a9-9135-b57dd9581865)

  </details>

<details>
  <summary>결제 </summary>

![Image](https://github.com/user-attachments/assets/174a220b-081f-4afa-bec0-7c0f5de6d190)

![Image](https://github.com/user-attachments/assets/42a234f7-ce73-4551-ba92-7754f09d56d3)

  </details>

<details>
  <summary> 쿠폰 </summary>

![Image](https://github.com/user-attachments/assets/debdf332-c871-49aa-97a3-36e87ad05c10)

  </details>

</details>
</details>
