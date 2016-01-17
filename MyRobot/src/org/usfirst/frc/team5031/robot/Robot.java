package org.usfirst.frc.team5031.robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {

	Joystick DriverStick = new Joystick(0);
	Joystick OperatorStick = new Joystick(1);
	//RobotDrive Drive = new RobotDrive(8, 9);
	Talon driveTrainLeft = new Talon(8);
	Talon driveTrainRight = new Talon(9);
	Victor IntakeLeft = new Victor(1);
	Victor IntakeRight = new Victor(2);// 3 victor not in use
	Victor SeatBelt = new Victor(5);
	Victor Elevator = new Victor(4);
	Victor Elevator1 = new Victor(6);
	/* Encoder Encoder = new Encoder(1,2); */
	Timer Timer = new Timer(), elevlag=new Timer();
	DoubleSolenoid DoubleSolenoid = new DoubleSolenoid(0, 1);
	AnalogPotentiometer APot = new AnalogPotentiometer(1);
	DigitalInput Top = new DigitalInput(1);
	DigitalInput Bottom = new DigitalInput(0);
	int position;
	boolean isPressed = false, isPressedA = false , activate = false , elevmove = true;
	double multiplyer = 0.85;
	private enum Intake {
		IN, OUT, OFF
	};

	private enum Level {
		BOTTOM(0), TOP(30), NONE(-1);

		double heightInches;

		Level(double heightInches) {
			this.heightInches = heightInches;
		}
	}

	SpeedController elevatorSpeed = new MockSpeedController();
	private PIDController elevatorPID = new PIDController(1.0, 0.0, 0.0, 1.0,
			APot, elevatorSpeed);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code11
	 */
	public void robotInit() {
		elevatorPID.disable();
		SmartDashboard.putData("Elevator PID", elevatorPID);
		LiveWindow.setEnabled(true);
		position = 2;
		isPressed = false;
		elevlag.start();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
//		for (int i = 0; i < 3; i++) {
//			Drive.drive(0.1, 0); // Driving forward 10% , no turning
//		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopInit(){
		SeatBelt.set(0);
	}
	
	public void teleopPeriodic() {
    	//Drive.setSafetyEnabled(true);
    	
    	// Wheel Drive
    	double x = -1 * DriverStick.getRawAxis(1);
    	double y = DriverStick.getRawAxis(3);
    	double tDistance = 0.15;
    	double p = 40;
    	
    	if(!(isPressedA))
    	{
    		if(DriverStick.getRawButton(2))
    		{
    			isPressedA=true;
    			if(multiplyer != 0.85)
    			multiplyer = 0.85;
    			else
    			multiplyer = 0.5;
    		}   		
    	}
    	if(!DriverStick.getRawButton(2))
    		isPressedA=false;
    	
    	
    	//"Better" Exponential drive functions\
    	// !!!Now with Multiplyer!!! !!!The Drive Mutilator!!!
    	
    	y = y*y*y*multiplyer;
    	x = x*x*x*multiplyer;
    	
    	//Sweet Exponential drive functions
//    	if(Right<0)
//    	{Right=Math.sqrt(-Right);}
//    	else
//    	{Right=-Math.sqrt(Right);};
//    	
//    	if(Left<0)
//    	{Left=Math.sqrt(-Left);}
//    	else
//    	{Left=-Math.sqrt(Left);};
    	
    	// driveTrainLeft.set(y + x);
    	// driveTrainRight.set(y - x);
    	
    	driveTrainLeft.set(x);
    	driveTrainRight.set(y);
    	//SeatBelt
	    if(OperatorStick.getRawButton(7))
	    	SeatBelt.set(-1);	    
	    else if(OperatorStick.getRawButton(6))
	       	SeatBelt.set(1);
	    else	    
	    	SeatBelt.set(0);
	    
	   
    	// Elevator
    	// Up button
    	System.out.print(APot);
//    	if (DriverStick.getRawButton(7)){
//    		elevatorPID.disable();
//    		
//    		//Check if not at limitswitch at top
//    		if (Top.get()){
//    			setElevator(0);
//    		} else {
//    			setElevator(1.0);
//    		}
//    	// Down button	
//    	} else if(DriverStick.getRawButton(8)){
//    		elevatorPID.disable();
//    		if (Bottom.get()) {
//    			setElevator(0);
//    		} else {
//    			setElevator(-1.0);
//    		}
//    	// No button	
//    	} else {
//    		Level level = getElevatorButtonLevel();
//    		if (level != Level.NONE) {
//    			driveToLevel(level);
//    		}
//    		if (elevatorPID.isEnable()) {
//    			// Emergency Stop
//    			if (Top.get() || Bottom.get()) {
//    				setElevator(0);
//    			} else {
//    				driveToLevel();
//    			}
//    		} else {
//    			setElevator(0);
//    		}
//    	}
    	
    	if(!(isPressed)){
    		if(OperatorStick.getRawButton(2)){
	    		position =0;
	    	}
	    	if(OperatorStick.getRawButton(3)){
	    		if(position==0)
	    		{
	    			elevlag.reset();
	    			elevlag.start();
	    		}
	    		position=3;    		
	    	} 
	    	
	    	if(OperatorStick.getRawButton(4)){
	    		if(position==0)
	    		{
	    			elevlag.reset();
	    			elevlag.start();
	    		}
	    		position = 1;	    		
	    	}
	    	
	    	if(OperatorStick.getRawButton(5)){
	    		if(position==0)
	    		{
	    			elevlag.reset();
	    			elevlag.start();
	    		}
	    		position = 2;
	    	}
    	}
    	SmartDashboard.putNumber("position",position);
    	//if(!OperatorStick.getRawButton(3) && !OperatorStick.getRawButton(2)){
    	// isPressed = false;
    	//}
    	
    	
    	
    	
    if(elevlag.get()>=1.0)
    {
    	switch(position){
    	case 0:
    		tDistance = 0.056;
    		break;
    	case 1:
    		tDistance = 0.115;
    		break;
    	case 2:
    		tDistance = 0.17;
    		break;
    	case 3:
    		tDistance = 0.23;
    		break;
    	//case 4:
    		//tDistance = xxx;
    		//break;
    	}
    }
       	if (OperatorStick.getRawButton(10)){
			tDistance = 0.112;
	}
    	double difference = tDistance - APot.get();
    	SmartDashboard.putNumber("Difference", difference*p);
    	SmartDashboard.putNumber("Position", position);
    	System.out.println(position);
    	setElevator(difference*p);
    	
    	
    	SmartDashboard.putBoolean("elevmove",elevmove);
    	SmartDashboard.putNumber("ElevLag",elevlag.get());
    	// Intake
    	if (DriverStick.getRawButton(6)) { //In take LB
    		setIntake(Intake.IN);
    	} else if (DriverStick.getRawButton(8)) { //Take Out RB
    		setIntake(Intake.OUT);
    	} else {
    		setIntake(Intake.OFF);
    	}

    	updateSmartDashboard();
	}
    	

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

	// Always set both elevator motors to the same
	// value. This routine ensures that the elevators
	// are never driven separately.
	private void setElevator(double speed) {
		Elevator1.set(speed);
		Elevator.set(speed);
		if(Math.abs(speed)>0.45)
			elevmove=true;
		else	
			elevmove=false;
	
	}

	private void setIntake(Intake intake) {
		if(elevmove==true)
			intake = Intake.OFF;
		
		switch (intake) {
		case IN:
			DoubleSolenoid.set(Value.kForward);
			IntakeLeft.set(-1.0);
			IntakeRight.set(-1.0);
			break;
		case OUT:
			DoubleSolenoid.set(Value.kForward);
			IntakeLeft.set(1.0);
			IntakeRight.set(1.0);
			break;
		case OFF:
			IntakeLeft.set(0.0);
			IntakeRight.set(0.0);
			DoubleSolenoid.set(Value.kReverse);
			break;
		}
	}

	private void driveToLevel(Level level) {

		// Set the setpoint
		elevatorPID.setSetpoint(level.heightInches);

		// Enable if not enabled
		if (!elevatorPID.isEnable()) {
			elevatorPID.enable();
		}

		// Drive the elevator with the output
		setElevator(elevatorPID.get());
	}

	private void driveToLevel() {

		// Drive the elevator with the output
		setElevator(elevatorPID.get());
	}

	private Level getElevatorButtonLevel() {

		if (DriverStick.getRawButton(1)) {
			return Level.TOP;
		}

		return Level.NONE;
	}

	private void updateSmartDashboard() {
		elevatorPID.updateTable();

		SmartDashboard.putNumber("elevatorPID Output", elevatorPID.get());
		SmartDashboard.putNumber("APot", APot.get());
	}
	
	private void setRight(double speed){
		
	}
	
	private void setLeft(double speed){
		
	}
}