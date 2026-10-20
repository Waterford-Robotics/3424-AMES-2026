package frc.robot.subsystems.Limelight;
//import edu.wpi.first.math.geometry.Pose3d;
//import edu.wpi.first.math.geometry.Rotation3d;
//import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.Constants.VisionConstants;
public class ApriltagHelpers {

public ApriltagHelpers(){
}
public static double getDistance(){
   double TY = Math.toRadians(LimelightHelpers.getTY(VisionConstants.LimelightName));

   // angleup = ty + pitch
   double angleUp = VisionConstants.CAMERA_PITCH + TY;
   // distance = (h2-h1)/tan(ty+pitch) = (h2-h1)/tan(angleup)
   double distance = (VisionConstants.TAG_HEIGHT - VisionConstants.CAMERA_HEIGHT)/Math.tan(angleUp);
   return distance;
}
public static double getTargetAngle(){
   double TY = Math.toRadians(LimelightHelpers.getTY(VisionConstants.LimelightName));
   double TX = Math.toRadians(LimelightHelpers.getTX(VisionConstants.LimelightName));

   // angleup = ty + pitch
   double angleUp = VisionConstants.CAMERA_PITCH + TY;
   // distance = (h2-h1)/tan(ty+pitch) = (h2-h1)/tan(angleup)
   double distance = (VisionConstants.TAG_HEIGHT - VisionConstants.CAMERA_HEIGHT)/Math.tan(angleUp);
   // xd = d sin(yaw + tx)
   double x_distance = distance * Math.sin(VisionConstants.CAMERA_YAW + TX);
   // yd = d cos(yaw + tx)
   double y_distance = distance * Math.cos(VisionConstants.CAMERA_YAW + TX);
   // hub angle = arctan(xd/(yd + 0.6))
   double hubAngle = Math.atan(x_distance/(y_distance + 0.6)); // 
   // target angle is the target tx (angle error)
   // target angle = hubAngle - yaw
   double targetAngle = hubAngle - VisionConstants.CAMERA_YAW;
   return targetAngle;
}
};