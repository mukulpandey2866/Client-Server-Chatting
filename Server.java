import javax.swing.*; // importing swing components
import java.awt.event.*; //  importing java awt event components 
import java.awt.*; //   importing java awt                           
import java.util.*; //     importing java utilities
import java.io.*;
import java.net.*;

public class Server extends JFrame implements ActionListener, Runnable

{

  JButton b1, b2; // Buttons
  JFrame frame; // frame
  JTextField t1;
  JTextArea ta;
  int port = 8081;
  ServerSocket serverSocket;
  static Socket server;

  Server() // Invoking Constructor

  {

    frame = new JFrame("Server Interface");
    frame.setLayout(new FlowLayout());

    // TextFields

    b1 = new JButton("Submit");
    b2 = new JButton("Clear");
    t1 = new JTextField(30);
    ta = new JTextArea("  ", 2, 30);

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

      serverSocket = new ServerSocket(port);
      System.out.println("Waiting for client" +
          serverSocket.getLocalPort());
      server = serverSocket.accept();

    } catch (Exception e) {
      System.out.println("Connection Error");
    }

  }

  void clear() // function to clear everything after submit is pressed

  {
    t1.setText("");

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
      t1.setText("Button Invoke Error");
    }

  }

  public void run() {
    while (true) {
      try {
        BufferedReader get = new BufferedReader(new InputStreamReader(server.getInputStream()));

        String fname;

        fname = get.readLine();
        ta.setText(fname);

      } catch (Exception e) {
        System.out.println("Input Stream Error");
      }
    }

  }

  void insert() {

    try {

      System.out.println("Just connected and inside insert fn " + server.getRemoteSocketAddress());

      PrintWriter put = new PrintWriter(server.getOutputStream(), true);
      put.println(t1.getText());

      if (server == null)
        server.close();

    } catch (Exception e) {
      System.out.println("Exception in Sending Message");

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

    Server H = new Server();

    Thread t = new Thread(H);

    t.start();

  } // main ends

}
