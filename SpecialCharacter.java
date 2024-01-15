/**
 * La classe SpecialCharacter représente un personnage spécial dans le jeu.
 * Elle hérite de la classe Character.
 * Un personnage spécial possède un nom, un dialogue, une réponse correcte à une énigme et une récompense.
 * Il peut également vérifier si tous les objets requis sont présents dans une liste d'objets.
 * 
 * @author HAKIM Justine
 * @version 21/05/2023
 */
public class SpecialCharacter extends Character
{
    /**
     * Constructeur d'objets de classe SpecialCharacter
     * 
     * @param pName Le nom du personnage spécial.
     * @param pDialog Le dialogue du personnage spécial.
     * @param pCorrectAnswer La réponse correcte à l'énigme posée par le personnage spécial.
     * @param pReward La récompense attribuée par le personnage spécial.
     */
    public SpecialCharacter(final String pName, final String pDialog, final String pCorrectAnswer, final Item pReward)
    {
        // initialisation des variables d'instance
        // Appel du constructeur de la classe mère (Character) pour initialiser les variables d'instance
        super(pName, pDialog, pCorrectAnswer, pReward);
    }//SpecialCharacter()
    
    /**
     * Vérifie si tous les objets requis sont présents dans une liste d'objets.
     * 
     * @param pItems La liste d'objets à vérifier.
     * @return true si tous les objets requis sont présents, false sinon.
     */
    public boolean checkAllItems(final ItemList pItems){
        // Vérifie si tous les objets requis sont présents dans la liste d'objets
        return pItems.hasItem("Jinbaori") && pItems.hasItem("Kote") && pItems.hasItem("Hitatare.et.hakama") && pItems.hasItem("Mengu") && pItems.hasItem("Kutsu") && pItems.hasItem("Do") && pItems.hasItem("Sode") && pItems.hasItem("Suneate") && pItems.hasItem("Kusazuri.et.Haidate") && pItems.hasItem("Kabuto");
    }//checkAllItems()
}//SpecialCharacter()
