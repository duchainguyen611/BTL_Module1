package entity;

import bussinessImp.CategoryBussinessImp;

import java.io.Serializable;

public class Category extends CategoryBussinessImp {
    private int id;
    private String name;
    private String description;
    private boolean status;

    public Category(int id, String name, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Mã danh mục: " + id +
                " - Tên danh mục: " + name  +
                " - Mô tả danh mục: " + description +
                " - Trạng thái:" + status;
    }
}
