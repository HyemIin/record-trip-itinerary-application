package org.ybe.global.common;

public enum Answer {
    Y,N;

    public static Answer from(String answer) {
        if ("y".equals(answer) || "Y".equals(answer)) {
            return Y;
        }
        if ("n".equals(answer) || "N".equals(answer)) {
            return N;
        }
        throw new IllegalArgumentException("답변이 올바르지 않습니다.");
    }
}
