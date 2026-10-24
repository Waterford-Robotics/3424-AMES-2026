package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class DashboardConfigsSubsystem {
    private static final NetworkTable table =
        NetworkTableInstance.getDefault().getTable("Scoring States");

    public static boolean get(int index) {
        return table.getEntry("ScoringStates" + index).getBoolean(false);
    }

    public static void set(int index, boolean value) {
        table.getEntry("ScoringStates" + index).setBoolean(value);
    }
    
}
