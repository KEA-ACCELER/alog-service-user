package kea.alog.user.utils;

import java.util.Random;

public class CreateRandomCode {
    // 6자리 숫자, 대소문자 코드 생성 메서드
    public static String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                default:
                    key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }
}
