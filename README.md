# 쿠폰 발급 프로젝트 

## 1. 사용 기술 
Redis, Kafka, Mysql

## 2. coupon-api 
* admin - 쿠폰정책 생성
* mobile - 쿠폰 발급 (카프카 메시지 발신(producer)) 

## 3. coupon-worker 
* 배치 - 해당 월 생일 쿠폰발급
* 선착순 쿠폰 생성 (카프카 메시지 수신(consumer)) 


## 4. 실행  방법 

1. docker-compose up - docker container 실행
2. coupon-api - 실행
3. 
