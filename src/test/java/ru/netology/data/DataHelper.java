package ru.netology.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("alex", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {

        return new VerificationCode("12345");
    }
    public static VerificationCode getVerificatioonCodeInvalid(AuthInfo authInfo) {
        return  new VerificationCode("54321");
    }

    @Value
    public static class CardDetails {
        private String number;
    }

    public static CardDetails getFirstCardDetails() {
        return new CardDetails("5559000000000001");
    }

    public static CardDetails getSecondCardDetails() {
        return new CardDetails("5559000000000002");
    }
}



