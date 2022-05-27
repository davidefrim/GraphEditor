import java.awt.*;
import java.io.Serializable;

public class Noduri implements Serializable {

   public static final Integer[] RADIUS_VALUES = {15, 20};
   protected int x;
   protected int y;
   protected int radius;
   protected Color color;
   private String key;

   private String text;

   public Noduri(int x, int y, Color color, int radius,String key) {
      this.x = x;
      this.y = y;
      this.radius = radius;
      this.color = color;
      setTextOfNode(text);
      setKeyOfNode(key);
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public void setColorOfNode(Color color) {
      this.color = color;
   }

   public void setRadiusOfNode(int radius) {
      if (radius < RADIUS_VALUES[0]) {
         this.radius = RADIUS_VALUES[0];
      } else if (radius > RADIUS_VALUES[RADIUS_VALUES.length - 1]) {
         this.radius = RADIUS_VALUES[RADIUS_VALUES.length - 1];
      } else {
         this.radius = radius;
      }
   }

   public void setTextOfNode(String text) {
      if (text == null) {
         this.text = "";
      } else {
         this.text = text;
      }
   }
   
   public void setKeyOfNode(String key) {
	      if (key == null) {
	         this.key = "";
	      } else {
	         this.key = key;
	      }
	   }

   public void drawNode(Graphics graphics) {
      graphics.setColor(color);
      graphics.fillOval(x - radius, y - radius, radius + radius, radius + radius);
      graphics.setColor(Color.BLACK);
      graphics.drawOval(x - radius, y - radius, radius + radius, radius + radius);

      FontMetrics fontMetrics = graphics.getFontMetrics();
      int xOfText = x - fontMetrics.stringWidth(text) * 2;
      int yOfText = y - fontMetrics.getHeight() / 2 + fontMetrics.getAscent();
      graphics.drawString(" " + key , xOfText, yOfText-40);
      graphics.drawString(" " + text , xOfText+10, yOfText);
      
   }

   public boolean isNodeUnderCursor(int mouseX, int mouseY) {
      int a = x - mouseX;
      int b = y - mouseY;

      return a * a + b * b <= radius * radius;
   }

   public void moveNode(int distanceX, int distanceY) {
      x += distanceX;
      y += distanceY;
   }
   
   @Override
	public String toString() {
		return "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")" + ", Key: " + key + ", Text: " + text ;
	}
}
