package Model;

public class MyException extends RuntimeException {
    private String msg;
    public MyException(String m) {
        msg = m;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
