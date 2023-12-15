package bussinessImp;

import bussiness.ConsoleColors;
import bussiness.IEntity;
import bussiness.Method;
import entity.Book;
import entity.Category;
import presentation.BookPresentation;
import presentation.CategoryPresentation;
import presentation.MainPresentation;

import java.io.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BookBussinessImp implements IEntity {
    @Override
    public void input() {
        Scanner scanner = MainPresentation.scanner;
        List<Book> bookList = BookPresentation.bookList;
        if (CategoryPresentation.categoryList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin thể loại sách! Mời nhập thông tin thể loại sách trước khi nhập thông tin sách!");
        } else {
            System.out.println(Method.makeColor("Nhập số sách muốn thêm để nhập thông tin:", ConsoleColors.WHITE_BOLD_BRIGHT));
            int n = Method.validateInteger(scanner);
            for (int i = 0; i < n; i++) {
                int serial = bookList.size() + 1;
                System.out.println(Method.makeColor("Sách thứ " + serial + ":", ConsoleColors.WHITE_BOLD_BRIGHT));
                String id = inputId(scanner, bookList);
                String title = inputTitle(scanner, bookList);
                String author = inputAuthor(scanner);
                String publisher = inputPublisher(scanner);
                int year = inputYear(scanner);
                String description = inputDescription(scanner);
                int categoryId = inputCategoryId(scanner);
                Book book = new Book(id, title, author, publisher, year, description, categoryId);
                bookList.add(book);
                System.out.println(Method.makeColor("✔ Thêm thành công sách thứ:" + bookList.size(), ConsoleColors.GREEN_BOLD_BRIGHT));
            }
        }
    }

    @Override
    public void output() {
        List<Book> bookList = BookPresentation.bookList;
        if (bookList.isEmpty() || CategoryPresentation.categoryList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin sách hoặc thể loại sách! Hãy kiểm tra lại!");
        } else {
            System.out.println(Method.makeColor("Hiển thị danh sách sách theo nhóm thể loại:", ConsoleColors.WHITE_BOLD_BRIGHT));
            for (int i = 0; i < bookList.size(); i++) {
                boolean processed = false;
                for (int j = 0; j < i; j++) {
                    if (bookList.get(i).getCategoryId() == bookList.get(j).getCategoryId()) {
                        processed = true;
                        break;
                    }
                }

                if (!processed) {
                    System.out.printf(Method.makeColor("❤️ Thể loại %s:\n", ConsoleColors.BLUE_BOLD_BRIGHT), getNameCategory(bookList.get(i).getCategoryId(), CategoryPresentation.categoryList));
//                    System.out.printf("Thể loại %s:\n", getNameCategory(bookList.get(i).getCategoryId(), CategoryPresentation.categoryList));
                    bookSameCategory(bookList.get(i).getCategoryId(), bookList);
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

    public void bookSameCategory(int id, List<Book> bookList) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getCategoryId() == id) {
                System.out.println(Method.makeColor("   - Sách " + bookList.get(i).getTitle(), ConsoleColors.YELLOW_BOLD_BRIGHT));
            }
        }
    }

    public void displayData() {
        if (BookPresentation.bookList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin sách! Ấn 1 để thêm thông tin sách!");
        } else {
            System.out.println(Method.makeColor("Thông tin tất cả các sách:", ConsoleColors.WHITE_BOLD_BRIGHT));
//            BookPresentation.bookList.forEach(System.out::println);
            drawTable(BookPresentation.bookList);
        }
    }

    public String inputId(Scanner scanner, List<Book> bookList) {
        System.out.println(Method.makeColor("Nhập mã sách (bắt đầu bằng “B”, độ dài 4 kí tự, duy nhất):", ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            String id = Method.lenghthString(3, 4, scanner);
            if (id.charAt(0) == 'B') {
                boolean isExit = true;
                for (int i = 0; i < bookList.size(); i++) {
                    if (bookList.get(i).getId().equals(id)) {
                        isExit = false;
                        break;
                    }
                }
                if (isExit) {
                    return id;
                } else {
                    System.err.println("⚠ Warning: Mã sách này bị trùng! Mời nhập lại!");
                }
            } else {
                System.err.println("⚠ Warning: Mã sách phải bắt đầu ký tự B! Mời nhập lại!");
            }
        } while (true);
    }

    public String inputTitle(Scanner scanner, List<Book> bookList) {
        System.out.println(Method.makeColor("Nhập tiêu đề sách (từ 6-50 kí tự, duy nhất):", ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            String title = Method.lenghthString(5, 50, scanner);
            boolean isExit = true;
            for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).getTitle().equals(title)) {
                    isExit = false;
                    break;
                }
            }
            if (isExit) {
                return title;
            } else {
                System.err.println("⚠ Warning: Tiêu đề sách này bị trùng! Mời nhập lại!");
            }
        } while (true);
    }

    public String inputAuthor(Scanner scanner) {
        System.out.println(Method.makeColor("Nhập tên tác giả  (không bỏ trống):", ConsoleColors.WHITE_BOLD_BRIGHT));
        return Method.lenghthString(0, 50, scanner);
    }

    public String inputPublisher(Scanner scanner) {
        System.out.println(Method.makeColor("Nhập nhà xuất bản (không bỏ trống)", ConsoleColors.WHITE_BOLD_BRIGHT));
        return Method.lenghthString(0, 50, scanner);
    }

    public int inputYear(Scanner scanner) {
        System.out.println(Method.makeColor("Nhập năm xuất bản (tối thiểu từ năm 1970 và không lớn hơn năm hiện tại)", ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            int year = Method.validateInteger(scanner);
            if (year >= 1970 && year < LocalDate.now().getYear()) {
                return year;
            } else {
                System.err.println("⚠ Warning: Năm xuất bản tối thiểu từ năm 1970 và không lớn hơn năm hiện tại! Mời nhập lại");
            }
        } while (true);
    }

    public String inputDescription(Scanner scanner) {
        System.out.println(Method.makeColor("Nhập mô tả sách (không bỏ trống):", ConsoleColors.WHITE_BOLD_BRIGHT));
        return Method.lenghthString(0, 200, scanner);
    }

    public int inputCategoryId(Scanner scanner) {
        List<Category> categoryList = CategoryPresentation.categoryList;
//        System.out.println(Method.makeColor("Danh sách các thể loại sách:",ConsoleColors.WHITE_BOLD_BRIGHT));
        categoryList.sort(Comparator.comparing(Category::getId));
        CategoryBussinessImp.drawTable(categoryList);
//        categoryList.stream().sorted(Comparator.comparing(Category::getId)).forEach(System.out::println);
        System.out.println(Method.makeColor("Nhập mã thể loại sách của sách:", ConsoleColors.WHITE_BOLD_BRIGHT));
        do {
            boolean isExit = false;
            int id = Method.validateInteger(scanner);
            for (int i = 0; i < categoryList.size(); i++) {
                if (id == categoryList.get(i).getId()) {
                    return id;
                }
            }
            if (!isExit) {
                System.err.println("⚠ Warning: Phải nhập mã thể loại đã có!");
            }
        } while (true);
    }

    public void updateBook(Scanner scanner, List<Book> bookList) {
        if (bookList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin sách! Ấn 1 để thêm thông tin sách!");
        } else {
            boolean isExit = true;
            int indexUpdate = lookForIndexById(scanner, bookList, "cập nhật");
            do {
                System.out.println(Method.makeColor("╔═══════════════════════════╗\n║------- MENU UPDATE -------║\n╚═══════════════════════════╝", ConsoleColors.BLUE_BOLD_BRIGHT));
                System.out.println(Method.makeColor("1. Tiêu đề sách\n2. Tác giả\n3. Nhà xuất bản\n4. Năm xuât bản\n5. Mô tả sách\n6. Mã thể loại sách\n7. Thoát", ConsoleColors.YELLOW_BOLD_BRIGHT));
                System.out.println(Method.makeColor("Mời nhập lựa chọn:", ConsoleColors.YELLOW_BOLD_BRIGHT));
                int choice = Method.validateInteger(scanner);
                switch (choice) {
                    case 1:
                        bookList.get(indexUpdate).setTitle(inputTitle(scanner, bookList));
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "✔ Cập nhật thành công!" + ConsoleColors.RESET);
                        break;
                    case 2:
                        bookList.get(indexUpdate).setAuthor(inputAuthor(scanner));
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "✔ Cập nhật thành công!" + ConsoleColors.RESET);
                        break;
                    case 3:
                        bookList.get(indexUpdate).setPublisher(inputPublisher(scanner));
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "✔ Cập nhật thành công!" + ConsoleColors.RESET);
                        break;
                    case 4:
                        bookList.get(indexUpdate).setYear(inputYear(scanner));
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "✔ Cập nhật thành công!" + ConsoleColors.RESET);
                        break;
                    case 5:
                        bookList.get(indexUpdate).setDescription(inputDescription(scanner));
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "✔ Cập nhật thành công!" + ConsoleColors.RESET);
                        break;
                    case 6:
                        bookList.get(indexUpdate).setCategoryId(inputCategoryId(scanner));
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "✔ Cập nhật thành công!" + ConsoleColors.RESET);
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

    public void deleteBook(Scanner scanner, List<Book> bookList) {
        if (bookList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin sách! Ấn 1 để thêm thông tin sách!");
        } else {
            int indexDelete = lookForIndexById(scanner, bookList, "xóa");
            System.out.println(Method.makeColor("Bạn chắc chắn muốn xóa Không?\n1. Xóa\n2. Thoát", ConsoleColors.BLUE_BOLD_BRIGHT));
            System.out.println(Method.makeColor("Mời nhập lựa chọn:", ConsoleColors.BLUE_BOLD_BRIGHT));
            int choice = Method.validateInteger(scanner);
            switch (choice) {
                case 1:
                    bookList.remove(indexDelete);
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "✔ Xóa thành công!" + ConsoleColors.RESET);
                    break;
                case 2:
                    BookPresentation.menuBook(MainPresentation.scanner);
                    break;
                default:
                    System.err.println("⚠ Warning: Mời nhập 1 và 2!");
                    break;
            }
        }
    }


    public void lookForBook(Scanner scanner, List<Book> bookList) {
        if (bookList.isEmpty()) {
            System.err.println("⚠ Warning: Không có thông tin sách! Ấn 1 để thêm thông tin sách!");
        } else {
            boolean isExit = false;
            do {
                System.out.println(Method.makeColor("Nhập từ khóa sách bạn muốn tìm:", ConsoleColors.WHITE_BOLD_BRIGHT));
                List<Book> bookListNew = new ArrayList<>();
                String lfb = Method.lenghthString(0, 50, scanner);
                for (int i = 0; i < bookList.size(); i++) {
                    if (bookList.get(i).getTitle().toLowerCase().contains(lfb.toLowerCase()) ||
                            bookList.get(i).getAuthor().toLowerCase().contains(lfb.toLowerCase()) ||
                            bookList.get(i).getPublisher().toLowerCase().contains(lfb.toLowerCase())) {
                        isExit = true;
                        bookListNew.add(bookList.get(i));
                    }
                }
                drawTable(bookListNew);
                if (!isExit) {
                    System.err.println("⚠ Warning: Dữ liệu nhập vào không tồn tại! Mời nhập lại!");
                }
            } while (!isExit);
        }
    }


    public int lookForIndexById(Scanner scanner, List<Book> bookList, String type) {
        do {
            System.out.println(Method.makeColor("Nhập mã sách muốn " + type + ":", ConsoleColors.WHITE_BOLD_BRIGHT));
            String id = Method.lenghthString(0, 50, scanner);
            for (int i = 0; i < bookList.size(); i++) {
                if (id.equals(bookList.get(i).getId())) {
                    return i;
                }
            }
            System.err.println("⚠ Warning: Mã sách không tồn tại! Mời nhập lại!");
        } while (true);
    }

    public static void writeDataToFile(List<Book> bookList) {
        File file = new File("books.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(bookList);
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

    public static List<Book> readDataFromFile() {
        List<Book> listBookRead = null;
        File file = new File("books.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            listBookRead = (List<Book>) ois.readObject();
            return listBookRead;
        } catch (FileNotFoundException e) {
            listBookRead = new ArrayList<>();
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
        return listBookRead;
    }

    public static void drawTable(List<Book> bookList) {
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "                                                 Bảng thông tin tất cả sách                         " + ConsoleColors.RESET);
        for (int i = 0; i < 141; i++) {
            System.out.print("─");
        }
        System.out.println();
        System.out.printf("|%-20s|%-35s|%-30s|%-35s|%-25s|%-25s|%-40s|%n",
                Method.makeColor("Mã sách", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Tiêu đề", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Tác giả", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Nhà xuất bản", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Năm sáng tác", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Mô tả", ConsoleColors.GREEN_BOLD_BRIGHT),
                Method.makeColor("Thể loại", ConsoleColors.GREEN_BOLD_BRIGHT));
        for (int i = 0; i < 141; i++) {
            System.out.print("─");
        }
        System.out.println();
        for (Book book : bookList) {
            System.out.printf("|%-20s|%-35s|%-30s|%-35s|%-25s|%-25s|%-40s|%n",
                    Method.makeColor(book.getId(), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(book.getTitle(), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(book.getAuthor(), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(book.getPublisher(), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(String.valueOf(book.getYear()), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(book.getDescription(), ConsoleColors.BLUE_BOLD),
                    Method.makeColor(BookBussinessImp.getNameCategory(book.getCategoryId(), CategoryPresentation.categoryList), ConsoleColors.BLUE_BOLD));
            for (int i = 0; i < 141; i++) {
                System.out.print("─");
            }
            System.out.println();
        }
    }
}
