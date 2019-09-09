package org.team997coders.spartanlib.limelight;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class for controller the LimeLight
 */
public class LimeLight {
  public double x = 0, y = 0;
  public boolean hasTarget = false;
  public boolean lightOn = false;

  private NetworkTable limeLightTable;

  public LimeLight() {
    limeLightTable = NetworkTableInstance.getDefault().getTable("limelight");

    setDouble(LED_MODE, LEDState.ForceOff);
  }

  public void setDouble(String entry, double value) {
    limeLightTable.getEntry(entry).setDouble(value);
  }

  public void setDouble(String entry, LimeLightValue value) {
    limeLightTable.getEntry(entry).setDouble(value.getValue());
  }

  public double getDouble(String entry, double defaultValue) {
    return limeLightTable.getEntry(entry).getDouble(defaultValue);
  }

  public interface LimeLightValue {
    public int getValue();
  }

  public void getDat() {
    x = limeLightTable.getEntry(TARGET_X).getDouble(0);
    y = limeLightTable.getEntry(TARGET_Y).getDouble(0);
    hasTarget = limeLightTable.getEntry(TARGET_VISIBLE).getDouble(0) == 1 ? true : false;

    SmartDashboard.putBoolean("Is Valid", hasTarget);
    SmartDashboard.putNumber("Target X", x);
    SmartDashboard.putNumber("Target Y", y);
  }

  public int getLED() {
    return (int) limeLightTable.getEntry(LED_MODE).getDouble(0);
  }

  public void setLED(double a) {
    limeLightTable.getEntry(LED_MODE).setDouble(a);
  }

  public enum CameraState implements LimeLightValue {
    VisionProccessing(0), DriverStation(1);

    int value;

    CameraState(int value) {
      this.value = value;
    }

    @Override
    public int getValue() {
      return value;
    }
  }

  public enum LEDState implements LimeLightValue {
    PipelinePreference(0), ForceOff(1), ForceBlink(2), ForceOn(3);

    int value;

    LEDState(int value) {
      this.value = value;
    }

    @Override
    public int getValue() {
      return value;
    }
  }

  public enum SnapshotMode implements LimeLightValue {
    StopTakingSnapshots(0), TakeSnapshots(1);

    int value;

    SnapshotMode(int value) {
      this.value = value;
    }

    @Override
    public int getValue() {
      return value;
    }
  }

  public static String
    LED_MODE = "ledMode",
    CAMERA_MODE = "camMode",
    CAPTURE_MODE = "snapshot",
    STREAM_SELECTION = "stream",
    TARGET_X = "tx",
    TARGET_Y = "ty",
    TARGET_AREA = "ta",
    TARGET_VISIBLE = "tv";

}