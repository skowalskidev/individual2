package rp.assignments.individual.ex2;

import lejos.robotics.RangeFinder;
import rp.config.RangeFinderDescription;
import rp.robotics.DifferentialDriveRobot;
import rp.systems.StoppableRunnable;

public class RangeController implements StoppableRunnable{

	private DifferentialDriveRobot _robot;
	private RangeFinderDescription _desc;
	private RangeFinder _ranger;
	private Float targetDistance;
	private Float minDistance;
	private Float slowdownDistance;
	private boolean stopped = false;
	
	public RangeController( DifferentialDriveRobot _robot, RangeFinderDescription _desc, RangeFinder _ranger, Float _maxDistance) {
		this._robot = _robot;
		this._desc = _desc;
		this._ranger = _ranger;
		targetDistance = _maxDistance * 0.95f;System.out.println("targetDistance "+targetDistance);
		if(targetDistance >= _desc.getMaxRange() - _desc.getNoise()){
			targetDistance = (_desc.getMaxRange() - _desc.getNoise()) * 0.95f;
		}System.out.println("targetDistance corrected "+targetDistance);
		minDistance = targetDistance * 0.4f;System.out.println("minDistance "+minDistance);
		slowdownDistance = targetDistance * 0.5f;
	}
	
	@Override
	public void run() {
		double speedCorrectionValue;
		double currentTravelSpeed = 0;
		while (!stopped ){
			try {
				Thread.sleep(83);//83 ~ 12Hz
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("RangeController:run:Could not sleep");
			}
			
			currentTravelSpeed =_robot.getDifferentialPilot().getTravelSpeed();
			speedCorrectionValue = (_ranger.getRange()/ targetDistance) * _robot.getDifferentialPilot().getMaxTravelSpeed() * 0.3;
			
			if(_ranger.getRange() <= slowdownDistance){
				speedCorrectionValue = ((_ranger.getRange() - minDistance)/slowdownDistance);
				if(currentTravelSpeed * speedCorrectionValue >= 0)
					_robot.getDifferentialPilot().setTravelSpeed(currentTravelSpeed * speedCorrectionValue);
				continue;
			}
			else if(_ranger.getRange() <= minDistance){
				_robot.getDifferentialPilot().stop();
				continue;
			}
			else if(!_robot.getDifferentialPilot().isMoving()){
				_robot.getDifferentialPilot().setTravelSpeed(_robot.getDifferentialPilot().getMaxTravelSpeed());
				_robot.getDifferentialPilot().forward();
				continue;
			}
			
			if(_ranger.getRange() < targetDistance && currentTravelSpeed + speedCorrectionValue < _robot.getDifferentialPilot().getMaxTravelSpeed()){
				_robot.getDifferentialPilot().setTravelSpeed(currentTravelSpeed + speedCorrectionValue);
			}
		}
	}

	@Override
	public void stop() {
		stopped = true;
	}
	
}
