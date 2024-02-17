package socialapp.newsservice.common.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import socialapp.newsservice.repository.EmailSendingRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledEmailSending {

//    private final KafkaTemplate<String,Object> kafkaTemplate;
//    private final EmailSendingRepository emailSendingRepository;
//
//    @Value("${app.email-sending-queue}")
//    private String emailSendingQueue;
//
//    @Value("${app.default-page-size}")
//    private Integer defaultPageSize;

    @Scheduled(cron = "${app.email-sending-cron}")
    public void scheduleEmailSending(){
    }
}
