package pack1;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Desktop;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * author Bousri Houssam
 */


public class P {

	public P() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			  JFrame frame = new JFrame("TP Tests Unitaires avec JUNIT");
			  
			  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			  JPanel panel = new JPanel();
			  
			  panel.setLayout(new FlowLayout(FlowLayout.CENTER));
			  JLabel label = new JLabel("<html>Bienvenue, Veillez faire un choix:<br><br>1- Description textuelle du projet.<br>2- Description XML du projet.<br>3- Générer les tests du projet.<br></html>");
			 
			  panel.add(label);
			  
			  JButton button = new JButton(".txt");
			  panel.add(button);
			  button.addActionListener (new Action1());
			  
			  JButton button2 = new JButton(".xml");
			  panel.add(button2);
			  button2.addActionListener (new Action2());

			  JButton button3 = new JButton("JUnit");
			  panel.add(button3);
			  button3.addActionListener (new Action3());
			  
			  frame.add(panel);
			  frame.pack();
			  frame.setVisible(true);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception: " + e.toString());
			ExceptionSave s = new ExceptionSave();
			s.save(e);
		}
	}
	
	static class Action1 implements ActionListener {   
			public void actionPerformed (ActionEvent e) {    
				ReflextionT RefT = new ReflextionT();
				RefT.exec();
			}
	}
	
	static class Action2 implements ActionListener {   
		public void actionPerformed (ActionEvent e) {    
			ReflexionX RefX = new ReflexionX();
			RefX.exe();
		}
}
	
	static class Action3 implements ActionListener {   
		public void actionPerformed (ActionEvent e) {    
			GenerateurTest gen = new GenerateurTest();
			gen.generer();
		}
} 

}
