import java.util.Stack;
import javax.swing.JOptionPane;
/**
 * Classe Player 
 * La classe Player représente un joueur dans le jeu.
 * Elle stocke les informations telles que le pseudo du joueur, sa position dans le jeu, les objets qu'il porte etc.
 * 
 * @author HAKIM Justine
 * @version 03/04/2023
 */
public class Player
{
    // variables d'instance
    private String aPseudo;//Le nom du joueur
    private Room aCurrentRoom; //Pièce actuelle
    private Stack <Room> aPreviousRoom;//Pièce précédente
    private int aWeight;//Le poids total des objets détenus par le joueur.
    private GameEngine aEngine;
    private ItemList aItems;//La liste des objets (items) détenus par le joueur.
    private int aWeightMax;
    private int aMaxDeplacements;//Le nombre maximum de déplacements autorisés pour le joueur.

    /**
     * Constructeur naturel d'objets de classe Player
     * 
     * @param pPseudo Pseudo du joueur
     * @param pCurrentRoom Pièce de départ du joueur
     * @param pEngine Le moteur de jeu associé au joueur
     */
    public Player(final String pPseudo, final Room pCurrentRoom, final GameEngine pEngine)
    {
        // initialisation des variables d'instance
        this.aPseudo = pPseudo;
        this.aWeight = 0;
        this.aWeightMax = 18000;
        this.aCurrentRoom = pCurrentRoom;
        this.aPreviousRoom = new Stack <Room>();
        this.aEngine = pEngine;
        this.aMaxDeplacements = 100;
        this.aItems = new ItemList ();
    }//Player()
    
    /**
     * Accesseur du Pseudo
     * 
     * @return Pseudo du joueur
     */
    public String getPseudo() 
    {
        return this.aPseudo;
    }//getPseudo()
    
    /**
     * Accesseur de la pièce actuelle du joueur
     * 
     * @return Pièce actuelle du joueur
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }//getCurrentRoom()
    
    /**
     * Accesseur de la pile de pièces précédentes visitées par le joueur
     * 
     * @return La pile de pièces précédentes visitées par le joueur
     */
    public Stack <Room> getPreviousRoom()
    {
        return this.aPreviousRoom;
    }//getPreviousRoom()
    
    /**
     * Accesseur de la liste des objets portés par le joueur
     * 
     * @return La liste des objets portés par le joueur
     */
    public ItemList getItems()
    {
        return this.aItems;
    }//getItems()
    
    /**
     * Accesseur du poids maximal que le joueur peut porter
     * 
     * @return Le poids Maximal que le joueur peut porter
     */
    public int getWeightMax()
    {
        return this.aWeightMax;
    }//getWeightMax()
    
    /**
     * Modificateur du poids maximal que le joueur peut porter.
     * 
     * @param pWeightMax Le nouveau poids maximal que le joueur peut porter
     */
    public void setWeightMax(final int pWeightMax)
    {
        this.aWeightMax = pWeightMax;
    }//setWeightMax()
    
    /**
     * Accesseur du poids total des objets portés par le joueur
     * 
     * @return Le poids total des objets portés par le joueur
     */
    public double getWeight()
    {
        return this.aWeight;
    }//getWeight()
    
    /**
     * Accesseur du nombre de déplacements restants
     * 
     * @return Entier représentant le nombre de déplacements restants avant que le jeu ne se termine
     */
    public int getMaxDeplacements(){
        return this.aMaxDeplacements;
    }//getMaxDeplacements()
    
