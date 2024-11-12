package SWP391.Fall24.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    INVALID_KEY(1001, "Uncategorized error"),
    USER_EXISTED(1002, "User existed"),
    USERNAME_INVALID(1003, "Username must be at least 3 characters"),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters"),
    USER_NOT_EXISTED(1005, "User not existed"),
    UNAUTHENTICATED(1006, "Unauthenticated"),
    PASSWORD_ERROR(1007, "Password error"),
    CONFIRMED_PASSWORD_ERROR(1008, "Comfirmed password error"),
    FAIL_LOGIN(1009, "Login failed"),
    FAIL_LOGIN_BANNED_ACCOUNT(1009, "Login failed because your account has been removed"),
    FAIL_OTP(1010, "OTP error"),
    FISH_NOT_EXISTED(1011, "Fish not existed"),
    KOI_NOT_EXISTED(1011, "Koi not existed"),
    BATCH_NOT_EXISTED(1011, "Batch not existed"),
    CONSIGNED_KOI_NOT_EXISTED(1011, "Consign koi not existed"),
    ORDER_NOT_EXISTED(1011, "Order not existed"),
    FAIL_RETURN_CONSIGNED_KOI(1012, "Fail to set status consigned koi from consign order"),
    FAIL_RETURN_CARED_KOI(1012, "Fail to set status for cared koi from consign order"),
    OUT_OF_ROLE(1013, "This staff does not have permission to resolve order"),
    CART_NULL(1013, "Cart is null"),
    OUT_OF_CATEGORY_FISH(1013, "This fish is out of fish's fish"),
    QUANTITY_EXCESS(1013, "The number of fish in warehouse is not enough to sell"),
    CONSIGNED_FISH_OWNER(1013, "This fish is possessed by this customer, can not buy")
    ;
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
