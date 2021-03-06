package org.team997coders.spartanlib.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.team997coders.spartanlib.interfaces.IServo;

/**
 * A subsystem to define an automated camera mount
 * that uses servos for panning and tilting.
 */
public class CameraMount extends Subsystem {
  private final IServo panServo;
  private final IServo tiltServo;
  private final int tiltLowerLimitInDegrees;
  private final int tiltUpperLimitInDegrees; 
  private final int panLowerLimitInDegrees;
  private final int panUpperLimitInDegrees;
  private double panAngleInDegrees;
  private double tiltAngleInDegrees;
  private final boolean reverseTilt;
  private final boolean reversePan;

  /**
   * Construct a non-reversed camera mount with angle limits set to 0..180 degrees.
   * 
   * @param panServo    An interface to a Servo for panning
   * @param tiltServo   An interface to a Servo for tilting
   */
  public CameraMount(IServo panServo, IServo tiltServo) {
    this(panServo, tiltServo, 0, 180, 0, 180);
  }

  /**
   * Construct a non-reversed camera mount specifing limits of travel in degrees.
   * 
   * @param panServo                  An interface to a Servo for panning
   * @param tiltServo                 An interface to a Servo for tilting
   * @param tiltLowerLimitInDegrees   The lower tilt limit angle in degrees
   * @param tiltUpperLimitInDegrees   The upper tilt limit angle in degrees
   * @param panLowerLimitInDegrees    The lower pan limit angle in degrees
   * @param panUpperLimitInDegrees    The upper pan limit angle in degrees
   */
  public CameraMount(IServo panServo, 
      IServo tiltServo, 
      int tiltLowerLimitInDegrees, 
      int tiltUpperLimitInDegrees, 
      int panLowerLimitInDegrees,
      int panUpperLimitInDegrees) {
    this(panServo, 
      tiltServo, 
      tiltLowerLimitInDegrees, 
      tiltUpperLimitInDegrees, 
      false, 
      panLowerLimitInDegrees, 
      panUpperLimitInDegrees, 
      false);
  }

  /**
   * Construct a camera mount specifing limits of travel in degrees with the
   * ability to reverse 0 to 180 servo travel direction.
   * 
   * @param panServo                  An interface to a Servo for panning
   * @param tiltServo                 An interface to a Servo for tilting
   * @param tiltLowerLimitInDegrees   The lower tilt limit angle in degrees
   * @param tiltUpperLimitInDegrees   The upper tilt limit angle in degrees
   * @param reverseTilt               Flip the 0 to 180 direction of the tilt servo.
   * @param panLowerLimitInDegrees    The lower pan limit angle in degrees
   * @param panUpperLimitInDegrees    The upper pan limit angle in degrees
   * @param reversePan                Flip the 0 to 180 direction of the pan servo.
   */
  public CameraMount(IServo panServo, 
      IServo tiltServo, 
      int tiltLowerLimitInDegrees, 
      int tiltUpperLimitInDegrees, 
      boolean reverseTilt,
      int panLowerLimitInDegrees,
      int panUpperLimitInDegrees,
      boolean reversePan) {
    super("CameraMount");
    this.panServo = panServo;
    this.tiltServo = tiltServo;
    this.reverseTilt = reverseTilt;
    if (reverseTilt) {
      this.tiltUpperLimitInDegrees = 180 - tiltLowerLimitInDegrees;
      this.tiltLowerLimitInDegrees = 180 - tiltUpperLimitInDegrees;
    } else {
      this.tiltLowerLimitInDegrees = tiltLowerLimitInDegrees;
      this.tiltUpperLimitInDegrees = tiltUpperLimitInDegrees;
    }
    this.reversePan = reversePan;
    if (reversePan) {
      this.panUpperLimitInDegrees = 180 - panLowerLimitInDegrees;
      this.panLowerLimitInDegrees = 180 - panUpperLimitInDegrees;
    } else {
      this.panLowerLimitInDegrees = panLowerLimitInDegrees;
      this.panUpperLimitInDegrees = panUpperLimitInDegrees;
    }
  }

