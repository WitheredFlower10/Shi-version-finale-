/**
 * La classe TransporterRoom représente une pièce de type "Transporteur".
 * Une pièce de type Transporteur permet de se téléporter aléatoirement vers une autre pièce.
 * Elle hérite de la classe Room.
 *
 * @author HAKIM Justine
 * @version 21/05/2023
 */
public class TransporterRoom extends Room
{
    // variables d'instance
    private RoomRandomizer aRoomRandomizer;

    /**
     * Constructeur d'objets de classe TransporterRoom
     * Il crée un objet TransporterRoom avec un nom, une description et le nom de l'image associée.
     *
     * @param pName Le nom de la pièce.
     * @param pDescription La description de la pièce.
     * @param pImageName Le nom de l'image associée à la pièce.
     */
    public TransporterRoom(final String pName, final String pDescription, final String pImageName)
    {
        // initialisation des variables d'instance
        super(pName, pDescription, pImageName);
        this.aRoomRandomizer = new RoomRandomizer();
    }//TransporterRoom()
    
    /**
     * Redéfinition de la méthode getExit de la classe mère Room.
     * Cette méthode renvoie une pièce aléatoire à la place de la pièce de sortie normale.
     *
     * @param pDirection La direction de sortie de la pièce.
     * @return Une pièce aléatoire sélectionnée par le RoomRandomizer.
     */
    @Override 
    public Room getExit(final String pDirection)
    {
            return getRandomRoom();
    }//getExit()
    
    /**
     * Renvoie une pièce aléatoire.
     *
     * @return Une pièce aléatoire sélectionnée par le RoomRandomizer.
     */
    public Room getRandomRoom(){
        return this.aRoomRandomizer.findRandomRoom();    
    }//getRandomRoom()
    
    /**
     * Accesseur pour l'objet RoomRandomizer de la pièce Transporter.
     *
     * @return L'objet RoomRandomizer associé à la pièce Transporter.
     */
    public RoomRandomizer getRoomRandomizer()
    {
        return this.aRoomRandomizer;
    }//getRoomRandomizer()
}//TransporterRoom()
