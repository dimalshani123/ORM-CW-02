package lk.ijse.bo.custom;

import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.dto.StudentDTO;

import java.util.List;

public interface RegistrationBO extends SuperBO {

    public StudentDTO searchStudent(String studentId);
    public ProgramDTO searchProgram(String programId);
    public boolean saveRegistration(RegistrationDTO registrationDTO);
    public String getCurrentReId();
    public List<RegistrationDTO> getAllRegistrations();
    public boolean updateRegistration(RegistrationDTO registrationDTO);
    public boolean deleteRegistration(String registerId);
    public RegistrationDTO searchRegistration(String registerId);
}