    /**
     * Procédure permettant de se déplacer de pièces en pièces dans la direction spécifiée.
     * 
     * @param pDirectionSouhaite Direction dans laquelle le joueur souhaite se rendre
     */
    public void goRoom(final Command pDirectionSouhaite)
    {
        if(!pDirectionSouhaite.hasSecondWord()){
            this.aEngine.getGUI().println("Où voulez-vous aller ?") ;   
            return;//Arrête la fonction prématurément car l'on n'a pas besoin de changer de pièce
        }//if       
        String vDirection = pDirectionSouhaite.getSecondWord();
        Door vNextDoor = this.aCurrentRoom.getDoor(vDirection); 
        if(vNextDoor == null && !(this.aCurrentRoom instanceof TransporterRoom)) {
            this.aEngine.getGUI().println("Il n'y a pas de porte !");
            return;
        }
         if (vNextDoor != null && vNextDoor.isLocked()) {
        if (this.aItems.hasItem(vNextDoor.getKey().getName())) {
            Room vNextRoom = this.aCurrentRoom.getExit(vDirection);
            this.aPreviousRoom.push(this.aCurrentRoom);
            this.aCurrentRoom = vNextRoom;
            this.aMaxDeplacements -= 1;
            this.aEngine.timerEnd();
        } else {
            this.aEngine.getGUI().println(this.aPseudo + " n'a pas de quoi ouvrir la porte dans son inventaire...");
        }
        } else {
        Room vNextRoom = this.aCurrentRoom.getExit(vDirection);
        this.aPreviousRoom.push(this.aCurrentRoom);
        this.aCurrentRoom = vNextRoom;
        this.aMaxDeplacements -= 1;
        this.aEngine.timerEnd();
        }
    }//goRoom()
    
    
    /** 
     * Permet de revenir en arrière, dans la pièce précédente
     * 
     * @return true si le retour en arrière est possible et effectué avec succès, false sinon.
     */
    public boolean back()
    {
        if(this.aPreviousRoom.isEmpty())
        {
            this.aEngine.getGUI().println("Il n'y a pas de pièces précédentes.");
            return false;//Arrêt prématuré
        }//if 
        if(passTrapDoor()){
            this.aEngine.getGUI().println(this.aPseudo + " ne peut pas revenir en arrière après avoir emprunté une Trap Door.");
            return false;
        }
        this.aCurrentRoom = this.aPreviousRoom.pop();//Change la pièce actuelle par la pièce suivante
        this.aMaxDeplacements -= 1;
        this.aEngine.timerEnd();
        return true;
    }//back()
    
    /**
     * Procédure qui affiche ce qu'il y a dans la pièce, la description, les sorties et les objets
     * 
     * @param pCommand Commande de l'utilisateur
     */
    public void look(final Command pCommand)
    {
        if(pCommand.hasSecondWord()){
        String vLook = pCommand.getSecondWord();
        Item vCurrentItem = this.getCurrentRoom().getRoomItems().getItem(vLook);
        if(vCurrentItem == null) {
            this.aEngine.getGUI().println("Désolé...Cet item n'est pas dans la pièce ou n'existe pas...");
            return;
        }else {
            this.aEngine.getGUI().println(vCurrentItem.getItemDescription());
            return;
        }
    } else {
        this.aEngine.getGUI().println(this.aCurrentRoom.getLongDescription());
    }
    }//look()
    
