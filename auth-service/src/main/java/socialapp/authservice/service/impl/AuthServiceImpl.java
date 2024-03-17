package socialapp.authservice.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import socialapp.authservice.common.exception.InvalidOtpException;
import socialapp.authservice.common.exception.InvalidRequestPayloadException;
import socialapp.authservice.common.exception.InvalidResetPasswordToken;
import socialapp.authservice.common.exception.OtpExpiredException;
import socialapp.authservice.common.mapper.UserMapper;
import socialapp.authservice.model.dto.EmailMessageDto;
import socialapp.authservice.model.dto.RegistrationDto;
import socialapp.authservice.model.entity.User;
import socialapp.authservice.repository.UserRepository;
import socialapp.authservice.security.AppUserDetails;
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
    private final KafkaTemplate<String, EmailMessageDto> kafkaTemplate;

    @Value("${spring.kafka.queues.email-verification}")
    private String emailVerificationQueue;

    private final SecureRandom random = new SecureRandom();
    @Value("${app.otp-ttl}")
    private Integer otpTtl;

    @Override
    public User register(
            RegistrationDto registrationDto
    ) {
        if (userRepository.existsByEmail(registrationDto.email())) {
            throw new InvalidRequestPayloadException("User with this email already exists");
        }

        if (userRepository.existsByTag(registrationDto.tag())) {
            throw new InvalidRequestPayloadException("User with this tag already exists");
        }

        var otp = randomOtp();
        var hashedPassword = passwordEncoder.encode(registrationDto.password());
        var user = userMapper.toModel(registrationDto, hashedPassword, otp);

//        kafkaTemplate.send( todo uncomment
//                emailVerificationQueue,
//                new EmailMessageDto(user.getEmail(), String.valueOf(otp))
//        );

        return userRepository.save(user);
    }

    @Override
    public void confirmEmail(Integer otp, AppUserDetails userDetails) {
        var currentUser = userDetails.user();

        if (currentUser.getOtpCreationTime().isBefore(LocalDateTime.now().minusSeconds(otpTtl))) {
            throw new OtpExpiredException();
        }

        if (!currentUser.getOtp().equals(otp)) {
            throw new InvalidOtpException();
        }

        currentUser.setEmailVerified(true);
        userRepository.save(currentUser);
    }

    private Integer randomOtp() {
        return random.nextInt(OTP_ORIGIN, OTP_BOUND);
    }

}


