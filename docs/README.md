# 크리스마스 프로모션

## 개요

우테코의 4주차 미션인 **"크리스마스 프로모션"** 어플리케이션 입니다.
우아한 비즈니스팀의 개발 요청을 받아 12월달 우테코 식당의 "12월 이벤트 플래너"를 개발 합니다.

### 12월 이벤트 플래너

이벤트 플래너는 고객에게 방문할 날짜와 메뉴를 입력 받아, 프로모션의 적용 결과를 알려주기 위한 어플리케이션 입니다.

프로모션은 크게 증정 이벤트와 할인 이벤트로 구분됩니다.

**증정 이벤트**
- 샴페인 증정 이벤트
  - 할인이 적용되지 않은 금액을 기준으로 총 주문 금액이 120,000원 이상일 경우 샴페인을 하나 증정합니다.
  - 이벤트 기간 : 12월 1일 ~ 12월 31일

**할인 이벤트**
- 크리스마스 디데이 할인
  - 12월 1일에 할인 금액이 1,000원 적용됩니다.
  - 하루가 지날 수록, 할인 금액이 100원 증가합니다.
  - 이벤트 기간 : 12월 1일 ~ 12월 25일
- 평일 할인 (일요일 ~ 목요일)
  - 디저트 메뉴 하나당 2,023원 할인이 적용됩니다.
  - 이벤트 기간 : 12월 1일 ~ 12월 31일
- 주말 할인 (금요일, 토요일)
  - 메인 메뉴 하나당 2,023원 할인이 적용됩니다.
  - 이벤트 기간 : 12월 1일 ~ 12월 31일
- 특별 할인
  - 이벤트 달력의 별이 있는 날짜에는 총 주문 금액에서 1,000원 할인이 적용됩니다.
  - 이벤트 기간 : 12월 1일 ~ 12월 31일

이벤트 달력
![event_calandar.png](event_calandar.png)

프로모션으로 받은 혜택 금액에 따라 12월의 이벤트 뱃지가 부여됩니다.
- 5천 원 이상: 별
- 1만 원 이상: 트리
- 2만 원 이상: 산타

### 주의사항

- 총주문 금액 10,000원 이상부터 이벤트가 적용됩니다.
- 음료만 주문 시, 주문할 수 없습니다.
- 메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다.
  - (e.g. 시저샐러드-1, 티본스테이크-1, 크리스마스파스타-1, 제로콜라-3, 아이스크림-1의 총개수는 7개)

## 어플리케이션 흐름

1. 식당의 방문 날짜를 입력 받습니다. (숫자로만 입력 가능)
   - 예외 상황 : 
     - 숫자가 아닌 입력이 존재하는 경우 
     - 입력된 숫자의 범위가 잘못된 경우 (1 ~ 31을 제외한 숫자)
2. 주문할 메뉴와 숫자를 입력 받습니다. (형식 : 해산물파스타-2,레드와인-1,초코케이크-1)
   - 예외 상황 : 
     - 메뉴판에 존재하지 않는 메뉴를 입력한 경우
     - 메뉴를 중복하여 입력한 경우
     - 메뉴의 개수를 0, 또는 음수로 입력한 경우
     - 메뉴의 총 합계가 20개 이상인 경우
     - 입력 형식이 잘못된 경우
3. 결과 출력
   1. 주문된 메뉴 출력
   2. 할인 전 총 금액 출력
   3. 증정 메뉴 출력
   4. 혜택 내역 출력
      - 혜택 내역은 할인과 증정을 모두 포함합니다.
   5. 혜택 금액 출력
   6. 할인 후 예상 결제 금액 출력
   7. 혜택 금액에 따른 이벤트 뱃지 출력

## 어플리케이션 구성

어플리케이션은 다음의 객체들로 구성됩니다.
- `Service` : 각각의 책임에 따라 기능을 제공
  - 날짜 서비스 : 날짜와 관련된 기능을 제공
  - 메뉴 서비스 : 메뉴와 관련된 기능을 제공
  - 혜택 서비스 : 혜택과 관련된 기능을 제공
    - 증정품과 할인을 포함한 모든 혜택과 관련된 기능을 제공한다.
  - 이벤트 서비스 : 이벤트 뱃지와 관련된 기능을 제공
- `Controller` : 어플리케이션 흐름에 따라 `Service`와 `UI` 객체들의 메서드를 호출
- `Model` : `Service` 클래스에서 비즈니스 로직에 사용하는 객체
  - 메뉴
  - 날짜 
  - 혜택 
  - 뱃지 

## 세부 기능 명세

### 날짜

#### [Model] - 날짜

- 날짜 정보를 저장
  - 날짜 : `java.time.LocalDate`
- 기능 
  - 요일 정보를 반환한다
    - 해당 날짜 객체가 주말인지, 또는 평일인지의 정보를 반환한다.
  - 해당 날짜가 스페셜 데이에 해당하는지의 정보를 반환한다.
  - 크리스마스까지 남은 날짜를 반환한다.

#### [Service] 

- 기능
  - 숫자를 입력 받아 날짜 Model을 반환한다.
- 예외 발생
  - 지정된 날짜를 제외한 숫자가 입력된 경우
  - 
### 메뉴

#### [Model] 메뉴 타입

- 메뉴의 카테고리를 저장
  - 에피타이저
  - 메인 메뉴
  - 디저트
  - 음료수

#### [Model] 메뉴

- 메뉴 정보를 저장
  - 메뉴 타입
  - 메뉴 이름
  - 가격
- 기능
  - 해당 메뉴가 전달받은 파라미터의 타입에 해당하는지의 여부를 반환한다.

#### [Model] 메뉴 + 개수

- 주문 메뉴 정보를 저장
  - 메뉴
  - 주문 개수
- 기능
  - 해당 메뉴가 전달받은 파라미터의 타입에 해당하는지의 여부를 반환한다.
  - 전체 주문 금액 반환한다.

#### [Model] 주문서

- 전체 주문 목록을 저장
- 기능
  - 전달받은 파라미터의 타입 주문을 가지고 있는지 확인한다.
  - 전달받은 파라미터 타입의 주문 개수를 반환한다.
  - 전체 주문 금액을 반환한다.

#### [Service]
- 기능
  - 메뉴 입력을 받으면 메뉴와 개수를 담은 정보를 반환한다.
- 예외 발생
  - 메뉴의 주문 개수가 1개 미만이거나, 20개를 초과하는 경우
  - 메뉴가 음료수밖에 없는 경우
  - 존재하지 않는 메뉴를 입력한 경우

### 혜택

#### [Model] 증정품

- 증정 정보를 저장
  - 증정품
- 기능
  - 증정품의 금액을 반환한다.
  - 증정품 목록을 반환한다.

#### [Model] 할인

- 할인 정보를 저장
  - 할인 타입
  - 할인 금액
- 기능
  - 할인 금액을 반환한다.

#### [Model] 혜택 컬렉션

- 적용 된 혜택들을 저장
  - 증정 또는 할인
  - 각각의 혜택이 적용된 횟수
- 기능
  - 혜택 목록을 반환한다.
  - 총 적용 혜택 금액 반환한다.
  - 할인 금액 반환한다.
  
#### [Service]

- 기능
  - 날짜 정보와 주문 정보를 전달 받아 혜택 정보를 반환한다.

### 뱃지

#### [Model] 뱃지

- 뱃지 정보를 저장
- 기능
  - 혜택 금액을 전달 받으면 적절한 뱃지를 반환한다.

#### [Service]

- 기능
  - 헤택 정보를 입력받아 뱃지 정보를 반환한다.