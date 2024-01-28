package socialapp.newsservice.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import socialapp.newsservice.model.payload.EmailResponse;

import java.util.List;

@FeignClient(name = "user-service", path = "/api/v1/user/")
public interface UserServiceClient {

    @GetMapping("/emails")
    List<EmailResponse> fetchEmailList(
            @RequestParam Integer page,
            @RequestParam Integer pageSize
    );

}
