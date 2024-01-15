/**
 * Cette classe est la classe principale du jeu d'aventure. 
 *  
 * Cette classe contient un tableau d'énumeration de toutes les commandes disponibles dans le jeu. 
 * Elle est utilisée pour reconnaître les commandes lorsqu'elles sont tapées par le joueur.
 *
 * @author  Michael Kolling and David J. Barnes + D.Bureau modifiée par HAKIM Justine
 * @version 2008.03.30 + 2019.09.25 + 15/02/2023
 */
public class CommandWords
{
    //Tableau constant qui contiendra les commandes valides
    private static final String[] aValidCommands = {"aller","aide","quitter","regarder","ingérer","reculer", "test","prendre","jeter","inventaire","charger","décharger","carte","alea","parler","répondre","gagner"};

    /**
     * Vérifie si une chaîne de caractères est une commande valide. 
     * 
     * @param pString Une chaîne de caractère à vérifier
     * @return Vrai si la commande entrée est valide, Faux si la commande ne l'est pas
     */
    public boolean isCommand( final String pString )
    {
        for ( int vI=0; vI<this.aValidCommands.length; vI++ ) {
            if ( this.aValidCommands[vI].equals( pString ) )
                return true;
        } // for
        //Si l'on arrive à cette étape, la String n'est pas une commande valide
        return false;
    } // isCommand()
    
    /**
     * Récupère la liste de toutes les commandes valides.
     * 
     * @return Les commandes valides sous forme de chaîne de caractères, sinon rien.
     */
    public String getCommandList()
    {
        StringBuilder vCommands = new StringBuilder(); 
        for(int i = 0; i < this.aValidCommands.length; i++) {
            vCommands.append( this.aValidCommands[i] + "  ");
        }//for
        return vCommands.toString();
    }//getCommandList()
} // CommandWords