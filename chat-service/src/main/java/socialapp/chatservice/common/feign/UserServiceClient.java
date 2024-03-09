package socialapp.chatservice.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import socialapp.chatservice.model.dto.IsExistsResponse;

@FeignClient(name = "user-service", url = "http://localhost:8083")
public interface UserServiceClient {

    @GetMapping("/api/v1/user/is-exists")
    IsExistsResponse isExists(@RequestParam String email);

}
