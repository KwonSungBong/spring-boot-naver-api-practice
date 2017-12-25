<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>설명</title>
    <link rel="stylesheet" type="text/css" href="/static/css/index.css" />
</head>
<body>
<div id="index">
    설명
</div>
<div>
    <h2># clubid 찾는 방법</h2>
    <h5>1. 크롬 브라우저에서 f12클릭 후 빨간 표시 선택</h5>
    <image src="/static/image/tool.png" />
    <h5>2. 카페 메인 이름과 로고인 부분을 클릭</h5>
    <image src="/static/image/clubid.png" />
    <h5>3. "clubid=숫자"에서 숫자인 부분이 clubid이며 카페를 구분하는 값임</h5>
</div>
<br>
<div>
    <h2># menuid 찾는 방법</h2>
    <h5>1. 크롬 브라우저에서 f12클릭 후 빨간 표시 선택</h5>
    <image src="/static/image/tool.png" />
    <h5>2. 카페 게시판 부분을 클릭</h5>
    <image src="/static/image/menuid.png" />
    <h5>3. "goMenu('숫자')"에서 숫자인 부분이 menuid이며 메뉴를 구분하는 값임</h5>
</div>
<br>
<div>
    <h2># clubid/menuid/카페이름, clubid/menuid/카페이름 이런식으로 작성</h2>
</div>

<script src="/static/js/index.js"></script>
</body>
</html>
