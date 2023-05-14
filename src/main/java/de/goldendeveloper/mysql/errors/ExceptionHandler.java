package de.goldendeveloper.mysql.errors;

@SuppressWarnings("unused")
public class ExceptionHandler {

    private boolean print = true;
    private boolean breakProgramm = false;

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

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
