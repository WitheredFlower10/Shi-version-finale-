import java.util.StringTokenizer;
/** 
 * Cet analyseur lit les entrées de l'utilisateur et essaie de les interpréter comme une commande.
 * Chaque fois qu'il est appelé, il lit une ligne du terminal et tente de l'interpréter comme une commande.
 * Il renvoie la commande comme un objet de la classe Command.
 *
 * Le parseur a un ensemble de mots de commande connus. Il vérifie l'entrée de l'utilisateur par rapport
 * aux commandes connues, et si l'entrée n'est pas une des commandes connues, il renvoie un objet de commande qui est marqué comme une commande inconnue.
 * 
 * @author  Michael Kolling and David J. Barnes + D.Bureau modifiée par HAKIM Justine
 * @version 2008.03.30 + 2013.09.15 + 15/02/2023
 */
public class Parser 
{
    //Attributs
    private CommandWords aValidCommands;  // Commande valide
    
    /**
     * Constructeur par defaut qui cree l'objet prévu pour l'attribut aValidCommands
     */
    public Parser() 
    {
        this.aValidCommands = new CommandWords();
    } // Parser()

    /**
     * Récupère la commande et renvoie une commande interprétable par les fonctions
     * 
     * @param pInputLine String qui permet de lire la commande
     * @return La prochaine commande du joueur.
     */
    public Command getCommand(final String pInputLine) 
    {
        String vWord1;
        String vWord2;
        
        StringTokenizer tokenizer = new StringTokenizer( pInputLine );
        
        if( tokenizer.hasMoreTokens())
            vWord1 = tokenizer.nextToken(); //Prend le premier mot
        else 
            vWord1 = null;
            
        if ( tokenizer.hasMoreTokens() )
            vWord2 = tokenizer.nextToken();      // get second word
        else
            vWord2 = null;

        // note: we just ignore the rest of the input line.

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).

        if ( this.aValidCommands.isCommand( vWord1 ) )
            return new Command( vWord1, vWord2 );
        else
            return new Command( null, vWord2 );
    } // getCommand()
    
    /**
     * Affiche une liste des commandes valides.
     * 
     * @return La liste des commandes valides.
     */
    public String getCommandString() 
    {
        return this.aValidCommands.getCommandList();
    }//getCommandString()   
} // Parser