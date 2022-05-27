import java.awt.*;
import java.io.Serializable;

public class Muchii implements Serializable {

   public static final Integer[] VALUES = {1, 2, 3, 4, 5};

   private final Noduri nodeA;

   private final Noduri nodeB;

  

   private int value;

   private String key;

   public Muchii(Noduri nodeA, Noduri nodeB, String key) {
      this.nodeA = nodeA;
      this.nodeB = nodeB;
      this.value = value;
      
      setKeyOfMuchii(key);
   }

   public void setValueOfMuchii(int value) {
      if (value < VALUES[0]) {
         this.value = VALUES[0];
      } else if (value > VALUES[VALUES.length - 1]) {
         this.value = VALUES[VALUES.length - 1];
      } else {
         this.value = value;
      }
   }
   public void setKeyOfMuchii(String key) {
	      if (key == null) {
	         this.key = "";
	      } else {
	         this.key = key;
	      }
	   }
   public Noduri getNodeA() {
      return nodeA;
   }

   public Noduri getNodeB() {
      return nodeB;
   }

   public void drawMuchii(Graphics graphics) {
      int xOfNodeA = getNodeA().getX();
      int yOfNodeA = getNodeA().getY();
      int xOfNodeB = getNodeB().getX();
      int yOfNodeB = getNodeB().getY();

      Graphics2D graphics2D = (Graphics2D) graphics;
      graphics2D.setStroke(new BasicStroke(value));
      
      graphics2D.drawLine(xOfNodeA, yOfNodeA, xOfNodeB, yOfNodeB);
      graphics2D.setStroke(new BasicStroke());
      
      FontMetrics fontMetrics = graphics2D.getFontMetrics();     
      int xOfText = ((xOfNodeA +xOfNodeB) /2)- fontMetrics.stringWidth(key) / 2;
      int yOfText =(( yOfNodeA +yOfNodeB)/2) - fontMetrics.getHeight() / 2 + fontMetrics.getAscent();
      
      graphics2D.drawString(" " + key , xOfText, yOfText-20);
     }

   public boolean isMuchiiUnderCursor(int mouseX, int mouseY) {
      if (mouseX < Math.min(nodeA.getX(), nodeB.getX()) ||
              mouseX > Math.max(nodeA.getX(), nodeB.getX()) ||
              mouseY < Math.min(nodeA.getY(), nodeB.getY()) ||
              mouseY > Math.max(nodeA.getY(), nodeB.getY())) {
         return false;
      }
      int A = nodeB.getY() - nodeA.getY();
      int B = nodeB.getX() - nodeA.getX();
      double distance = Math.abs(A * mouseX - B * mouseY + nodeB.getX()
              * nodeA.getY() - nodeB.getY() * nodeA.getX()) / Math.sqrt(A * A + B * B);
      return distance <= VALUES[4];
   }

   
   

   public void moveMuchii(int distanceX, int distanceY) {
      nodeA.moveNode(distanceX, distanceY);
      nodeB.moveNode(distanceX, distanceY);
   }

   
  
}
