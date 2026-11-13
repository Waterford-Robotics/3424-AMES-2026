package frc.robot.subsystems.Limelight;

import static edu.wpi.first.units.Units.Rotation;

import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.subsystems.DashboardConfigsSubsystem;

public class VisionSubsystem {
    

   
   
    

    public static Pose3d getBotPose(){
                return LimelightHelpers.getBotPose3d_wpiBlue("limelight");

    }
    public static int getTargetAlign(){
        boolean testing=true;
        if (testing){
            return 21;
        }
        if (!LimelightHelpers.getTV("limelight")) {
            return -1;
        }

        double tagId = LimelightHelpers.getFiducialID("limelight");
        if (tagId == 1 || tagId == 2) {
            for (int slot = 1; slot <= 12; slot++) {
                if (!DashboardConfigsSubsystem.get("Peg Board Slot " + slot)) {
                    return slot + 8;
                }
            }
        } else if (tagId == 3 || tagId == 4) {
            for (int slot = 1; slot <= 9; slot++) {
                if (!DashboardConfigsSubsystem.get("Shelf Slot " + slot)) {
                    return slot - 1;
                }
            }
        }

        return -1;
    
    }


    
}

