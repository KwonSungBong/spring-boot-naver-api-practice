<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>네이버로그인</title>
    <link rel="stylesheet" type="text/css" href="/static/css/index.css" />
</head>
<body>
<div id="index">
    네이버로그인
</div>
<%
    String clientId = "auUkvIIQQ1TemTtqL6jt";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://bookstorage.kr:8888/oauth2/callback", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
%>
<a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
<script src="/static/js/index.js"></script>
</body>
</html>
