package org.team997coders.spartanlib.motion.pathfollower;

import edu.wpi.first.wpilibj.trajectory.Trajectory;

public interface PathData {

  public Trajectory processTrajectory();

  public String getName();

}