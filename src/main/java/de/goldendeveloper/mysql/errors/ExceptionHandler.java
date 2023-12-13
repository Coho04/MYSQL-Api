package de.goldendeveloper.mysql.errors;

/**
 * The ExceptionHandler class handles exceptions and takes action based on the configuration flags.
 */
@SuppressWarnings("unused")
public class ExceptionHandler {

    private boolean print;
    private boolean breakProgramm;

    /**
     * The ExceptionHandler class handles exceptions and takes action based on the configuration flags.
     */
    public ExceptionHandler() {
        this.print = true;
        this.breakProgramm = false;
    }

    /**
     * Returns the value of the 'print' flag.
     *
     * @return true if print is enabled, false otherwise
     */
    public boolean isPrint() {
        return print;
    }

    /**
     * Sets the value of the 'print' flag.
     *
     * @param print the new value of the 'print' flag
     */
    public void setPrint(boolean print) {
        this.print = print;
    }

    /**
     * Sets the value of the 'breakProgramm' flag.
     *
     * @param breakProgramm the new value of the 'breakProgramm' flag
     */
    public void setBreakProgramm(boolean breakProgramm) {
        this.breakProgramm = breakProgramm;
    }

    /**
     * Throws an exception and takes action based on the configuration flags.
     *
     * @param exception The exception to throw.
     * @throws Exception if the 'print' flag is enabled.
     */
    public void callException(Exception exception) throws Exception {
        if (print) {
            throw new Exception(exception);
        }
        if (breakProgramm) {
            System.exit(1);
        }
    }
}
