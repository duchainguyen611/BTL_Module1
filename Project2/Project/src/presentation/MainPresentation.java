package presentation;

import bussiness.ConsoleColors;
import bussiness.Method;

import java.util.Scanner;

public class MainPresentation {

    public static Scanner scanner = new Scanner(System.in);

    public static void menuMain() {
        {
            System.out.println(Method.makeColor("\n╔════════════════════════════════════╗\n" +
                                                     "║     ⁂ ⁂ ⁂ QUẢN LÝ KHO ⁂ ⁂ ⁂     ║\n" +
                                                     "╚════════════════════════════════════╝", ConsoleColors.CYAN_BOLD_BRIGHT));
            System.out.println(Method.makeColor("1. Quản lý danh mục\n" +
                    "2. Quản lý sản phẩm\n" +
                    "3. Thoát", ConsoleColors.YELLOW_BOLD_BRIGHT));
            System.out.println(Method.makeColor("Mời nhập lựa chọn:", ConsoleColors.YELLOW_BOLD_BRIGHT));
            int choice = Method.validateInteger(scanner);
            switch (choice) {
                case 1:
                    CategoryPresentation.menuCategory(scanner);
                    break;
                case 2:
                    ProductPresentation.menuProduct(scanner);
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.err.println("Mời nhập từ 1 đén 3!");
                    break;
            }
        }
        while (true) ;
    }
}
