import javax.swing.*; // importing swing components
import java.awt.event.*; //  importing java awt event components 
import java.awt.*; //   importing java awt                           
import java.util.*; //     importing java utilities
import java.io.*;
import java.net.*;

// GUI for Application

public class Client extends JFrame implements ActionListener, Runnable

{

  JButton b1, b2; // Buttons
  JFrame frame; // frame
  JTextField t1;

  static public JTextArea ta;
  static Socket client;
  String serverName = "47.15.208.231";
  int port = 23440;

  Client() // Invoking Constructor

  {

    frame = new JFrame("Client Interface");
    frame.setLayout(new FlowLayout());

    // TextFields

    ta = new JTextArea(" ", 2, 30);
    b1 = new JButton("Submit");
    b2 = new JButton("Clear");
    t1 = new JTextField(30);

    frame.add(t1);
    frame.add(b1);
    frame.add(b2);
    frame.add(ta);
    b1.addActionListener(this);
    b2.addActionListener(this);

    frame.setVisible(true);
    frame.setSize(500, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    try {

      System.out.println("Connecting to " + serverName + " on port " + port);

      client = new Socket(serverName, port);

      System.out.println("Just connected to " + client.getRemoteSocketAddress());
    } catch (Exception e) {
      System.out.println("Connection Error");
    }

  }

  void clear() // function to clear everything after submit is pressed

  {
    t1.setText("");

  }

  public void run() {

    while (true) {
      try {
        BufferedReader get = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String fname;

        fname = get.readLine();
        ta.setText(fname);
      } catch (Exception ee) {
        System.out.println("Input Stream Error");
      }
    }

  }

  public void actionPerformed(ActionEvent e) // action performed method
  {

    try {

      if (e.getSource() == b1) {
        insert();
      }

      else if (e.getSource() == b2) {
        clear();
      }

    } catch (Exception x) {
      System.out.println(x);

      t1.setText("Button method Error");
    }

  }

  void insert() {

    try {

      PrintWriter put = new PrintWriter(client.getOutputStream(), true);
      put.println(t1.getText());

      if (client == null)
        client.close();

    } catch (Exception e) {
      System.out.println(" Exception from Chat");
      // e.printStackTrace();
    }

  }

  public static void main(String args[]) // main method to perform operation

  {

    try {

      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // changing window look and feel

    }

    catch (Exception e) {

      System.out.println("Look and Feel not set");

    }

    Client H = new Client();
    Thread tt = new Thread(H);

    tt.start();

  } // main ends

}
