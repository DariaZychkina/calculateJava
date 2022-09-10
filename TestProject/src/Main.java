import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class Main {


    public static void main(String[] args) throws  Exception{
        //ввод стрроки
        Scanner in = new Scanner(System.in);
        String expression = in.nextLine();
        expression = expression.trim(); //удаление лишних пробелов

        //регулярные выражения для определения правильности ввода выражения
        String regexArab = "[0-9]{1,2}\\s?[\\+\\-\\/\\*]\\s?[0-9]{1,2}"; // для арабских чисел
        String regexRom = "[IVX]{1,4}\\s?[\\+\\-\\/\\*]\\s?[IVX]{1,4}"; // для римских

        if (expression.matches(regexRom) || expression.matches(regexArab)){
            //рег выражение для извлечения оператора
            Pattern patternOperator = Pattern.compile("[\\+\\-\\*\\/]");
            Matcher matcherOperator = patternOperator.matcher(expression);
            matcherOperator.find();
            String operator = matcherOperator.group();

            //разделение операндов, удаление пробелов
            String[] operands = expression.split(patternOperator.toString());
            for(String op : operands){
                op.trim();
            }

            //если числа арабские
            if (expression.matches(regexArab)){
                int firstOperand = Integer.parseInt(operands[0]), secondOperand = Integer.parseInt(operands[1]);
                if (firstOperand > 10 || secondOperand > 10){
                    throw  new Exception("Значения не должны быть больше 10 ");
                }
                else {
                    System.out.println(resultOfAnArithmeticOperation(firstOperand, secondOperand, operator));
                }
            }

            //если римские
            else {
                int firstOperand = 0, secondOperand = 0;
                StringBuilder op1 = new StringBuilder(operands[0]);
                StringBuilder op2 = new StringBuilder(operands[1]);
                //перебор всех римских чисел
                for (RomanNumber romNumb : RomanNumber.values()) {
                    if (op1.indexOf(romNumb.toString()) != -1) {
                        //цикл на случай неоднократного ввода одинокового символа (III)
                        int countString = countStr(op1.toString(), romNumb.toString());
                        for(int i=0; i<countString; i++) {
                            firstOperand += romNumb.getArabicNumber();
                            if (romNumb.toString().length() == 1)
                                op1 = op1.deleteCharAt(op1.indexOf(romNumb.toString()));
                            else
                                op1 = op1.delete(op1.indexOf(romNumb.toString()), 2);
                        }
                    }
                    //для второго операнда
                    if (op2.indexOf(romNumb.toString()) != -1) {
                        int countString = countStr(op2.toString(), romNumb.toString());
                        for(int i=0; i<countString; i++) {
                            secondOperand += romNumb.getArabicNumber();
                            if (romNumb.toString().length() == 1)
                                op2 = op2.deleteCharAt(op2.indexOf(romNumb.toString()));
                            else
                                op2 = op2.delete(op2.indexOf(romNumb.toString()), 2);
                        }
                    }
                    if(op1.isEmpty() && op2.isEmpty()){
                        break;
                    }
                }
                if (firstOperand<=10 && secondOperand<=10){
                    int resultCalc = resultOfAnArithmeticOperation(firstOperand, secondOperand, operator);
                    if(resultCalc>0){
                        System.out.println(arabicNumberToRomanNum(resultCalc));
                    }
                    else
                        throw  new Exception("В римской системе нет отрицательных чисел ");
                }
                else
                    throw  new Exception("Значения не должны быть больше 10 ");
            }
        }
        else{
            throw  new Exception("Не удовлетворительный формат выражения");
        }
    }

    //расчет
    public static int resultOfAnArithmeticOperation(int firstOperand, int secondOperand, String operator) throws  Exception{
        switch (operator){
            case "+":
                return firstOperand+secondOperand;
            case "-":
                return firstOperand-secondOperand;
            case "*":
                return firstOperand*secondOperand;
            case "/":
                return firstOperand/secondOperand;
            default:
                throw  new Exception("");
        }
    }
    //преобразование результата расчета выражения с римскими цифрами из арабского числа в римское
    public static  String arabicNumberToRomanNum(Integer arabicNum){
        String result="";
        switch (arabicNum/10){
            case 10:
                result += "C";
                break;
            case 9:
                result +="XC";
                break;
            case 5:
                result += "L";
                break;
            case 4:
                result += "XL";
                break;
            default:
                if(arabicNum/10 <4){
                    for(int i =0; i< arabicNum/10; i++){
                        result+="X";
                    }
                }
                else if(arabicNum/10 > 5 && arabicNum/10 <9){
                    result += "L";
                    for(int i =0; i< arabicNum/10-5; i++){
                        result+="X";
                    }
                }
                break;
        }
        if (arabicNum%10 !=0){
            switch (arabicNum%10){
                case 9:
                    result+="IX";
                    break;
                case 5:
                    result+="V";
                    break;
                case 4:
                    result+="IV";
                    break;
                default:
                    if (arabicNum%10 < 5){
                        for (int i=0; i<arabicNum%10; i++){
                            result+="I";
                        }
                    }
                    else{
                        result+="V";
                        for (int i=0; i<arabicNum%10-5; i++){
                            result += "I";
                        }
                    }
            }
        }

        return  result;
    }

    //подсчет количества вхождений подстроки в строке
    public static int countStr(String str, String target) {
        return (str.length() - str.replace(target, "").length()) / target.length();
    }




}
