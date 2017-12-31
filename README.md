# spring-boot-naver-api-practice

https://developers.naver.com/apps/#/myapps/auUkvIIQQ1TemTtqL6jt/config

https://developers.naver.com/docs/login/api/

https://developers.naver.com/docs/cafe/api/

http://programmerhoiit.tistory.com/31

https://github.com/naver/smarteditor2/blob/master/dist/SmartEditor2.html

http://forum.developers.naver.com/c/13-category

############################################################################################

시작
./gradlew build && java -jar build/libs/spring-boot-naver-api-practice-0.0.1-SNAPSHOT.jar

종료
lsof -i :8888
kill -9 pid

###############################


1. docker image 생성
./gradlew build buildDocker

2. docker 실행
docker run -p 8888:8888 -t com.example/spring-boot-naver-api-practice:0.0.1-SNAPSHOT

3. docker 실행 시 프로파일 설정
docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8888:8888 -t com.example/spring-boot-naver-api-practice:0.0.1-SNAPSHOT
