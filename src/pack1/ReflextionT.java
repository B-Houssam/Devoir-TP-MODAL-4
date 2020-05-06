package pack1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ReflextionT {

	public ReflextionT() {
		// TODO Auto-generated constructor stub
	}

	public void exec() {
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
			
			File f=new File(pathh + "/ResultsT.txt");
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
