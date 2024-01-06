package socialapp.authservice.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import socialapp.authservice.common.exception.InvalidCredentialsException;
import socialapp.authservice.common.exception.InvalidOtpException;
import socialapp.authservice.common.exception.OtpExpiredException;
import socialapp.authservice.common.mapper.UserMapper;
import socialapp.authservice.config.rabbit.RabbitProperties;
import socialapp.authservice.model.dto.EmailMessageDto;
import socialapp.authservice.model.dto.EmailVerificationRequestDto;
import socialapp.authservice.model.dto.RegistrationDto;
import socialapp.authservice.model.entity.User;
import socialapp.authservice.repository.UserRepository;
import socialapp.authservice.service.AuthService;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import static socialapp.authservice.common.AppConstants.OTP_BOUND;
import static socialapp.authservice.common.AppConstants.OTP_ORIGIN;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    private final SecureRandom random = new SecureRandom();
    @Value("${app.otp-ttl}")
    private Integer otpTtl;

    @Override
    public User register(
            RegistrationDto registrationDto
    ) {
        var otp = randomOtp();
        var hashedPassword = passwordEncoder.encode(registrationDto.password());
        var user = userMapper.toModel(registrationDto, hashedPassword, otp);

        rabbitTemplate.convertAndSend(
                rabbitProperties.getEmailVerificationExchange(),
                rabbitProperties.getEmailVerificationRoutingKey(),
                new EmailMessageDto(user.getEmail(), String.valueOf(otp))
        );

        return userRepository.save(user);
    }

    @Override
    public void confirmEmail(EmailVerificationRequestDto emailVerificationRequestDto) {
        var user = userRepository.findByEmail(emailVerificationRequestDto.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (user.getOtpCreationTime().isBefore(LocalDateTime.now().minusSeconds(otpTtl))) {
            throw new OtpExpiredException();
        }

        if (!user.getOtp().equals(emailVerificationRequestDto.otp())) {
            throw new InvalidOtpException();
        }

        user.setEmailVerified(true);
        userRepository.save(user);
    }

    private Integer randomOtp() {
        return random.nextInt(OTP_ORIGIN, OTP_BOUND);
    }

}


