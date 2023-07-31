package com.keremturak.service;

import com.keremturak.dto.request.UserSaveRequestDto;
import com.keremturak.exceptions.ErrorType;
import com.keremturak.exceptions.UserException;
import com.keremturak.repository.IUserRepository;
import com.keremturak.repository.entity.User;
import com.keremturak.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository repository;
    private final JwtTokenManager jwtTokenManager;

    public void save(UserSaveRequestDto dto){
        repository.save(User.builder()
                .authid(dto.getAuthid())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build());
    }

    public List<User> findAll(){
        return repository.findAll();
    }
    public List<User> findAll(String token){
        Optional<Long> id = jwtTokenManager.getByIdFromToken(token);
        if (id.isEmpty()) throw new UserException(ErrorType.INVALID_TOKEN);
        return repository.findAll();
    }
}
