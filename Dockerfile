# Base Image 선택 (Zulu 17 이미지를 사용)
FROM azul/zulu-openjdk-alpine:17-jre

  # 작업 디렉토리 설정
WORKDIR /app

  # 호스트의 빌드된 Jar 파일을 컨테이너의 /app/ 디렉토리로 복사
COPY ./home/ec2-user/spring-github-action/application.jar /app/

  # Spring Boot 애플리케이션의 포트 번호 (application.properties 또는 application.yml과 동일하게 설정)
EXPOSE 8080

  # 애플리케이션 실행
CMD ["java", "-jar", "application.jar"]
