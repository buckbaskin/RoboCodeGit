package arc.model;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;

public class TargettingModel {
	
	private TargettingType most_likely;
	
	public TargettingModel(RobotModel parent) {
		// TODO constructor
		// models/calculates the targetting of the parent robot
		// two functions:
		// 		provide domain knowledge about how the robot could target/shoot
		// 		provide maximum likelihood fitting to a Targetting Type
	}
	public void test(HitByBulletEvent current, TimeCapsule history) {
		// Test models for fit
		// TODO
		/*
			current.getBearingRadians(); // bearing from robot to bullet
			current.getBullet(); // bullet that hit robot
			current.getBullet().getHeadingRadians(); // heading of bullet
			current.getBullet().getName(); // name of robot that fired
			current.getBullet().getPower(); // power of bullet
			current.getBullet().getVelocity(); // velocity of bullet
			current.getBullet().getVictim(); // name of victim of bullet
			current.getBullet().getX(); // x of bullet
			current.getBullet().getY(); // y of bullet
			current.getHeadingRadians(); // heading of bullet
			current.getName(); // name of robot that fired bullet
			current.getPower(); // power of bullet 
			current.getTime(); // time of event
			current.getVelocity(); // velocity of the bullet
		*/
	}
	public void test(AdvancedRobot ar, TimeCapsule history) {
		// TODO test in the case of self
	}
	
	public double predict_gun_heading(ScannedRobotEvent current, TimeCapsule history) {
		// TODO
		// based on past tests, info where is the gun pointing?
		return 0.0;
	}
	public double predict_gun_heat(ScannedRobotEvent current, TimeCapsule history) {
		// TODO
		// based on past tests, info what is the gun heat?
		return 0.0;
	}
	
	abstract class TargettingType {
		// TODO implement
	} 
	class HeadOn extends TargettingType {
		// TODO implement example
	}
}
