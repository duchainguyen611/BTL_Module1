package presentation;

import bussiness.ConsoleColors;
import bussiness.Method;

import bussinessImp.BookBussinessImp;
import bussinessImp.CategoryBussinessImp;
import com.sun.tools.javac.Main;
import entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoryPresentation {
    public static List<Category> categoryList = new ArrayList<>();

    public static void menuCategory(Scanner scanner) {

        CategoryBussinessImp categoryBussinessImp = new CategoryBussinessImp();
        do {
            System.out.println(Method.makeColor("\n╔═══════════════════════════════════╗\n║▄▀▄▀▄▀ MENU QUẢN LÝ THỂ LOẠI ▄▀▄▀▄▀║\n╚═══════════════════════════════════╝", ConsoleColors.CYAN_BOLD_BRIGHT));
            System.out.println(Method.makeColor("1. Thêm mới thể loại\n" +
                    "2. Hiển thị danh sách theo tên A – Z\n" +
                    "3. Thống kê thể loại và số sách có trong mỗi thể loại\n" +
                    "4. Cập nhật thể loại\n" +
                    "5. Xóa thể loại\n" +
                    "6. Quay lại",ConsoleColors.YELLOW_BOLD_BRIGHT));
            System.out.println(Method.makeColor("Mời nhập lựa chọn:",ConsoleColors.YELLOW_BOLD_BRIGHT));
            int choice = Method.validateInteger(scanner);
            switch (choice) {
                case 1:
                    categoryBussinessImp.input();
                    CategoryBussinessImp.writeDataToFile(categoryList);
                    break;
                case 2:
                    categoryBussinessImp.output();
                    break;
                case 3:
                    categoryBussinessImp.statisticCategory(categoryList);
                    break;
                case 4:
                    categoryBussinessImp.updateCategory(scanner, categoryList);
                    CategoryBussinessImp.writeDataToFile(categoryList);
                    break;
                case 5:
                    categoryBussinessImp.deleteCategory(scanner, categoryList, BookPresentation.bookList);
                    CategoryBussinessImp.writeDataToFile(categoryList);
                    break;
                case 6:
                    MainPresentation.mainMenu();
                    break;
                default:
                    System.err.println("⚠ Warning: Mời nhập từ 1 đến 6!");
                    break;
            }
        } while (true);
    }
}
