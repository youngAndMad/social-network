package socialapp.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import socialapp.authservice.common.exception.InvalidCredentialsException;
import socialapp.authservice.common.exception.ResetPasswordTokenExpired;
import socialapp.authservice.common.mapper.ResetPasswordTokenMapper;
import socialapp.authservice.config.ServerProperties;
import socialapp.authservice.config.rabbit.RabbitProperties;
import socialapp.authservice.model.dto.ResetPasswordRequestDto;
import socialapp.authservice.repository.ResetPasswordTokenRepository;
import socialapp.authservice.repository.UserRepository;
import socialapp.authservice.security.AppUserDetails;
import socialapp.authservice.service.ResetPasswordService;

import java.util.UUID;

import static java.time.LocalDateTime.now;
import static socialapp.authservice.common.AppConstants.RESET_PASSWORD_ENDPOINT;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;
    private final ServerProperties serverProperties;
    private final ResetPasswordTokenMapper resetPasswordTokenMapper;

    @Value("${app.password-reset-token-ttl}")
    private Integer resetPasswordTokenTtl;

    @Override
    public void resetRequest(AppUserDetails userDetails) {
        log.info("reset password request from = {}", userDetails.getUsername());

        var token = UUID.randomUUID().toString();
        var resetPasswordToken = resetPasswordTokenMapper
                .toModel(userDetails.getUser(),token, now().plusSeconds(resetPasswordTokenTtl));

        resetPasswordTokenRepository.save(resetPasswordToken);

        rabbitTemplate.convertAndSend(
                rabbitProperties.getResetPasswordExchange(),
                rabbitProperties.getResetPasswordRoutingKey(),
                serverProperties.getPath().concat(RESET_PASSWORD_ENDPOINT.concat(resetPasswordToken.getToken()))
        );
    }

    @Override
    public void resetAttempt(AppUserDetails userDetails, ResetPasswordRequestDto resetPasswordRequestDto) {
        var currentUser = userDetails.getUser();
        var optionalToken = resetPasswordTokenRepository.findByToken(resetPasswordRequestDto.token());

        if (optionalToken.isEmpty()){
            log.warn("invalid reset password token was taken");
            throw new ResetPasswordTokenExpired();
        }

        var resetPasswordToken = optionalToken.get();

        if (!currentUser.equals(resetPasswordToken.getUser())){
            throw new InvalidCredentialsException();
        }

        currentUser.setPassword(passwordEncoder.encode(resetPasswordRequestDto.password()));

        log.info("updated password for user = {}", currentUser.getEmail());

        userRepository.save(currentUser);
    }
}
