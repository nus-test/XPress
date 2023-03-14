package XTest;

public class DataCheckUtils {
    public static boolean integerAdditionCheck(Integer num1, Integer num2) {
        if(num1 >= 0 && num2 <= 0) return true;
        if(num1 <= 0 && num2 >= 0) return true;
        if(num1 >= 0) {
            return num1 <= (Integer.MAX_VALUE - num2);
        }
        else return num1 >= Integer.MIN_VALUE - num2;
    }

    public static boolean integerSubtractionCheck(Integer num1, Integer num2) {
        if(num1 >= 0 && num2 >= 0) return true;
        if(num1 <= 0 && num2 <= 0) return true;
        if(num1 >= 0) {
            return num1 <= Integer.MAX_VALUE + num2;
        }
        else return num1 >= Integer.MIN_VALUE + num2;
    }
}
