package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
@Log4j2
@Service
public class ApiService {

    private final WebtoonRepository webtoonRepository;

    public void getApi(String jsonData){
        try{
            JSONObject jObj;
            JSONParser jsonParser = new JSONParser(jsonData);
            JSONObject jsonObj = (JSONObject) jsonParser.parse();

            JSONArray array = (JSONArray) jsonObj.get("webtoons");

            Set<Webtoon> webtoons = new HashSet<Webtoon>();

            for(int i=0; i<array.size(); i++){
                jObj = (JSONObject) array.get(i);

                JSONObject additional = (JSONObject) jObj.get("additional");

                boolean isAdult = (boolean) additional.get("adult");
                boolean isRest = (boolean) additional.get("rest");

                String adultValue = isAdult ? "Y" : "N";
                String restValue = isRest ? "Y" : "N";
                String isUpdateDay = jObj.get("updateDays").toString();
                String updateDayValue = isUpdateDay.substring(2, isUpdateDay.length()-2);

                Webtoon webtoon = Webtoon.builder()
                        .title(jObj.get("title").toString())
                        .author(jObj.get("author").toString())
                        .url(jObj.get("url").toString())
                        .img(jObj.get("img").toString())
                        .platform(jObj.get("service").toString())
                        .updateDay(updateDayValue)
                        .adult(adultValue.toString())
                        .rest(restValue.toString())
                        .searchKeyword(jObj.get("searchKeyword").toString())
                        .build();

                webtoons.add(webtoon);
            }
            webtoonRepository.saveAll(webtoons);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getTotalCount(String jsonData){
        int result = 0;

        try{
            JSONParser jsonParser = new JSONParser(jsonData);
            JSONObject jsonObj = (JSONObject) jsonParser.parse();

            String naverCount = jsonObj.get("naverWebtoonCount").toString();
            String kakaoCount = jsonObj.get("kakaoWebtoonCount").toString();

            result = parseInt(naverCount)+ parseInt(kakaoCount);

            log.info("result : "+result);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }
}
