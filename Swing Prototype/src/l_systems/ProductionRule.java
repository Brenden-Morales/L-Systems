package l_systems;

public class ProductionRule {
	
	//production rule with Predecessor and Successor
	public ProductionRule(String Pre, String Post){
		Predecessor = Pre;
		Successor = Post;
	}
	
	//just parsing production rules in this class, should be fairly self explanatory
	private String Predecessor;
	public String getPredecessor(){return Predecessor;}
	
	private String Successor;
	public String getSuccessor(){return Successor;}
	
	private float Probability;
	
	public String toString(){
		return "(" + Predecessor + " → " + Successor + ")";
	}
	
	//overriding equals for comparison
	@Override
	public boolean equals(Object object){
		boolean Equal = false;
		
		if(object != null && object instanceof ProductionRule){
			if( this.Predecessor == ((ProductionRule)object).Predecessor){
				if (this.Successor == ((ProductionRule)object).Successor) Equal = true;
			}
		}
		
		return Equal;
	}
}
