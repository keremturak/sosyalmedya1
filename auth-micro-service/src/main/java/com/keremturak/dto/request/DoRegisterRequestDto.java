package com.keremturak.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DoRegisterRequestDto {
    @NotEmpty(message = "Kullanıcı adı boş geçilemez")
    @Size(min = 3,max=64,message = "Kullanıcı adı 3 ile 64 karakter arasında olmalıdır")
    String username;
    @Email(message = "Lütfen Geçerli bir e-posta adresi giriniz ")
    String email;
    @NotEmpty(message = "Şifreyi boş geçemezsiniz")
    @Size(min = 8,max = 64, message = "Şifre 8 ile 64 karakter arasında olmalıdır.")

    String password;
    @NotEmpty(message = "Şifreyi boş geçemezsiniz")
    @Size(min = 8,max = 64, message = "Şifre 8 ile 64 karakter arasında olmalıdır.")

    String passwordConfirm;
}