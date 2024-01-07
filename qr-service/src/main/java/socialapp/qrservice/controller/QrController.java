package socialapp.qrservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import socialapp.qrservice.service.QrService;

import java.util.concurrent.CompletableFuture;

@RestController
@Tag(name = "QR Code Generator", description = "Generates QR codes for links.")
@RequiredArgsConstructor
public class QrController {

    private final QrService qrService;

    @GetMapping("generate")
    @Operation(summary = "Generates a QR code for the provided link.")
    CompletableFuture<Void> generateQR(
            HttpServletResponse response,
            @RequestParam String link
    ) {
        return qrService.generateQRAsync(response, link);
    }


}
