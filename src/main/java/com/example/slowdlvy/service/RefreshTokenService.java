package com.example.slowdlvy.service;

import com.example.slowdlvy.domain.refreshToken.RefreshToken;
import com.example.slowdlvy.domain.refreshToken.RefreshTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    //로그인시 refreshToken DB에 저장
    @Transactional
    public void saveOrUpdateRefreshToken(String username, String refreshToken){
        Optional<RefreshToken> findToken = refreshTokenRepository.findByUsername(username);
        if (findToken.isPresent()) {
            findToken.get().updateToken(refreshToken);
        } else {
            refreshTokenRepository.save(new RefreshToken(username, refreshToken));
        }
    }

    @Transactional(readOnly = true)
    public Boolean refreshTokenIsExist(String username, String refreshToken){
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findByUsername(username);
        if (findRefreshToken.isPresent()){
            return findRefreshToken.get().isEqual(refreshToken);
        }else {
            return false;
        }
    }

    @Transactional
    public void deleteRefreshToken(String username){
        refreshTokenRepository.deleteByUsername(username);
    }
}
