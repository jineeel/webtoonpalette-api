package com.dev.webtoonpalette.controller;

import com.dev.webtoonpalette.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 웹툰 OPEN API 호출하여 DB 저장하는 CONTROLLER
 */
@RequiredArgsConstructor
@RestController
public class ApiController {

    private final ApiService apiService;

    /**
     * API 요청
     */
//    @GetMapping("/contents")
    public void getApi() throws IOException {
        int totalCount = getTotalCount();
        String apiUrl = "https://korea-webtoon-api.herokuapp.com" +
                "/?perPage="+totalCount+
                "&service=kakao"+
                "&service=naver";
        String result = apiConnection(apiUrl);

        apiService.getApi(result);

//        return result;
    }

    /**
     * NAVER, KAKAO Webtoon Total Count
     */
    public int getTotalCount() throws IOException{

        String apiUrl = "https://korea-webtoon-api.herokuapp.com" +
                "/?perPage=1";

        String result = apiConnection(apiUrl);
        return apiService.getTotalCount(result);
    }

    /**
     * API Connection
     */
    public String apiConnection(String apiUrl) throws IOException {

        StringBuilder result = new StringBuilder();

        String urlStr = apiUrl;

        URL url = new URL(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;

        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        String returnLine;

        while((returnLine = br.readLine()) != null) {
            result.append(returnLine+"\n\r");
        }
        urlConnection.disconnect();

        return result.toString();
    }
}

