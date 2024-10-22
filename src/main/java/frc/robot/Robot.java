// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.photonvision.PhotonCamera;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.FiducialVision;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private FiducialVision m_fiducialVision;

  public static PhotonCamera camAprTg = new PhotonCamera("camAprTg");
  public static PhotonCamera camObj = new PhotonCamera("camObj");

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    camObj.setDriverMode(false);
    camAprTg.setDriverMode(false);
    DriverStation.silenceJoystickConnectionWarning(true); // Disable joystick connection warning
    m_fiducialVision = new FiducialVision(m_robotContainer.drivetrain);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    SmartDashboard.putBoolean("camObj Connected",camObj.isConnected()); // Check if the camera is connected
    SmartDashboard.putBoolean("camAprTg Connected",camAprTg.isConnected()); // Check if the camera is connected
    SmartDashboard.putBoolean("camObj has Targets",camObj.getLatestResult().hasTargets()); // Check if the camera has targets
    SmartDashboard.putBoolean("camAprTg has Targets",camAprTg.getLatestResult().hasTargets()); // Check if the camera has targets
    m_fiducialVision.updateVisionField(); // Update the vision field
    m_fiducialVision.updatePoseEstimation(m_robotContainer.drivetrain); // Update the pose estimation from the fiducial vision
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    watchForNote();
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationPeriodic() {}

  public static boolean watchForNote(){
    boolean hasTargets = false;
    var result = camObj.getLatestResult(); //Get the latest result from PhotonVision
    hasTargets = result.hasTargets(); // Check if the latest result has any targets.
    if (hasTargets == true){
      // LEDsSubSystem.setLED(.25);
    } else{
      // LEDsSubSystem.setLED(.99);
    }
    return hasTargets;
  }

}