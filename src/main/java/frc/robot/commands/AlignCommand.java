package frc.robot.commands;
import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.DashboardConfigsSubsystem;

import frc.robot.subsystems.Limelight.LimelightHelpers;
import frc.robot.subsystems.Limelight.LimelightHelpers.LimelightTarget_Retro;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import frc.robot.Constants.AlignmentConstants;
import frc.robot.Constants.ElavatorConstants;
import frc.robot.RobotContainer;


public class AlignCommand extends Command{
  CommandSwerveDrivetrain swerveSubsystem;
  private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();
    
 
  DashboardConfigsSubsystem dashboard;
  
  double m_seconds;
  Timer m_timer = new Timer();
  PIDController drivexPID = new PIDController(AlignmentConstants.pxDrive, AlignmentConstants.ixDrive, AlignmentConstants.dxDrive);
  PIDController driveyPID = new PIDController(AlignmentConstants.pyDrive, AlignmentConstants.iyDrive, AlignmentConstants.dyDrive);
  PIDController rotPID = new PIDController(AlignmentConstants.pRot, AlignmentConstants.iRot, AlignmentConstants.dRot);
  
  
  

  // Constructor
  CommandSwerveDrivetrain m_swerveSubsystem;
  public AlignCommand(double seconds,DashboardConfigsSubsystem m_dashboard,CommandSwerveDrivetrain m_swerveSubsystem) {
       
    
    dashboard = m_dashboard;
    m_seconds = seconds;
    swerveSubsystem = m_swerveSubsystem;
    addRequirements(swerveSubsystem);
    
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
    
    
    
  }
  public int getTargetAlign(){
    
    boolean[] shelfSlots = {
        DashboardConfigsSubsystem.get("Shelf Slot 1"),
        DashboardConfigsSubsystem.get("Shelf Slot 2"),
        DashboardConfigsSubsystem.get("Shelf Slot 3"),
        DashboardConfigsSubsystem.get("Shelf Slot 4"),
        DashboardConfigsSubsystem.get("Shelf Slot 5"),
        DashboardConfigsSubsystem.get("Shelf Slot 6"),
        DashboardConfigsSubsystem.get("Shelf Slot 7"),
        DashboardConfigsSubsystem.get("Shelf Slot 8"),
        DashboardConfigsSubsystem.get("Shelf Slot 9")
    };

    boolean[] pegboard = {
        DashboardConfigsSubsystem.get("Peg Board Slot 1"),
        DashboardConfigsSubsystem.get("Peg Board Slot 2"),
        DashboardConfigsSubsystem.get("Peg Board Slot 3"),
        DashboardConfigsSubsystem.get("Peg Board Slot 4"),
        DashboardConfigsSubsystem.get("Peg Board Slot 5"),
        DashboardConfigsSubsystem.get("Peg Board Slot 6"),
        DashboardConfigsSubsystem.get("Peg Board Slot 7"),
        DashboardConfigsSubsystem.get("Peg Board Slot 8"),
        DashboardConfigsSubsystem.get("Peg Board Slot 9"),
        DashboardConfigsSubsystem.get("Peg Board Slot 10"),
        DashboardConfigsSubsystem.get("Peg Board Slot 11"),
        DashboardConfigsSubsystem.get("Peg Board Slot 12")
    };

    // Check shelf slots (Indices 0 to 8 mapped to targets 1 to 9)
    for (int i = 0; i < shelfSlots.length; i++) {
        if (shelfSlots[i]) {
            return i + 1;
        }
    }

    // Check pegboard slots (Indices 0 to 11 mapped to targets 10 to 21)
    for (int i = 0; i < pegboard.length; i++) {
        if (pegboard[i]) {
            return i + 10;
        }
    }

    // Default target if none selected (e.g., 0 for fallback pose or error handling)
    return 22;
}

 
  
 @Override
  // Actual command
  public void execute() {
    //drive x and y
    Pose3d botPose = new Pose3d();
    Pose3d poses[]={Constants.AlignmentConstants.shelf1};

    Pose3d targetPose = poses[getTargetAlign()];
    double maxSpeed = 4.0;
    
    

    double x = drivexPID.calculate(botPose.getX(),targetPose.getX());
    double y = driveyPID.calculate(botPose.getY(),targetPose.getY());

    
    double ratioDrive=0;
    double speed = Math.hypot(x, y);

    if (speed > maxSpeed) {
     ratioDrive = speed / maxSpeed;

     x /= ratioDrive;
     y /= ratioDrive;
    }

     //Time for rotation

     double currentRot=0;
     double targetRot=0.1;
     double maxAngularSpeed=2;
     double angularspeed = rotPID.calculate(currentRot,targetRot);
     angularspeed = MathUtil.clamp(angularspeed,-maxAngularSpeed,maxAngularSpeed);
     if(!(RobotContainer.tolerant(currentRot,0.1,targetRot))){

      swerveSubsystem.setControl(drive.withRotationalRate(angularspeed));

     }
     if(RobotContainer.tolerant(currentRot,0.05,targetRot)){

      swerveSubsystem.setControl(drive.withVelocityX(x).withVelocityY(y));

     }
     
}
    
    
  


  // Stuff that happens when command is over
  public void end(boolean interrupted) {
    
  }


  // Checks if the command is done
  public boolean isFinished() {
    // Am I done?  Am I done? Am I finally done?
    
    return m_timer.hasElapsed(m_seconds);
  }
}
    


    