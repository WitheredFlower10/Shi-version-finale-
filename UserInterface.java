import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.KeyStroke;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.KeyboardFocusManager; 

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.net.URL;

/**
 * Cette classe met en œuvre une interface utilisateur graphique simple avec une zone de saisie de texte, une zone de sortie de texte et une image optionnelle.
 * Les utilisateurs peuvent entrer des commandes pour jouer. Les commandes sont interprétées par un objet GameEngine.
 * 
 * Les commandes peuvent également être entrées via les touches fléchées et d'autres touches définies dans la méthode keyPressed.
 * Les méthodes print, println, showImage et enable sont utilisées pour afficher les résultats et les images des commandes.
 *
 *
 * @author HAKIM Justine
 * @version 26/03/2023
 */
public class UserInterface implements ActionListener, KeyListener
{
    // Variables d'instance
    private GameEngine aEngine;
    private JFrame     aMyFrame;
    private JTextField aEntryField;
    private JTextArea  aLog;
    private JLabel     aImage;
    private JPanel aPanelEast;
    private JPanel aPanelWest;
    private JPanel aPanelCenter;
    private JButton aButtonRegarder;
    private JButton aButtonInventaire;
    private JButton aButtonReculer;
    private JButton aButtonAide;
    private JButton aButtonQuitter;
    private JButton aButtonEst;
    private JButton aButtonOuest;
    private JButton aButtonSud;
    private JButton aButtonNord;
    private JButton aButtonVide1;
    private JButton aButtonVide2;
    private JButton aButtonMontée;
    private JButton aButtonDescente;
    private JButton aButtonCarte;
    private JButton aButtonGagner;

    
    /**
     * Constructeur d'objets de classe UserInterface
     * Construire une interface utilisateur. 
     * En tant que paramètre, un moteur de jeu (un objet qui traite et exécute les commandes du jeu) est nécessaire.
     * 
     * @param pGameEngine L'objet GameEngine implementant la logique du jeu.
     */
    public UserInterface(final GameEngine pGameEngine)
    {
        // initialisation des variables d'instance
        this.aEngine = pGameEngine;
        this.createGUI();
        this.aMyFrame.setFocusable(true);
        this.aMyFrame.requestFocusInWindow();
        this.aMyFrame.addKeyListener(this);
        this.aMyFrame.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.singleton(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0)));
    }//userInterface()

    /**
     * Imprimez un texte dans la zone de texte.  
     * 
     * @param pText Un texte saisi 
     */
    public void print( final String pText )
    {
        this.aLog.append( pText );
        this.aLog.setCaretPosition( this.aLog.getDocument().getLength() );
    } // print()
    
    /**
     * Imprimez du texte dans la zone de texte, suivi d'un saut de ligne.
     * 
     * @param pText Un texte saisi
     */
    public void println( final String pText )
    {
        this.print( pText + "\n" );
    } // println()
    
    /**
     * Afficher un fichier image dans l'interface.
     * 
     * @param pImageName Le nom de l'image
     */
    public void showImage( final String pImageName )
    {
        String vImagePath = "" + pImageName; // to change the directory
        URL vImageURL = this.getClass().getClassLoader().getResource( vImagePath );
        if ( vImageURL == null )
            System.out.println( "Image not found : " + vImagePath );
        else {
            ImageIcon vIcon = new ImageIcon( vImageURL );
            this.aImage.setIcon( vIcon );
            this.aMyFrame.pack();
        }
    } // showImage()
    
    /**
     * Permet d'activer ou de désactiver la saisie dans le champ de saisie.
     * 
     * @param pOnOff Indique si le champ de saisie doit être activé (true) ou désactivé (false).
     * Si la saisie est activée, le curseur clignote à une fréquence de 500 millisecondes et la classe réagit aux entrées utilisateur. 
     * Si la saisie est désactivée, le curseur ne clignote pas et la classe ne réagit pas aux entrées utilisateur.
     */
    public void enable( final boolean pOnOff )
    {
        this.aEntryField.setEditable( pOnOff ); // enable/disable
        if ( pOnOff ) { // enable
            this.aEntryField.getCaret().setBlinkRate( 500 ); // cursor blink
            this.aEntryField.addActionListener( this ); // reacts to entry
        }
        else { // disable
            this.aEntryField.getCaret().setBlinkRate( 0 ); // cursor won't blink
            this.aEntryField.removeActionListener( this ); // won't react to entry
        }
    } // enable()

    
    /**
     * Réagit lorsqu'une touche du clavier est pressée.
     * Si la touche pressée est une touche fléchée, une touche de page ou de suppression, la méthode interprète la commande correspondante en invoquant la méthode interpretCommand() de l'objet aEngine.
     * 
     * @param e KeyEvent qui représente l'événement de frappe de touche.
    */
    @Override 
    public void keyPressed(final KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP :
                this.aEngine.interpretCommand("aller nord");
                break;
             case KeyEvent.VK_DOWN:
                this.aEngine.interpretCommand("aller sud");
                break;
            case KeyEvent.VK_LEFT:
                this.aEngine.interpretCommand("aller ouest");
                break;
            case KeyEvent.VK_RIGHT:
                this.aEngine.interpretCommand("aller est");
                break;
            case KeyEvent.VK_PAGE_UP :
                this.aEngine.interpretCommand("aller montée");
                break;
            case KeyEvent.VK_PAGE_DOWN :
                this.aEngine.interpretCommand("aller descente");
                break;
            case KeyEvent.VK_BACK_SPACE :
                this.aEngine.interpretCommand("reculer");
                break;
            default:
        }
    }//keyPressed()
    
    /**
     * Ne fait rien en réponse à la libération d'une touche de clavier.
     * 
     * @param e L'événement de libération de touche de clavier.
     */
    @Override 
    public void keyReleased(KeyEvent e) {
        // Do nothing
    }//keyReleased()

    /**
     * Cette méthode ne fait rien car elle est déclenchée lorsqu'une touche est tapée et ne nécessite aucune action spécifique.
     * 
     * @param e l'événement KeyEvent déclenché par la frappe de touche.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }//keyTyped()
    
    /**
     * Mise en place de l'interface utilisateur graphique.
     */
    private void createGUI()
    {
        this.aMyFrame = new JFrame( "Shi : Kiyowara Fumiaki's Adventure" );
        this.aEntryField = new JTextField( 34 );
      
        this.aLog = new JTextArea();
        this.aLog.setEditable( false );
        JScrollPane vListScroller = new JScrollPane( this.aLog );
        vListScroller.setPreferredSize( new Dimension(200, 200) );
        vListScroller.setMinimumSize( new Dimension(100,100) );
        
        Color vRouge = new Color(255,102,102);
        Color vBleu = new Color(135, 206, 250);
        Color vViolet = new Color(211,85,203);
        Color vOrange = new Color(247,208,52);
        Color vJaune = new Color(255,203,116);
        Color vVert = new Color(0,250,154);

        this.aImage = new JLabel();
        this.aPanelEast = new JPanel();
        this.aPanelEast.setLayout(new GridLayout(3,3));
        
        this.aPanelWest = new JPanel();
        this.aPanelWest.setLayout(new GridLayout(4,4));
        
        //Création d'un bouton vide 1
        this.aButtonVide1 = new JButton("");
        this.aButtonVide1.setBackground(Color.white);
        this.aPanelEast.add(this.aButtonVide1);
        this.aButtonVide1.setEnabled(false);
        
        //Création du bouton Nord
        this.aButtonNord = new JButton("Nord");
        this.aButtonNord.addActionListener(this);
        this.aButtonNord.setBackground(vViolet);
        this.aPanelEast.add(this.aButtonNord);
        
        //Création du bouton Montée
        this.aButtonMontée = new JButton("Montée");
        this.aButtonMontée.addActionListener(this);
        this.aButtonMontée.setBackground(vOrange);
        this.aPanelEast.add(this.aButtonMontée);
        
        //Création du bouton Ouest
        this.aButtonOuest = new JButton("Ouest");
        this.aButtonOuest.addActionListener(this);
        this.aButtonOuest.setBackground(vViolet);
        this.aPanelEast.add(this.aButtonOuest);
        
        //Création du bouton Reculer
        this.aButtonReculer = new JButton("Reculer");
        this.aButtonReculer.addActionListener(this);
        this.aButtonReculer.setBackground(vViolet);
        this.aPanelEast.add(this.aButtonReculer);
    
        //Création du bouton Est
        this.aButtonEst = new JButton("Est");
        this.aButtonEst.addActionListener(this);
        this.aButtonEst.setBackground(vViolet);
        this.aPanelEast.add(this.aButtonEst);
        
        //Création du bouton Vide 2
        this.aButtonVide2 = new JButton("");
        this.aButtonVide2.setBackground(Color.white);
        this.aPanelEast.add(this.aButtonVide2);
        this.aButtonVide2.setEnabled(false);
        
        //Création du bouton Sud
        this.aButtonSud = new JButton("Sud");
        this.aButtonSud.addActionListener(this);
        this.aButtonSud.setBackground(vViolet);
        this.aPanelEast.add(this.aButtonSud);
        
        //Création du bouton Descente
        this.aButtonDescente = new JButton("Descente");
        this.aButtonDescente.addActionListener(this);
        this.aButtonDescente.setBackground(vOrange);
        this.aPanelEast.add(this.aButtonDescente);
        
        // Création du bouton Carte
        this.aButtonCarte = new JButton("Carte");
        this.aButtonCarte.addActionListener(this);
        this.aButtonCarte.setBackground(vBleu);
        this.aPanelWest.add(this.aButtonCarte);
        
        //Création du bouton Inventaire
        this.aButtonInventaire = new JButton("Inventaire");
        this.aButtonInventaire.addActionListener(this);
        this.aButtonInventaire.setBackground(vBleu);
        this.aPanelWest.add(this.aButtonInventaire);
        
        //Création du bouton Regarder
        this.aButtonRegarder = new JButton("Regarder");
        this.aButtonRegarder.addActionListener(this);
        this.aButtonRegarder.setBackground(vBleu);
        this.aPanelWest.add(this.aButtonRegarder);
         
        //Création du bouton Gagner
        this.aButtonGagner = new JButton("Gagner");
        this.aButtonGagner.addActionListener(this);
        this.aButtonGagner.setBackground(vVert);
        this.aPanelWest.add(this.aButtonGagner);
        
        
        //Création du bouton Aide
        this.aButtonAide = new JButton("Aide");
        this.aButtonAide.addActionListener(this);
        this.aButtonAide.setBackground(vJaune);
        this.aPanelWest.add(this.aButtonAide);
        
        //Création du bouton quitter
        this.aButtonQuitter = new JButton("Quitter");
        this.aButtonQuitter.addActionListener(this);
        this.aButtonQuitter.setBackground(vRouge);
        this.aPanelWest.add(this.aButtonQuitter);

        JPanel vPanel = new JPanel();
        vPanel.setLayout( new BorderLayout() ); // ==> only five places
        vPanel.add( this.aImage, BorderLayout.NORTH );
        vPanel.add( vListScroller, BorderLayout.CENTER );
        vPanel.add( this.aEntryField, BorderLayout.SOUTH );
        vPanel.add(this.aPanelEast, BorderLayout.EAST );
        vPanel.add(this.aPanelWest, BorderLayout.WEST);

        this.aMyFrame.getContentPane().add( vPanel, BorderLayout.CENTER );

        // add some event listeners to some components
        this.aEntryField.addActionListener( this );

        // to end program when window is closed
        this.aMyFrame.addWindowListener(
            new WindowAdapter() { // anonymous class
                @Override public void windowClosing(final WindowEvent pE)
                {
                    System.exit(0);
                }
        } );

        this.aMyFrame.pack();
        this.aMyFrame.setVisible( true );
        this.aEntryField.requestFocus();
    } // createGUI()

    /**
     * Interface Actionlistener pour le champ de saisie.
     * 
     * @param pE Action réalisée
     */
    @Override public void actionPerformed( final ActionEvent pE ) 
    {
        // no need to check the type of action at the moment
        // because there is only one possible action (text input) :
        if(pE.getSource() == this.aButtonQuitter) 
        {
          this.aEngine.interpretCommand("quitter");
        } 
        else if (pE.getSource() == this.aButtonAide)
        {
            this.aEngine.interpretCommand("aide");
        }
        else if (pE.getSource() == this.aButtonRegarder) 
        {
            this.aEngine.interpretCommand("regarder");
        }
        else if (pE.getSource() == this.aButtonMontée) 
        {
            this.aEngine.interpretCommand("aller montée");
        } 
        else if (pE.getSource() == this.aButtonInventaire)
        {
            this.aEngine.interpretCommand("inventaire");
        } 
        else if (pE.getSource() == this.aButtonReculer)
        {
            this.aEngine.interpretCommand("reculer");
        }
        else if (pE.getSource() == this.aButtonSud)
        {
            this.aEngine.interpretCommand("aller sud");
        }
        else if (pE.getSource() == this.aButtonNord)
        {
            this.aEngine.interpretCommand("aller nord");
        }
        else if (pE.getSource() == this.aButtonEst)
        {
            this.aEngine.interpretCommand("aller est");
        }
        else if (pE.getSource() == this.aButtonOuest)
        {
            this.aEngine.interpretCommand("aller ouest");
        }
        else if (pE.getSource() == this.aButtonDescente)
        {
            this.aEngine.interpretCommand("aller descente");
        }
        else if (pE.getSource() == this.aButtonRegarder)
        {
            this.aEngine.interpretCommand("regarder");
        }
        else if(pE.getSource() == this.aButtonCarte) {
            this.aEngine.interpretCommand("carte");
        }
        else if(pE.getSource() == this.aButtonGagner){
            this.aEngine.interpretCommand("gagner");
        }
        else {
        this.processCommand(); // never suppress this line
        }
    } // actionPerformed()
    
    /**
     * Une commande a été introduite dans le champ de saisie.  
     * Lit la commande et fait le nécessaire pour la traiter.
     */ 
    private void processCommand()
    {
        String vInput = this.aEntryField.getText();
        this.aEntryField.setText( "" );
        this.aEngine.interpretCommand( vInput );
    } // processCommand()
}//UserInterface()
