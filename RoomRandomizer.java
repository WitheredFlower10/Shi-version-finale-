import java.util.ArrayList;
import java.util.Random;
import java.util.List;
/**
 * La classe RoomRandomizer crée une pièce aléatoire.
 * Cette classe permet de sélectionner une pièce aléatoire à partir d'une liste de pièces.
 *
 * @author HAKIM Justine
 * @version 21/05/2023
 */
public class RoomRandomizer {
    private List<Room> aRooms;
    private Random aRandom;
    private String aForcedRoom;

    /**
     * Constructeur de la classe RoomRandomizer.
     * Il crée un objet RoomRandomizer avec une liste vide de pièces.
     */
    public RoomRandomizer() {
        this.aRandom = new Random();
        this.aRooms = new ArrayList<Room>();
    }//RoomRandomizer()
    
    /**
     * Ajoute une pièce à la liste de pièces du RoomRandomizer.
     *
     * @param pRoom La pièce à ajouter.
     */
    public void addRoom(final Room pRoom)
    {
        this.aRooms.add(pRoom);
    }//addRoom()

    /**
     * Recherche une pièce aléatoire.
     *
     * @return Une pièce aléatoire sélectionnée dans la liste de pièces.
     */
    public Room findRandomRoom() {
        if (this.aForcedRoom != null) {
            for(Room vRoom : this.aRooms)
            {
                if(vRoom.getName().equals(this.aForcedRoom))
                {
                    return vRoom;
                }
            }
            return null;
        }
        else 
        {
            int vNb = this.aRandom.nextInt(this.aRooms.size());
            return this.aRooms.get(vNb);
        }
    }//findRandomRoom()
    
    /**
     * Définit une pièce forcée.
     *
     * @param pName Le nom de la pièce à forcer.
     * @return Un message indiquant si la pièce a été forcée ou si elle n'existe pas.
     */
    public String setForcedRoom(final String pName) {
        if (pName.length() ==0) {
        this.aForcedRoom = null;
        return "Le tirage aléatoire a été réinitialisé. ";
        } else {
            for(Room vRoom : this.aRooms)
            {
                if(pName.equals(vRoom.getName())){
                    this.aForcedRoom = pName;
                    return "Le tirage aléatoire a été forcé sur " + pName;
                }
            }
            return "Cette pièce n'existe pas ou vous ne pouvez pas vous y transportez. ";
        }
    }//setForcedRoom()
}//RoomRandomizer()