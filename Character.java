/**
 * La classe Character représente un personnage dans le jeu.
 * Un personnage possède un nom, un dialogue, une réponse correcte à une énigme, une récompense.
 *
 * @author HAKIM Justine
 * @version 21/05/2023
 */
public class Character
{
    // variables d'instance
    private String aName ;
    private String aDialog;
    private String aCorrectAnswer;
    private Item aReward;
    private int aNbError;

    /**
     * Constructeur d'objets de classe Character
     * 
     * @param pName Le nom du personnage.
     * @param pDialog Le dialogue du personnage.
     * @param pCorrectAnswer La réponse correcte à l'énigme posée par le personnage.
     * @param pReward La récompense attribuée par le personnage.
     */
    public Character(final String pName, final String pDialog, final String pCorrectAnswer, final Item pReward)
    {
        // initialisation des variables d'instance
        this.aName = pName;
        this.aDialog = pDialog;
        this.aCorrectAnswer = pCorrectAnswer;
        this.aReward = pReward;
        this.aNbError= 0;
    }//Character()
    
    /**
     * Accesseur du nombre d'erreurs.
     * 
     * @return Le nombre d'erreurs.
     */
    public int getNbError(){
        return this.aNbError;
    }//getNbError()
    
    /**
     * Incrémente le nombre d'erreurs.
     */
    public void upNbError(){
        this.aNbError += 1;
    }//upNbError()
    
    /**
     * Accesseur du nom du personnage.
     * 
     * @return Le nom du personnage.
     */
    public String getName(){
        return this.aName;
    }//getName()
    
    /**
     * Retourne le dialogue complet du personnage.
     * 
     * @return Le dialogue du personnage.
     */
    public String getDialogString() {
        return this.aName + ": " + this.aDialog ;
    }//getDialogString()

    /**
     * Vérifie si la réponse du joueur est correcte.
     * 
     * @param pPlayerAnswer La réponse du joueur.
     * @return true si la réponse est correcte, false sinon.
     */
    public boolean checkAnswer(final String pPlayerAnswer) {
        return pPlayerAnswer.equals(this.aCorrectAnswer);
    }//checkAnswer()

    /**
     * Accesseur de la récompense attribuée par le personnage.
     * 
     * @return La récompense.
     */
    public Item getReward() {
        return this.aReward;
    }//getReward()    
}//Character()