    /**
     * Proédure qui permet au joueur d'ingérer un objet consommable et applique les effets associés.
     * 
     * @param pCommand Commande de l'utilisateur
     */
    public void eat(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()){
            this.aEngine.getGUI().println("Que voulez-vous ingérer ?");
            return;
        }
        String vItem = pCommand.getSecondWord();
        Item vMange = this.aItems.getItem(vItem);
        if(vMange == null) {
            this.aEngine.getGUI().println("Aucun objet dans l'inventaire peut se consommer...");
        } else if (vItem.equals("Saké") || vItem.equals("Sobas")){
            this.aItems.removeItem(vItem, vMange);
            this.setWeightMax(getWeightMax() + 2000);
            this.aWeight -= vMange.getWeight();
            this.aEngine.getGUI().println(this.aPseudo + " est requinqué et possède à présent la force de porter de nouveaux objets. Le poids maximum de l'inventaire est à présent de : " + getWeightMax() + " !");
        } else {
            this.aEngine.getGUI().println("Aucun objet dans l'inventaire peut se consommer... (ou alors " + this.aPseudo + " n'a plus faim !) ");
        }
    }//eat()
    
    /**
     * Procédure qui permet au joueur de ramasser un objet et de le mettre dans son inventaire
     * 
     * @param pCommand Une commande entrée par l'utilisateur
     */
    public void take(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()) {
            this.aEngine.getGUI().println("Que voulez-vous prendre ?");
            return;
        }//if
        String vPrise = pCommand.getSecondWord();
        Item vItem = this.aCurrentRoom.getRoomItems().getItem(vPrise);
        if(this.aCurrentRoom.getRoomItems().getItem(vPrise) == null) 
        {
            this.aEngine.getGUI().println("Désolé... Cet objet n'existe pas..."); 
            return;
        }else if(canTake(vItem.getWeight())==false){
            this.aEngine.getGUI().println(this.aPseudo + " porte déjà une charge lourde. " + this.aPseudo + " ne peut donc pas prendre cet objet.");
            return;
        }
        this.aEngine.getGUI().println(this.aPseudo + " a pris " + vPrise + " . Cet objet est à présent dans son inventaire.");
        this.aItems.addItem(vPrise, vItem);
        this.aCurrentRoom.getRoomItems().removeItem(vItem.getName(), vItem);
        this.aWeight += vItem.getWeight();
    }//take()
    
    /**
     * Procédure qui permet au joueur de lâcher un des objets de son inventaire
     * 
     * @param pCommand Une commande entrée par l'utilisateur
     */
    public void drop(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()) {
            this.aEngine.getGUI().println("Que voulez-vous jeter ?");
            return;
        }//if
        String vPoubelle = pCommand.getSecondWord();
        Item vItem = this.aItems.getItem(vPoubelle);
        if(vItem == null) {
            this.aEngine.getGUI().println("Désolé... Cet objet n'est pas dans l'inventaire de" + this.aPseudo + "...");
            return;
        }//if 
        this.aItems.removeItem(vPoubelle, vItem);
        this.aCurrentRoom.getRoomItems().addItem(vItem.getName(), vItem);
        this.aEngine.getGUI().println(" "   + this.aPseudo  + " n'a plus " + vPoubelle + " dans son inventaire.");
        this.aWeight -= vItem.getWeight();
    }//drop()
    
    /**
     * Commande qui permet de montrer l'inventaire du joueur
     */
    public void inventory()
    {
        String vInventory = this.aItems.getItemString();
        if(vInventory.equals("Les objets dans l'inventaire de " + this.aPseudo + " sont : ")){
            this.aEngine.getGUI().println("");
        }else {
            this.aEngine.getGUI().println("L'inventaire de " + this.aPseudo + " contient : " + vInventory + ". Son poids total est de " + this.aWeight + "g.");
        }
    }//inventory()
    
    /**
     * Commande qui permet de charger le Beamer dans la pièce actuelle où se trouve le joueur
     * 
     * @param pCommand Une commande entrée par l'utilisateur
     */
    public void charge(final Command pCommand) {
        this.aEngine.timerEnd();
        if(!pCommand.hasSecondWord()) {
            this.aEngine.getGUI().println("Que voulez-vous charger ?");
            return;
        }//if
        
        String vSecondWord = pCommand.getSecondWord();
        if(!vSecondWord.equals("Shukkô.ki")){
            this.aEngine.getGUI().println("Désolé... Cet objet ne peut pas être chargé...");
            return;
        }//if
        
        Item vItem = this.aItems.getItem(vSecondWord);
        Beamer vBeamer =(Beamer)vItem;
        if(vItem == null){
            this.aEngine.getGUI().println(this.aPseudo + " ne possède pas le beamer dans son inventaire.");
            return;
        }//if
        if(vBeamer.isCharged()){
            this.aEngine.getGUI().println(this.aPseudo + " a déjà chargé le beamer !");
            return;
        }//if
        vBeamer.charged(this.aCurrentRoom);
        this.aEngine.getGUI().println(this.aPseudo + " a chargé le beamer !");
    }//charge()
    
    /**
     * Commande qui décharge le beamer et effectue la téléportation vers la pièce chargée.
     * 
     * @param pCommand Une commande entrée par l'utilisateur
     */
    public void fire (final Command pCommand){
        if(!pCommand.hasSecondWord()){
            this.aEngine.getGUI().println("Que voulez-vous décharger ?");
            return;
        }//if
        
        String vSecondWord = pCommand.getSecondWord();
        if(!vSecondWord.equals("Shukkô.ki")){
            this.aEngine.getGUI().println("Désolé... Cet objet ne peut pas être déchargé...");
            return;
        }
        
        Item vItem = this.aItems.getItem(vSecondWord);
        Beamer vBeamer = (Beamer)vItem;
        if(vItem == null){
            this.aEngine.getGUI().println(this.aPseudo + " ne possède pas le beamer dans son inventaire.");
            return;
        }//if
        if(!vBeamer.isCharged()){
            this.aEngine.getGUI().println(this.aPseudo + " doit d'abord charger le beamer...");
            return;
        }//if
        vBeamer.discharged();
        this.aEngine.getGUI().println(this.aPseudo + " a déchargé le beamer !"); 
        this.aPreviousRoom.push(this.aCurrentRoom);//Ajoute la pièce actuelle aux anciennes pièces
        this.aCurrentRoom = vBeamer.getChargedRoom();//Remplace la pièce actuelle par celle où le beamer a été chargé
        this.aItems.removeItem(vBeamer.getName(), vItem);//Retire le beamer de l'inventaire
        this.aWeight -= vBeamer.getWeight();//Retire le poids du beamer du poids de l'inventaire
        this.aMaxDeplacements -= 1; //Retire un déplacement
        this.aEngine.printLocationInfo();
        this.aEngine.timerEnd();
    }//fire()
    
    /**
     * Procédure qui permet de savoir si le joueur peut prendre ou pas l'objet
     * 
     * @param pWeight Poids de l'item
     * @return true si la somme du poids passé en paramètre et du poids actuel porté par le joueur ne dépasse pas le poids maximal que le joueur peut porter
     */
    public boolean canTake(final double pWeight)
    {
        return (this.aWeight + pWeight <= this.aWeightMax);
    }//canTake()
    
    /**
     * Fonction booléenne qui permet de savoir si le joueur a emprunté une Trap Door
     * 
     * @return Vrai si la dernière pièce ajoutée à la pile des anciennes pièces est une sortie de la pièce courante, Faux dans le cas contraire
     */
    public boolean passTrapDoor(){
        if(!this.aPreviousRoom.peek().isExit(this.aCurrentRoom)){
            return true;
        }
        return false;
    }//passTrapDoor()
    
    /**
     * Procédure permettant d'engager une conversation avec un personnage donné.
     * 
     * @param pCommand La commande de conversation entrée par l'utilisateur
     */
    public void talk(final Command pCommand) {
        if(!pCommand.hasSecondWord()){
            this.aEngine.getGUI().println("Vous parlez à vous même ?");
            return;
        }//if
        String vSecondWord = pCommand.getSecondWord();
        Character vCharacter = this.aCurrentRoom.getCharacter(vSecondWord);
        if(vCharacter == null){
            this.aEngine.getGUI().println("Cette personne n'existe pas...");
            return;
        }//if
        if(vCharacter.getName().equals("Yakushi.Nyorai") || vCharacter.getName().equals("Statue.Sacrée")){
            if(this.aItems.hasItem("¥50")){
                Item vYen50 = this.aItems.getItem("¥50");
                this.aItems.removeItem("¥50", vYen50);
                this.aWeight -= vYen50.getWeight();
                if (!this.aItems.hasItem(vCharacter.getReward().getName()) && (canTake(vCharacter.getReward().getWeight()))) {// Vérification si le joueur n'a pas déjà la récompense
                    this.aItems.addItem(vCharacter.getReward().getName(), vCharacter.getReward());
                    this.aWeight += vCharacter.getReward().getWeight();
                    this.aEngine.getGUI().println(this.aPseudo + " a vénéré la statue. Pour son culte, " + this.aPseudo + " reçoit :" + vCharacter.getReward().getName());
                    return;
                } else {
                    this.aEngine.getGUI().println(this.aPseudo + " a déjà reçu la récompense ou porte déjà une charge trop lourde.");
                    return;
                }
            } else {
                this.aEngine.getGUI().println(this.aPseudo + " a besoin d'une pièce pour vénérer la statue.");
                return;
            } 
        }
         if(vCharacter.getName().equals("Sanji")){
            this.aEngine.getGUI().println(vCharacter.getDialogString());
            if(this.aItems.hasItem("Sauce.Soja")){
                Item vSauce = this.aItems.getItem("Sauce.Soja");
                this.aItems.removeItem("Sauce.Soja", vSauce);
                this.aWeight -= vSauce.getWeight();
                if (!this.aItems.hasItem(vCharacter.getReward().getName())&& (canTake(vCharacter.getReward().getWeight()))) {// Vérification si le joueur n'a pas déjà la récompense
                    this.aItems.addItem(vCharacter.getReward().getName(), vCharacter.getReward());
                    this.aWeight += vCharacter.getReward().getWeight();
                    this.aEngine.getGUI().println("Merci, je vois que tu possèdes l'ingrédient parfait, grâce à toi je peux terminer ma recette. Je t'offre ceci pour te remercier :" + vCharacter.getReward().getName());
                    return;
                } else {
                    this.aEngine.getGUI().println(this.aPseudo + " a déjà reçu la récompense ou porte déjà une charge trop lourde.");
                    return;
                }
            } else {
                this.aEngine.getGUI().println("");
                return;
            } 
        }
         if(vCharacter.getName().equals("Dan")){
            this.aEngine.getGUI().println(vCharacter.getDialogString());
            if(this.aItems.hasItem("Livre.Torii")){
                Item vLivre = this.aItems.getItem("Livre.Torii");
                this.aItems.removeItem("Livre.Torii", vLivre);
                this.aWeight -= vLivre.getWeight();
                if (!this.aItems.hasItem(vCharacter.getReward().getName()) && (canTake(vCharacter.getReward().getWeight()))) {// Vérification si le joueur n'a pas déjà la récompense
                    this.aItems.addItem(vCharacter.getReward().getName(), vCharacter.getReward());
                    this.aWeight += vCharacter.getReward().getWeight();
                    this.aEngine.getGUI().println("Merci, je vois que tu possèdes le livre que j'ai égaré. Grâce à toi je me sens mieux. Donc je t'offre ceci pour te remercier :" + vCharacter.getReward().getName());
                    return;
                } else {
                    this.aEngine.getGUI().println(this.aPseudo + "  a déjà reçu la récompense ou porte déjà une charge trop lourde.");
                    return;
                }
            } else {
                this.aEngine.getGUI().println("");
                return;
            } 
        }
        if(vCharacter.getName().equals("Shizue")){
            if(this.aItems.hasItem("Shojin.Ryori")){
                Item vPlat = this.aItems.getItem("Shojin.Ryori");
                this.aItems.removeItem("Shojin.Ryori", vPlat);
                this.aWeight -= vPlat.getWeight();
                if (!this.aItems.hasItem(vCharacter.getReward().getName())&& (canTake(vCharacter.getReward().getWeight()))) {// Vérification si le joueur n'a pas déjà la récompense
                    this.aItems.addItem(vCharacter.getReward().getName(), vCharacter.getReward());
                    this.aWeight += vCharacter.getReward().getWeight();
                    this.aEngine.getGUI().println("Je vois que tu possèdes le titre de samouraï et le shōjin ryōri qui atteste de ta méditation. Tu mérite donc de récupérer ce qui t'appartient : " + vCharacter.getReward().getName());
                    return;
                } else {
                    this.aEngine.getGUI().println(this.aPseudo + " a déjà reçu la récompense ou porte déjà une charge trop lourde.");
                    return;
                }
            } else {
                this.aEngine.getGUI().println("Je ne peux te donner ce qu'il t'appartient. Tu n'as pas médité ou tu ne possèdes pas le titre de samouraï.");
                return;
            } 
        }
        if(vCharacter.getName().equals("Maître.Kai") || vCharacter.getName().equals("Maître.Zoro")){
                 if (!this.aItems.hasItem(vCharacter.getReward().getName()) && (canTake(vCharacter.getReward().getWeight()))) { // Vérification si le joueur n'a pas déjà la récompense
                    this.aItems.addItem(vCharacter.getReward().getName(), vCharacter.getReward());
                    this.aWeight += vCharacter.getReward().getWeight();
                } else {
                    this.aEngine.getGUI().println(this.aPseudo + " a déjà reçu la récompense ou porte déjà une charge trop lourde.");
                    return;
                }
        } 
        if(vCharacter.getName().equals("Sogen")){
                 if (!this.aItems.hasItem(vCharacter.getReward().getName()) && (canTake(vCharacter.getReward().getWeight()))) { // Vérification si le joueur n'a pas déjà la récompense
                    this.aItems.addItem(vCharacter.getReward().getName(), vCharacter.getReward());
                    this.aWeight += vCharacter.getReward().getWeight();
                    this.aEngine.getGUI().println(this.aPseudo + " vient de méditer et reçoit donc : " + vCharacter.getReward().getName() + "\nLa récompense du shōjin ryōri après une méditation rigoureuse est considérée comme une nourriture qui soutient le corps et l'esprit, favorisant la clarté mentale et la paix intérieure. \nC'est une façon de célébrer la pratique et de cultiver la gratitude pour les bienfaits de la méditation.");
                } else {
                    this.aEngine.getGUI().println(this.aPseudo + " a déjà reçu la récompense ou porte déjà une charge trop lourde.");
                    return;
                }
        } 
        if(vCharacter instanceof SpecialCharacter){
            if(((SpecialCharacter)vCharacter).checkAllItems(this.aItems)){
                if (!this.aItems.hasItem(vCharacter.getReward().getName()) && (canTake(vCharacter.getReward().getWeight()))) { // Vérification si le joueur n'a pas déjà la récompense
                this.aItems.addItem(vCharacter.getReward().getName(), vCharacter.getReward());
                this.aWeight += vCharacter.getReward().getWeight();
                this.aEngine.getGUI().println(this.aPseudo + " est maintenant un samouraï accompli et reçoit donc le titre de samouraï : " + vCharacter.getReward().getName()); 
                return;
            } else {
                this.aEngine.getGUI().println(this.aPseudo + " a déjà reçu la récompense ou porte déjà une charge trop lourde."); 
                return;
            }
            } else {
                this.aEngine.getGUI().println(this.aPseudo + " est encore un samouraï junior...");
                return;
            }
        }
        this.aEngine.getGUI().println(vCharacter.getDialogString());
    }//talk()
    
    /**
     * Procédure permettant de répondre à un personnage.
     * 
     * @param pCommand La commande entrée par l'utilisateur.
     */
    public void reply(final Command pCommand){
        if(!pCommand.hasSecondWord()){
            this.aEngine.getGUI().println("Quel est votre réponse ?");
            return;
        }//if
        String [] vSecondWord = pCommand.getSecondWord().split(":");
        if(vSecondWord.length < 2){
            this.aEngine.getGUI().println("Vous devez renseigner : répondre personnage:réponse");
            return;
        }
        String vName = vSecondWord[0];
        String vReply = vSecondWord[1];
        Character vCharacter = this.aCurrentRoom.getCharacter(vName);
        if(vCharacter == null){
            this.aEngine.getGUI().println("Cette personne n'existe pas...");
            return;
        }//if
        vCharacter.upNbError();
        if(vCharacter.checkAnswer(vReply)){
            if(vCharacter.getReward() != null) {
                if(!this.aItems.hasItem(vCharacter.getReward().getName()) && (canTake(vCharacter.getReward().getWeight()))){
                    this.aEngine.getGUI().println("Bravo ! " + this.aPseudo + " a répondu correctement et gagne donc : " + vCharacter.getReward().getName());
                    this.aItems.addItem(vCharacter.getReward().getName(),vCharacter.getReward());  
                    this.aWeight += vCharacter.getReward().getWeight();
                    return;
                } else {
                    this.aEngine.getGUI().println(this.aPseudo + " a déjà reçu la récompense ou porte une charge trop lourde");
                    return;
                }  
            }
        } else {
            if(vCharacter.getNbError() > 10){
                JOptionPane.showInternalMessageDialog(null,"Désolé...Vous avez fait trop d'essais... \n Merci d'avoir joué au jeu  " + this.aPseudo + ". Au revoir !", "Message d'au revoir", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
                return;
            }
            this.aEngine.getGUI().println("Dommage...Il ne s'agit pas de la bonne réponse");
        }
    }//reply()
    
    /**
     * Procédure permettant de vérifier si le joueur peut remporter la partie.
     */
    public void toWin(){
        if(this.aCurrentRoom.getName().equals("Chinjusha")){
            if(this.aItems.hasItem("Badge") && this.aItems.hasItem("Shi")) {
                JOptionPane.showInternalMessageDialog(null,"Bravo ! Vous avez gagné ! Vous avez récupérer Shi. \n Merci d'avoir joué au jeu  " + this.aPseudo + ". Au revoir !", "Message d'au revoir", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else {
                this.aEngine.getGUI().println("Désolé... Vous n'avez pas récupérer Shi. Continuez votre aventure.");
            }
        } else  {
            this.aEngine.getGUI().println("Désolé... Vous n'êtes pas dans la bonne pièce pour récupérer Shi. Continuez votre aventure.");
        }
    }//toWin()
}//Player()
