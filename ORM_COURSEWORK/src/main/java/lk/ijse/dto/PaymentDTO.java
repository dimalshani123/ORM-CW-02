package lk.ijse.dto;

import lk.ijse.entity.Registration;

public class PaymentDTO {
    private String payment_id;
    private Registration registration;
    private String payment_date;
    private double upfront_payment;
    private double total_amount;
    private double due_amount;
    public PaymentDTO() {
    }

    public PaymentDTO(String payment_id, Registration registration, String payment_date, double upfront_payment, double total_amount, double due_amount) {
        this.payment_id = payment_id;
        this.registration = registration;
        this.payment_date = payment_date;
        this.upfront_payment = upfront_payment;
        this.total_amount = total_amount;
        this.due_amount = due_amount;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public double getUpfront_payment() {
        return upfront_payment;
    }

    public void setUpfront_payment(double upfront_payment) {
        this.upfront_payment = upfront_payment;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(double due_amount) {
        this.due_amount = due_amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payment_id='" + payment_id + '\'' +
                ", registration=" + registration +
                ", payment_date='" + payment_date + '\'' +
                ", upfront_payment=" + upfront_payment +
                ", total_amount=" + total_amount +
                ", due_amount=" + due_amount +
                '}';
    }
}
