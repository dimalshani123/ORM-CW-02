package lk.ijse.dto;

import lk.ijse.entity.User;

import java.util.Date;

public class StudentDTO {
    private String student_id;
    private User user;
    private String name;
    private String address;
    private String email;
    private String contact;

    public StudentDTO() {
    }

    public StudentDTO(String student_id,User user, String name){
        this.student_id = student_id;
        this.user = user;
        this.name = name;
    }

    public StudentDTO(String student_id, User user, String name, String address, String email, String contact) {
        this.student_id = student_id;
        this.user = user;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "student_id='" + student_id + '\'' +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
