package bussinessImp;

import bussiness.ConsoleColors;
import bussiness.IProduct;
import bussiness.Method;
import entity.Category;
import entity.Product;
import presentation.CategoryPresentation;
import presentation.MainPresentation;
import presentation.ProductPresentation;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ProductBussinessImp implements IProduct {

    double profit = 0;
    double importPrice = 0;
    double exportPrice = 0;

    @Override
    public void inputData() {
        if (CategoryPresentation.categoryList.isEmpty()) {
            System.err.println("Không có thông tin danh mục! Thêm thông tin danh mục trước khi thêm thông tin sản phẩm!");
        } else {
            Scanner scanner = MainPresentation.scanner;
            List<Product> productList = ProductPresentation.productList;
            System.out.println(Method.makeColor("Nhập số sản phẩm muốn thêm để nhập thông tin:",ConsoleColors.WHITE_BOLD_BRIGHT));
            int n = Method.validateInteger(scanner);
            for (int i = 0; i < n; i++) {
                int serial = productList.size() + 1;
                System.out.println(Method.makeColor("Sản phẩm thứ " + serial + ":", ConsoleColors.WHITE_BOLD_BRIGHT));
                String id = inputId(scanner, productList);
                String name = inputName(scanner, productList);
                calProfit();
                String description = inputDescription(scanner);
                boolean status = inputStatus(scanner);
                int categoryId = inputCategoryId(scanner);
                Product product = new Product(id, name, importPrice, exportPrice, profit, description, status, categoryId);
                productList.add(product);
                System.out.println(Method.makeColor("✔ Thêm thành công sản phẩm thứ:" + productList.size(), ConsoleColors.GREEN_BOLD_BRIGHT));
            }
        }
    }

    @Override
    public void displayData() {
        if (ProductPresentation.productList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin sản phẩm! Ấn 1 để thêm thông tin sản phẩm!");
        } else {
            ProductPresentation.productList.sort(Comparator.comparing(Product::getName));
            drawTable(ProductPresentation.productList);
        }
    }

    @Override
    public void calProfit() {
        importPrice = inputImportPrice(MainPresentation.scanner);
        exportPrice = inputExportPrice(MainPresentation.scanner);
        profit = exportPrice - importPrice;
    }

    public String inputId(Scanner scanner, List<Product> productList) {
        System.out.println(Method.makeColor("Nhập mã sản phẩm (bắt đầu bằng 'P', độ dài 4 kí tự, duy nhất):",ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            String id = Method.lenghthString(3, 4, scanner);
            if (id.charAt(0) == 'P') {
                boolean isExit = true;
                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getId().equals(id)) {
                        isExit = false;
                        break;
                    }
                }
                if (isExit) {
                    return id;
                } else {
                    System.err.println("⚠ Warning: Mã sản phẩm này bị trùng! Mời nhập lại!");
                }
            } else {
                System.err.println("⚠ Warning: Mã sản phẩm phải bắt đầu ký tự P! Mời nhập lại!");
            }
        } while (true);
    }

    public String inputName(Scanner scanner, List<Product> productList) {
        System.out.println(Method.makeColor("Nhập tên sản phẩm (từ 6-50 kí tự, duy nhất):",ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            String name = Method.lenghthString(5, 50, scanner);
            boolean isExit = true;
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getName().equals(name)) {
                    isExit = false;
                    break;
                }
            }
            if (isExit) {
                return name;
            } else {
                System.err.println("⚠ Warning: Tên sản phẩm này bị trùng! Mời nhập lại!");
            }
        } while (true);
    }

    public double inputImportPrice(Scanner scanner) {
        System.out.println(Method.makeColor("Nhập giá nhập",ConsoleColors.WHITE_BOLD_BRIGHT));
        return Method.validateDouble(scanner);
    }

    public double inputExportPrice(Scanner scanner) {
        System.out.println(Method.makeColor("Nhập giá bán:",ConsoleColors.WHITE_BOLD_BRIGHT));
        double exportPrice = Method.validateDouble(scanner);
        do {
            if (exportPrice > importPrice + importPrice * MIN_INTEREST_RATE) {
                return exportPrice;
            } else {
                System.err.println("⚠ Warning: Giá bán có giá trị lớn hơn giá nhập ít nhất 0.2 lần");
            }
        } while (true);
    }

    public String inputDescription(Scanner scanner) {
        System.out.println(Method.makeColor("Nhập mô tả sản phẩm (không bỏ trống):",ConsoleColors.WHITE_BOLD_BRIGHT));
        return Method.lenghthString(0, 200, scanner);
    }

    public boolean inputStatus(Scanner scanner) {
        System.out.println(Method.makeColor("Nhập trạng thái sản phẩm (chỉ nhận true/false khi nhập):",ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            String statusString = Method.lenghthString(0, 50, scanner);
            if (statusString.equalsIgnoreCase("true") || statusString.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(statusString);
            } else {
                System.err.println("⚠ Warning: Chỉ nhận giá trị true | false! Mời nhập lại!");
            }
        } while (true);
    }

    public int inputCategoryId(Scanner scanner) {
        List<Category> categoryList = CategoryPresentation.categoryList;
        categoryList.sort(Comparator.comparing(Category::getId));
        CategoryBussinessImp.drawTable(categoryList);
        System.out.println(Method.makeColor("Nhập mã danh mục:",ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            boolean isExit = false;
            boolean isCheck = true;
            int id = Method.validateInteger(scanner);
            for (int i = 0; i < categoryList.size(); i++) {
                if (id == categoryList.get(i).getId()) {
                    if (categoryList.get(i).isStatus()){
                        return id;
                    }else {
                        isCheck = false;
                        break;
                    }
                }
            }
            if (!isCheck){
                System.err.println("⚠ Warning: Mã danh mục đang không hoạt động! Mời nhập mã khác!");
            }else if (!isExit) {
                System.err.println("⚠ Warning: ⚠ Warning: Phải nhập mã danh mục đã có!!");
            }
        } while (true);
    }

    public void updateProduct(Scanner scanner, List<Product> productList) {
        if (productList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin sản phẩm! Ấn 1 để thêm thông tin sản phẩm!");
        } else {
            boolean isExit = true;
            int indexUpdate = lookForIndexById(scanner, productList, "cập nhật");
            do {
                System.out.println(Method.makeColor("╔═══════════════════════════╗\n║------- MENU UPDATE -------║\n╚═══════════════════════════╝", ConsoleColors.BLUE_BOLD_BRIGHT));
                System.out.println(Method.makeColor("1. Tên sản phẩm\n2. Giá nhập\n3. Giá bán\n4. Mô tả sản phẩm\n5. Trạng thái sản phẩm\n6. Mã danh mục sản phẩm\n7. Thoát", ConsoleColors.YELLOW_BOLD_BRIGHT));
                System.out.println(Method.makeColor("Mời nhập lựa chọn:", ConsoleColors.YELLOW_BOLD_BRIGHT));
                int choice = Method.validateInteger(scanner);
                switch (choice) {
                    case 1:
                        productList.get(indexUpdate).setName(inputName(scanner, productList));
                        System.out.println(Method.makeColor("✔ Cập nhật thành công", ConsoleColors.GREEN_BOLD_BRIGHT));
                        break;
                    case 2:
                        productList.get(indexUpdate).setImportPrice(inputImportPrice(scanner));
                        System.out.println(Method.makeColor("✔ Cập nhật thành công", ConsoleColors.GREEN_BOLD_BRIGHT));
                        break;
                    case 3:
                        productList.get(indexUpdate).setExportPrice(inputExportPrice(scanner));
                        System.out.println(Method.makeColor("✔ Cập nhật thành công", ConsoleColors.GREEN_BOLD_BRIGHT));
                        break;
                    case 4:
                        productList.get(indexUpdate).setDescription(inputDescription(scanner));
                        System.out.println(Method.makeColor("✔ Cập nhật thành công", ConsoleColors.GREEN_BOLD_BRIGHT));
                        break;
                    case 5:
                        productList.get(indexUpdate).setStatus(!productList.get(indexUpdate).isStatus());
                        System.out.println(Method.makeColor("✔ Cập nhật thành công", ConsoleColors.GREEN_BOLD_BRIGHT));
                        break;
                    case 6:
                        productList.get(indexUpdate).setCategoryId(inputCategoryId(scanner));
                        System.out.println(Method.makeColor("✔ Cập nhật thành công", ConsoleColors.GREEN_BOLD_BRIGHT));
                        break;
                    case 7:
                        isExit = false;
                        break;
                    default:
                        System.err.println("⚠ Warning: Mời nhập từ 1 đến 7!");
                        break;
                }
            } while (isExit);
        }
    }

    public void deleteProduct(Scanner scanner, List<Product> productList) {
        if (productList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin sản phẩm! Ấn 1 để thêm thông tin sản phẩm!");
        } else {
            int indexDelete = lookForIndexById(scanner, productList, "xóa");
            System.out.println(Method.makeColor("Bạn chắc chắn muốn xóa Không?\n1. Xóa\n2. Thoát", ConsoleColors.BLUE_BOLD_BRIGHT));
            System.out.println(Method.makeColor("Mời nhập lựa chọn:", ConsoleColors.BLUE_BOLD_BRIGHT));
            int choice = Method.validateInteger(scanner);
            switch (choice) {
                case 1:
                    productList.remove(indexDelete);
                    System.out.println(Method.makeColor("✔ Xóa thành công", ConsoleColors.GREEN_BOLD_BRIGHT));
                    break;
                case 2:
                    ProductPresentation.menuProduct(scanner);
                    break;
                default:
                    System.err.println("⚠ Warning: Mời nhập 1 và 2!");
                    break;
            }
        }
    }

    public int lookForIndexById(Scanner scanner, List<Product> productList, String type) {
        boolean isExit = false;
        System.out.println(Method.makeColor("Nhập mã sản phẩm muốn " + type + ":", ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            String id = Method.lenghthString(0, 50, scanner);
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getId().equals(id)) {
                    return i;
                }
            }
            if (!isExit) {
                System.err.println("⚠ Warning: Mã sản phẩm không tồn tại! Mời nhập lại!");
            }
        } while (true);
    }

    public void displayProductByProfit(List<Product> productList) {
        if (productList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin sản phẩm! Ấn 1 để thêm thông tin sản phẩm!");
        } else {
            productList.sort(Comparator.comparing(Product::getProfit).reversed());
            drawTable(productList);
        }
    }

    public void lookForProduct(List<Product> productList, Scanner scanner) {
        if (productList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin sản phẩm! Ấn 1 để thêm thông tin sản phẩm!");
        } else {
            System.out.println(Method.makeColor("Nhập từ khóa tìm kiếm sản phẩm",ConsoleColors.WHITE_BOLD_BRIGHT));
            String lfp = Method.lenghthString(0, 50, scanner);
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "                                                 Bảng thông tin sản phẩm được tìm kiếm theo tên                        " + ConsoleColors.RESET);
            for (int i = 0; i < 141; i++) {
                System.out.print("─");
            }
            System.out.println();
            System.out.printf("|%-25s|%-40s|%-20s|%-20s|%-20s|%-30s|%-25s|%-40s|%n",
                    Method.makeColor("Mã sản phẩm", ConsoleColors.GREEN_BOLD_BRIGHT),
                    Method.makeColor("Tên sản phẩm", ConsoleColors.GREEN_BOLD_BRIGHT),
                    Method.makeColor("Giá nhập", ConsoleColors.GREEN_BOLD_BRIGHT),
                    Method.makeColor("Giá xuất", ConsoleColors.GREEN_BOLD_BRIGHT),
                    Method.makeColor("Lợi nhuận sản phẩm", ConsoleColors.GREEN_BOLD_BRIGHT),
                    Method.makeColor("Mô tả", ConsoleColors.GREEN_BOLD_BRIGHT),
                    Method.makeColor("Trạng thái", ConsoleColors.GREEN_BOLD_BRIGHT),
                    Method.makeColor("Danh mục sản phẩm", ConsoleColors.GREEN_BOLD_BRIGHT));
            for (int i = 0; i < 141; i++) {
                System.out.print("─");
            }
            System.out.println();
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getName().toLowerCase().contains(lfp.toLowerCase()) || String.valueOf(productList.get(i).getImportPrice()).contains(lfp)
                        || String.valueOf(productList.get(i).getExportPrice()).contains(lfp)) {
                    System.out.printf("|%-25s|%-40s|%-20s|%-20s|%-20s|%-30s|%-25s|%-40s|%n",
                            Method.makeColor(productList.get(i).getId(), ConsoleColors.BLUE_BOLD),
                            Method.makeColor(productList.get(i).getName(), ConsoleColors.BLUE_BOLD),
                            Method.makeColor(String.valueOf(productList.get(i).getImportPrice()), ConsoleColors.BLUE_BOLD),
                            Method.makeColor(String.valueOf(productList.get(i).getExportPrice()), ConsoleColors.BLUE_BOLD),
                            Method.makeColor(String.valueOf(productList.get(i).getProfit()), ConsoleColors.BLUE_BOLD),
                            Method.makeColor(productList.get(i).getDescription(), ConsoleColors.BLUE_BOLD),
                            Method.makeColor(productList.get(i).isStatus() ? "Bán" : "Không bán", ConsoleColors.BLUE_BOLD),
                            Method.makeColor(ProductBussinessImp.getNameCategory(productList.get(i).getCategoryId(), CategoryPresentation.categoryList), ConsoleColors.BLUE_BOLD));
                    for (int j = 0; j < 141; j++) {
                        System.out.print("─");
                    }
                    System.out.println();
                }
            }
        }
    }

    public static String getNameCategory(int id, List<Category> categoryList) {
        String name = null;
        for (int i = 0; i < categoryList.size(); i++) {
            if (id == categoryList.get(i).getId()) {
                name = categoryList.get(i).getName();
                break;
            }
        }
        return name;
    }

    public static void writeDataToFile(List<Product> productList) {
        File file = new File("products.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(productList);
            oos.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public static List<Product> readDataFromFile() {
        List<Product> listProductRead = null;
        File file = new File("products.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            listProductRead = (List<Product>) ois.readObject();
            return listProductRead;
        } catch (FileNotFoundException e) {
            listProductRead = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return listProductRead;
    }

    public static void drawTable(List<Product> productList) {
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "                                                 Bảng thông tin tất cả sản phẩm                         " + ConsoleColors.RESET);
        for (int i = 0; i < 141; i++) {
            System.out.print("─");
        }
        System.out.println();
        System.out.printf("|%-25s|%-40s|%-20s|%-20s|%-20s|%-30s|%-25s|%-40s|%n",
                Method.makeColor("Mã sản phẩm", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Tên sản phẩm", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Giá nhập", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Giá xuất", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Lợi nhuận", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Mô tả", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Trạng thái", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Danh mục sản phẩm", ConsoleColors.GREEN_BOLD_BRIGHT));
        for (int i = 0; i < 141; i++) {
            System.out.print("─");
        }
        System.out.println();
        for (Product product : productList) {
            System.out.printf("|%-25s|%-40s|%-20s|%-20s|%-20s|%-30s|%-25s|%-40s|%n",
                    Method.makeColor(product.getId(), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(product.getName(), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(String.valueOf(product.getImportPrice()), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(String.valueOf(product.getExportPrice()), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(String.valueOf(product.getProfit()), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(product.getDescription(), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(product.isStatus() ? "Bán" : "Không bán", ConsoleColors.BLUE_BOLD),
                    Method.makeColor(ProductBussinessImp.getNameCategory(product.getCategoryId(), CategoryPresentation.categoryList), ConsoleColors.BLUE_BOLD));
            for (int i = 0; i < 141; i++) {
                System.out.print("─");
            }
            System.out.println();
        }
    }
}
