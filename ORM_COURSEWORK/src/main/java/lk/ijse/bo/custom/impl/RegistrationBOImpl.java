package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.ProgramDAO;
import lk.ijse.dao.custom.RegistrationDAO;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.entity.Program;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);
    RegistrationDAO registrationDAO = (RegistrationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REGISTRATION);
    @Override
    public StudentDTO searchStudent(String studentId) {
        Student student = studentDAO.search(studentId);
        return new StudentDTO(student.getStudent_id(),student.getUser(),student.getName(),student.getAddress(),student.getEmail(),student.getContact());
    }

    @Override
    public ProgramDTO searchProgram(String programId) {
        Program program = programDAO.search(programId);
        return new ProgramDTO(program.getProgram_id(),program.getProgram_name(),program.getDuration(),program.getFee());
    }

    @Override
    public boolean saveRegistration(RegistrationDTO registrationDTO) {
        return registrationDAO.save(new Registration(registrationDTO.getRegistrationID(),registrationDTO.getDate(),registrationDTO.getStudent(),registrationDTO.getProgram(),registrationDTO.getStudentName(),registrationDTO.getProgramName(),registrationDTO.getProgramFee(),registrationDTO.getUpfrontPayment()));
    }

    @Override
    public String getCurrentReId() {
        return registrationDAO.getCurrentID();
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        List<Registration> registrations = registrationDAO.getAll();
        List<RegistrationDTO> regList = new ArrayList<>();

        for (Registration registration : registrations){
            RegistrationDTO registrationDTO = new RegistrationDTO(registration.getRegistrationID(),registration.getDate(),registration.getStudent(),registration.getProgram(),registration.getStudentName(),registration.getProgramName(),registration.getProgramFee(),registration.getUpfrontPayment());
            regList.add(registrationDTO);
        }
        return regList;
    }

    @Override
    public boolean updateRegistration(RegistrationDTO registrationDTO) {
        return registrationDAO.update(new Registration(registrationDTO.getRegistrationID(),registrationDTO.getDate(),registrationDTO.getStudent(),registrationDTO.getProgram(),registrationDTO.getStudentName(),registrationDTO.getProgramName(),registrationDTO.getProgramFee(),registrationDTO.getUpfrontPayment()));
    }

    @Override
    public boolean deleteRegistration(String registerId) {
        return registrationDAO.delete(registerId);
    }

    @Override
    public RegistrationDTO searchRegistration(String registerId) {
        Registration registration = registrationDAO.search(registerId);
        return new RegistrationDTO(registration.getRegistrationID(),registration.getDate(),registration.getStudent(),registration.getProgram(),registration.getStudentName(),registration.getProgramName(),registration.getProgramFee(),registration.getUpfrontPayment());
    }
}
