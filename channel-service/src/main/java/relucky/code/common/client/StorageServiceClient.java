package relucky.code.common.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "storage-service-client")
public interface StorageServiceClient {
}
