package lk.ijse.bo.custom;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;
    public BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        USER,STUDENT,PROGRAM,REGISTRATION,PAYMENT
    }
    public SuperBO getBOTypes(BOTypes boTypes){
        switch (boTypes){
            case USER:
                return new UserBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case PROGRAM:
                return new ProgramBOImpl();
            case REGISTRATION:
                return new RegistrationBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            default:
                return null;
        }
    }
}
