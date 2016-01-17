
package org.usfirst.frc.team5031.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Joystick;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive Drive = new RobotDrive(8,9);
	Joystick DriverStick = new Joystick(0);
	Joystick OperaterStick = new Joystick(1);
	Victor Elevator1 = new Victor(4);
	Victor Elevator2 = new Victor(6);
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	double Right = DriverStick.getRawAxis(3);
    	double Left = DriverStick.getRawAxis(1);    	  
    	Drive.tankDrive(-Left, -Right);

    	
    	if (OperaterStick.getRawButton(1)){
    		Elevator1.set(1.0);
    		Elevator2.set(1.0);
    	}else if(OperaterStick.getRawButton(2)){
    		Elevator1.set(-1.0);
    		Elevator2.set(-1.0);
    	}else{
    		Elevator1.set(0.0);
        	Elevator2.set(0.0);
    		}
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
