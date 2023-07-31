package com.keremturak.controller;

import com.keremturak.dto.request.DoLoginRequestDto;
import com.keremturak.dto.request.DoRegisterRequestDto;
import com.keremturak.dto.response.DoLoginResponseDto;
import com.keremturak.dto.response.DoRegisterResponseDto;
import com.keremturak.rabbitmq.model.CreateProfile;
import com.keremturak.rabbitmq.producer.CreateProfileProducer;
import com.keremturak.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.keremturak.constants.RestApis.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CreateProfileProducer createProfileProducer;

    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Merhaba bu servis Auth");
    }

    @GetMapping("/testrabbit")
    public ResponseEntity<Void> testRabbitSendMessage(String username, String email, Long authid) {
        createProfileProducer.sendCreateProfileMessage(
                CreateProfile.builder()
                        .authid(authid)
                        .email(email)
                        .username(username)
                        .build()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping(LOGIN)
    @CrossOrigin("*")
    public ResponseEntity<DoLoginResponseDto> doLogin(@RequestBody @Valid DoLoginRequestDto dto) {
        String token = authService.login(dto);
        return ResponseEntity.ok(DoLoginResponseDto.builder()
                .status(200)
                .result("Giriş İşlemi Başarılı")
                .token(token)
                .build());
    }

    @PostMapping(REGISTER)
    public ResponseEntity<DoRegisterResponseDto> doRegister(@RequestBody @Valid DoRegisterRequestDto dto) {
        Boolean isRegister = authService.register(dto);
        if (isRegister)
            return ResponseEntity.ok(DoRegisterResponseDto.builder()
                    .status(200)
                    .result("Kayıt İşlemi Başarılı")
                    .build());
        return ResponseEntity.badRequest().body(
                DoRegisterResponseDto.builder()
                        .status(400)
                        .result("Kayıt İşlemi Başarısız oldu. Lütfen tekrar deneyiniz.")
                        .build()
        );
    }
}