import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GraphEditorPage extends JFrame implements ActionListener {

	
   private static final String TITLE = "Graph Editor app";
   private final GraphPanel graphPanel = new GraphPanel(null);
   private final JMenuBar menuBar = new JMenuBar();
   private final JMenu fileMenu = new JMenu("New graph");
   private final JMenuItem newGraphMenuItem = new JMenuItem("Create new graph");

   private final JMenu graphMenu = new JMenu("Noduri & Muchii");
   private final JMenuItem nodesMenuItem = new JMenuItem("Show list of nodes");
   private final JMenuItem MuchiisMenuItem = new JMenuItem("Show list of Muchiis");
	
   public GraphEditorPage() {
      super(TITLE);
      setSize(800, 600);
      setResizable(true);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setContentPane(graphPanel);
      setFont(new Font("Arial", Font.BOLD, 20));
      UIManager.put("OptionPane.messageFont", new Font("Monospaced", Font.BOLD, 16));
      addActionListeners();
      createMenuBar();
      setVisible(true);
   }

   private void addActionListeners() {
      newGraphMenuItem.addActionListener(this);
      nodesMenuItem.addActionListener(this);
      MuchiisMenuItem.addActionListener(this);
   }

   private void createMenuBar() {
      fileMenu.add(newGraphMenuItem);
      menuBar.add(fileMenu);
      setJMenuBar(menuBar);
	  graphMenu.add(nodesMenuItem);
	  graphMenu.add(MuchiisMenuItem);
	  menuBar.add(graphMenu);
   }

   @Override
   public void actionPerformed(ActionEvent event) {
      Object eventSource = event.getSource();

      if (eventSource == newGraphMenuItem) {
         graphPanel.createNewGraph();
      }
      
      if(eventSource == nodesMenuItem) {
			graphPanel.showNodesList();
		}
		
		if(eventSource == MuchiisMenuItem) {
			graphPanel.showMuchiisList();
		}
   }
}
