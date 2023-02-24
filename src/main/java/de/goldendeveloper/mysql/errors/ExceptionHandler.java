package de.goldendeveloper.mysql.errors;

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
        if (print && !breakProgramm) {
            throw new Exception(exception);
        } else if (print && breakProgramm) {
            System.out.println(exception.getMessage());
        } else if (!print && breakProgramm) {
            System.exit(1);
        }
    }
}
