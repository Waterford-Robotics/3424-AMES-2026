// package frc.robot.subsystems;

// import static edu.wpi.first.units.Units.MetersPerSecond;

// import java.util.List;

// import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.path.GoalEndState;
// import com.pathplanner.lib.path.PathConstraints;
// import com.pathplanner.lib.path.PathPlannerPath;
// import com.pathplanner.lib.path.Waypoint;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants.AlignmentConstants;
// import frc.robot.generated.TunerConstants;

// public class AlignSubsystem extends SubsystemBase{
//     final CommandSwerveDrivetrain swerveDrive = TunerConstants.createDrivetrain();
//     private Command activeAlignCommand;
//     public AlignSubsystem() {
        

//     }
//     public int getTargetAlign(){
//     int alignTarget=-1;
    


//     //Add auto logic to find proper align target
//     boolean ss1 = DashboardConfigsSubsystem.get("Shelf Slot 1");
//     boolean ss2 = DashboardConfigsSubsystem.get("Shelf Slot 2");
//     boolean ss3 = DashboardConfigsSubsystem.get("Shelf Slot 3");
//     boolean ss4 = DashboardConfigsSubsystem.get("Shelf Slot 4");
//     boolean ss5 = DashboardConfigsSubsystem.get("Shelf Slot 5");
//     boolean ss6 = DashboardConfigsSubsystem.get("Shelf Slot 6");
//     boolean ss7 = DashboardConfigsSubsystem.get("Shelf Slot 7");
//     boolean ss8 = DashboardConfigsSubsystem.get("Shelf Slot 8");
//     boolean ss9 = DashboardConfigsSubsystem.get("Shelf Slot 9");
//     boolean pb1 = DashboardConfigsSubsystem.get("Peg Board Slot 1");
//     boolean pb2 = DashboardConfigsSubsystem.get("Peg Board Slot 2");
//     boolean pb3 = DashboardConfigsSubsystem.get("Peg Board Slot 3");
//     boolean pb4 = DashboardConfigsSubsystem.get("Peg Board Slot 4");
//     boolean pb5 = DashboardConfigsSubsystem.get("Peg Board Slot 5");
//     boolean pb6 = DashboardConfigsSubsystem.get("Peg Board Slot 6");
//     boolean pb7 = DashboardConfigsSubsystem.get("Peg Board Slot 7");
//     boolean pb8 = DashboardConfigsSubsystem.get("Peg Board Slot 8");
//     boolean pb9 = DashboardConfigsSubsystem.get("Peg Board Slot 9");
//     boolean pb10 = DashboardConfigsSubsystem.get("Peg Board Slot 10");
//     boolean pb11 = DashboardConfigsSubsystem.get("Peg Board Slot 11");
//     boolean pb12 = DashboardConfigsSubsystem.get("Peg Board Slot 12");
//     // Put all your shelf slot values into an array
//     boolean[] shelfSlots = { ss1, ss2, ss3, ss4, ss5, ss6, ss7, ss8, ss9 };
//     boolean[] pegboard = {pb1,pb2,pb3,pb4,pb5,pb6,pb7,pb8,pb9,pb10,pb11,pb12};


//     for (int i = 0; i < (shelfSlots.length); i++) {
//         if (shelfSlots[i]) {
//             alignTarget=(i);
//             break;
//         }}
//     if(alignTarget==-1){
//         for (int i = 0; i < pegboard.length; i++) {
//         if (pegboard[i]) {
//             alignTarget=(i+10);
//             break;
//         }}
//     }
//     //it works
//     return alignTarget;
//   }

//     public void startPathing(){
//         double[] xAlignment = { AlignmentConstants.x1 };
//         double[] yAlignment = {AlignmentConstants.y1};
//         double [] radAlignment = {AlignmentConstants.rad1};
//         Pose2d targetPose= new Pose2d(xAlignment[getTargetAlign()],yAlignment[getTargetAlign()],Rotation2d.fromRadians(radAlignment[getTargetAlign()]));
        
//         List<Pose2d> poses = List.of(swerveDrive.getPose(), targetPose);
//         List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses(poses);
//         PathConstraints constraints = new PathConstraints(
//             TunerConstants.kSpeedAt12Volts.in(MetersPerSecond), // Max Vel (m/s)
//             4.0, // Max Accel (m/s^2)
//             Units.degreesToRadians(540), 
//             Units.degreesToRadians(720)
//         );
//         PathPlannerPath path = new PathPlannerPath(
//             waypoints,
//             constraints,
//             null, 
//             new GoalEndState(0.0, targetPose.getRotation())
//         );
//         path.preventFlipping = true;
//         activeAlignCommand = AutoBuilder.followPath(path);
//         activeAlignCommand.schedule();

        
//     }
//     public void stopPathing(){
//        if (activeAlignCommand != null && activeAlignCommand.isScheduled()) {
//             activeAlignCommand.cancel();
//         }
//     }
    
    
// }