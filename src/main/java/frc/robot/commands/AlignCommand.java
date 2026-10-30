package frc.robot.commands;
import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.MathUtil;
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


public class AlignCommand extends Command{
    
 
  DashboardConfigsSubsystem dashboard;
  
  double m_seconds;
  Timer m_timer = new Timer();
  
  
  

  // Constructor
  CommandSwerveDrivetrain m_swerveSubsystem;
  public AlignCommand(double seconds,DashboardConfigsSubsystem m_dashboard,CommandSwerveDrivetrain drivetrain) {
       
    
    dashboard = m_dashboard;
    m_seconds = seconds;
    
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
    
    
  }
  public int getTargetAlign(){
    int alignTarget=0;
    


    //Add auto logic to find proper align target
    boolean ss1 = DashboardConfigsSubsystem.get("Shelf Slot 1");
    boolean ss2 = DashboardConfigsSubsystem.get("Shelf Slot 2");
    boolean ss3 = DashboardConfigsSubsystem.get("Shelf Slot 3");
    boolean ss4 = DashboardConfigsSubsystem.get("Shelf Slot 4");
    boolean ss5 = DashboardConfigsSubsystem.get("Shelf Slot 5");
    boolean ss6 = DashboardConfigsSubsystem.get("Shelf Slot 6");
    boolean ss7 = DashboardConfigsSubsystem.get("Shelf Slot 7");
    boolean ss8 = DashboardConfigsSubsystem.get("Shelf Slot 8");
    boolean ss9 = DashboardConfigsSubsystem.get("Shelf Slot 9");
    boolean pb1 = DashboardConfigsSubsystem.get("Peg Board Slot 1");
    boolean pb2 = DashboardConfigsSubsystem.get("Peg Board Slot 2");
    boolean pb3 = DashboardConfigsSubsystem.get("Peg Board Slot 3");
    boolean pb4 = DashboardConfigsSubsystem.get("Peg Board Slot 4");
    boolean pb5 = DashboardConfigsSubsystem.get("Peg Board Slot 5");
    boolean pb6 = DashboardConfigsSubsystem.get("Peg Board Slot 6");
    boolean pb7 = DashboardConfigsSubsystem.get("Peg Board Slot 7");
    boolean pb8 = DashboardConfigsSubsystem.get("Peg Board Slot 8");
    boolean pb9 = DashboardConfigsSubsystem.get("Peg Board Slot 9");
    boolean pb10 = DashboardConfigsSubsystem.get("Peg Board Slot 10");
    boolean pb11 = DashboardConfigsSubsystem.get("Peg Board Slot 11");
    boolean pb12 = DashboardConfigsSubsystem.get("Peg Board Slot 12");
    // Put all your shelf slot values into an array
    boolean[] shelfSlots = { ss1, ss2, ss3, ss4, ss5, ss6, ss7, ss8, ss9 };
    boolean[] pegboard = {pb1,pb2,pb3,pb4,pb5,pb6,pb7,pb8,pb9,pb10,pb11,pb12};


    for (int i = 0; i < shelfSlots.length; i++) {
        if (shelfSlots[i]) {
            alignTarget=(i+1);
            break;
        }}
    if(alignTarget==0){
        for (int i = 0; i < pegboard.length; i++) {
        if (pegboard[i]) {
            alignTarget=(i+10);
            break;
        }}
    }
    //it works
    return alignTarget;
  }
 
  
 
  // Actual command
  public void execute() {
    Pose3d poseBlue = LimelightHelpers.getBotPose3d_TargetSpace("blue");
    Pose3d poseGreen =LimelightHelpers.getBotPose3d_TargetSpace("green");
    Pose2d poses[]={Constants.AlignmentConstants.shelf1};
    
    

    double x = (poseBlue.getX()+poseGreen.getX())/2;
    double y = (poseBlue.getY()+poseGreen.getY())/2;
    double z = (poseBlue.getZ()+poseGreen.getX())/2;
    double xvel=x-poses[getTargetAlign()].getX();
    
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
    


    

