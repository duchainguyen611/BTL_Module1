import bussinessImp.CategoryBussinessImp;
import bussinessImp.ProductBussinessImp;
import presentation.CategoryPresentation;
import presentation.MainPresentation;
import presentation.ProductPresentation;

import java.util.ArrayList;

public class Store {
    public static void main(String[] args) {

        //Đọc file
        try {
            ProductPresentation.productList = ProductBussinessImp.readDataFromFile();
        } catch (Exception ex) {
            System.err.println("File products.txt not found!");
            ProductPresentation.productList = new ArrayList<>();
        }
        try {
            CategoryPresentation.categoryList = CategoryBussinessImp.readDataFromFile();
        } catch (Exception ex) {
            System.err.println("File categoties.txt not found!");
            CategoryPresentation.categoryList = new ArrayList<>();
        }
        MainPresentation.menuMain();
    }
}
