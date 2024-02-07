package java;

import jssc.SerialPortException;

public class Main extends LiaisonSerie {
    public static void main(String[] args) {
        Mb1414 mb1414 = new Mb1414() ;
        System.out.println(mb1414.listerLesPorts().toString());

        try {
            mb1414.initialisationCapteur("com3");
        } catch (SerialPortException e) {
            throw new RuntimeException(e);
        }



    }
}
