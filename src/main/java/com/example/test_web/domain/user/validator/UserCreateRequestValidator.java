package com.example.test_web.domain.user.validator;

import com.example.test_web.domain.user.dto.UserCreateRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@Component
@Validated
public class UserCreateRequestValidator {

    public UserCreateRequestValidator() {
    }

    public String validate(UserCreateRequest request) {
        System.out.println(request);
        Map<String, Supplier<String>> fields = Map.of(
                "아이디", request::getUserId,
                "비밀번호", request::getPass,
                "게임 코드", request::getLimbusGameCode
        );

        Pattern koreanPattern = Pattern.compile("[ㄱ-ㅎㅏ-ㅣ가-힣]");

        for (Map.Entry<String, Supplier<String>> entry : fields.entrySet()) {
            String label = entry.getKey();
            String value = entry.getValue().get();

            // 빈값 체크
            if (value.isEmpty()) {
                return label + "를 입력하세요.";
            }

            // 40자 넘는지 체크
            if (value.length() > 40) {
                return label + "는 40자 이하로 입력해주세요.";
            }

            // 아이디 한글 포함 여부만 검사
            if (label.equals("아이디") && koreanPattern.matcher(value).find()) {
                return "아이디에는 한글을 사용할 수 없습니다.";
            }
        }

        return null;
    }
}
