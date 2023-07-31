package com.keremturak.manager;

import com.keremturak.dto.request.UserSaveRequestDto;
import com.keremturak.dto.response.UserSaveResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import static com.keremturak.constants.RestApis.SAVE;

@FeignClient(name = "user-micro-service-manager", url = "${my-project.user-service.url", dismiss404 = true)
public interface IUserManager {
    @PostMapping(SAVE)
    ResponseEntity<UserSaveResponseDto> save(@RequestBody UserSaveRequestDto dto);
}
