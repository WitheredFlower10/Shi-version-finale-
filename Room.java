import java.util.HashMap;
/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 * 
 * Une "Room" représente un emplacement dans le décor du jeu.  Elle est 
 * reliée à d'autres pièces par des sorties.  Pour chaque sortie existante, la pièce 
 * stocke une référence à la pièce voisine.
 * Cette classe permet définir les pièces de notre jeu et leurs sorties possibles
 * 
 * @author HAKIM Justine
 * @version 15/02/2023
 */
public class Room
{
    //Attributs de la classe
    private String aDescription;//Description de la pièce actuelle
    private HashMap<String, Room> aExits;//HashMap ("direction", pièce dans cette direction)
    private String aImageName;//Nom de l'image  
    private ItemList aRoomItems;//Liste d'objets présents dans la pièce
    private HashMap<String, Door> aDoors; // Stocke les portes de la pièce
    private String aName;//Nom de la pièce
    private HashMap<String, Character> aCharacters;//HashMap("nom", personnage)
    
    /**
     * Constructeur naturel
     * Crée une pièce décrite par la chaine "description"
     * Au départ, il n'existe aucune sortie
     * "description" est une chaîne
     * 
     * @param pName Nom de la pièce
     * @param pDescription Description de la pièce 
     * @param pImageName Nom de l'image de la pièce
     */
    public Room(final String pName, final String pDescription, final String pImageName){
        this.aDescription = pDescription; 
        this.aImageName = pImageName;
        this.aName = pName;
        this.aExits = new HashMap<String, Room>(); //Créer un nouveau HashMap vide 
        this.aDoors = new HashMap<String, Door>();
        this.aCharacters = new HashMap <String, Character>();
        this.aRoomItems = new ItemList (); 
    }//Room()
    
    /**
     * Accesseur de la liste d'objets de la pièce.
     *
     * @return La liste d'objets de la pièce.
     */
    public ItemList getRoomItems()
    {
        return this.aRoomItems;
    }//getRoomItems()
    
    /**
     * Accesseur de la description de la pièce
     * 
     * @return La description de la pièce actuelle
     * (telle que définie par le constructeur).
     */
    public String getShortDescription()
    {
        return this.aDescription;
    }//getShortDescription()
    
    /**
     * Accesseur du nom de la pièce
     * 
     * @return Le nom de la pièce
     */
    public String getName()
    {
        return this.aName;
    }//getName()
    
    /**
     * Définit une sortie de cette pièce dans la direction indiquée.
     * La sortie est représentée par la pièce voisine dans la direction donnée.
     * Une porte est également créée dans cette direction.
     * 
     * @param pDirection Indique la direction de la sortie.
     * @param pVoisine Indique la pièce dans la direction donnée.
     */
    public void setExit(final String pDirection, final Room pVoisine)
    {
        this.aExits.put(pDirection, pVoisine);
        this.aDoors.put(pDirection, new Door());
    }//setExit()
    
    /**
     * Accesseur de la sortie indiquée 
     * 
     * Renvoie la pièce atteinte si nous nous déplaçons dans la direction "direction"
     * S'il n'y a pas de pièces dans cette direction, alors on renvoie null. 
     * Acceseurs d'une des sorties
     * 
     * @param pDirection Direction dont on souhaite connaître la sortie 
     * @return L'objet Room accessible dans la direction donnée, ou nul si la direction n'est pas valide
     */
    public Room getExit(final String pDirection)
    {
        return this.aExits.get(pDirection);
    }//getExit()
    
    /**
     * Ajoute un personnage à la pièce
     * 
     * @param pCharacter Le personnage à ajouter
     */
    public void addCharacter(final Character pCharacter){
         this.aCharacters.put(pCharacter.getName(), pCharacter);
    }//addCharacter()    
    
    /**
     * Renvoie une description des sorties disponibles
     * 
     * Par exemple : "Les sorties : Nord Sud "
     * 
     * @return Une chaine de caractères indiquant une description des sorties disponibles.
     */
    private String getExitString()
    {
        StringBuilder returnString = new StringBuilder("Les sorties sont : ");
        for (String vS : this.aExits.keySet())
            returnString.append( " " + vS );
        return returnString.toString();
    }//getExitString()
    
    /**
     * Renvoie une description des personnages présents dans la pièce.
     *
     * @return Une chaîne de caractères indiquant les personnages présents dans la pièce.
     */
    private String getCharacterString() {
        StringBuilder returnString = new StringBuilder("Les personnages sont : ");
        if(this.aCharacters.isEmpty())
        {
            returnString.append("Désolé...Il n'y a pas de personnages...");
        } else {
            for(String vJ : this.aCharacters.keySet())
            returnString.append( " " + vJ + " - ");
        }
        return returnString.toString();
    }//getCharacterString()
    
    /**
     * Renvoie le personnage correspondant au nom donné.
     *
     * @param pCharacter Le nom du personnage recherché.
     * @return Le personnage correspondant au nom donné, ou null s'il n'existe pas.
     */
    public Character getCharacter(final String pCharacter){
        return this.aCharacters.get(pCharacter);
    }//getCharacter()
     
    /**
     * Renvoie une description détaillée de cette pièce sous la forme :
     * Vous etes dans (pièce).
     * Sorties : nord sud.
     * Les objets dans cette pièce sont : (items)
     * Les personnages dans cette pièce sont : (personnages)
     * 
     * La description inclut le nom de la pièce, les sorties disponibles, les objets présents
     * et les personnages présents.
     * 
     * @return Une description de la pièce, ainsi que les sorties, les objets et les personnages.
     */
    public String getLongDescription()
    {
        return "Vous êtes dans " + this.aName + ", " + this.aDescription + "\n" + this.getExitString() + "\n" + "Les objets dans cette pièce sont :" + this.aRoomItems.getItemString() + "\n" + this.getCharacterString();
    }//getLongDescription()
    
    /**
     * Le nom de l'image est stocké dans l'attribut aImageName de la classe Room.
     * Retourne le nom de l'image de la pièce. 
     * 
     * @return Le nom de l'image de la pièce.
     */
    public String getImageName() 
    {
        return this.aImageName;
    }//getImageName()
    
    /**
     * Fonction booléenne qui vérifie si la pièce est acccessible, est une sortie possible
     * 
     * @param pRoom Pièce dont on vérifie l'accessibilité
     * @return Vrai si la pièce est une sortie possible, Faux dans le cas contraire
     */
    public boolean isExit (final Room pRoom) {
        return pRoom.aExits.containsValue(this);
    }//isExit()

    /**
     * Verrouille une porte dans la direction donnée avec un certain objet.
     *
     * @param pDirection La direction de la porte à verrouiller.
     * @param pItem     L'objet utilisé pour verrouiller la porte.
     */
    public void lockDoor(final String pDirection, final Item pItem) {
      this.aDoors.get(pDirection).lock(pItem);
    }//lockDoor()
    
    /**
     *  Retourne l'objet Door associé à la porte dans la direction donnée.
     *
     * @param pDirection La direction de la porte.
     * @return L'objet Door associé à la porte.
     */
    public Door getDoor(final String pDirection) {
      return this.aDoors.get(pDirection);
    }//getDoor()
}// Room
