// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.XboxController.Axis;


public class Constants {

  public static final class ControllerConstants {
    
    
    public static final int shell=1;
    public static final int cell=1;
    public static final int score=1;
    public static final int pegboard1=2;
    public static final int pegboard2=3;
    public static final int pegboard3=4;
    public static int k_rightTrigger = Axis.kRightTrigger.value;
    public static final int k_leftTrigger = Axis.kLeftTrigger.value;
    public static final int shelf = 6;
    
    
  }  


  public static final class MotorIDConstants {
    public static final int k_elavatorLeftKrakenID = 21;
    public static final int k_elavatorRightKrakenID = 22;
    public static final int k_scorerKrakenID = 23;
    public static final int k_indererKrakenID=24;
   

  }
  public static final class ScorerConstants {
    public static final double scoreSpeed=0.7;
    public static final double indexSpeed=0.7;
        
  }
  public static final class ElavatorConstants{
    public static final double kP=0.1;
    public static final double kI=0;
    public static final double kD=0.1;
    public static final double elevatorHeights []={5,5,5,5,5,5};
    
  }
  public static final class AlignmentConstants {
   
    public static double pDrive = 0.1;
    public static double iDrive = 0.1;
    public static double dDrive = 0.1;

   


    public static double pRot = 2.0;
    public static double iRot = 0.1;
    public static double dRot = 0.1;
    
  }

  
     public static final class VisionConstants{
     
     public static double x1Modifier=0;
     public static double xModifier=0;
     public static double y1Modifier=0;
     public static double yModifier=0;
    
     public static double targetX[]={-1.6+x1Modifier,-1.2952+x1Modifier,0+x1Modifier,-0.6856+x1Modifier,-0.3808+x1Modifier,-0.076+x1Modifier,0.2288+x1Modifier,0.5336+x1Modifier,0.8384+xModifier,
      

      -1.5+xModifier,-0.75+xModifier,0.75+xModifier,1.5+xModifier,
      
      -1.5+xModifier,-0.75+xModifier,0.75+xModifier,1.5+xModifier,
      
      -1.5+xModifier,-0.75+xModifier,0.75+xModifier,1.5+xModifier,
     1//test
    };


     public static double targetY[]={1+y1Modifier,1+y1Modifier,1+y1Modifier,1+y1Modifier,1+y1Modifier,1+y1Modifier,1+y1Modifier,1+y1Modifier,1+y1Modifier,
      
      
      1+yModifier,1+yModifier,1+yModifier,1+yModifier,
      
      1+yModifier,1+yModifier,1+yModifier,1+yModifier,
      
      1+yModifier,1+yModifier,1+yModifier,1+yModifier,
    1//test}
  };
}}

