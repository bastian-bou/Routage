package dii.vrp.tp;

public class Saving implements Comparable<Saving> {

	/**
	 * The id of the node
	 */
	private final int i;
	/**
	 * The id of the neighbor node
	 */
	private final int j;
	/**
	 * The distance between <code>id</code> and <code>neighborID</code>
	 */
	private final double sij;

	/**
	 * Constructs a new neighbor
	 * @param id
	 * @param neighborID
	 * @param distance
	 */
	public Saving(int i, int j, double sij){
		this.i=i;
		this.j=j;
		this.sij=sij;
	}

	/**
	 * 
	 * @return the id of the node
	 */
	public int getI() {
		return i;
	}
	/**
	 * 
	 * @return the id of the neighbor node
	 */
	public int getJ() {
		return j;
	}
	/**
	 * 
	 * @return the distance to the neighbor
	 */
	public double getSIJ() {
		return sij;

	}

	@Override
	public int compareTo(Saving s) {
		if (sij < s.sij)
			return 1;
		return (sij > s.sij) ? -1 : 0;
	}
	
	@Override
	public String toString(){
		return "("+this.i+","+this.j+")\t"+this.sij;
	}


	
}
