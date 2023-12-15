package presentation;

import bussiness.ConsoleColors;
import bussiness.Method;
import bussinessImp.ProductBussinessImp;
import entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductPresentation {

    public static List<Product> productList = new ArrayList<>();
    public static void menuProduct(Scanner scanner){
        ProductBussinessImp productBussinessImp = new ProductBussinessImp();
        do {
            System.out.println(Method.makeColor("\n╔════════════════════════════════════╗\n" +
                                                     "║🟰🟰🟰🟰 QUẢN LÝ SẢN PHẨM 🟰🟰🟰🟰║\n" +
                                                     "╚════════════════════════════════════╝", ConsoleColors.CYAN_BOLD_BRIGHT));
            System.out.println(Method.makeColor("1. Thêm mới sản phẩm\n" +
                    "2. Cập nhật sản phẩm\n" +
                    "3. Xóa sản phẩm\n" +
                    "4. Hiển thị sản phẩm theo tên A-Z\n" +
                    "5. Hiển thị sản phẩm theo lợi nhuận từ cao-thấp\n" +
                    "6. Tìm kiếm sản phẩm\n" +
                    "7. Quay lại",ConsoleColors.YELLOW_BOLD_BRIGHT));
            System.out.println(Method.makeColor("Mời nhập lựa chọn:",ConsoleColors.YELLOW_BOLD_BRIGHT));
            int choice = Method.validateInteger(scanner);
            switch (choice){
                case 1:
                    productBussinessImp.inputData();
                    ProductBussinessImp.writeDataToFile(productList);
                    break;
                case 2:
                    productBussinessImp.updateProduct(scanner,productList);
                    ProductBussinessImp.writeDataToFile(productList);
                    break;
                case 3:
                    productBussinessImp.deleteProduct(scanner,productList);
                    ProductBussinessImp.writeDataToFile(productList);
                    break;
                case 4:
                    productBussinessImp.displayData();
                    break;
                case 5:
                    productBussinessImp.displayProductByProfit(productList);
                    break;
                case 6:
                    productBussinessImp.lookForProduct(productList,scanner);
                    break;
                case 7:
                    MainPresentation.menuMain();
                    break;
                default:
                    System.err.println("Mời nhập từ 1 đến 7!");
                    break;
            }
        }while (true);
    }
}
