package org.usfirst.frc.team5031.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class MockSpeedController implements SpeedController {
	double speed = 0.0;
	
	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public double get() {
		return speed;
	}

	@Override
	public void set(double speed, byte syncGroup) {
		this.speed = speed;
	}

	@Override
	public void set(double speed) {
		this.speed = speed;
	}

	@Override
	public void disable() {
		speed = 0.0d;
	}

}
