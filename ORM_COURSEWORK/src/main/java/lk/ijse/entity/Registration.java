package lk.ijse.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
public class Registration {

    @Id
    private String registrationID;
    private String date;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    private String studentName;
    private String programName;
    private double programFee;
    private double upfrontPayment;

}
