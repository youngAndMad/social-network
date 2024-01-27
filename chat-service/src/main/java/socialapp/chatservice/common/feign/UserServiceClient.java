package socialapp.chatservice.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import socialapp.chatservice.model.dto.IsExistsResponse;

@FeignClient(name = "user-service", path = "api/v1/user-service/user")
public interface UserServiceClient {

    @GetMapping("is-exists")
    IsExistsResponse isExists(@RequestParam String email);

}
