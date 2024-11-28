package lk.ijse.dao.custom;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;
    public DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        USER,STUDENT,PROGRAM,REGISTRATION,PAYMENT
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case USER:
                return new UserDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case PROGRAM:
                return new ProgramDAOImpl();
            case REGISTRATION:
                return new RegistrationDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            default:
                return null;
        }

    }
}
