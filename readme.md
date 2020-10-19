# 뿌리기 

## 개요 
카카오페이의 머니 뿌리기 기능을 개발한다. 

## 구현사항 

### 여러 Instance와 Traffic 대응 전략
* Redis를 이용하여 분산 Lock을 구현하여 여러 Instance에서도 문제없이 동작하도록 구현함.
* Redis를 최대한 활용하여 DB 접근을 최소화 하여 다량의 Traffic에서도 부하를 최소화 하도록 구현함. 

### Token 
Token은 뿌리기를 접근하기 위한 일회성 값 이다. <br>
따라서 Token은 조회가 가능한 7일 동안만 유효하다. <br>
3자리 수 제약으로 필요시 재사용이 가능해야 한다.
  
### 금액 분배
*  금액분배 값 또한 일회성 값으로 간주했다. <br>
뿌리기가 종료되면 더 이상 활용되지 않기 때문이다. <br>
따라서 뿌리기 생성 시 Redis 에 값을 저장한다. (DB에 접근하는 것 보다 부하가 적어진다.) <br>
Redis의 List 자료구조를 사용하여 Queue에 저장하였다. 

* 분배 된 금액은 평균 값에서 크게 벗어나지 않도록 계산을 했다. 

### 뿌리기 API
1. 뿌리기에 사용되지 않는 Random 한 3자리 Token을 생성한다.
2. 뿌리기에 사용한 금액을 차감 후 뿌리기를 저장한다. 
3. 뿌리기 정보와 분배된 금액 정보를 Token 기준으로 Redis에 저장한다. 

### 받기 API
1. Token으로 Redis에서 뿌리기 정보를 받아온다. 
2. Validation을 수행한다.
3. 받을 금액을 가져 온다.
4. 받은 금액을 잔액에 더하고 뿌리기에 받은 정보를 저장한다.

### 조회 API
1. Token으로 Redis에서 뿌리기 정보를 조회한다. 
2. Validation 을 수행한다.
3. 필요한 정보를 반환한다.

### Refund
* 뿌리기 완료 후 남은 금액을 반환해야 한다.  <br>
Scheduler 기능을 사용하여 1분마다 만료 된 뿌리기를 환불 한다. <br>
분산 Lock을 사용하여 여러 Instance에서 동시에 수행되는 것을 방지 했다. <br>
(API로 구현한 다음 Jenkins에서 Trigger를 주면 어땠을까?)

1. 환불되지 않은 뿌리기를 조회한다.
2. 만료된 뿌리기를 찾아 뿌리기를 만든 유저에게 환불한다. 
 