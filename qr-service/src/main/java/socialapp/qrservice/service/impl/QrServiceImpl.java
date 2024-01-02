package socialapp.qrservice.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import socialapp.qrservice.common.exception.QRGenerationException;
import socialapp.qrservice.service.QrService;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.CompletableFuture;

/**
 * @author Daneker
 */
@Service
@Slf4j
public class QrServiceImpl implements QrService {


    @Value("${qr.charset}")
    private String charset;

    /**
     * Asynchronously generates a QR code for the provided link and writes it
     * to the specified HttpServletResponse.
     *
     * @param response The HttpServletResponse to which the generated QR code
     *                 image will be written.
     * @param link     The link or data for which the QR code should be generated.
     * @return A CompletableFuture representing the asynchronous operation. The
     * CompletableFuture will be completed when the QR code generation
     * is finished. If the generation is successful, the CompletableFuture
     * will be completed normally; otherwise, it will be completed with
     * an exception.
     */
    @Override
    public CompletableFuture<Void> generateQRAsync(
            HttpServletResponse response,
            String link
    ) {
        return CompletableFuture.runAsync(() -> {
            BitMatrix matrix;
            try {
                matrix = new MultiFormatWriter()
                        .encode(
                                new String(link.getBytes(charset), charset),
                                BarcodeFormat.QR_CODE, 600, 600
                        );


                var bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
                response.setContentType("image/png");
                response.setHeader("Content-Disposition", "inline; filename=qr-code.png");

                var outputStream = response.getOutputStream();

                var byteArrayOutputStream = new ByteArrayOutputStream();

                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                byteArrayOutputStream.writeTo(outputStream);

                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                throw new QRGenerationException(link, e);
            }
        });
    }
}
