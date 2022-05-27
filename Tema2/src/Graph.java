
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graph implements Serializable {

   private String graphTitle;
   private List<Noduri> nodes;

   private List<Muchii> Muchiis;

   public Graph(String title) {
	  setGraphTitle(title);
      setNodes(new ArrayList<Noduri>());
      setMuchiis(new ArrayList<Muchii>());
   }
   
   public String getGraphTitle() {
		return graphTitle;
	}

	public void setGraphTitle(String graphTitle) {
		if(graphTitle == null)
			graphTitle = "";
		else
			this.graphTitle = graphTitle;
	}

   public List<Noduri> getNodes() {
      return nodes;
   }

   public void setNodes(List<Noduri> nodes) {
      this.nodes = nodes;
   }

   public List<Muchii> getMuchiis() {
      return Muchiis;
   }

   public void setMuchiis(List<Muchii> Muchiis) {
      this.Muchiis = Muchiis;
   }

   public void draw(Graphics graphics) {
      for (Muchii Muchii : getMuchiis()) {
         Muchii.drawMuchii(graphics);
      }

      for (Noduri node : getNodes()) {
         node.drawNode(graphics);
      }
   }

   public void addNode(Noduri node) {
      nodes.add(node);
   }

   public void addMuchii(Muchii newMuchii) {
      for (Muchii Muchii : Muchiis) {
         if (newMuchii.equals(Muchii)) {
            return;
         }
      }
      Muchiis.add(newMuchii);
   }

   public Noduri findNodeUnderCursor(int mouseX, int mouseY) {
      for (Noduri node : nodes) {
         if (node.isNodeUnderCursor(mouseX, mouseY)) {
            return node;
         }
      }
      return null;
   }

   public Muchii findMuchiiUnderCursor(int mouseX, int mouseY) {
      for (Muchii Muchii : Muchiis) {
         if (Muchii.isMuchiiUnderCursor(mouseX, mouseY)) {
            return Muchii;
         }
      }
      return null;
   }

   public void removeNode(Noduri nodeUnderCursor) {
      removeAttachedMuchiis(nodeUnderCursor);
      nodes.remove(nodeUnderCursor);
   }

   protected void removeAttachedMuchiis(Noduri nodeUnderCursor) {
      Muchiis.removeIf(Muchii -> Muchii.getNodeA().equals(nodeUnderCursor) || Muchii.getNodeB().equals(nodeUnderCursor));
   }

   public void removeMuchii(Muchii MuchiiUnderCursor) {
      Muchiis.remove(MuchiiUnderCursor);
   }

   public void moveGraph(int distanceX, int distanceY) {
      for (Noduri node : nodes) {
         node.moveNode(distanceX, distanceY);
      }
   }
   
   public String getListOfNodes() {
		int index = 1;
		String list = "Number of nodes: " + Integer.toString(nodes.size()) + "\n";
		list += "ID  : (position), Key , Text{parameters}\n";
		list += "________________________________________\n";
		for (Noduri node : nodes) {
			list += "ID ";
			list += Integer.toString(index++);
			list += ": ";
			list += node.toString();
			list += "\n";
		}
		return list;
	}

	public String getListOfMuchiis() {
		int index = 1;
		String list = "Numarul de Muchiis: " + Integer.toString(Muchiis.size()) + "\n";
		list += "ID  : Value Size, Color Hex, Muchii Key, (Node A, Node Key A, Node Text A) ===> (Node B, Node Key B, Node Text B) {parameters}\n";
		list += "____________________________________________________________________________________________________________________________\n";
		for (Muchii Muchii : Muchiis) {
			list += "ID ";
			list += Integer.toString(index++);
			list += ": ";
			list += Muchii.toString();
			list += "\n";
		}
		return list;
	}
	
	
	@Override
	public String toString() {
		return graphTitle + "("+ nodes.size() + " nodes, " + Muchiis.size() + " Muchiis)";
	}
}
