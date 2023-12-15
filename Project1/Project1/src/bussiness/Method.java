package bussiness;

import java.util.List;
import java.util.Scanner;

public class Method {

    //Bắt lỗi số nguyên tố
    public static int validateInteger(Scanner scanner) {
        do {
            try {
                int number = Integer.parseInt(scanner.nextLine().trim());
                if (number > 0) {
                    return number;
                } else {
                    System.err.println("Giá trị số nguyên > 0 vui lòng nhập lại");
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Vui lòng nhập số nguyên");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (true);
    }

    //Bắt lỗi chuỗi với độ dài và không để trống
    public static String lenghthString(int a, int b, Scanner scanner) {
        do {
            try {
                String s = scanner.nextLine().trim();
                if (s.trim().isEmpty()) {
                    System.err.println("Giá trị không được để trống.");
                } else {
                    if (s.length() > a && s.length() <= b) {
                        return s;
                    } else {
                        System.err.println("Nhập đúng số lượng ký tự!");
                    }
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (true);
    }

//    public static String centerText(String text, int width) {
//        int padding = (width - text.length()) / 2;
//        return String.format("%" + padding + "s%s%" + padding + "s", "", text, "");
//    }

    public static String makeColor(String s, String color) {
        return color + s + ConsoleColors.RESET;
    }

}
