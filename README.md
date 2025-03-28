# InkBridge 팀 프로젝트 리팩토링

[전체 프로젝트](https://github.com/nhnacademy-be4-InkBridge)  
[원본 레포지토리](https://github.com/nhnacademy-be4-InkBridge/backend)

## 리팩토링 내용
- [Object Storage 업로드 방식과 이미지 조회 방식 변경](https://nuheajiohc.tistory.com/44)
- [설정파일 암호화 로직 리팩토링으로 의존성 분리와 API 호출 최적화하기](https://nuheajiohc.tistory.com/43)
- [DBCP2에서 HikariCP로 전환 과정](https://nuheajiohc.tistory.com/42)

## 리팩토링 하기 전 트러블 슈팅
- [Logback 설정 실수로 인해 로그가 출력되지 않은 문제 해결 과정](https://nuheajiohc.tistory.com/41)
- [Maven 프로젝트에서 Lombok이 인식되지 않는 문제 해결](https://nuheajiohc.tistory.com/40)

## 요구사항

### 주소

- **주소 등록**
  - [x] 주소는 도로명 주소 + 상세 주소를 함께 등록한다. 
  - [x] 배송지 등록 시 기본 배송지 여부를 선택할 수 있다.
  - [x] 주소는 최대 10개까지 등록 할 수 있다.
- **주소 변경**
  - [x] 기본 배송지를 변경할 수 있다.
  - [x] 주소 내용을 변경한다.
- **주소 삭제**
  - [x] 주소를 삭제한다.
  - [x] 유일한 배송지라면 삭제할 수 없다.
  - [x] 기본 배송지는 삭제할 수 없다.
- **주소 조회**
  - [x] 기본 배송지로 선정한 주소는 상단에 고정된다.  
  - [x] 주소 목록은 최근 등록 순으로 정렬된다.
- [ ] 회원가입 시 기입한 주소를 기본 배송지로 등록한다.

