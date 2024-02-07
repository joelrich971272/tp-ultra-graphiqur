package java;

import jssc.SerialPortEvent;
import jssc.SerialPortException;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;

public class Mb1414 extends LiaisonSerie {
   private static double distance = 0.0;
   DecimalFormat df = new DecimalFormat("0.## cm");
   DecimalFormat fef = new DecimalFormat("0.## cm/s");
   DecimalFormat def = new DecimalFormat("0.## cm/s²");
   DecimalFormat dfSec = new DecimalFormat("0.## s");


   Instant tInit ;
   Instant tFinal ;
   double vitesseFinal ;
   double vitesseInit ;
   double distanceInit = 0 ;
   double acceleration ;
   double vitesse ;
   double deltaTemps ;
   double deltaDistance;




   public  Mb1414 (){
      tInit = Instant.now();
   }
   public void  calcul (SerialPortEvent event){
      byte[] laTram = lireTrame(event.getEventValue());
      if (laTram.length==8) {
         tFinal = Instant.now();
         distance = ((((laTram[1] - 0x30) * 100 + (laTram[2] - 0x30) * 10 + (laTram[3] - 0x30) * 2.54)));
         deltaTemps = (Duration.between(tInit,tFinal).toMillis())*0.001;
         System.out.println("distance :" + df.format(distance));
         vitesseFinal = (((distance-distanceInit))/((Duration.between(tInit,tFinal).toMillis())*0.001));
         System.out.println("vitesse = " + fef.format(vitesseFinal));
         acceleration = (((vitesseFinal-vitesseInit))/((Duration.between(tInit,tFinal).toMillis())*0.001));
         deltaDistance = (distance-distanceInit);
         System.out.println("acceleration = : " + def.format(acceleration));
         System.out.println("Delta t = : " + dfSec.format(deltaTemps));
         System.out.println("delta d = : " + df.format(deltaDistance));
         distanceInit = distance;
         vitesseInit = vitesseFinal;
         tInit = tFinal;
      }
   }



   public void deconnexionCapteur() throws SerialPortException {
      serialPort.closePort();


   }

   @Override
   public void serialEvent(SerialPortEvent event) {
         calcul(event);
         tFinal = Instant.now();


         super.serialEvent(event);
      }



   public void initialisationCapteur(String initCapteur) throws SerialPortException {
   super.initCom(initCapteur);
   super.configurerParametres(57600,8,0,1);


   }

   public double getDistance() {
      return distance;
   }

   public void setDistance(double distance) {
      this.distance = distance;
   }
}
