import java.util.HashMap;
/**
 * Classe ItemList
 * 
 * La classe ItemList permet de gérer une liste d'items et mutualise ainsi la gestion des items qui se retrouvait dupliquée dans Room et dans Player. 
 * Cette classe utilise une HashMap pour stocker les objets, avec le nom de l'objet comme clef et l'objet lui-même comme valeur associée.
 * Cette classe possède des méthodes pour ajouter et supprimer des items, récupérer un item par son nom, récupérer la liste complète des items sous forme de chaîne de caractères, et récupérer la HashMap des objets présents dans l'inventaire.
 * Cette classe est utilisée par les classes Room et Player pour gérer les objets qui se trouvent dans une pièce ou dans l'inventaire du joueur.
 *
 * @author HAKIM Justine
 * @version 10/04/2023
 */
public class ItemList
{
    // variables d'instance 
    private HashMap <String, Item> aItems;//HashMap ("Nom de l'objet", objet)

    /**
     * Constructeur d'objets de classe ItemList
     */
    public ItemList()
    {
        // initialisation des variables d'instance
        this.aItems = new HashMap <String, Item>() ;
    }//ItemList()
    
    /**
     * Accesseur de l'item
     * Cette méthode permet d'accéder à l'objet "Item" stocké dans la HashMap en fonction de la clé "pItem".
     * 
     * @param pItem Clef de l'item à récupérer dans la HashMap
     * @return L'objet "Item" lié à la clef "pItem"
     */
    public Item getItem(final String pItem)
    {
        return this.aItems.get(pItem);
    }//getItem()
    
     /**
     * Accesseur permettant d'accéder à la HashMap des objets présents dans la liste d'items.
     * 
     * @return La HashMap des objets présents dans la liste d'items. L'inventaire.
     */
    public HashMap <String, Item> getItemsInventory()
    {
        return aItems; 
    }//getItemsInventory()
    
    /**
     * Ajoute un objet "Item "à la liste en l'associant à la clé "pName"
     *      
     * @param pName Clef de la HashMap associée à l'objet "Item" à ajouter (nom de l'item)
     * @param pItem L'objet "Item" à ajouter dans la HashMap
     */
    public void addItem(final String pName, final Item pItem)
    {
        this.aItems.put(pName, pItem);
    }//addItem()
    
    /**
     * Supprime un objet "Item" de notre HashMap, de la liste en utilisant la clé "pName".
     * 
     * @param pName La clé associée à l'objet "Item" à supprimer de la liste.   
     * @param pItem L'objet "Item" à supprimer de la liste.
     */
    public void removeItem(final String pName, final Item pItem)
    {
        this.aItems.remove(pName, pItem);
    }//removeItem()
    
    /**
     * Teste si la liste contient l'item demandé.
     * 
     * @param pNom Le nom de l'item à rechercher dans la liste
     * @return Vrai si la liste contient l'item, Faux dans le cas contraire
     */
    public boolean hasItem(final String pNom)
    {
        return this.aItems.containsKey(pNom);
    }
    
    /**
     * Accesseur d'une chaîne de caractères représentant tous les objets "Item" stockés dans la liste.
     *  
     * @return Une chaîne de caractères représentant les objets "Item" stockés dans la liste. 
     * Si la liste est vide, la méthode renvoie "Désolé...Il n'y a pas d'objets...".
     */
    public String getItemString()
    {
        StringBuilder returnString = new StringBuilder ();
        if(this.aItems.isEmpty())
        {
            returnString.append(" Désolé...Il n'y a pas d'objets...");
        } else {
        for(String vItemName : this.aItems.keySet()){
            returnString.append(" " + vItemName + " - ");
        }//for()
        }//else if
        return returnString.toString();
    }//getItemString()
}//ItemList()
