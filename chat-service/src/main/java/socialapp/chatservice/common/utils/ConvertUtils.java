package socialapp.chatservice.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import socialapp.chatservice.model.entity.AppUser;

@UtilityClass
public class ConvertUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convert(Object source, Class<T> targetClass) {
        return objectMapper.convertValue(source, targetClass);
    }

    public static AppUser convertToAppUser(String source) {
        try {
            return objectMapper.readValue(source, AppUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
