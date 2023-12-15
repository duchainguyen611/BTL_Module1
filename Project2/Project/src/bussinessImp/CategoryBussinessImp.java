package bussinessImp;

import bussiness.ConsoleColors;
import bussiness.ICategory;
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

public class CategoryBussinessImp implements ICategory {
    @Override
    public void inputData() {
        Scanner scanner = MainPresentation.scanner;
        List<Category> categoryList = CategoryPresentation.categoryList;
        System.out.println(Method.makeColor("Nhập số danh mục cần thêm:",ConsoleColors.WHITE_BOLD_BRIGHT));
        int n = Method.validateInteger(scanner);
        for (int i = 0; i < n; i++) {
            int serial = categoryList.size() + 1;
            System.out.println(Method.makeColor("Danh mục thứ " + serial + ":", ConsoleColors.WHITE_BOLD_BRIGHT));
            int id = inputId(categoryList);
            String name = inputName(categoryList, scanner);
            String description = inputDescription(scanner);
            boolean status = inputStatus(scanner);
            Category category = new Category(id, name, description, status);
            categoryList.add(category);
            System.out.println(Method.makeColor("✔ Thêm thành công danh mục thứ:" + categoryList.size(), ConsoleColors.GREEN_BOLD_BRIGHT));
        }
    }

    @Override
    public void displayData() {
        if (CategoryPresentation.categoryList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin danh mục! Ấn 1 để thêm thông tin danh mục!");
        } else {
//            CategoryPresentation.categoryList.forEach(System.out::println);
            drawTable(CategoryPresentation.categoryList);
        }
    }

    public int inputId(List<Category> categoryList) {
        if (categoryList.isEmpty()) {
            return 1;
        } else {
            int max = categoryList.get(0).getId();
            for (Category element : categoryList) {
                if (max < element.getId()) {
                    max = element.getId();
                }
            }
            return max + 1;
        }
    }

