package edu.duan.app.api;

public enum UnCheckedExceptionType {
    NPE_EXCEPTION(2001, "NullPointer Exception"),
    ARRAY_INDEX_EXCEPTION(2002, "ArrayIndexOutOfBounds Exception"),
    ILLEGAL_ARGUMENT_EXCEPTION(2003, "IllegalArgument Exception"),
    CLASS_CAST_EXCEPTION(2004, "ClassCast Exception"),
    ARITHMETIC_EXCEPTION(2005, "Arithmetic Exception"),;
    private int statusCode;
    private String responseCode;

    UnCheckedExceptionType(int statusCode, String responseCode) {
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
