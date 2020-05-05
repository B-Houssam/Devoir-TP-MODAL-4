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
				try {
				    
				    JFileChooser fii = new JFileChooser();
				    fii.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
				    fii.showOpenDialog(null);
				     
				    String pathh = fii.getSelectedFile().toString();
					
					
					File filee = new File(pathh);
					String project = filee.getName();
					File packss = new File(pathh+"/src/");

					String[] directories = packss.list(new FilenameFilter() {
					  @Override
					  public boolean accept(File current, String name) {
					    return new File(current, name).isDirectory();
					  }
					});
					int nbPacks = directories.length;
					//System.out.println(Arrays.toString(directories));
					
					
					//pour recuperer le nombre total des classes dans tout les packages
					List<String> lc = new ArrayList<String>();
					for (int i = 0; i < nbPacks; i++) {
						File cls = new File(packss+"/"+directories[i]+"/");
						String[] classes = cls.list(new FilenameFilter() {
							  @Override
							  public boolean accept(File current, String name) {
							    return new File(current, name).isFile();
							  }
							});
						for (int j = 0; j < classes.length; j++) {
							lc.add(classes[j]);
						}
					}	
					
					/*
					
					*/
					
					File f=new File(System.getProperty("user.dir") + "/Results.txt");
					BufferedWriter br = new BufferedWriter(new FileWriter(f,false));
					br.write("-------------------------------------------------------------------------------------------------------------------------------------");			
					br.newLine();
					br.write("Description textuelle -- Date : "+ new Date());
					br.newLine();
					br.write("-------------------------------------------------------------------------------------------------------------------------------------");			
					br.newLine();
					br.write("Information projet");
					br.newLine();
					br.write("-------------------------------------------------------------------------------------------------------------------------------------");			
					br.newLine();
					br.write("Nom du projet: "+project);
					br.newLine();
					br.write("Nombre de packages: "+nbPacks);
					br.newLine();
					br.write("Nombre de classe: "+lc.size());
					br.newLine();
					br.write("Java version: "+System.getProperty("java.version"));
					br.newLine();
					br.write("OS: "+System.getProperty("os.name"));
					br.newLine();
					br.write("-------------------------------------------------------------------------------------------------------------------------------------");
					br.newLine();
					br.write("Informations classes");
					br.newLine();
					br.write("-------------------------------------------------------------------------------------------------------------------------------------");			
					int count=1;
					
					for (int j = 0; j < nbPacks; j++) {
						
						List<String> lcc = new ArrayList<String>();
					
							File cls = new File(packss+"/"+directories[j]+"/");
							String[] classes = cls.list(new FilenameFilter() {
								  @Override
								  public boolean accept(File current, String name) {
								    return new File(current, name).isFile();
								  }
								});
							for (int jj = 0; jj < classes.length; jj++) {
								lcc.add(classes[jj]);
							}
						
						
						
						for (int i = 0; i <lcc.size() ; i++) {
							
							File ff = new File(pathh+"/bin/");
							URL[] cp = {ff.toURI().toURL()};
							URLClassLoader urlcl = new URLClassLoader(cp);
							Class c = urlcl.loadClass(directories[j]+"."+lcc.get(i).substring(0, lcc.get(i).lastIndexOf('.')));
							
											
							br.newLine();
							br.write("Classe "+ count + ":");
							br.newLine();
							br.write("Nom Package: "+ c.getPackage().getName());
							br.newLine();
							br.write("Nom Class: "+ c.getSimpleName());
							br.newLine();
							br.write("Nombre des attributs publics: "+ c.getFields().length);
							br.newLine();
							br.write("Liste et types des attributs publics: [ ");
							Field[] fields = c.getFields();
							for (Field oneField : fields) {
								Field field = c.getField(oneField.getName());
								br.write(field.getName() + " - " + field.getType().getSimpleName() + ", ");
							}
							br.write("]");
							br.newLine();					
							br.write("Nombre des attributs privés: "+(c.getDeclaredFields().length - c.getFields().length));
							br.newLine();
							br.write("Liste et types des attributs privés : [ ");	
							List<Field> pl = new ArrayList<Field>(); 
							Field[] pFields = c.getDeclaredFields();
							Field[] ppFields = c.getFields();
							for (Field oneField : pFields) {
								pl.add(oneField);
							}
							for (Field oneField : ppFields) {
								pl.remove(oneField);
							}
							for (int k = 0; k < pl.size(); k++) {
								Field privateField = pl.get(k);
								br.write(privateField.getName() + " - " + privateField.getType().getSimpleName() + ", ");
							}
							br.write("]");
							br.newLine();
							br.write("Liste des constructeurs: [ ");
							Constructor[] constructor = c.getDeclaredConstructors();
							for (Constructor oneCon : constructor) {
								br.write(oneCon.getName() + " ");
							}
							br.write("]");
							br.newLine();
							Method[] declaredMethodes = c.getDeclaredMethods();
							br.write("Nombre de méthodes: " + declaredMethodes.length);
							br.newLine();
							br.write("Liste des méthodes: [ ");
							for (Method meth : declaredMethodes) {
								br.write(meth.getName() + ", ");
							}
							br.write("]");
							br.newLine();
							br.write("Type de retour des méthodes: [ ");
							for (Method meth : declaredMethodes) {
								br.write(meth.getName() + ": " + meth.getReturnType().getSimpleName() +", ");
							}
							br.write("]");
							br.newLine();
							br.write("Paramètres et types des méthodes: ");
							for (Method meth : declaredMethodes) {
								br.write(meth.getName()+"[");
								Class[] parameterTypes = meth.getParameterTypes();
								for (int k = 0; k < parameterTypes.length; k++) {
									br.write("param" + (k+1) + ": " + parameterTypes[k].getSimpleName() +", ");
								}
								br.write("], ");
							}
							br.newLine();
							br.write("-------------------------------------------------------------------------------------------------------------------------------------");					
							count++;
						}
					}
					
					br.close();
				    
				} catch(Exception ex) {
					// TODO: handle exception
					System.out.println("Exception: " + ex.toString());
					ExceptionSave s = new ExceptionSave();
					s.save(ex);
				} finally {
					JOptionPane.showMessageDialog(null, ".txt:\n Please check Source folder for exceptions or Results!");
					System.out.println("\n->.txt generated!");
				}
				
			}
	}
	
	static class Action2 implements ActionListener {   
		public void actionPerformed (ActionEvent e) {    
			try {
				JFileChooser fi = new JFileChooser();
			    fi.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
			    fi.showOpenDialog(null);
			     
			    String path = fi.getSelectedFile().toString();
			    
			    File file = new File(path);
				String project = file.getName();
				File packs = new File(path+"/src/");
				
				//on recupere tts les packages du projet
				String[] directories = packs.list(new FilenameFilter() {
				  @Override
				  public boolean accept(File current, String name) {
				    return new File(current, name).isDirectory();
				  }
				});
				int nbPacks = directories.length;
				//System.out.println(Arrays.toString(directories));
				
				
				//pour recuperer le nombre total des classes dans tout les packages
				List<String> lc = new ArrayList<String>();
				for (int i = 0; i < nbPacks; i++) {
					File cls = new File(packs+"/"+directories[i]+"/");
					String[] classes = cls.list(new FilenameFilter() {
						  @Override
						  public boolean accept(File current, String name) {
						    return new File(current, name).isFile();
						  }
						});
					for (int j = 0; j < classes.length; j++) {
						lc.add(classes[j]);
					}
				}	
						
				int count=1;
				
				for (int j = 0; j < nbPacks; j++) {
					
					List<String> lcc = new ArrayList<String>();
				
						File cls = new File(packs+"/"+directories[j]+"/");
						String[] classes = cls.list(new FilenameFilter() {
							  @Override
							  public boolean accept(File current, String name) {
							    return new File(current, name).isFile();
							  }
							});
						for (int jj = 0; jj < classes.length; jj++) {
							lcc.add(classes[jj]);
						}
					
					
					
					for (int i = 0; i <lcc.size() ; i++) {
						
						File f =new File(System.getProperty("user.dir") + "/Results" + count + ".xml");
						BufferedWriter bw = new BufferedWriter(new FileWriter(f,false));
						
						File ff = new File(path+"/bin/");
						URL[] cp = {ff.toURI().toURL()};
						URLClassLoader urlcl = new URLClassLoader(cp);
						Class c = urlcl.loadClass(directories[j]+"."+lcc.get(i).substring(0, lcc.get(i).lastIndexOf('.')));
						
						
						bw.write("<DateTime>");
						bw.write(new Date().toString());
						bw.write("</DateTime>");
						bw.newLine();
						bw.write("<ProjectName>");
						bw.newLine();
						bw.write(""+project);
						bw.newLine();
						bw.write("</ProjectName>");
						bw.newLine();
						bw.write("<JavaVersion>");
						bw.newLine();
						bw.write(System.getProperty("java.version"));
						bw.newLine();
						bw.write("</JavaVersion>");
						bw.newLine();
						bw.write("<OS>");
						bw.newLine();
						bw.write(System.getProperty("os.name"));
						bw.newLine();
						bw.write("</OS>");
						bw.newLine();
						bw.write("<Class>");
						bw.newLine();
						bw.write("<ClassName>");
						bw.newLine();
						bw.write(c.getSimpleName());
						bw.newLine();
						bw.write("</ClassName>");
						bw.newLine();
						bw.write("<PackageName>");
						bw.newLine();
						bw.write(c.getPackage().getName());
						bw.newLine();
						bw.write("</PackageName>");
						bw.newLine();
						bw.write("<nbAttributsPubliques>");
						bw.newLine();
						bw.write(""+c.getFields().length);
						bw.newLine();
						bw.write("</nbAttributsPubliques>");
						bw.newLine();
						bw.write("<NomsTypesAttributs>");
						bw.newLine();
						
						Field[] fields = c.getFields();
						for (Field oneField : fields) {
							Field field = c.getField(oneField.getName());
							bw.write("<Attribut>");
							bw.newLine();
							bw.write("<NomAttribut>");
							bw.newLine();
							bw.write(field.getName());
							bw.newLine();
							bw.write("</NomAttribut>");
							bw.newLine();
							bw.write("<TypeAttribut>");
							bw.newLine();
							bw.write(field.getType().getSimpleName());
							bw.newLine();
							bw.write("</TypeAttribut>");
							bw.newLine();
							bw.write("</Attribut>");
							bw.newLine();
						}
						
						bw.write("</NomsTypesAttributs>");
						bw.newLine();
						bw.write("<nbAttributsPrivés>");
						bw.newLine();
						bw.write(""+(c.getDeclaredFields().length - c.getFields().length));
						bw.newLine();
						bw.write("</nbAttributsPrivés>");
						bw.newLine();
						bw.write("<NomsTypesAttributsPrivés>");
						bw.newLine();
						
						List<Field> pl = new ArrayList<Field>(); 
						Field[] pFields = c.getDeclaredFields();
						Field[] ppFields = c.getFields();
						for (Field oneField : pFields) {
							pl.add(oneField);
						}
						for (Field oneField : ppFields) {
							pl.remove(oneField);
						}
						for (int k = 0; k < pl.size(); k++) {
							Field privateField = pl.get(k);
							bw.write("<AttributPrivé>");
							bw.newLine();
							bw.write("<NomAttribut>");
							bw.newLine();
							bw.write(privateField.getName());
							bw.newLine();
							bw.write("</NomAttribut>");
							bw.newLine();
							bw.write("<TypeAttribut>");
							bw.newLine();
							bw.write(privateField.getType().getSimpleName());
							bw.newLine();
							bw.write("</TypeAttribut>");
							bw.newLine();
							bw.write("</AttributPrivé>");
							bw.newLine();
						}
						
						bw.write("</NomsTypesAttributsPrivés>");
						bw.newLine();
						bw.write("<ListConstructeurs>");
						bw.newLine();
						
						Constructor[] constructor = c.getDeclaredConstructors();
						for (Constructor oneCon : constructor) {
							bw.write("<Constructor>");
							bw.newLine();
							bw.write(oneCon.getName());
							bw.newLine();
							bw.write("</Constructor>");
							bw.newLine();
						}
						
						bw.write("</ListConstructeurs>");
						bw.newLine();
						
						Method[] declaredMethodes = c.getDeclaredMethods();
						
						bw.write("<nbMethodes>");
						bw.newLine();
						bw.write(""+declaredMethodes.length);
						bw.newLine();
						bw.write("</nbMethodes>");
						bw.write("<NomTypeRetourMethodes>");
						bw.newLine();

						for (Method meth : declaredMethodes) {
							Class[] parameterTypes = meth.getParameterTypes();
							
							bw.write("<Methode>");
							bw.newLine();
							bw.write("<NomMethode>");
							bw.newLine();
							bw.write(meth.getName());
							bw.newLine();
							bw.write("</NomMethode>");
							bw.newLine();
							bw.write("<TypeRetourMethode>");
							bw.newLine();
							bw.write(meth.getReturnType().getSimpleName());
							bw.newLine();
							bw.write("</TypeRetourMethode>");
							bw.newLine();
							bw.write("<nbParametres>");
							bw.newLine();
							bw.write("" + parameterTypes.length);
							bw.newLine();
							bw.write("</nbParametres>");
							bw.write("<ParametresMethodes>");
							bw.newLine();
							
							
							for (int k = 0; k < parameterTypes.length; k++) {
								bw.write("<TypeParametre"+(k+1)+">");
								bw.newLine();
								bw.write(parameterTypes[k].getSimpleName());
								bw.newLine();
								bw.write("</TypeParametre"+(k+1)+">");
								bw.newLine();
							}
						
							bw.write("</ParametresMethodes>");
							bw.newLine();
							bw.write("</Methode>");
							bw.newLine();
						}
						
						bw.write("</NomTypeRetourMethodes>");
						bw.newLine();		
						bw.write("</Class>");

						count++;
						bw.close();
					}
				}	
			    
			} catch(Exception ex) {
				// TODO: handle exception
				System.out.println("Exception: " + ex.toString());
				ExceptionSave s = new ExceptionSave();
				s.save(ex);
			} finally {
				JOptionPane.showMessageDialog(null, ".xml:\n Please check Source folder for exceptions or Results!");
				/*
				File file = new File (System.getProperty("user.dir"));
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(file.toURI());;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.save();
				}
				*/
				System.out.println("\n->.xml generated!");
			}
			
		}
}
	
	static class Action3 implements ActionListener {   
		public void actionPerformed (ActionEvent e) {    
			try {
				JFileChooser fi = new JFileChooser();
			    fi.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
			    fi.showOpenDialog(null);
			     
			    String path = fi.getSelectedFile().toString();
			    
			    System.out.println(path);
			    
			} catch(Exception ex) {
				// TODO: handle exception
				System.out.println("Exception: " + ex.toString());
				ExceptionSave s = new ExceptionSave();
				s.save(ex);
			} finally {
				JOptionPane.showMessageDialog(null, "JUnit:\n Please check Source folder for exceptions or Results!");
				System.out.println("\n->JUnit generated!");
			}
			
		}
} 

}
