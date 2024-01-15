/**
 * La classe Item permet de mettre des items dans des pièces
 * La classe Item représente un objet dans le jeu.
 * Elle permet de définir des items qui peuvent être placés dans les pièces.
 * Chaque item a un nom, une description et un poids.
 * 
 * @author HAKIM Justine
 * @version 27/03/2023
 */
public class Item
{
    // variables d'instance
    private String aName;
    private String aDescription;
    private double aItemWeight;

    /**
     * Constructeur naturel d'objets de classe Item
     * 
     * @param pName Nom de l'item
     * @param pDescription Description de l'item
     * @param pWeight Poids de l'item
     */
    public Item(final String pName, final String pDescription, final double pWeight)
    {
        // initialisation des variables d'instance
        this.aName = pName;
        this.aDescription = pDescription;
        this.aItemWeight = pWeight;
    }//Item()
    
    /**
     * Accesseur pour le nom de l'item
     * 
     * @return Nom de l'item
     */
    public String getName() 
    {
        return this.aName;
    }//getName()
    
    /**
     * Accesseur pour la description de l'item
     * 
     * @return Description de l'item
     */
    public String getDescription()
    {
        return this.aDescription;
    }//getDescription()
    
    /**
     * Accesseur pour le poids de l'item
     * 
     * @return Poids de l'item
     */
    public double getWeight()
    {
        return this.aItemWeight;
    }//getWeight()
    
    /** 
     * Accesseur pour la description complète de l'item
     * 
     * @return La description complète de l'item, incluant le nom, la description et le poids.     
    */
    public String getItemDescription()
    {
        return "Il y a un " + this.getName() + " à prendre... " + " " + this.getDescription() + ". " + "Cet objet pèse " + this.getWeight() + " g.";
    }//getItemDescription()
}//Item()