    public String inputName(List<Category> categoryList, Scanner scanner) {
        System.out.println(Method.makeColor("Nhập tên danh mục (không trùng nhau, từ 6-30 kí tự):",ConsoleColors.WHITE_BOLD_BRIGHT));
        String name = Method.lenghthString(5, 30, scanner);
        do {
            boolean isExit = true;
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getName().equals(name)) {
                    isExit = false;
                    break;
                }
            }
            if (isExit) {
                return name;
            } else {
                System.err.println("⚠ Warning: Tên danh mục này bị trùng! Mời nhập lại!");
            }
        } while (true);
    }

    public String inputDescription(Scanner scanner) {
        System.out.println(Method.makeColor("Nhập Mô tả danh mục (không bỏ trống):",ConsoleColors.WHITE_BOLD_BRIGHT));
        return Method.lenghthString(0, 200, scanner);
    }

    public boolean inputStatus(Scanner scanner) {
        System.out.println(Method.makeColor("Nhâp trạng thái danh mục (chỉ nhận true/false khi nhập):",ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            String status = Method.lenghthString(0, 50, scanner);
            if (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(status);
            } else {
                System.err.println("⚠ Warning: Trạng thái danh mục chỉ nhận true/false khi nhập! Mời nhập lại!");
            }
        } while (true);
    }

    public void updateCategory(Scanner scanner, List<Category> categoryList) {
        if (categoryList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin danh mục! Ấn 1 để thêm thông tin danh mục!");
        } else {
            int indexUpdate = lookForIndexById(scanner, categoryList, "cập nhật");
            boolean isExit = true;
            do {
                System.out.println(Method.makeColor("╔═══════════════════════════╗\n║------- MENU UPDATE -------║\n╚═══════════════════════════╝", ConsoleColors.BLUE_BOLD_BRIGHT));
                System.out.println(Method.makeColor("1. Tên danh mục\n2. Mô tả danh mục\n3. Trạng thái danh mục\n4. Thoát", ConsoleColors.YELLOW_BOLD_BRIGHT));
                System.out.println(Method.makeColor("Mời nhập lựa chọn:", ConsoleColors.YELLOW_BOLD_BRIGHT));
                int choice = Method.validateInteger(scanner);
                switch (choice) {
                    case 1:
                        categoryList.get(indexUpdate).setName(inputName(categoryList, scanner));
                        System.out.println(Method.makeColor("✔ Cập nhật thành công", ConsoleColors.GREEN_BOLD_BRIGHT));
                        break;
                    case 2:
                        categoryList.get(indexUpdate).setDescription(inputDescription(scanner));
                        System.out.println(Method.makeColor("✔ Cập nhật thành công", ConsoleColors.GREEN_BOLD_BRIGHT));
                        break;
                    case 3:
                        categoryList.get(indexUpdate).setStatus(!categoryList.get(indexUpdate).isStatus());
                        System.out.println(Method.makeColor("✔ Cập nhật thành công", ConsoleColors.GREEN_BOLD_BRIGHT));
                        break;
                    case 4:
                        isExit = false;
                        break;
                    default:
                        System.err.println("⚠ Warning: Mời nhập từ 1 đến 4!");
                        break;
                }
            } while (isExit);
        }
    }

    public void deleteCategory(List<Category> categoryList, List<Product> productList, Scanner scanner) {
        if (categoryList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin danh mục! Ấn 1 để thêm thông tin danh mục!");
        } else {
            int indexDelete = lookForIndexById(scanner, categoryList, "xóa");
            System.out.println(Method.makeColor("Bạn chắc chắn muốn xóa Không?\n1. Xóa\n2. Thoát", ConsoleColors.BLUE_BOLD_BRIGHT));
            System.out.println(Method.makeColor("Mời nhập lựa chọn:", ConsoleColors.BLUE_BOLD_BRIGHT));
            int choice = Method.validateInteger(scanner);
            switch (choice) {
                case 1:
                    boolean isDuplicate = false;
                    for (int i = 0; i < productList.size(); i++) {
                        if (categoryList.get(indexDelete).getId() == productList.get(i).getCategoryId()) {
                            isDuplicate = true;
                            break;
                        }
                    }
                    if (isDuplicate) {
                        System.err.println("⚠ Warning: Danh mục đang có sản phẩm! Không thể xóa!");
                    } else {
                        categoryList.remove(indexDelete);
                        System.out.println(Method.makeColor("✔ Xóa thành công", ConsoleColors.GREEN_BOLD_BRIGHT));
                    }
                    break;
                case 2:
                    CategoryPresentation.menuCategory(scanner);
                    break;
                default:
                    System.err.println("⚠ Warning: Mời nhập 1 và 2!");
                    break;
            }
        }
    }

    public void lookForCategory(Scanner scanner, List<Category> categoryList) {
        if (categoryList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin danh mục! Ấn 1 để thêm thông tin danh mục!");
        } else {
            System.out.println(Method.makeColor("Tìm kiếm danh mục theo tên danh mục:",ConsoleColors.WHITE_BOLD_BRIGHT));
            System.out.println(Method.makeColor("Nhập tên danh mục muốn tìm kiếm:",ConsoleColors.WHITE_BOLD_BRIGHT));
            String name = Method.lenghthString(0, 50, scanner);
            boolean isExit = false;
            do {
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "                  Bảng thông tin danh mục được tìm kiếm theo tên                       " + ConsoleColors.RESET);
                for (int i = 0; i < 86; i++) {
                    System.out.print("─");
                }
                System.out.println();
                System.out.printf("|%-25s|%-40s|%-30s|%-30s|%n",
                        Method.makeColor("Mã danh mục", ConsoleColors.PURPLE_BOLD_BRIGHT),
                        Method.makeColor("Tên danh mục", ConsoleColors.PURPLE_BOLD_BRIGHT),
                        Method.makeColor("Mô tả", ConsoleColors.PURPLE_BOLD_BRIGHT),
                        Method.makeColor("Trạng thái", ConsoleColors.PURPLE_BOLD_BRIGHT));
                for (int i = 0; i < 86; i++) {
                    System.out.print("─");
                }
                System.out.println();
                for (int i = 0; i < categoryList.size(); i++) {
                    if (categoryList.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
                        System.out.printf("|%-25s|%-40s|%-30s|%-30s|%n",
                                Method.makeColor(String.valueOf(categoryList.get(i).getId()), ConsoleColors.BLUE_BOLD),
                                Method.makeColor(categoryList.get(i).getName(), ConsoleColors.BLUE_BOLD),
                                Method.makeColor(categoryList.get(i).getDescription(), ConsoleColors.BLUE_BOLD),
                                Method.makeColor(categoryList.get(i).isStatus() ? "Hoạt động" : "Không hoạt động", ConsoleColors.BLUE_BOLD));
                        isExit = true;
                        for (int j = 0; j < 86; j++) {
                            System.out.print("─");
                        }
                        System.out.println();
                    }
                }
                if (!isExit) {
                    System.err.println("⚠ Warning: Tên danh mục bạn nhập không có dữ liệu! Mời nhập lại!");
                }
            } while (!isExit);
        }
    }

    public int lookForIndexById(Scanner scanner, List<Category> categoryList, String type) {
        boolean isExit = false;
        System.out.println(Method.makeColor("Nhập mã danh mục muốn " + type + ":", ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            int id = Method.validateInteger(scanner);
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == id) {
                    return i;
                }
            }
            if (!isExit) {
                System.err.println("⚠ Warning: Mã danh mục không tồn tại! Mời nhập lại!");
            }
        } while (true);
    }

    public void statisticCategory(List<Category> categoryList) {
        if (categoryList.isEmpty() || ProductPresentation.productList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin danh mục hoặc sản phẩm! Hãy kiểm tra lại!");
        } else {
            System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + " Thống kê danh mục và số lượng sản phẩm có trong mỗi danh mục      " + ConsoleColors.RESET);
            for (int i = 0; i < 66; i++) {
                System.out.print("─");
            }
            System.out.println();
            System.out.printf("|%-25s|%-40s|%-30s|%n",
                    Method.makeColor("Mã danh mục", ConsoleColors.PURPLE_BOLD_BRIGHT),
                    Method.makeColor("Tên danh mục", ConsoleColors.PURPLE_BOLD_BRIGHT),
                    Method.makeColor("Số sản phẩm", ConsoleColors.PURPLE_BOLD_BRIGHT));
            for (int i = 0; i < 66; i++) {
                System.out.print("─");
            }
            System.out.println();
            for (int i = 0; i < categoryList.size(); i++) {
                System.out.printf("|%-25s|%-40s|%-30s|%n",
                        Method.makeColor(String.valueOf(categoryList.get(i).getId()), ConsoleColors.BLUE_BOLD),
                        Method.makeColor(categoryList.get(i).getName(), ConsoleColors.BLUE_BOLD),
                        Method.makeColor(String.valueOf(countProduct(categoryList.get(i).getId(), ProductPresentation.productList)), ConsoleColors.BLUE_BOLD));
                for (int j = 0; j < 66; j++) {
                    System.out.print("─");
                }
                System.out.println();
            }
        }
    }

    public int countProduct(int id, List<Product> productList) {
        return (int) productList.stream().filter(product -> product.getCategoryId() == id).count();
    }

    public static void writeDataToFile(List<Category> categoryList) {
        File file = new File("categories.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(categoryList);
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

    public static List<Category> readDataFromFile() {
        List<Category> listCategoryRead = null;
        File file = new File("categories.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            listCategoryRead = (List<Category>) ois.readObject();
            return listCategoryRead;
        } catch (FileNotFoundException e) {
            listCategoryRead = new ArrayList<>();
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
        return listCategoryRead;
    }

    public static void drawTable(List<Category> categoryList) {
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "                            Bảng thông tin tất cả danh mục                        " + ConsoleColors.RESET);
        for (int i = 0; i < 86; i++) {
            System.out.print("─");
        }
        System.out.println();
        System.out.printf("|%-25s|%-40s|%-30s|%-30s|%n",
                Method.makeColor("Mã danh mục", ConsoleColors.PURPLE_BOLD_BRIGHT),
                Method.makeColor("Tên danh mục", ConsoleColors.PURPLE_BOLD_BRIGHT),
                Method.makeColor("Mô tả", ConsoleColors.PURPLE_BOLD_BRIGHT),
                Method.makeColor("Trạng thái", ConsoleColors.PURPLE_BOLD_BRIGHT));
        for (int i = 0; i < 86; i++) {
            System.out.print("─");
        }
        System.out.println();
        for (Category category : categoryList) {
            System.out.printf("|%-25s|%-40s|%-30s|%-30s|%n",
                    Method.makeColor(String.valueOf(category.getId()), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(category.getName(), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(category.getDescription(), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(category.isStatus() ? "Hoạt động" : "Không hoạt động", ConsoleColors.BLUE_BOLD));
            for (int i = 0; i < 86; i++) {
                System.out.print("─");
            }
            System.out.println();
        }
    }
}
