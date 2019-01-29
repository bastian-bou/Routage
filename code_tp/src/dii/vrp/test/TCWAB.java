/**
 * Clarke and Wright Test
 * Author : Bastian Bouchardon
 * 			DII5A 2018/2019
 * Polytech Tours
 * 
 * Routage et modélisation du traffic
 */


package dii.vrp.test;


import dii.vrp.data.IDemands;
import dii.vrp.data.IDistanceMatrix;
import dii.vrp.data.VRPREPInstanceReader;
import dii.vrp.tp.CWAB;

import dii.vrp.tp.VRPSolution;

public class TCWAB {

	public static void main(String[] args) {

		String file= "data/christofides-et-al-1979-cmt/CMT12.xml"; //Instance
		int T=1000;
		
		//Read data from an instance file
		IDistanceMatrix distances=null;
		IDemands demands=null;
		double Q=Double.NaN;
		try(VRPREPInstanceReader parser=new VRPREPInstanceReader(file)){
			distances=parser.getDistanceMatrix();
			demands=parser.getDemands();
			Q=parser.getCapacity("0");
		}
		
		//Set up CWHeuristic
		CWAB cw = new CWAB(distances, demands, Q, 1);

		long start=System.currentTimeMillis();

		//Run CWHeuristic
		VRPSolution s=(VRPSolution)cw.run();
		
		System.out.println(System.currentTimeMillis()-start);
		//Report solution
		System.out.println(s);
	}

}
