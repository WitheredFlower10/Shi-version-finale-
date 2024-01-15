import javax.swing.JOptionPane;
/**
 * Classe Game - le moteur du jeu d'aventure.
 * Cette classe est le moteur principal du jeu.
 * 
 * Pour jouer à ce jeu, créez une instance de cette classe.
 * 
 * Elle contient un attribut UserInterface qui permet de gérer l'interface utilisateur.
 * Elle contient un attribut GameEngine qui permet de gérer la logique du jeu.
 * Lorsqu'une instance de Game est créée, elle demande un pseudo à l'utilisateur grâce à la classe JOptionPane.
 * Ce pseudo est ensuite transmis au GameEngine pour initialiser le joueur.
 * Cette classe ne possède pas de méthode particulière, mais elle permet de lancer le jeu en créant une instance de GameEngine et de UserInterface et en les reliant entre eux.
 * 
 * @author HAKIM Justine
 * @version 15/02/2023
 */

public class Game
{   
    //Attributs
    private UserInterface aGui;
    private GameEngine aEngine;
    
    /**
     * Constructeur par défaut de la classe Game
     * Instancie le jeu
     */
    public Game()
    {
        //Demande un Pseudo à l'utilisateur 
        String vPseudo = "";
        while(vPseudo.length() == 0) {
            vPseudo = JOptionPane.showInputDialog("Quel est votre pseudo ?");
        }
        // Crée une instance de GameEngine avec le pseudo fourni
        this.aEngine = new GameEngine(vPseudo);
        // Crée une instance de UserInterface en utilisant le GameEngine créé
        this.aGui = new UserInterface( this.aEngine);
        // Définit l'interface utilisateur créée comme interface du GameEngine
        this.aEngine.setGUI( this.aGui );
    }//Game()
}//Game()
