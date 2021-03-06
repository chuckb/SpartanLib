package org.team997coders.spartanlib.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

@SuppressWarnings("unused")
public class TankDrive extends DriveTrain {

  private GearBox leftBox, rightBox;
  private TalonSRX leftTalon, rightTalon;
  private VictorSPX leftVictor1, leftVictor2, rightVictor1, rightVictor2;

  public TankDrive(int talonLeft, int talonRight, int victorLeft1, int victorLeft2, int victorRight1,
      int victorRight2) {

    leftBox = BlackHole.standTalonSRXSetup(talonLeft, victorLeft1, victorLeft2, false);
    rightBox = BlackHole.standTalonSRXSetup(talonRight, victorRight1, victorRight2, true);

    leftTalon = leftBox.talon;
    rightTalon = rightBox.talon;
    leftVictor1 = leftBox.victor1;
    leftVictor2 = leftBox.victor2;
    rightVictor1 = rightBox.victor1;
    rightVictor2 = rightBox.victor2;
  }

  public void setVoltagePercentage(double left, double right) {
    leftTalon.set(ControlMode.PercentOutput, left);
    rightTalon.set(ControlMode.PercentOutput, right);
  }

  public void setVoltagePosition(double left, double right) {
    leftTalon.set(ControlMode.Position, left);
    rightTalon.set(ControlMode.Position, right);
  }

  public void setVoltageVelocity(double left, double right) {
    leftTalon.set(ControlMode.Velocity, left);
    rightTalon.set(ControlMode.Velocity, right);
  }

  public void stopVoltage() {
    leftTalon.set(ControlMode.PercentOutput, 0);
    rightTalon.set(ControlMode.PercentOutput, 0);
  }

}