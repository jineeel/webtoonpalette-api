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

    @PutMapping("/{id}")
    public MemberDTO modifyMember (@PathVariable("id")Long id, MemberDTO memberDTO){

        String uploadFileName = "";
        String oldFileName = "";
        boolean isChangeImage = memberDTO.isChangeImage();

        memberDTO.setId(id);

        MemberDTO oldMemberDTO = memberService.getMember(id);
        MultipartFile file = memberDTO.getFile();

        if(file == null) {
            uploadFileName = memberDTO.getUploadFileName();
            oldFileName = oldMemberDTO.getUploadFileName();

        }else{
            uploadFileName = fileUtil.saveFile(file);
            oldFileName = oldMemberDTO.getUploadFileName();
        }

        MemberDTO result = memberService.modify(memberDTO, uploadFileName, oldFileName, isChangeImage);

        if(oldFileName != null && isChangeImage){
            log.info("------oldFile DELETE-----");
            String removeFile = oldFileName;
            fileUtil.deleteFile(removeFile);
        }
        return result;
    }


    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName){
        return fileUtil.getFile(fileName);
    }
}
