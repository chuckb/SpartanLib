package org.team997coders.spartanlib.motion.pathfinder;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class TrajectoryIngredients {
  public String name;
  public Waypoint[] points;
  public Trajectory.Config config;

  public TrajectoryIngredients(String name, Trajectory.Config config, Waypoint... points) {
    this.name = name;
    this.points = points;
    this.config = config;
  }
}