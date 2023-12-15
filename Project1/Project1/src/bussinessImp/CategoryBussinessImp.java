package bussinessImp;

import bussiness.ConsoleColors;
import bussiness.IEntity;
import bussiness.Method;

import com.sun.tools.javac.Main;
import entity.Book;
import entity.Category;
import presentation.BookPresentation;
import presentation.CategoryPresentation;
import presentation.MainPresentation;

import java.io.*;
import java.util.*;


public class CategoryBussinessImp implements IEntity {
    @Override
    public void input() {
        List<Category> categoryList = CategoryPresentation.categoryList;
        Scanner scanner = MainPresentation.scanner;
        System.out.println(Method.makeColor("Nhập số thể loại sách muốn thêm cần nhập thông tin:", ConsoleColors.WHITE_BOLD_BRIGHT));
        int n = Method.validateInteger(scanner);
        for (int i = 0; i < n; i++) {
            int serial = categoryList.size() + 1;
            System.out.println(Method.makeColor("Thể loại thứ " + serial + ":", ConsoleColors.WHITE_BOLD_BRIGHT));
            int id = inputId(categoryList);
            String name = inputName(categoryList, scanner);
            boolean status = inputStatus(scanner);
            Category category = new Category(id, name, status);
            categoryList.add(category);
            System.out.println(Method.makeColor("✔ Thêm thành công thể loại thứ:" + categoryList.size(), ConsoleColors.GREEN_BOLD_BRIGHT));
        }
    }

