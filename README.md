데이터 출처: https://www.kaggle.com/datasets/nelgiriyewithana/top-spotify-songs-2023?resource=download<br>
2023년에 스포티파이에서 가장 많이 스트리밍된 노래 데이터셋에서 가수명, 노래명, 앨범명, 발매년도만을 추출.
인코딩 오류가 발생하는 노래들을 제외한 439개의 데이터를 기본 데이터셋으로 설정함

### 변경사항
1. Playlist-Song 관계를 n:m으로 수정함으로써 한 개의 노래가 여러 개의 재생목록에 포함되도록 함.
2. Song의 album 속성을 삭제하고 released_year로 대체
3. Playlist 테이블의 creationDate를 creationTime으로 바꾸고 날짜와 시간을 저장하도록 함

