package com.keremturak.service;


import com.keremturak.dto.request.DoLoginRequestDto;
import com.keremturak.dto.request.DoRegisterRequestDto;
import com.keremturak.dto.request.UserSaveRequestDto;
import com.keremturak.exceptions.AuthException;
import com.keremturak.exceptions.ErrorType;
import com.keremturak.manager.IUserManager;
import com.keremturak.mapper.IAuthMapper;
import com.keremturak.repository.IAuthRepository;
import com.keremturak.repository.entity.Auth;
import com.keremturak.utility.JwtTokenManager;
import com.keremturak.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository repository;
    private final IUserManager userManager;
    private final JwtTokenManager jwtTokenManager;

    public AuthService(IAuthRepository repository, IUserManager userManager, JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository = repository;
        this.userManager = userManager;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Boolean register(DoRegisterRequestDto dto){
        if(!dto.getPassword().equals(dto.getPasswordConfirm())) // eğer şifre ile şifre doğrulama eşit değiş ise hata fırlat
            throw new AuthException(ErrorType.REGISTER_PASSWORDS_NOT_MATCH);
        repository.findOptionalByUsername(dto.getUsername()) // eğer kullanıcı adı var ise hata fırlat
                .ifPresent(auth -> {
                    throw new AuthException(ErrorType.REGISTER_KULLANICIADI_KAYITLI);
                });

        Auth auth = IAuthMapper.INSTANCE.authFromDto(dto);
        repository.save(auth);
        userManager.save(UserSaveRequestDto.builder().authid(auth.getId()).email(auth.getEmail()).email(auth.getEmail()).build());
        return true;
    }
    public String login(DoLoginRequestDto dto){
        Optional<Auth> auth = repository.findOptionalByUsernameAndPassword(dto.getUsername(),dto.getPassword());

        if(auth.isEmpty()) throw new AuthException(ErrorType.DOLOGIN_INVALID_USERNAME_PASSWORD);
        Optional<String> token = jwtTokenManager.createToken(auth.get().getId());
        if (token.isEmpty()) throw new AuthException(ErrorType.BAD_REQUEST_ERROR);
        return token.get();
    }

    public Optional<Auth> loginAlternatif(DoLoginRequestDto dto){
        return repository.findOptionalByUsernameAndPassword(dto.getUsername(),dto.getPassword());
    }


}