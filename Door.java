/**
 * La classe Door permet de créer des portes fermées et ouvrables avec une clé.
 *
 * La classe Door représente une porte dans le jeu.
 * Une porte peut être verrouillée ou déverrouillée. Elle peut être associée à une clé.
 * La classe contient des méthodes pour récupérer la clé associée à la porte, verrouiller la porte avec une clé donnée, et vérifier si la porte est verrouillée.
 * 
 * @author HAKIM Justine
 * @version 03/05/2023
 */
public class Door
{
    // variables d'instance
    private boolean aState;// État de la porte (verrouillée ou déverrouillée)
    private Item aKey; // Clé associée à la porte
    private Player aPlayer;
    
    /**
    * Constructeur par défaut de la classe Door.
    * Initialise l'état de la porte à false, ce qui signifie qu'elle est déverrouillée.
    */
    public Door(){
        this.aState = false;
    }//Door()
    
    /**
     * Renvoie la clé associée à cette porte.
     * 
     * @return La clé associée à cette porte.
     */
    public Item getKey() {
         return this.aKey;
    }//getKey()
    
    /**
     * Verrouille la porte avec la clé donnée.
     * 
     * @param pKey la clé utilisée pour verrouiller la porte
     */
    public void lock(final Item pKey) {
        this.aState = true;
        this.aKey = pKey;
    } // lock()
    
    /**
     * Vérifie si la porte est verrouillée.
     * 
     * @return un booléen indiquant si la porte est verrouillée (true) ou non (false)
     */
    public boolean isLocked() {
        return this.aState;
    } // isLocked()
}//Door()

