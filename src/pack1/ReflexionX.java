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

public class ReflexionX {

	public ReflexionX() {
		// TODO Auto-generated constructor stub
	}
	
	public void exe() {
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
					
					File f =new File(path + "/ResultsX" + count + ".xml");
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
