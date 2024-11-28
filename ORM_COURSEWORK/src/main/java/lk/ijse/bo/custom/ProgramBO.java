package lk.ijse.bo.custom;

import lk.ijse.dto.ProgramDTO;

import java.util.List;

public interface ProgramBO extends SuperBO {

    public boolean saveProgram(ProgramDTO programDTO);

    public String getCurrentPrId();

    public List<ProgramDTO> getAllPrograms();

    public boolean updateProgram(ProgramDTO programDTO);

    public boolean deleteProgram(String programId);
    public ProgramDTO searchById(String programId);


}
