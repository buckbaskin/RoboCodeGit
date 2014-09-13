package cepl;

import java.util.ArrayList;

import cepl.dataStorage.BotBin;
import cepl.dataStorage.DataPoint;
import robocode.ScannedRobotEvent;

public class DataCollection extends Control 	{
	
	ArrayList<BotBin> robots;
	
	BotBin selfie;
	
	public DataCollection()
	{
		robots = new ArrayList<BotBin>();
		id = "DataCollection";
	}
	
	@Override
	public void setRobot(Ceepl s) {
		source = s;
		selfie = new BotBin(source.getName());
	}

	@Override
	public void update(ScannedRobotEvent sre) {
		System.out.println("Scanned Robot Event");
		String name = sre.getName();
		boolean name_found = false;
		BotBin scanned_robot = null;
		for(int i = 0 ; i < robots.size() ; i++ )
		{
			System.out.println("Robots list = "+robots);
			System.out.println("Robot = "+robots.get(i));
			System.out.println("Robot.name = "+robots.get(i).name);
			if (robots.get(i).name.equals(name))
			{
				name_found = true;
				scanned_robot = robots.get(i);
			}
		}
		if (!name_found)
		{
			robots.add(new BotBin(name));
			scanned_robot = robots.get(robots.size()-1);
		}
		
		DataPoint prior;
		if (scanned_robot.info.size() >= 1)
		{
			prior = scanned_robot.info.get(scanned_robot.info.size()-1);
		}
		else
		{
			prior = null;
		}
		
		double myX = source.getX();
		double myY = source.getY();
		
		double robo_bearing = (sre.getBearingRadians()+source.getHeadingRadians())%(Math.PI*2);
		double real_bearing = -robo_bearing + Math.PI/2;
		double robo_distance = sre.getDistance();
		
		double eX = myX + robo_distance*Math.cos(real_bearing);
		double eY = myY + robo_distance*Math.sin(real_bearing);
		
		DataPoint dp = new DataPoint(eX, eY, sre.getEnergy(), sre.getVelocity(), sre.getHeadingRadians()
				, sre.getTime(), prior, false);
		
		scanned_robot.addData(dp);
		
		if (robots.size() > 1)
		{
			source.melee_mode = true;
		}
		System.out.println("End Scanned Robot Event");
	}

	@Override
	public void update() 
	{
		DataPoint prior = null;
		if(selfie.info.size()>1)
		{
			prior = selfie.info.get(selfie.info.size()-1);
		}
		
		DataPoint dp = new DataPoint(source.getX(), source.getY(), source.getEnergy(), source.getVelocity(), 
				source.getHeadingRadians(), source.getTime(), prior, false);
		selfie.addData(dp);
	}

	@Override
	public String toFile() {
		String complete = new String();
		
		//self
		complete += "<BotBin name="+selfie.name+
		" fill%= "+selfie.percent_fill()+">"+"\n";
		for(int j = 0; j < selfie.info.size(); j++)
		{
			complete += selfie.info.get(j).toString()+"\n";
		}
		complete += "</BotBin>";
		//others
		for(int i = 0; i < robots.size(); i++)
		{
			complete += "<BotBin name="+robots.get(i).name+
			" fill%= "+robots.get(i).percent_fill()+">"+"\n";
			for(int j = 0; j < robots.get(i).info.size(); i++)
			{
				complete += robots.get(i).info.get(j).toString()+"\n";
			}
			complete += "</BotBin>";
		}
		
		return complete;
	}
	
}
