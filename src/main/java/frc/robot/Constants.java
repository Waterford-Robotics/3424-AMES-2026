// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.XboxController.Axis;


public class Constants {

  public static final class ControllerConstants {
    
    
    public static final int scoreHLButton=1;
    public static final int scorePB1=2;
    public static final int scorePB2=3;
    public static final int scorePB3=4;
    public static int k_rightTrigger = Axis.kRightTrigger.value;
    public static final int k_leftTrigger = Axis.kLeftTrigger.value;
    public static final int scoreShelf = 6;
    public static final int autoAlign = 7;
  }  


  public static final class MotorIDConstants {
    public static final int k_elavatorLeftKrakenID = 21;
    public static final int k_elavatorRightKrakenID = 22;
    public static final int k_scorerKrakenID = 23;
   

  }
  public static final class ScorerConstants {
    public static final double scoreSpeed=0.2;
        
  }
  public static final class ElavatorConstants{
    public static final double kI=0.05;
    public static final double kP=0.2;
    public static final double kD=0;
    public static final double targetRotHL=1;
    public static final double targetRotPB1=5;
    public static final double targetRotPB2=20;
    public static final double targetRotPB3=50;
    public static final double targetRotShelf=70;
    public static final double elavatorSpeed=0.2;
  }
  public static final class AlignmentConstants {
    public static Pose2d shelf1 = new Pose2d();
  
    
  }

  
     public static final class VisionConstants{
     //see 3424 Rebuilt for what was here
  }
}