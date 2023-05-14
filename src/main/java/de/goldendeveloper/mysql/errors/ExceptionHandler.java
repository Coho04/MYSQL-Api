package de.goldendeveloper.mysql.errors;

public class ExceptionHandler {

    private boolean print = true;
    private boolean breakProgramm = false;

    @SuppressWarnings("unused")
    public boolean isPrint() {
        return print;
    }

    @SuppressWarnings("unused")
    public void setPrint(boolean print) {
        this.print = print;
    }

    @SuppressWarnings("unused")
    public void setBreakProgramm(boolean breakProgramm) {
        this.breakProgramm = breakProgramm;
    }

    public void callException(Exception exception) throws Exception {
        if (print) {
            throw new Exception(exception);
        }
        if (breakProgramm) {
            System.exit(1);
        }
    }
}
