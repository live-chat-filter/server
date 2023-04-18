package com.gamjiduck.nlp.users.controller;

import com.gamjiduck.nlp.base.BaseException;
import com.gamjiduck.nlp.base.BaseResponse;
import com.gamjiduck.nlp.base.BaseResponseStatus;
import com.gamjiduck.nlp.users.dto.JoinDto;
import com.gamjiduck.nlp.users.dto.LoginDto;
import com.gamjiduck.nlp.users.dto.TokenDto;
import com.gamjiduck.nlp.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/join")
    public BaseResponse<String> join(@RequestBody JoinDto joinDto, BindingResult bindingResult) {
        try{
            usersService.join(joinDto);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getBaseResponseStatus());
        }
    }

    @PostMapping("/login")
    public BaseResponse<TokenDto> login(@RequestBody LoginDto loginDto, BindingResult bindingResult) {
        try {
            return new BaseResponse<>(usersService.login(loginDto));
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getBaseResponseStatus());
        }
    }
}
