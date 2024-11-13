package frc.robot.Commands;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.LauncherSubsystem;

public class SpeakerCmd extends Command{

    private final LauncherSubsystem Launcher;

    public SpeakerCmd(LauncherSubsystem Launcher) {
        this.Launcher = Launcher;
    
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    Launcher.topLauncherPID.setReference(Constants.LauncherConstants.topSpeakerSpeed, CANSparkMax.ControlType.kSmartMotion);
    if(Launcher.leftLauncher.getAbsoluteEncoder().getVelocity() >= 900) {
      Launcher.bottomLauncherPID.setReference(Constants.LauncherConstants.bottomSpeakerSpeed, CANSparkMax.ControlType.kSmartMotion);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Launcher.topLauncherPID.setReference(Constants.LauncherConstants.zeroSpeed, CANSparkMax.ControlType.kSmartMotion);
    Launcher.bottomLauncherPID.setReference(Constants.LauncherConstants.zeroSpeed, CANSparkMax.ControlType.kSmartMotion);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
