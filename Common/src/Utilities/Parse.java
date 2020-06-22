package Utilities;

// https://stackoverflow.com/questions/8391979/does-java-have-a-int-tryparse-that-doesnt-throw-an-exception-for-bad-data
public class Parse {
    private Parse() {
    } // static

    public static Result<Integer> tryParseInt(String value) {
        Result<Integer> result = new Result(Result.State.FAILED, "", null);
        try {
            result.setObject(Integer.parseInt(value));
            result.setState(Result.State.SUCCESSFULL);
        } catch (NumberFormatException e) {
            result.setObject(null);
            result.setState(Result.State.FAILED);
        }

        return result;
    }

    public static int parseInteger(String value){
        return Integer.parseInt(value);
    }

    public static Result<Double> tryParseDouble(String value) {
        Result<Double> result = new Result(Result.State.FAILED, "", null);
        try {
            result.setObject(Double.parseDouble(value));
            result.setState(Result.State.SUCCESSFULL);
        } catch (NumberFormatException e) {
            result.setObject(null);
            result.setState(Result.State.FAILED);
        }

        return result;
    }

    public static double parseDouble(String value){
        return Double.parseDouble(value);
    }
}
