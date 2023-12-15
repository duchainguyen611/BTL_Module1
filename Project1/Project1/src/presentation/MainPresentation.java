package presentation;

import bussiness.ConsoleColors;
import bussiness.Method;

import java.util.Scanner;

public class MainPresentation {

    public static Scanner scanner = new Scanner(System.in);

    public static void mainMenu() {
        do {
            System.out.println(Method.makeColor("\n╔═════════════════════════════════╗\n║===== MENU QUẢN LÝ THƯ VIỆN =====║\n╚═════════════════════════════════╝", ConsoleColors.PURPLE_BOLD_BRIGHT));
            System.out.println(Method.makeColor("1. Quản lý Thể loại\n" +
                    "2. Quản lý Sách\n" +
                    "3. Thoát",ConsoleColors.YELLOW_BOLD_BRIGHT));
            System.out.println(Method.makeColor("Mời nhập lựa chọn:",ConsoleColors.YELLOW_BOLD_BRIGHT));
            int choice = Method.validateInteger(scanner);
            switch (choice) {
                case 1:
                    CategoryPresentation.menuCategory(scanner);
                    break;
                case 2:
                    BookPresentation.menuBook(scanner);
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.err.println("⚠ Warning: Mời nhập từ 1 đén 3!");
                    break;
            }
        } while (true);
    }
}
