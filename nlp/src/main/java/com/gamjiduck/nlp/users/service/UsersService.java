package com.gamjiduck.nlp.users.service;

import com.gamjiduck.nlp.base.BaseException;
import com.gamjiduck.nlp.config.JwtTokenProvider;
import com.gamjiduck.nlp.users.domain.RefreshToken;
import com.gamjiduck.nlp.users.domain.Users;
import com.gamjiduck.nlp.users.dto.JoinDto;
import com.gamjiduck.nlp.users.dto.LoginDto;
import com.gamjiduck.nlp.users.dto.TokenDto;
import com.gamjiduck.nlp.users.repository.RefreshTokenRedisRepository;
import com.gamjiduck.nlp.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.gamjiduck.nlp.base.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public void join(JoinDto joinDto) throws BaseException {
        joinDto.setPassword(passwordEncoder.encode(joinDto.getPassword()));
        if (!usersRepository.findByUsername(joinDto.getUsername()).isEmpty()) {
            throw new BaseException(DUPLICATED_USERNAME);
        }
        if (!usersRepository.findByNickname(joinDto.getNickname()).isEmpty()) {
            throw new BaseException(DUPLICATED_NICKNAME);
        }
        usersRepository.save(Users.makeUser(joinDto));
    }

    public TokenDto login(LoginDto loginDto) throws BaseException {
        Users users = usersRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new BaseException(NOT_FOUND_USERNAME));
        if (!passwordEncoder.matches(loginDto.getPassword(), users.getPassword()))
            throw new BaseException(INVALID_PASSWORD);
        String username = users.getUsername();
        String accessToken = jwtTokenProvider.generateAccessToken(username);
        RefreshToken refreshToken = saveRefreshToken(username);
        return TokenDto.of(accessToken, refreshToken.getRefreshToken());
    }

    private RefreshToken saveRefreshToken(String username) {
        return refreshTokenRedisRepository.save(RefreshToken.createRefreshToken(username,
                jwtTokenProvider.generateRefreshToken(username), 1000L * 60 * 60 * 24 * 30));
    }
}
