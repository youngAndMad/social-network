package socialapp.qrservice.service;

import jakarta.servlet.http.HttpServletResponse;

import java.util.concurrent.CompletableFuture;

/**
 * This interface defines a service for generating QR codes asynchronously.
 * Implementing classes should provide the functionality to generate QR codes
 * based on a given link and write the result to an HttpServletResponse.
 * @author Daneker
 */
public interface QrService {

    /**
     * Asynchronously generates a QR code for the provided link and writes it
     * to the specified HttpServletResponse.
     *
     * @param response The HttpServletResponse to which the generated QR code
     *                 image will be written.
     * @param link     The link or data for which the QR code should be generated.
     * @return A CompletableFuture representing the asynchronous operation. The
     *         CompletableFuture will be completed when the QR code generation
     *         is finished. If the generation is successful, the CompletableFuture
     *         will be completed normally; otherwise, it will be completed with
     *         an exception.
     */
    CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String link);

}
