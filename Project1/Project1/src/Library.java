
import bussinessImp.BookBussinessImp;
import bussinessImp.CategoryBussinessImp;
import presentation.BookPresentation;
import presentation.CategoryPresentation;
import presentation.MainPresentation;

import java.util.ArrayList;


public class Library {

    public static void main(String[] args) {

        //Đọc file
        try {
            BookPresentation.bookList = BookBussinessImp.readDataFromFile();
        } catch (Exception ex) {
            System.err.println("⚠ Warning: File books.txt not found!");
            BookPresentation.bookList = new ArrayList<>();
        }
        try {
            CategoryPresentation.categoryList = CategoryBussinessImp.readDataFromFile();
        } catch (Exception ex) {
            System.err.println("⚠ Warning: File categories.txt not found!");
            CategoryPresentation.categoryList = new ArrayList<>();
        }

        MainPresentation.mainMenu();
    }


}
