package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.ProgramDAO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.entity.Program;

import java.util.ArrayList;
import java.util.List;

public class ProgramBOImpl implements ProgramBO {

    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);
    @Override
    public boolean saveProgram(ProgramDTO programDTO) {
        return programDAO.save(new Program(programDTO.getProgram_id(),programDTO.getProgram_name(),programDTO.getDuration(),programDTO.getFee()));
    }

    @Override
    public String getCurrentPrId() {
        return programDAO.getCurrentID();

    }

    @Override
    public List<ProgramDTO> getAllPrograms() {
        List<Program> programs = programDAO.getAll();
        List<ProgramDTO> prgList = new ArrayList<>();

        for (Program program : programs){
            ProgramDTO programDTO = new ProgramDTO(program.getProgram_id(),program.getProgram_name(),program.getDuration(),program.getFee());
            prgList.add(programDTO);
        }
        return prgList;
    }

    @Override
    public boolean updateProgram(ProgramDTO programDTO) {
        return programDAO.update(new Program(programDTO.getProgram_id(),programDTO.getProgram_name(),programDTO.getDuration(),programDTO.getFee()));
    }

    @Override
    public boolean deleteProgram(String programId) {
        return programDAO.delete(programId);
    }

    @Override
    public ProgramDTO searchById(String programId) {
        Program program = programDAO.search(programId);
        return new ProgramDTO(program.getProgram_id(),program.getProgram_name(),program.getDuration(),program.getFee());
    }

}
