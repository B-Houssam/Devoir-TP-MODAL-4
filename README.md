# Devoir-TP-MODAL-4

travail demandé:

Écrire un programme Java (Générateur des tests unitaires) qui permet (par l’utilisation de la réflexion) la création des classes et des méthodes de tests unitaires (selon JUnit) pour un projet Java quelconque (Un projet Java contient plusieurs classes Java).

Remarque :
-Les valeurs attendues sont remplacées par le symbole "?" dans les assertions "assertEquals".
-Les paramètres des méthodes sont générés aléatoirement selon leurs types.

Exemple :
---------------------------------------------------------------------------------------------------------------------------------------
Classe Java : Calcule.java
---------------------------------------------------------------------------------------------------------------------------------------
public class Calcule {
public Calcule() {super();}
public int somme(int num1,int num2){ return num1+num2;}
public int produit(int num1,int num2){ return num1*num2;}
public String TPReflexion(String nameFile) { return "Class - Package [pack]..";}
}
---------------------------------------------------------------------------------------------------------------------------------------
Classe et méthodes de test générées à partir de la classe Calcule.java
---------------------------------------------------------------------------------------------------------------------------------------
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CalculeTest {
Calcule cal=new Calcule();
@Test
void Testsomme() {assertEquals(?,cal.somme(5,5));}
@Test
void Testproduit() {assertEquals( ?,cal.produit(5,5));}
@Test
void TestTPReflexion() {
assertEquals(?,cal.TPReflexion("TP.java"));}
}
---------------------------------------------------------------------------------------------------------------------------------------

Ce programme doit être intégré avec les parties précédentes. Utiliser le menu ci-dessous pour le choix et la sélection des parties :

Menu :
1- Générer la description textuelle du projet.
2- Générer la description XML du projet.
2- Générer les classes et les méthodes de tests de projet.

Le programme à réaliser doit prendre en considération la gestion des exceptions. 
