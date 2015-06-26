package arc.model.motions;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import arc.model.TimeCapsule;
import arc.model.motion.MotionProjection;
import arc.model.motion.MotionType;

public class ProbableAction extends MotionType {
	
	Random random;
	
	HashMap<Pair<Integer,Integer>, HashMap<Pair<Integer,Integer>, Double>> transition_map;
	

	public ProbableAction(TimeCapsule data_source) {
		super(data_source);
		random = new Random();
		transition_map = new HashMap<Pair<Integer,Integer>, HashMap<Pair<Integer,Integer>, Double>>();
	}

	/* UPDATE */
	
	@Override
	public void update() {}
	@Override
	public void update(ScannedRobotEvent sre) {
		include_new_state(data.last().get(0), data.last().get(1));
	}
	@Override
	public void update(AdvancedRobot ar) {
		include_new_state(data.last().get(0), data.last().get(1));
	}
	public void include_new_state(TimeCapsule.StateVector s0, TimeCapsule.StateVector s1) {
		int v = round_zero(s1.velocity());
		int omega = round_zero(s1.heading() - s0.heading());
		
		
	}

	/* PROJECT */
	
	@Override
	public MotionProjection project(TimeCapsule tc, long time_forward) {
		double[] x = new double[(int) time_forward];
		double[] y = new double[(int) time_forward];
		long[] t = new long[(int) time_forward];
		
		TimeCapsule.StateVector state = tc.last().get(0);
		for(int i = 0; i < time_forward; i++) {
			x[i] = state.x();
			y[i] = state.y();
			t[i] = (long) state.time();
			
			state = transition(state);
		}
		
		return new MotionProjection(x, y, t);
	}
	
	public TimeCapsule.StateVector transition(TimeCapsule.StateVector s0) {
		// defines a transition from an initial state s0 to the returned state s1;
		TimeCapsule.StateVector s1 = data.sv_create(s0.time()+1, -1, 
				-1, -1, heading(s0), velocity(s0), x(s0), y(s0));
		
		return s1;
	}
	
	public double velocity(TimeCapsule.StateVector s0) {
		// calculate a new velocity based on the old state
		return s0.velocity();
	}
	public double heading(TimeCapsule.StateVector s0) {
		// calculate a new heading based on the old state
		return s0.heading();
	}
	public double x(TimeCapsule.StateVector s0) {
		// calculate a new x based on the old state
		return s0.x();
	}
	public double y(TimeCapsule.StateVector s0) {
		// calculate a new y based on the old state
		return s0.y();
	}
	
	// UTILITIES
	
	public int round_zero(double val) {
	    if (val < 0) {
	        return (int) Math.ceil(val);
	    }
	    return (int) Math.floor(val);
	}
	
	
	
	/* TEST Probable Action */

	public static boolean test() {
		return true;
	}
	
	public static void main(String[] args) {
		boolean t = test();
		if(t)
			System.out.println("Succesful test.");
		else
			System.out.println("Failed test.");
	}

	
	class Pair<T,U> {
		T t;
		U u;
		public Pair(T t_, U u_) {
			t = t_;
			u = u_;
		}
	}
}
