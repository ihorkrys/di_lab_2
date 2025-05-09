package edu.duan.app.api;

public enum CheckedExceptionType {
    SQL_EXCEPTION(1001, "SQL Exception"),
    IO_EXCEPTION(1002, "IO Exception"),
    PARSE_EXCEPTION(1003, "Parse Exception"),
    CLASS_NOT_FOUND_EXCEPTION(1004, "Class Not Found Exception"),
    METHOD_NOT_FOUND_EXCEPTION(1005, "Method Not Found Exception");

    private int statusCode;
    private String responseCode;

    CheckedExceptionType(int statusCode, String responseCode) {
        this.statusCode = statusCode;
        this.responseCode = responseCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
    public String getResponseCode() {
        return responseCode;
    }
}
