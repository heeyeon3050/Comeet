package com.a506.comeet.app.member.controller;

import com.a506.comeet.app.member.MemberUtil;
import com.a506.comeet.app.member.controller.dto.MemberDuplicationRequestDto;
import com.a506.comeet.app.member.controller.dto.MemberSigninRequestDto;
import com.a506.comeet.app.member.controller.dto.MemberUpdateRequestDto;
import com.a506.comeet.app.member.entity.Member;
import com.a506.comeet.error.errorcode.CommonErrorCode;
import com.a506.comeet.error.exception.RestApiException;
import com.a506.comeet.app.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity signup(@RequestBody @Valid MemberSigninRequestDto req){
        req.setRoles(List.of("USER"));
        Member created = memberService.create(req);
        return new ResponseEntity<String>(created.getMemberId(), HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<Void> update(@Valid @RequestBody MemberUpdateRequestDto req){
        // 요청자 정보 가져오기
        String memberId = MemberUtil.getMemberId();
        memberService.update(req, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/delete")
    public ResponseEntity<Void> delete(){
        // 요청자 정보 가져오기
        String memberId = MemberUtil.getMemberId();
        memberService.delete(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<?> duplicationValidate(@RequestBody MemberDuplicationRequestDto req){
        if(req.isAllNull()) throw new RestApiException(CommonErrorCode.WRONG_REQUEST);
        return new ResponseEntity<Boolean>(memberService.duplicationValid(req),HttpStatus.OK);
    }

}
