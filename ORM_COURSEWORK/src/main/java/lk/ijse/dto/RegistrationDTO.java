package lk.ijse.dto;

import lk.ijse.entity.Program;
import lk.ijse.entity.Student;

public class RegistrationDTO {

    private String registrationID;
    private String date;
    private Student student;
    private Program program;
    private String studentName;
    private String programName;
    private double programFee;
    private double upfrontPayment;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String registrationID, String date, Student student, Program program, String studentName, String programName, double programFee, double upfrontPayment) {
        this.registrationID = registrationID;
        this.date = date;
        this.student = student;
        this.program = program;
        this.studentName = studentName;
        this.programName = programName;
        this.programFee = programFee;
        this.upfrontPayment = upfrontPayment;
    }

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public double getProgramFee() {
        return programFee;
    }

    public void setProgramFee(double programFee) {
        this.programFee = programFee;
    }

    public double getUpfrontPayment() {
        return upfrontPayment;
    }

    public void setUpfrontPayment(double upfrontPayment) {
        this.upfrontPayment = upfrontPayment;
    }

    @Override
    public String toString() {
        return "RegistrationDTO{" +
                "registrationID='" + registrationID + '\'' +
                ", date='" + date + '\'' +
                ", student=" + student +
                ", program=" + program +
                ", studentName='" + studentName + '\'' +
                ", programName='" + programName + '\'' +
                ", programFee=" + programFee +
                ", upfrontPayment=" + upfrontPayment +
                '}';
    }
}
