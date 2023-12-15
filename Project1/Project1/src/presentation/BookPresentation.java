package presentation;

import bussiness.ConsoleColors;
import bussiness.Method;
import bussinessImp.BookBussinessImp;
import bussinessImp.CategoryBussinessImp;
import entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookPresentation {
    public static List<Book> bookList = new ArrayList<>();

    public static void menuBook(Scanner scanner) {

        BookBussinessImp bookBussinessImp = new BookBussinessImp();

        do {
            System.out.println(Method.makeColor("\n╔═════════════════════════════════════╗\n║▂ ▄ ▅ ▇ █ MENU QUẢN LÝ SÁCH █ ▇ ▆ ▄ ▂║\n╚═════════════════════════════════════╝",ConsoleColors.BLUE_BOLD_BRIGHT));
            System.out.println(Method.makeColor("1. Thêm mới sách\n" +
                    "2. Cập nhật thông tin sách\n" +
                    "3. Xóa sách\n" +
                    "4. Tìm kiếm sách \n" +
                    "5. Hiển thị danh sách sách theo nhóm thể loại\n" +
                    "6. Hiển thị tất cả thông tin sách\n" +
                    "7. Quay lại", ConsoleColors.YELLOW_BOLD_BRIGHT));
            System.out.println(Method.makeColor("Mời nhập lựa chọn:",ConsoleColors.YELLOW_BOLD_BRIGHT));
            int choice = Method.validateInteger(scanner);
            switch (choice) {
                case 1:
                    CategoryPresentation.categoryList = CategoryBussinessImp.readDataFromFile();
                    bookBussinessImp.input();
                    BookBussinessImp.writeDataToFile(bookList);
                    break;
                case 2:
                    bookBussinessImp.updateBook(scanner, bookList);
                    BookBussinessImp.writeDataToFile(bookList);
                    break;
                case 3:
                    bookBussinessImp.deleteBook(scanner, bookList);
                    BookBussinessImp.writeDataToFile(bookList);
                    break;
                case 4:
                    bookBussinessImp.lookForBook(scanner, bookList);
                    break;
                case 5:
                    bookBussinessImp.displayData();
                    break;
                case 6:
                    bookBussinessImp.output();
                    break;
                case 7:
                    MainPresentation.mainMenu();
                    break;
                default:
                    System.err.println("⚠ Warning: Mời nhập từ 1 đến 7!");
                    break;
            }
        } while (true);
    }
}
