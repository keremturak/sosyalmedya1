package com.keremturak.controller;

import com.keremturak.dto.request.UserSaveRequestDto;
import com.keremturak.dto.response.UserSaveResponseDto;
import com.keremturak.repository.entity.User;
import com.keremturak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.keremturak.constants.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {
    private final UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> getHello(){
        return ResponseEntity.ok("Merhaba bu servis User");
    }

    @PostMapping(SAVE)
    @CrossOrigin("*")
    public ResponseEntity<UserSaveResponseDto> save(@RequestBody UserSaveRequestDto dto){
        userService.save(dto);
        return ResponseEntity.ok(UserSaveResponseDto.builder()
                .status(200)
                .result("Başarılı bir şekilde kayıt edildi.")
                .build());
    }

    @GetMapping(FINDALL)
    @CrossOrigin("*")
    public ResponseEntity<List<User>> findAll(String token){
        return ResponseEntity.ok(userService.findAll(token));
    }
}