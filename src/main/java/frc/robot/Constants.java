// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;

import edu.wpi.first.units.measure.Angle;

public class Constants {

  public static final class ControllerConstants {
    public static final int k_driverControllerPort = 0; // Driver
    public static final int k_operatorControllerPort = 1; // Operator

    public static final int k_rightTrigger = Axis.kRightTrigger.value; // Intake on driver controller and shooter on operator controller
    public static final int k_leftTrigger = Axis.kLeftTrigger.value; // Belt + roller floor on driver controller
    public static final int k_rightBumper = Button.kRightBumper.value; // Flipout on driver controller
    public static final int k_leftBumper = Button.kLeftBumper.value; // Flipin on driver controller 
    
    public static final int k_A = Button.kA.value;
    public static final int k_B = Button.kB.value;
    public static final int k_X = Button.kX.value; //reverse intake on operator controller
    public static final int k_Y = Button.kY.value;

    public static final int k_Start = Button.kStart.value; //reset NavX on driver
    public static final int k_Back = Button.kBack.value; //X-Wheels on operator controller
  
    
  }  

  public static final class MotorIDConstants {
    //left shooter
    public static final int k_leftshooterKrakenID = 20;
    public static final int k_rightshooterKrakenID = 21;

    public static final int k_shooterIndexerKrakenID = 30;
    public static final int k_beltFloorKrakenID = 31;

    public static final int k_rightIntakeKrakenID = 40;
    public static final int k_leftIntakeKrakenID = 41; //FIND IN PHEONIX TUNER

    public static final int k_flipoutKrakenID = 42; // CHANGE FROM 41 to 42 in pheonix tuner
    

  }

  public static final class ShooterConstants {
    public static final double k_shooterSpeed = 0.65;

  }

  public static final class IntakeConstants {
    public static final double k_intakeSpeed = 0.4;
    
  }

  public static final class IntakeFlipoutConstants {
    public static final Angle k_intakeFlipoutHomeAngle = edu.wpi.first.units.Units.Rotations.of(0);
    public static final Angle k_intakeFlipoutIntakeAngle = edu.wpi.first.units.Units.Rotations.of(16.3); //MEASURED IN SMARTDASHBOARD
  
  
  }

  public static final class IndexerConstants {
 
    public static final double k_beltFloorSpeed = 0.5;
    public static final double k_shooterIndexerSpeed = 0.7;

  }
  
    //limelight
     public static final class VisionConstants{
     public static final String LimelightName = "limelight-red";
     public static final double kP = 1.0;
     public static final double kI = 1.0;
     public static final double kD = 1.0;
     public static final double kAimThreshold = 0.1; // 0.1 radians = 5.7 degrees error is ok
     public static final double timeoutTime = 2.0;
     public static final double turnScale = -0.5; //scale down turn rate and PID
     public static final double driveScale = -0.1;
     public static final double CAMERA_HEIGHT = 0.5; // meters
     public static final double TAG_HEIGHT = 1.12;    // meters, actual measurement for hub.
     public static final double CAMERA_PITCH = 0.785;  // upwards tilt. RADIANS! aka 45 degrees. Update if needed
     public static final double CAMERA_YAW = 0.0; // side tilt. Positive is to the left. RADIANS!
  }
}