    @Override
    public void output() {
        List<Category> categoryList = CategoryPresentation.categoryList;
        if (categoryList.isEmpty()) {
            System.err.println(Method.makeColor("Không có thông tin thể loại sách! Ấn 1 để thêm thông tin sách!", ConsoleColors.WHITE_BOLD_BRIGHT));
        } else {
            System.out.println(Method.makeColor("Hiển thị danh sách theo tên A – Z:", ConsoleColors.WHITE_BOLD_BRIGHT));
            categoryList.sort(Comparator.comparing(Category::getName));
            drawTable(categoryList);
//            categoryList.stream().sorted(Comparator.comparing(Category::getName)).forEach(System.out::println);
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
        System.out.println(Method.makeColor("Nhập tên thể loại sách (không trùng nhau, từ 6-30 kí tự):", ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            String name = Method.lenghthString(5, 30, scanner);
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
                System.err.println("⚠ Warning: Thể loại này bị trùng! Mời nhập lại!");
            }
        } while (true);
    }

    public boolean inputStatus(Scanner scanner) {
        System.out.println(Method.makeColor("Nhập trạng thái thể loại (chỉ nhận true/false khi nhập):", ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            String statusString = Method.lenghthString(0, 50, scanner);
            if (statusString.equals("true") || statusString.equals("false")) {
                return Boolean.parseBoolean(statusString);
            } else {
                System.err.println("⚠ Warning: Chỉ nhận giá trị true | false! Mời nhập lại!");
            }
        } while (true);
    }


    public void statisticCategory(List<Category> categoryList) {
        if (categoryList.isEmpty() || BookPresentation.bookList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin thể loại sách hoặc sách! Hãy kiểm tra lại!");
        } else {
            System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + " Thống kê thể loại và số sách có trong mỗi thể loại       " + ConsoleColors.RESET);
            for (int i = 0; i < 66; i++) {
                System.out.print("─");
            }
            System.out.println();
            System.out.printf("|%-25s|%-40s|%-30s|%n",
                    Method.makeColor("Mã thể loại", ConsoleColors.PURPLE_BOLD_BRIGHT),
                    Method.makeColor("Tên thể loại", ConsoleColors.PURPLE_BOLD_BRIGHT),
                    Method.makeColor("Số sách", ConsoleColors.PURPLE_BOLD_BRIGHT));
            for (int i = 0; i < 66; i++) {
                System.out.print("─");
            }
            System.out.println();
            for (int i = 0; i < categoryList.size(); i++) {
                System.out.printf("|%-25s|%-40s|%-30s|%n",
                        Method.makeColor(String.valueOf(categoryList.get(i).getId()), ConsoleColors.BLUE_BOLD),
                        Method.makeColor(categoryList.get(i).getName(), ConsoleColors.BLUE_BOLD),
                        Method.makeColor(String.valueOf(countBook(categoryList.get(i).getId(), BookPresentation.bookList)), ConsoleColors.BLUE_BOLD));
                for (int j = 0; j < 66; j++) {
                    System.out.print("─");
                }
                System.out.println();
            }
        }
    }

    public int countBook(int id, List<Book> bookList) {
        return (int) bookList.stream().filter(book -> book.getCategoryId() == id).count();
    }

    public void updateCategory(Scanner scanner, List<Category> categoryList) {
        if (categoryList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin thể loại sách! Ấn 1 để thêm thông tin sách!");
        } else {
            int indexUpdate = lookForIndexById(scanner, categoryList, "cập nhật");
            boolean isExit = false;
            do {
                System.out.println(Method.makeColor("╔═══════════════════════════╗\n║------- MENU UPDATE -------║\n╚═══════════════════════════╝", ConsoleColors.BLUE_BOLD_BRIGHT));
                System.out.println(Method.makeColor("1. Tên thể loại\n2. Trạng thái\n3. Thoát", ConsoleColors.YELLOW_BOLD_BRIGHT));
                System.out.println(Method.makeColor("Mời nhập lựa chọn:", ConsoleColors.YELLOW_BOLD_BRIGHT));
                int choice = Method.validateInteger(scanner);
                switch (choice) {
                    case 1:
                        categoryList.get(indexUpdate).setName(inputName(categoryList, scanner));
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "✔ Cập nhật thành công!" + ConsoleColors.RESET);
                        break;
                    case 2:
                        categoryList.get(indexUpdate).setStatus(!categoryList.get(indexUpdate).getStatus());
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "✔ Cập nhật thành công!" + ConsoleColors.RESET);
                        break;
                    case 3:
                        isExit = true;
                        break;
                    default:
                        System.out.println("⚠ Warning: Mời nhập từ 1 đến 3!");
                        break;
                }
            } while (!isExit);
        }
    }

    public void deleteCategory(Scanner scanner, List<Category> categoryList, List<Book> bookList) {
        if (categoryList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin thể loại sách! Ấn 1 để thêm thông tin sách!");
        } else {
            int indexDelete = lookForIndexById(scanner, categoryList, "xóa");
            System.out.println(Method.makeColor("Bạn chắc chắn muốn xóa Không?\n1. Xóa\n2. Thoát", ConsoleColors.BLUE_BOLD_BRIGHT));
            System.out.println(Method.makeColor("Mời nhập lựa chọn:", ConsoleColors.BLUE_BOLD_BRIGHT));
            int choice = Method.validateInteger(scanner);
            switch (choice) {
                case 1:
                    boolean isDuplicate = false;
                    for (int i = 0; i < bookList.size(); i++) {
                        if (categoryList.get(indexDelete).getId() == bookList.get(i).getCategoryId()) {
                            isDuplicate = true;
                            break;
                        }
                    }
                    if (isDuplicate) {
                        System.err.println("⚠ Warning: Thể loại đang có sách! Không thể xóa!");
                    } else {
                        categoryList.remove(indexDelete);
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "✔ Xóa thành công!" + ConsoleColors.RESET);
                    }
                    break;
                case 2:
                    CategoryPresentation.menuCategory(MainPresentation.scanner);
                    break;
                default:
                    System.err.println("⚠ Warning: Mời nhập 1 và 2!");
                    break;
            }
        }
    }

    public int lookForIndexById(Scanner scanner, List<Category> categoryList, String type) {
        do {
            System.out.println(Method.makeColor("Nhập mã thể loại muốn " + type + ":", ConsoleColors.WHITE_BOLD_BRIGHT));
            int id = Method.validateInteger(scanner);
            for (int i = 0; i < categoryList.size(); i++) {
                if (id == categoryList.get(i).getId()) {
                    return i;
                }
            }
            System.err.println("⚠ Warning: Mã thể loại không tồn tại! Mời nhập lại!");
        } while (true);
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
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "                  Bảng thông tin tất cả thể loại sách                      " + ConsoleColors.RESET);
        for (int i = 0; i < 66; i++) {
            System.out.print("─");
        }
        System.out.println();
        System.out.printf("|%-25s|%-40s|%-30s|%n",
                Method.makeColor("Mã thể loại", ConsoleColors.PURPLE_BOLD_BRIGHT),
                Method.makeColor("Tên thể loại", ConsoleColors.PURPLE_BOLD_BRIGHT),
                Method.makeColor("Trạng thái", ConsoleColors.PURPLE_BOLD_BRIGHT));
        for (int i = 0; i < 66; i++) {
            System.out.print("─");
        }
        System.out.println();
        for (Category category : categoryList) {
            System.out.printf("|%-25s|%-40s|%-30s|%n",
                    Method.makeColor(String.valueOf(category.getId()), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(category.getName(), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(category.getStatus() ? "Hoạt động" : "Không hoạt động", ConsoleColors.BLUE_BOLD));
            for (int i = 0; i < 66; i++) {
                System.out.print("─");
            }
            System.out.println();
        }

    }
}
