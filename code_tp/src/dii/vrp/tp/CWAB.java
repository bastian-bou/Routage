/**
 * Clarke and Wright
 * Author : Bastian Bouchardon
 * 			DII5A 2018/2019
 * Polytech Tours
 * 
 * Routage et modélisation du traffic
 */

package dii.vrp.tp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import dii.vrp.data.IDemands;
import dii.vrp.data.IDistanceMatrix;

public class CWAB implements IOptimizationAlgorithm{
	
	// matrice distances
	private final IDistanceMatrix distances;
	// demands
	private final IDemands demands;
	// capacité max
	private final double Q;
	// lambda
	private final double lambda;
	
	/**
	 * 
	 * @param distances
	 * @param demands
	 * @param Q
	 */
	public CWAB(IDistanceMatrix distances, IDemands demands, double Q, double lambda) {
		this.distances = distances;
		this.demands = demands;
		this.Q = Q;
		this.lambda = lambda;
	}
	
	@Override
	public ISolution run() {
		
		// Generate savings list
		ArrayList<Saving> list = new ArrayList<Saving>();
		for (int i = 0; i < distances.size(); i++) {
			for (int j = i + 1; j < distances.size(); j++) {
				if (i != j) {
					list.add(new Saving(i, j, distances.getDistance(0, i) 
							+ distances.getDistance(0, j) - (lambda * distances.getDistance(i, j))));
				}
			}
		}

		// mise dans l'ordre
		Collections.sort(list);
				
		VRPSolution solution = new VRPSolution();
		
		for (int i = 1; i < distances.size(); i++) {
			VRPRoute temp = new VRPRoute();
			temp.add(0);
			temp.add(i);
			temp.add(0);
			
			temp.setCost(distances.getDistance(0, i) + distances.getDistance(i, 0));
			temp.setLoad(demands.getDemand(i));
			
			solution.addRoute(temp);
		}
		
		List<IRoute> routes = solution.getRoutes();
		
		for (int i = 0; i < list.size(); i++) {
			
			int I = list.get(i).getI(), J = list.get(i).getJ();
			
			IRoute routeI = null;
			IRoute routeJ = null;

			for (IRoute route : routes) {
				if (route.contains(I)) {
					routeI = route;
				} if (route.contains(J)) {
					routeJ = route;
				}
			}
						
			// Si les deux nodes sont dans la même route, on passe à l'itération suivante
			if (routeI.equals(routeJ)) {
				continue;
			}
			// Si la sommes des charges des deux routes est supérieur à la capacité du camion, on passe à l'itération suivante
			if (((VRPRoute)routeI).getLoad() + ((VRPRoute)routeJ).getLoad() > Q) {
				continue;
			}
			
			// TRAITEMENT POSITIONNEMENT NODES I ET J
			int indexI = routeI.positionOf(I), indexJ = routeJ.positionOf(J);
			
			if ((indexI == 1) && (indexJ == routeJ.size() - 2)) {
				routeI.reverse();
				routeJ.reverse();
			} else if ((indexI == 1) && (indexJ == 1)) {
				routeI.reverse();
			} else if ((indexI == routeI.size()-2) && (routeI.size() != 3) && (indexJ == 1)) {
			} else if ((indexI == routeI.size()-2) && (routeI.size() != 3) && (indexJ == routeJ.size()-2) && (routeJ.size() != 3)) {
				routeJ.reverse();
			} else {
				if ((indexI > 1) && (indexI < routeI.size()-2)) {
					continue;
				} if ((indexJ > 1) && (indexJ < routeJ.size()-2)) {
					continue;
				}
			}
			
			for (int k = 1; k < routeJ.size()-1; k++) {
				routeI.insert(routeJ.get(k), routeI.size()-1);
			}
			
			((VRPRoute)routeI).setCost(((VRPRoute)routeI).getCost() 
										+ ((VRPRoute)routeJ).getCost() + distances.getDistance(I, J) 
										- distances.getDistance(I, 0) - distances.getDistance(0, J));
			
			((VRPRoute)routeI).setLoad(((VRPRoute)routeI).getLoad() + ((VRPRoute)routeJ).getLoad());
			
			routes.remove(routeJ);
			
			solution.setRoutes(routes);
			
			double tempOF = 0.0;
			for (int k = 0; k < solution.getRoutes().size(); k++) {
				tempOF += solution.getCost(k);
			}
			solution.setOF(tempOF);
		}
		
		return solution;
	}
}