import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.Scanner;
import pack1.Calcule;
public class CalculeTest {
Calcule cl = new Calcule();
Random rand = new Random();
int upperbound = 10;
@BeforeEach
public void setUp() throws Exception {
System.out.println("Qu'attendez vous de cette execution: ");
}
int random11 = rand.nextInt(upperbound);
int random21 = rand.nextInt(upperbound);
@Test
void Testsomme() {
System.out.println("somme("+ random11 + ","+ random21 + ")");
Scanner myObj1 = new Scanner(System.in);
int res1 = myObj1.nextInt();
assertEquals(res1,cl.somme(random11,random21));
}
int random12 = rand.nextInt(upperbound);
int random22 = rand.nextInt(upperbound);
@Test
void Testproduit() {
System.out.println("produit("+ random12 + ","+ random22 + ")");
Scanner myObj2 = new Scanner(System.in);
int res2 = myObj2.nextInt();
assertEquals(res2,cl.produit(random12,random22));
}
@Test
void TestTPReflexion() {
System.out.println("TPReflexion():");
Scanner myObj3 = new Scanner(System.in);
String res3 = myObj3.nextLine();
assertEquals(res3,cl.TPReflexion());
}

}