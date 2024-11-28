package lk.ijse.view.tm;

public class RegistrationTm{
    private String registrationID;
    private String date;
    private String student_id;
    private String program_id;
    private String studentName;
    private String programName;
    private double programFee;
    private double upfrontPayment;

    public RegistrationTm() {
    }

    public RegistrationTm(String registrationID, String date, String student_id, String program_id, String studentName, String programName, double programFee, double upfrontPayment) {
        this.registrationID = registrationID;
        this.date = date;
        this.student_id = student_id;
        this.program_id = program_id;
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

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getProgram_id() {
        return program_id;
    }

    public void setProgram_id(String program_id) {
        this.program_id = program_id;
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
        return "RegistrationTm{" +
                "registrationID='" + registrationID + '\'' +
                ", date='" + date + '\'' +
                ", student_id='" + student_id + '\'' +
                ", program_id='" + program_id + '\'' +
                ", studentName='" + studentName + '\'' +
                ", programName='" + programName + '\'' +
                ", programFee=" + programFee +
                ", upfrontPayment=" + upfrontPayment +
                '}';
    }
}
