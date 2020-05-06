package pack1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GenerateurTest {

	public GenerateurTest() {
		// TODO Auto-generated constructor stub
	}
	
	public void generer() {
		try {
			JFileChooser fi = new JFileChooser();
		    fi.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		    fi.showOpenDialog(null);
		     
		    String path = fi.getSelectedFile().toString();
		    
			File pack = new File(path+"/src/");

			String[] directories = pack.list(new FilenameFilter() {
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
					File cls = new File(pack+"/"+directories[i]+"/");
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
			
			for (int j = 0; j < nbPacks; j++) {
				
				List<String> lcc = new ArrayList<String>();
			
					File cls = new File(pack+"/"+directories[j]+"/");
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
					
					File ff = new File(path+"/bin/");
					URL[] cp = {ff.toURI().toURL()};
					URLClassLoader urlcl = new URLClassLoader(cp);
					Class c = urlcl.loadClass(directories[j]+"."+lcc.get(i).substring(0, lcc.get(i).lastIndexOf('.')));
					
					File f=new File(path + "/src/"+ c.getSimpleName() +"Test.java");
					BufferedWriter br = new BufferedWriter(new FileWriter(f,false));			
					
					br.write("import static org.junit.Assert.assertEquals;");
					br.newLine();
					br.write("import org.junit.jupiter.api.BeforeEach;");
					br.newLine();
					br.write("import org.junit.jupiter.api.Test;");
					br.newLine();
					br.write("import java.util.Random;");
					br.newLine();
					br.write("import java.util.Scanner;");
					br.newLine();
					br.write("import " + directories[j] + "." + c.getSimpleName() + ";");
					br.newLine();
					br.write("public class " + c.getSimpleName() + "Test {");
					br.newLine();
					br.write(c.getSimpleName() + " cl = new " + c.getSimpleName() + "();");
					br.newLine();
				      
					br.write("Random rand = new Random();");
					br.newLine();
					br.write("int upperbound = 10;");
					br.newLine();
					br.write("@BeforeEach");
					br.newLine();
					br.write("public void setUp() throws Exception {");
					br.newLine();
					br.write("System.out.println(\"Qu'attendez vous de cette execution: \");");
					br.newLine();
					br.write("}");
					br.newLine();
					
					Method[] declaredMethodes = c.getDeclaredMethods();
					int ii=1;
					
					for (Method meth : declaredMethodes) {
						int nbParam = meth.getParameterCount();
						if (nbParam == 0) {
							br.write("@Test");
							br.newLine();
							br.write("void Test" + meth.getName() + "() {");
							br.newLine();
							br.write("System.out.println(\""+meth.getName() + "():\");");
							br.newLine();
							br.write("Scanner myObj"+ ii +" = new Scanner(System.in);");
							br.newLine();
							if (meth.getReturnType().getSimpleName().equalsIgnoreCase("int")) {
								br.write("int res"+ii+" = myObj"+ ii +".nextInt();");
								br.newLine();
							}
							if (meth.getReturnType().getSimpleName().equalsIgnoreCase("string")) {
								br.write("String res"+ii+" = myObj"+ ii +".nextLine();");
								br.newLine();
							}
							br.write("assertEquals(res"+ii+ ",cl." + meth.getName()+"());");
							ii++;
							br.newLine();
							br.write("}");
							br.newLine();
							//--------------//
						}else {
							Class[] parameterTypes = meth.getParameterTypes();
							for (int k = 0; k < nbParam; k++) {
								if (parameterTypes[k].getSimpleName().equalsIgnoreCase("String") == false) {
									br.write(parameterTypes[k].getSimpleName() + " random" + (k+1) + (ii) + " = rand.nextInt(upperbound);");
									br.newLine();
								}
								if (parameterTypes[k].getSimpleName().equalsIgnoreCase("String")) {
								    br.write(parameterTypes[k].getSimpleName() + " random" + (k+1) + (ii) + " = \"HHBBCC\";");
								    br.newLine();
								}
							}
							br.write("@Test");
							br.newLine();
							br.write("void Test" + meth.getName() + "() {");
							br.newLine();
							br.write("System.out.println(\""+meth.getName() + "(\"");
							for (int k = 0; k < nbParam; k++) {
								if (k == (nbParam - 1)) {
									br.write("+ random"+ (k+1) + (ii) +" + ");
								}
								else {
									br.write("+ random"+ (k+1) + (ii) +" + \",\"");
								}
							}
							br.write("\")\");");
							br.newLine();
							br.write("Scanner myObj"+ ii +" = new Scanner(System.in);");
							br.newLine();
							if (meth.getReturnType().getSimpleName().equalsIgnoreCase("int")) {
								br.write("int res"+ii+" = myObj"+ ii +".nextInt();");
								br.newLine();
							}
							if (meth.getReturnType().getSimpleName().equalsIgnoreCase("string")) {
								br.write("String res"+ii+" = myObj"+ ii +".nextLine();");
								br.newLine();
							}
							br.write("assertEquals(res"+ii+ ",cl." + meth.getName()+"(");
							for (int k = 0; k < nbParam; k++) {
								if (k == (nbParam -1) ) {
									br.write("random"+ (k+1) + (ii));
								}else {
									br.write("random"+ (k+1) + (ii) + ",");
								}	
							}
							br.write("));");
							ii++;
							br.newLine();
							br.write("}");
							br.newLine();
						}	
					}
					br.newLine();
					br.write("}");
					br.close();
				}
			}
			
		} catch(Exception ex) {
			// TODO: handle exception
			System.out.println("Exception: " + ex.toString());
			ExceptionSave s = new ExceptionSave();
			s.save(ex);
		} finally {
			JOptionPane.showMessageDialog(null, "JUnit:\nPlease check /src/ dir. for results!");
			System.out.println("\n->JUnit generated!");
			
		}
	}

}
