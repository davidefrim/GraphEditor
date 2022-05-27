import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener {

   private Graph graph;
   private boolean mouseLeftButton = false;
   private int mouseX;
   private int mouseY;
   private Noduri nodeUnderCursor;
   private Muchii MuchiiUnderCursor;
   private boolean chooseNodeB = false;
   private Noduri newMuchiiNodeA;

   public GraphPanel(Graph g) {
      if (g == null) {
         graph = new Graph("Graf");
      } else {
         setGraph(g);
      }

      addMouseMotionListener(this);
      addMouseListener(this);
      setFocusable(true);
      requestFocus();
      setFont(new Font("Verdana", Font.BOLD, 16));
   }

   public void setGraph(Graph graph) {
      if (graph == null) {
         this.graph = new Graph("Graf");
      } else {
         this.graph = graph;
      }
   }

   @Override
   protected void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);

      if (graph != null) {
         graph.draw(graphics);
      }
   }

   public void createNewGraph() {
      setGraph(new Graph("Graf"));
      repaint();
   }
   
   public void showNodesList() {
		String nodesList = graph.getListOfNodes();
		JOptionPane.showMessageDialog(this, nodesList,"Nodes list", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showMuchiisList() {
		String nodesList = graph.getListOfMuchiis();
		JOptionPane.showMessageDialog(this, nodesList,"Muchiis list", JOptionPane.INFORMATION_MESSAGE);
	}

   @Override
   public void mouseDragged(MouseEvent event) {
      if (mouseLeftButton) {
         moveGraphDrag(event.getX(), event.getY());
      } else {
         setMouseCursor(event);
      }
   }

   @Override
   public void mouseMoved(MouseEvent event) {
      setMouseCursor(event);
   }

   @Override
   public void mouseClicked(MouseEvent event) {
   }

   @Override
   public void mouseEntered(MouseEvent event) {
   }

   @Override
   public void mouseExited(MouseEvent event) {
   }

   @Override
   public void mousePressed(MouseEvent event) {
      if (event.getButton() == MouseEvent.BUTTON1) {
         mouseLeftButton = true;
      }
      setMouseCursor(event);
   }

   @Override
   public void mouseReleased(MouseEvent event) {
      if (event.getButton() == MouseEvent.BUTTON1) {
         mouseLeftButton = false;
         finalizeAddMuchii();
      }

      if (event.getButton() == MouseEvent.BUTTON3) {
         chooseNodeB = false;
         if (nodeUnderCursor != null) {
            createNodePopupMenu(event, nodeUnderCursor);
         } else if (MuchiiUnderCursor != null) {
            createMuchiiPopupMenu(event, MuchiiUnderCursor);
         } else {
            createPlainPopupMenu(event);
         }
      }
      setMouseCursor(event);
   }

   private void createPlainPopupMenu(MouseEvent event) {
      JPopupMenu popupMenu = new JPopupMenu();
      JMenuItem newNodeMenuItem = new JMenuItem("New node");
      popupMenu.add(newNodeMenuItem);
      newNodeMenuItem.addActionListener((action) -> createNewNode(event.getX(), event.getY()));
      popupMenu.show(event.getComponent(), event.getX(), event.getY());
   }

   private void createNodePopupMenu(MouseEvent event, Noduri node) {
      JPopupMenu popupMenu = new JPopupMenu();
      JMenuItem removeNodeMenuItem = new JMenuItem("Remove node");
      popupMenu.add(removeNodeMenuItem);
      removeNodeMenuItem.addActionListener((action) -> removeNode(node));

      popupMenu.addSeparator();

      JMenuItem addMuchiiMenuItem = new JMenuItem("Add Muchii");
      popupMenu.add(addMuchiiMenuItem);
      addMuchiiMenuItem.addActionListener((action) -> initializeAddMuchii(node));

      if (nodeUnderCursor != null) {
         popupMenu.addSeparator();

         JMenuItem changeNodeRadiusMenuItem = new JMenuItem("Change node size");
         popupMenu.add(changeNodeRadiusMenuItem);
         changeNodeRadiusMenuItem.addActionListener((action) -> changeNodeRadius(node));

         
         JMenuItem changeKeyMenuItem = new JMenuItem("Change node key");
         popupMenu.add(changeKeyMenuItem);
         changeKeyMenuItem.addActionListener((action) -> changeNodeKey(node));

         JMenuItem changeNodeColor = new JMenuItem("Change node color");
         popupMenu.add(changeNodeColor);
         changeNodeColor.addActionListener((action) -> changeNodeColor(node));
      }
      popupMenu.show(event.getComponent(), event.getX(), event.getY());
   }


   private void createMuchiiPopupMenu(MouseEvent event, Muchii Muchii) {
      JPopupMenu popupMenu = new JPopupMenu();
      JMenuItem removeMuchiiMenuItem = new JMenuItem("Remove Muchii");
      popupMenu.add(removeMuchiiMenuItem);
      removeMuchiiMenuItem.addActionListener((action) -> removeMuchii(Muchii));

      if (Muchii != null) {
         popupMenu.addSeparator();


         JMenuItem changeMuchiiTextMenuItem = new JMenuItem("Change Muchii key");
         popupMenu.add(changeMuchiiTextMenuItem);
         changeMuchiiTextMenuItem.addActionListener((action) -> changeMuchiiKey(Muchii));

         
        
      }
      popupMenu.show(event.getComponent(), event.getX(), event.getY());
   }

   public void setMouseCursor(MouseEvent event) {
      if (event != null) {
         nodeUnderCursor = graph.findNodeUnderCursor(event.getX(), event.getY());
         if (nodeUnderCursor == null) {
            MuchiiUnderCursor = graph.findMuchiiUnderCursor(event.getX(), event.getY());
         }
         mouseX = event.getX();
         mouseY = event.getY();
      }

      int mouseCursor;
      if (nodeUnderCursor != null) {
         mouseCursor = Cursor.HAND_CURSOR;
      } else if (MuchiiUnderCursor != null) {
         mouseCursor = Cursor.CROSSHAIR_CURSOR;
         if (event != null) {
            MuchiiUnderCursor = graph.findMuchiiUnderCursor(event.getX(), event.getY());
         }
         setToolTipText(MuchiiUnderCursor.toString());
      } else if (chooseNodeB) {
         mouseCursor = Cursor.WAIT_CURSOR;
      } else if (mouseLeftButton) {
         mouseCursor = Cursor.MOVE_CURSOR;
      } else {
         mouseCursor = Cursor.DEFAULT_CURSOR;
      }
      setCursor(Cursor.getPredefinedCursor(mouseCursor));
   }

   private void moveGraphDrag(int mouseX, int mouseY) {
      int dragX = mouseX - this.mouseX;
      int dragY = mouseY - this.mouseY;

      if (nodeUnderCursor != null) {
         nodeUnderCursor.moveNode(dragX, dragY);
      } else if (MuchiiUnderCursor != null) {
         MuchiiUnderCursor.moveMuchii(dragX, dragY);
      } else {
         graph.moveGraph(dragX, dragY);
      }

      this.mouseX = mouseX;
      this.mouseY = mouseY;
      repaint();
   }

   private void createNewNode(int mouseX, int mouseY) {
      Color color = JColorChooser.showDialog(this, "Choose color", Color.WHITE);
      int radius = (Integer) JOptionPane.showInputDialog(this, "Choose size", "New node",
              JOptionPane.PLAIN_MESSAGE, null, Noduri.RADIUS_VALUES, Noduri.RADIUS_VALUES[0]);
      
      String key = JOptionPane.showInputDialog(this, "Input key:", "New key",
              JOptionPane.QUESTION_MESSAGE);
      graph.addNode(new Noduri(mouseX, mouseY, color, radius, key));
      repaint();
   }

   private void removeNode(Noduri node) {
      graph.removeNode(node);
      repaint();
   }

   private void initializeAddMuchii(Noduri node) {
      if (nodeUnderCursor != null) {
         newMuchiiNodeA = node;
         chooseNodeB = true;
         setMouseCursor(null);
      }
   }

   private void finalizeAddMuchii() {
      if (chooseNodeB) {
         if (nodeUnderCursor != null) {
            if (nodeUnderCursor.equals(newMuchiiNodeA)) {
               JOptionPane.showMessageDialog(this, "Choose different node!", "Error!",
                       JOptionPane.ERROR_MESSAGE);
            } else {
               try {
                  Noduri newMuchiiNodeB = nodeUnderCursor;
                  Color color = JColorChooser.showDialog(this, "Choose color", Color.BLACK);
                  
                  String text = JOptionPane.showInputDialog(this, "Input text key:", "New Muchii",
                          JOptionPane.QUESTION_MESSAGE);
                  graph.addMuchii(new Muchii(newMuchiiNodeA, newMuchiiNodeB, text));
                  repaint();
               } catch (NullPointerException exception) {
                  JOptionPane.showMessageDialog(this, "Operation canceled.", "Canceled",
                          JOptionPane.INFORMATION_MESSAGE);
               }
            }
         }
         chooseNodeB = false;
      }
   }

   private void removeMuchii(Muchii Muchii) {
      graph.removeMuchii(Muchii);
      repaint();
   }

   private void changeNodeRadius(Noduri node) {
      try {
         int radius = (Integer) JOptionPane.showInputDialog(this, "Choose radius:",
                 "Edit node", JOptionPane.QUESTION_MESSAGE, null, Noduri.RADIUS_VALUES, Noduri.RADIUS_VALUES[0]);
         node.setRadiusOfNode(radius);
         repaint();
      } catch (ClassCastException exception) {
         JOptionPane.showMessageDialog(this, "This node cannot have different radius.",
                 "Error!", JOptionPane.ERROR_MESSAGE);
      } catch (NullPointerException exception) {
         JOptionPane.showMessageDialog(this, "Operation canceled.", "Canceled",
                 JOptionPane.INFORMATION_MESSAGE);
      }
   }

   private void changeNodeText(Noduri node) {
      String text = JOptionPane.showInputDialog(this, "Input text:", "Edit node",
              JOptionPane.QUESTION_MESSAGE);
      try {
         node.setTextOfNode(text);
         repaint();
      } catch (NullPointerException exception) {
         JOptionPane.showMessageDialog(this, "Operation canceled.", "Canceled",
                 JOptionPane.INFORMATION_MESSAGE);
      }
   }
   
   private void changeNodeKey(Noduri node) {
	      String key = JOptionPane.showInputDialog(this, "Input key:", "Edit node",
	              JOptionPane.QUESTION_MESSAGE);
	      try {
	         node.setKeyOfNode(key);
	         repaint();
	      } catch (NullPointerException exception) {
	         JOptionPane.showMessageDialog(this, "Operation canceled.", "Canceled",
	                 JOptionPane.INFORMATION_MESSAGE);
	      }
	   }

   private void changeNodeColor(Noduri node) {
      Color color = JColorChooser.showDialog(this, "Choose color", Color.BLACK);
      try {
         node.setColorOfNode(color);
         repaint();
      } catch (NullPointerException exception) {
         JOptionPane.showMessageDialog(this, "Operation canceled.", "Canceled",
                 JOptionPane.INFORMATION_MESSAGE);
      }
   }


   private void changeMuchiiKey(Muchii Muchii) {
      try {
         String key = JOptionPane.showInputDialog(this, "Input text key:", "Edit Muchii",
                 JOptionPane.QUESTION_MESSAGE);
         Muchii.setKeyOfMuchii(key);
         repaint();
      } catch (ClassCastException exception) {
         JOptionPane.showMessageDialog(this, "Aceste Muchii nu pot avea un text diferit.",
                 "Error!", JOptionPane.INFORMATION_MESSAGE);
      } catch (NullPointerException exception) {
         JOptionPane.showMessageDialog(this, "Operation canceled.", "Canceled",
                 JOptionPane.INFORMATION_MESSAGE);
      }
   }
}
   
