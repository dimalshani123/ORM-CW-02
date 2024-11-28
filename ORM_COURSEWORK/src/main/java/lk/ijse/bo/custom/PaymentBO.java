package lk.ijse.bo.custom;

import lk.ijse.dto.PaymentDTO;

import java.util.List;

public interface PaymentBO extends SuperBO {

    public boolean savePayment(PaymentDTO paymentDTO);
    public List<PaymentDTO> getAllPayments();
    public boolean updatePayment(PaymentDTO paymentDTO);
    public String getCurrentPmId();
    public boolean deletePayment(String paymentId);
}