  protected void initDefaultCommand() {}

  /**
   * Pan to an angle within limits set at construction time.
   * 
   * @param angleInDegrees
   */
  public void panToAngle (double angleInDegrees) {
    int roundedAngleInDegrees;
    // If the servo is "reversed", simply flip the 0 to 180
    // direction, but do not cause the consumer to worry about
    // whether the servo is left to right or right to left.
    if (reversePan) {
      roundedAngleInDegrees = (int) Math.round(180D - angleInDegrees);
    } else {
      roundedAngleInDegrees = (int) Math.round(angleInDegrees);
    }
    if (roundedAngleInDegrees >= panUpperLimitInDegrees) {
      panServo.write(panUpperLimitInDegrees);
      panAngleInDegrees = panUpperLimitInDegrees;
    } else if (roundedAngleInDegrees <= panLowerLimitInDegrees) {
      panServo.write(panLowerLimitInDegrees);
      panAngleInDegrees = panLowerLimitInDegrees;
    } else {
      panServo.write(roundedAngleInDegrees);
      panAngleInDegrees = angleInDegrees;
    }
  }

  /**
   * Tilt to an angle within limits set at construction time.
   * 
   * @param angleInDegrees
   */
  public void tiltToAngle (double angleInDegrees) {
    int roundedAngleInDegrees;
    if (reverseTilt) {
      roundedAngleInDegrees = (int) Math.round(180D - angleInDegrees);
    } else {
      roundedAngleInDegrees = (int) Math.round(angleInDegrees);
    }
    if (roundedAngleInDegrees >= tiltUpperLimitInDegrees) {
      tiltServo.write(tiltUpperLimitInDegrees);
      tiltAngleInDegrees = tiltUpperLimitInDegrees;
    } else if (roundedAngleInDegrees <= tiltLowerLimitInDegrees) {
      tiltServo.write(tiltLowerLimitInDegrees);
      tiltAngleInDegrees = tiltLowerLimitInDegrees;
    } else {
      tiltServo.write(roundedAngleInDegrees);
      tiltAngleInDegrees = angleInDegrees;
    }
  }

  /**
   * Get the last set tilt angle. Note that hardware
   * positions are set based on integers and so this angle
   * is rounded before it is set.
   * 
   * @return  Tilt angle in degrees
   */
  public double getTiltAngleInDegrees() {
    return tiltAngleInDegrees;
  }

  /**
   * Get the last set pan angle. Note that hardware
   * positions are set based on integers and so this angle
   * is rounded before it is set.
   * 
   * @return  Pan angle in degrees
   */
  public double getPanAngleInDegrees() {
    return panAngleInDegrees;
  }

  /**
   * Get the last set tilt angle
   * 
   * @return  Tilt angle in degrees
   */
  public int getRoundedTiltAngleInDegrees() {
    return (int) Math.round(tiltAngleInDegrees);
  }

  /**
   * Get the last set tilt angle
   * 
   * @return  Tilt angle in degrees
   */
  public int getRoundedPanAngleInDegrees() {
    return (int) Math.round(panAngleInDegrees);
  }

  /**
   * Determines if panning is at a limit.
   * 
   * @return  True if at a limit
   */
  public boolean atPanLimit() {
    return getRoundedPanAngleInDegrees() <= panLowerLimitInDegrees || getRoundedPanAngleInDegrees() >= panUpperLimitInDegrees;
  }

  /**
   * Determines if tilting is at a limit.
   * 
   * @return  True if at a limit
   */
  public boolean atTiltLimit() {
    return getRoundedTiltAngleInDegrees() <= tiltLowerLimitInDegrees || getRoundedTiltAngleInDegrees() >= tiltUpperLimitInDegrees;
  }
}