package lk.ijse.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Student {

    @Id
    private String student_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    private String address;
    private String email;
    private String contact;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Registration> registrations;

    public Student() {
    }

    public Student(String student_id, User user, String name, String address, String email, String contact) {
        this.student_id = student_id;
        this.user = user;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;

    }

    public Student(String student_id,String name,User user){
        this.student_id = student_id;
        this.name = name;
        this.user = user;
    }

    public Student(String student_id, User user, String name, String address, String email, String contact,List<Registration> registrations) {
        this.student_id = student_id;
        this.user = user;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.registrations = registrations;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
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
        return "Student{" +
                "student_id='" + student_id + '\'' +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
