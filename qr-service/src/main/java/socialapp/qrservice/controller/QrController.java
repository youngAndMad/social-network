package socialapp.qrservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import socialapp.qrservice.service.QrService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class QrController {

    private final QrService qrService;

    @GetMapping("qr")
    public CompletableFuture<Void> generateQR(
            HttpServletResponse response,
            @RequestParam String link
    ) {
        return qrService.generateQRAsync(response, link);
    }

}
