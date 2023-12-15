package presentation;

import bussiness.ConsoleColors;
import bussiness.Method;
import bussinessImp.CategoryBussinessImp;
import entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoryPresentation {
    public static List<Category> categoryList = new ArrayList<>();

    public static void menuCategory(Scanner scanner) {
        CategoryBussinessImp categoryBussinessImp = new CategoryBussinessImp();
        do {
            System.out.println(Method.makeColor("\n╔════════════════════════════════════╗\n" +
                                                     "║🟰🟰🟰🟰 QUẢN LÝ DANH MỤC 🟰🟰🟰🟰║\n" +
                                                     "╚════════════════════════════════════╝", ConsoleColors.CYAN_BOLD_BRIGHT));
            System.out.println(Method.makeColor("1. Thêm mới danh mục\n" +
                    "2. Cập nhật danh mục\n" +
                    "3. Xóa danh mục\n" +
                    "4. Tìm kiếm danh mục theo tên danh mục\n" +
                    "5. Thống kê số lượng sp đang có trong danh mục\n" +
                    "6. Hiển thị tất cả thông tin danh mục\n" +
                    "7. Quay lại",ConsoleColors.YELLOW_BOLD_BRIGHT));
            System.out.println(Method.makeColor("Mời nhập lựa chọn:", ConsoleColors.YELLOW_BOLD_BRIGHT));
            int choice = Method.validateInteger(scanner);
            switch (choice) {
                case 1:
                    categoryBussinessImp.inputData();
                    CategoryBussinessImp.writeDataToFile(categoryList);
                    break;
                case 2:
                    categoryBussinessImp.updateCategory(scanner, categoryList);
                    CategoryBussinessImp.writeDataToFile(categoryList);
                    break;
                case 3:
                    categoryBussinessImp.deleteCategory(categoryList, ProductPresentation.productList, scanner);
                    CategoryBussinessImp.writeDataToFile(categoryList);
                    break;
                case 4:
                    categoryBussinessImp.lookForCategory(scanner, categoryList);
                    break;
                case 5:
                    categoryBussinessImp.statisticCategory(categoryList);
                    break;
                case 6:
                    categoryBussinessImp.displayData();
                    break;
                case 7:
                    MainPresentation.menuMain();
                    break;
                default:
                    System.err.println("Mời nhập từ 1 đến 7!");
                    break;
            }
        } while (true);
    }
}
