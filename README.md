- 'spotify2023'테이블 데이터 출처: https://www.kaggle.com/datasets/nelgiriyewithana/top-spotify-songs-2023?resource=download<br>
2023년에 스포티파이에서 가장 많이 스트리밍된 노래 데이터셋에서 가수명, 노래명, 앨범명, 발매년도만을 추출.
인코딩 오류가 발생하는 노래들을 제외한 439개의 데이터를 기본 데이터셋으로 설정함

# 4조 프로젝트

저희의 애플리케이션은 로컬 환경에서 실행되는 애플리케이션입니다. 실행 방법은 아래와 같습니다. 

## 1. 깃 클론
프로젝트를 실행할 디렉터리에서 git bash를 열고 다음을 실행합니다.
```bash
git clone https://github.com/freshberryy/DBdesign_project.git
```

## 2. 실행 환경
- JDK 17 이상
- Gradle
- MySQL 8.0 이상

## 3. 빌드 및 실행
프로젝트 디렉터리에서 터미널을 열고 다음을 실행합니다.
### 빌드
윈도우:
```
gradlew.bat build
```

리눅스:
```
./gradlew build
```

### 실행
윈도우:
```
gradlew.bat bootRun
```

리눅스:
```
./gradlew bootRun
```

# 4. 사용 방법

브라우저에 링크를 입력하여 실행합니다.
```
http://localhost:8080/
```



