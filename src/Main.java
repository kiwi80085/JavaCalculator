import java.util.*;
import java.util.stream.Collectors;

enum RomanNumeral
{
    I(1), IV(4), V(5), IX(9), X(10),
    XL(40), L(50), XC(90), C(100),
    CD(400), D(500), CM(900), M(1000);

    private int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static List<RomanNumeral> getReverseSortedValues() {
        return Arrays.stream(values()).sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed()).collect(Collectors.toList());
    }
}

class RomanNumber
{
    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
        }

        return result;
    }

    public static String arabicToRoman(int number)
    {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}

class CheckSystemOfNumber
{
    String number;
    public CheckSystemOfNumber(String number) {
        this.number = number;
    }
    boolean Check() throws Exception
    {
        boolean characterSystem = true; // true => Arabic; false => Roman

        if (number.charAt(0) != '-' & !Character.isDigit(number.charAt(0)))
        {
            characterSystem = false;
        }
        for (int i = 1; i<number.length(); i++)
        {
            if (characterSystem & Character.isDigit(number.charAt(i)))
            {
                characterSystem = true;
            }
            else if (!characterSystem & !Character.isDigit(number.charAt(i)))
            {
                characterSystem = false;
            }
            else
            {
                throw new Exception("Проверьте выражение на валидность");
            }
        }
        return characterSystem;
    }

}


public class Main {
    public static void main(String[] argv) throws Exception
    {
        System.out.print("Введите выражение: \n -> ");
        Scanner in = new Scanner(System.in);
        String expression = in.nextLine();
        System.out.println(calc(expression));
    }
    static char[] operators = {'-', '+', '*', '/'};
    public static int Calculating(int num1, int num2, String Operator) throws Exception
    {
        int result = 404;

        if (num1 > 0 & num1 <=10 & num2 > 0 & num2 <=10)
        {
            char operator = Operator.charAt(0);
            switch (operator){
                case '-':
                    result = num1 - num2;
                    break;
                case '+':
                    result = num1 + num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    result = num1 / num2;
                    break;
            }
        }
        else
        {
            throw new Exception("Размер чисел НЕ УДОВЛЕТВОРЯЕТ заданию - от 1 до 10 включительно, не более");
        }
        return result;
    }
    public static String calc(String input)throws Exception {

        String[] elements = input.split(" ");

        if(elements.length == 3)
        {
            String Operand1 = elements[0];
            String Operator = elements[1];
            String Operand2 = elements[2];

            boolean first = new CheckSystemOfNumber(Operand1).Check();
            boolean second = new CheckSystemOfNumber(Operand2).Check();

            if (first & second)
            {
                int firstOperand = Integer.parseInt(Operand1.trim());
                int secondOperand = Integer.parseInt(Operand2.trim());
                return Integer.toString(Calculating(firstOperand, secondOperand, Operator));
            }
            else if(!first & !second)
            {
                try {
                    int firstOperand = RomanNumber.romanToArabic(elements[0]);
                    int secondOperand = RomanNumber.romanToArabic(elements[2]);
                    int res = Calculating(firstOperand, secondOperand, Operator);
                    if (res < 1)
                    {
                        throw new Exception("В римской системе НЕТ отрицательных чисел");
                    }
                    return RomanNumber.arabicToRoman(res);
                }catch (IllegalArgumentException e){
                    System.out.println("Один из операндов не является числом");
                }
            }
            else
            {
                throw new Exception("Используются одновременно разные системы счисления");
            }

        }
        else if (elements.length < 3)
        {
            throw new Exception("Строка НЕ ЯВЛЯЕТСЯ математической операцией");
        }
        else
        {
            throw new Exception("Формат математической операции НЕ УДОВЛЕТВОРЯЕТ заданию - два операнда и один оператор (+, -, /, *)");
        }

        return ("The end");
    }
}