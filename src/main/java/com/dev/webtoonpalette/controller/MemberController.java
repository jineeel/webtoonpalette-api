package com.dev.webtoonpalette.controller;

import com.dev.webtoonpalette.config.jwt.JWTUtil;
import com.dev.webtoonpalette.dto.MemberDTO;
import com.dev.webtoonpalette.service.MemberImageService;
import com.dev.webtoonpalette.service.MemberService;
import com.dev.webtoonpalette.util.FileUtil;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;
    private final FileUtil fileUtil;

    /**
     * 회원 정보 가져오기
     */
    @GetMapping("/{id}")
    public Map<String,Object> getMember (@PathVariable("id") Long id){

        MemberDTO memberDTO = memberService.getMember(id);
        Map<String, Object> claims = memberDTO.getClaims();

        String jwtAccessToken = jwtUtil.createJwt(claims, 10); //accessToken 10분
        String jwtRefreshToken = jwtUtil.createJwt(claims, 60*24);

        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);

        return claims;
    }

    /**
     * 회원 정보 수정
     */
    @PutMapping("/{id}")
    public MemberDTO modifyMember (@PathVariable("id")Long id, MemberDTO memberDTO){

        String uploadFileName = "";
        String oldFileName = "";
        boolean isChangeImage = memberDTO.isChangeImage();

        memberDTO.setId(id);

        MemberDTO oldMemberDTO = memberService.getMember(id);
        MultipartFile file = memberDTO.getFile();

        // 사용자가 프로필 사진을 새로 추가하지 않았을 경우 이전 파일을 그대로 쓴다.
        if(file == null) {
            uploadFileName = memberDTO.getUploadFileName();
            oldFileName = oldMemberDTO.getUploadFileName();
         }
        // 사용자가 프로필 사진을 새로 추가 하였을 경우 새로 파일을 저장한다.
        else{
            uploadFileName = fileUtil.saveFile(file);
            oldFileName = oldMemberDTO.getUploadFileName();
        }

        MemberDTO result = memberService.modify(memberDTO, uploadFileName, oldFileName, isChangeImage);

        // 이전 프로필 사진이 존재한다면 이미지 파일 삭제
        if(oldFileName != null && isChangeImage){
            String removeFile = oldFileName;
            fileUtil.deleteFile(removeFile);
        }
        return result;
    }


    /**
     * 프로필 사진 보기
     */
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName){
        return fileUtil.getFile(fileName);
    }
}
