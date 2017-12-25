package com.example.demo.controller;

import com.example.demo.dto.Param;
import com.example.demo.dto.Token;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ksb on 2017. 12. 23..
 */
@Controller
public class NaverController {

    private String code;
    private String state;
    private Token token;
    private List<String> naverCafeSuccessList;
    private List<String> naverCafeFailureList;

    @GetMapping("/explain")
    public String explain() {
        return "explain";
    }

    @GetMapping("/login")
    public String loginNaver() {
        return "login";
    }

    @GetMapping("/oauth2/callback")
    public String oauth2(@RequestParam("code") String code,
                              @RequestParam("state") String state,
                              Map<String, Object> model) throws UnsupportedEncodingException {
        return callback(code, state);
    }

    @PostMapping("/save")
    @ResponseBody
    public Map<String, List<String>> save(@RequestBody Param param) {
        post(param);
        Map<String, List<String>> map = new HashMap<>();
        map.put("success", naverCafeSuccessList);
        map.put("failure", naverCafeFailureList);
        return map;
    }

    private String callback(String code, String state) throws UnsupportedEncodingException {
        String clientId = "auUkvIIQQ1TemTtqL6jt";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "Yb1DGnBT_X";//애플리케이션 클라이언트 시크릿값";
        String redirectURI = URLEncoder.encode("http://bookstorage.kr:8888/oauth2/callback", "UTF-8");
        String apiURL;
        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        apiURL += "client_id=" + clientId;
        apiURL += "&client_secret=" + clientSecret;
        apiURL += "&redirect_uri=" + redirectURI;
        apiURL += "&code=" + code;
        apiURL += "&state=" + state;
        this.code = code;
        this.state = state;
        System.out.println("apiURL="+apiURL);
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            System.out.print("responseCode="+responseCode);
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            if(responseCode==200) {
                System.out.println(res.toString());
            }

            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> tokenMap = mapper.readValue(res.toString(),
                    new TypeReference<HashMap<String, String>>() {});
            if(tokenMap.get("access_token") == null) {
                System.out.println(res.toString());
                return "login";
            } else {
                if(token == null) token = new Token();
                token.setAccessToken(tokenMap.get("access_token"));
                token.setRefreshToken(tokenMap.get("refresh_token"));
                token.setTokenType(tokenMap.get("token_type"));
                token.setExpiresIn(tokenMap.get("expires_in"));
                return "cafe";
            }
        } catch (Exception e) {
            System.out.println(e);
            return "login";
        }
    }

    private void post(Param param) {
        naverCafeSuccessList = new ArrayList<>();
        naverCafeFailureList = new ArrayList<>();
        if(token == null) return;
        String accessToken = token.getAccessToken();// 네이버 로그인 접근 토큰;
        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
        param.setContent(param.getContent().replace("\"", "\'"));
        param.getNaverCafeList().forEach(naverCafe -> {
            try {
                String clubid = naverCafe.getClubid();// 카페의 고유 ID값
                String menuid = naverCafe.getMenuid(); // 카페 게시판 id (상품게시판은 입력 불가)
                String apiURL = "https://openapi.naver.com/v1/cafe/"+ clubid+"/menu/" + menuid + "/articles";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", header);
                // post request
                String subject = URLEncoder.encode(URLEncoder.encode(param.getSubject(), "UTF-8"), "MS949");;
                String content = URLEncoder.encode(URLEncoder.encode(param.getContent(), "UTF-8"), "MS949");
                // 카페 api 한글은  UTF8로 인코딩 한 값을 MS949로 한번더 인코딩 해야 함
                String postParams = "subject="+subject + "&content="+ content;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                ObjectMapper mapper = new ObjectMapper();
                HashMap<String, Object> responseMap = mapper.readValue(response.toString().replace("@", ""),
                        new TypeReference<HashMap<String, Object>>() {});
                String resultCode = ((Map<String, String>)responseMap.get("message")).get("status");
                if(resultCode.equals("200")) {
                    naverCafeSuccessList.add(naverCafe.getClubid() + "/" + naverCafe.getMenuid());
                } else {
                    Map<String, Object> error = (Map<String, Object>)((Map<String, Object>)responseMap.get("message")).get("error");
                    naverCafeFailureList.add(naverCafe.getClubid() + "/" + naverCafe.getMenuid() + "/" + error.get("msg"));
                }
                System.out.println(response.toString());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }

}
