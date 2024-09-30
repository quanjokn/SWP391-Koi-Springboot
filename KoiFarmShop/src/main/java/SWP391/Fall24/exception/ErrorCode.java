package SWP391.Fall24.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(1000, "Uncategorized Exception"),
    INVALID_KEY(1001, "Invalid Key"),
    USER_EXIST(1111, "User already exist!");

    private int code;
    private String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
