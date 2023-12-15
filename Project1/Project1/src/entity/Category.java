package entity;

import bussinessImp.CategoryBussinessImp;

public class Category extends CategoryBussinessImp {
    private int id;
    private String name;
    private Boolean status;

    public Category() {
    }

    public Category(int id, String name, Boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nMã thể loại: " + id +
                " - Tên thể loại: " + name +
                " - Trạng thái: " + (status ? "Hoạt động" : "Không hoạt động");
    }
}
