package lk.ijse.view.tm;

import jakarta.persistence.JoinColumn;
import lk.ijse.entity.User;

public class StudentTm {

    private String student_id;
    private String user_id;
    private String name;
    private String address;
    private String email;
    private String contact;

    public StudentTm(String student_id, String user_id, String name, String address, String email, String contact) {
        this.student_id = student_id;
        this.user_id = user_id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
    }
    public StudentTm(String student_id,String user_id,String name){
        this.student_id = student_id;
        this.user_id = user_id;
        this.name = name;
    }
    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
        return "StudentTm{" +
                "student_id='" + student_id + '\'' +
                ", userId='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
