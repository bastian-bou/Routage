package dii.vrp.test;

import dii.vrp.data.CMT01;
import dii.vrp.data.IDemands;
import dii.vrp.data.IDistanceMatrix;
import dii.vrp.data.VRPREPInstanceReader;
import dii.vrp.data.CVRPRouteEvaluator;
import dii.vrp.tp.INeighborhood;
import dii.vrp.tp.Relocate;
import dii.vrp.tp.VRPSolution;

public class TRelocate {

	public static void main(String[] args) {

		//Parameters
		String file= "./data/christofides-et-al-1979-cmt/CMT01.xml"; //Instance

		//Read data from an instance file
		IDistanceMatrix distances=null;
		IDemands demands=null;
		double Q=Double.NaN;
		try(VRPREPInstanceReader parser=new VRPREPInstanceReader(file)){
			distances=parser.getDistanceMatrix();
			demands=parser.getDemands();
			Q=parser.getCapacity("0");
		}
		
		//Create a route evaluator
		CVRPRouteEvaluator evaluator=new CVRPRouteEvaluator(distances, demands);
		
		//Create the optimal solution for instance CMT01
		VRPSolution s=CMT01.getS1(evaluator);
		
		System.out.println(s);
		
		//Set up neighborhood
		INeighborhood n=new Relocate(distances, demands, Q);
		
		//Search the neighborhood
		s=(VRPSolution) n.explore(s);
		
		System.out.println(s);

	}

}
