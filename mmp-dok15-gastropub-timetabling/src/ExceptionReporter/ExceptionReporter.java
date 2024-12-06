package ExceptionReporter;

public class ExceptionReporter {
    public static void reportException( Exception e ){
        e.printStackTrace();
        System.err.println(
            "Class:\t" + e.getClass().getName() +
            "\n\tMessage:\t" + e.getMessage() +
            "\n\tCause:\t" + e.getCause()
        );
    }
}
