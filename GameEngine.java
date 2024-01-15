import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * Classe GameEngine 
 * 
 * Cette classe crée toutes les salles, crée l'analyseur et démarre le jeu.
 * Elle évalue et exécute également les commandes renvoyées par l'analyseur. 
 * 
 *
 * @author HAKIM Justine
 * @version 25/03/2023
 */
public class GameEngine
{
    //Attributs
    private Parser aParser;// Commande de l'utilisateur
    private UserInterface aGui;//Interface visuelle
    private HashMap<String, Room> aRooms;// Stocke les pièces du jeu
    private Player aPlayer;//Joueur
    private String aName;//Nom du joueur
    private HashMap<String, Door> aDoors;//Stocke les portes du jeu
    private static final String MAP_IMAGE = "./Images/Plan.png";//Chemin d'accès à l'image de la carte du jeu
    private Character aCharacter;//Personnages dans le jeu
    
    /**
     * Constructeur par défaut pour les objets de la classe GameEngine
     * 
     * @param pPseudo Le pseudo du joueur 
     */ 
    public GameEngine(final String pPseudo)
    {
        this.aName = pPseudo;
        this.aParser = new Parser();
        this.aRooms = new HashMap<String, Room>();
        this.createRooms();
        this.aDoors = new HashMap<String,Door>();
        this.aPlayer = new Player(aName,this.aRooms.get("Karesansui"), this);
    }//GameEngine()
    
    /**
     * Accesseur de l'interface
     * Renvoie l'interface utilisateur (GUI) associée au jeu.
     * 
     * @return L'interface utilisateur associée au jeu
     */
    public UserInterface getGUI() 
    {
        return this.aGui;
    }//getGUI()
        
    /**
     * Procédure qui permet de modifier la valeur de l'interface et utilise printWelcome
     * Démarre l'interface graphique
     * 
     * @param pUserInterface Interface graphique
     */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.printWelcome();         
    }//setGUI()

    /**
     * Procédure qui permet d'afficher les messages de bienvenue au début du jeu     
     */
    private void printWelcome()
    {
    this.aGui.print( "\n" );
    this.aGui.println( "Bienvenue " + this.aPlayer.getPseudo() + " dans Shi : Kiyowara Fumiaki's Adventure !" );
    this.aGui.println( "Shi : Kiyowara Fumiaki's Adventure est un nouveau jeu d'aventure dans lequel " + this.aPlayer.getPseudo() + " va jouer le personnage de Kiyowara Fumiaki et va devoir récupérer Shi, le katana légendaire de la descendance de Kiyowara Fumiaki.");
    this.aGui.println( "Il va falloir que " + this.aPlayer.getPseudo() + " ramasse une armure complète de samouraï afin de pouvoir récupérer auprès de celui qui l'a volé, le katana." );
    this.aGui.println( "Taper 'aller + direction' pour changer de pièce ou bien utiliser les flèches directionnelles.");
    this.aGui.println( "Essayer 'aide' si vous en avez besoin." );
    this.aGui.println(this.aPlayer.getPseudo() + " peut encore faire "+ this.aPlayer.getMaxDeplacements() + " déplacements avant que le jeu prenne fin.");
    this.aGui.print( "\n" );
    this.printLocationInfo();
    }//printWelcome()
    
    /**
     * Crée les différentes pièces du temple et les relie entre elles en définissant leurs sorties.
     * Initialise également les objets et les personnages présents dans chaque pièce.
     */
    private void createRooms()
    {
        //Création des différents lieux du temple
        
        // Création des pièces du temple avec leurs descriptions et images
        Room vKaresansui = new Room("Karesansui","un jardin de rocaille.", "./Images/Karesansui.png");
        Room vChinjusha = new Room("Chinjusha","un petit sanctuaire.", "./Images/Chinjusha.png");
        Room vKyozo = new Room("Kyozo","un dépôt de livres traitant de l'histoire du temple.", "./Images/Kyozo.png");
        Room vYakushi_do = new Room("Yakushi.Do","un bâtiment dans lequel est vénérée une statue de Yakushi Nyorai.", "./Images/Yakushi_do.png");
        Room vDojo_1 = new Room("Dojo1","la pièce qui historiquement était la salle du temple religieux. Cette salle est maintenant utilisée pour l'enseignement des arts martiaux. Le dojo est un lieu où l'on progresse. Cette progression est obligatoirement supervisée et contrôlée par un maître. ", "./Images/Dojo_1.png");
        Room vHojo = new Room("Hojo","les quartiers d'habitations du prêtre responsable du temple. ", "./Images/Hojo.png");
        Room vHokke_do = new Room("Hokke.Do","une salle dont la disposition permet la marche autour d'une statue pour la méditation. Le but de la marche est de se concentrer et de chercher la vérité ultime.", "./Images/Hokke_do.png");
        Room vKairo = new Room("Kairo","un long passage couvert semblable à un portique reliant deux bâtiments.","./Images/Kairo.png");
        Room vHatto = new Room("Hatto","un bâtiment où l'abbé donne des conférences sur les écritures du bouddhisme.","./Images/Hatto.png");
        Room vKoro = new Room("Koro","la tour dans laquelle se trouve un tambour qui marque le passage du temps.","./Images/Koro.png");
        Room vDojo_2 = new Room("Dojo2","la pièce qui historiquement était la salle du temple religieux. Cette grande salle a aussi été utilisée par la suite pour l'enseignement des arts martiaux. Le dojo est un lieu où l'on progresse. Cette progression est obligatoirement supervisée et contrôlée par un maître.", "./Images/Dojo_2.png");
        Room vMi_do = new Room("Mi.Do","un bâtiment dans lequel est vénérée une statue sacrée. ", "./Images/Mi_do.png");
        Room vKuri = new Room("Kuri","un bâtiment abritant les cuisines du temple.","./Images/Kuri.png");
        Room vGate = new Room("Porte","la porte devant Hokke Do .","./Images/Gate.png");
        TransporterRoom vTransporterRoom = new TransporterRoom("TransporterRoom","une pièce qui vous téléporte aléatoirement dans une autre lorsque vous sortez.", "./Images/transporter_room.png");       
        
        vTransporterRoom.getRoomRandomizer().addRoom(vKairo);
        vTransporterRoom.getRoomRandomizer().addRoom(vKaresansui);
        vTransporterRoom.getRoomRandomizer().addRoom(vGate);
        vTransporterRoom.getRoomRandomizer().addRoom(vKyozo);


        //Sorties du Gate
        vGate.setExit("ouest", vHokke_do);
        vGate.setExit("est", vKaresansui);
        
        //Sorties du Jardin
        vKaresansui.setExit("montée",vKairo);
        vKaresansui.setExit("ouest", vGate);
        vKaresansui.setExit("est", vChinjusha);
        
        //Sortie du Sanctuaire
        vChinjusha.setExit("ouest",vKaresansui);
      
        //Sortie de la "Bibliothèque"
        vKyozo.setExit("nord", vYakushi_do);
        vKyozo.setExit("ouest", vKairo);
        
        //Sorties du batîment dans lequel on peut vénérer la statue Yakushi
        vYakushi_do.setExit("nord",vDojo_1);
        vYakushi_do.setExit("sud",vKyozo);
        
        //Sorties du Premier Dojo
        vDojo_1.setExit("sud",vYakushi_do);
        vDojo_1.setExit("nord",vHojo);
        
        //Sorties des Quartiers d'Habitation du prêtre
        vHojo.setExit("sud", vDojo_1);
        vHojo.setExit("ouest", vTransporterRoom);
        
        //Sortie de la salle de Méditation
        vHokke_do.setExit("est",vGate);
        
        //Sorties du long Passage 
        vKairo.setExit("descente",vKaresansui);
        vKairo.setExit("ouest", vKuri);
        vKairo.setExit("est", vKyozo);
        
        //Sorties du Batîment où l'abbé donne des conférences
        vHatto.setExit("sud", vKairo);
        vHatto.setExit("ouest", vKoro);
        vHatto.setExit("est", vTransporterRoom);
        
        //Sorties de la Tour
        vKoro.setExit("sud",vDojo_2);
        vKoro.setExit("est",vHatto);
        
        //Sorties du Deuxième Dojo
        vDojo_2.setExit("nord",vKoro);
        vDojo_2.setExit("sud",vMi_do);
        
        //Sorties du Batîment dans lequel est vénérée une statue sacrée
        vMi_do.setExit("nord",vDojo_2);
        vMi_do.setExit("sud",vKuri);
        
        //Sortie de la cuisine
        vKuri.setExit("nord",vMi_do);
                
        //Stockage des pièces
        this.aRooms.put("Karesansui",vKaresansui);
        this.aRooms.put("Chinjusha",vChinjusha);
        this.aRooms.put("Kyozo",vKyozo);
        this.aRooms.put("Yakushi_do",vYakushi_do);
        this.aRooms.put("Dojo_1",vDojo_1);
        this.aRooms.put("Hojo",vHojo);
        this.aRooms.put("Hokke_do",vHokke_do);
        this.aRooms.put("Kairo",vKairo);
        this.aRooms.put("Hatto",vHatto);
        this.aRooms.put("Koro",vKoro);
        this.aRooms.put("Dojo_2",vDojo_2);
        this.aRooms.put("Mi_do",vMi_do);
        this.aRooms.put("Kuri",vKuri);
        this.aRooms.put("Porte", vGate);
        this.aRooms.put("TransporterRoom", vTransporterRoom);
        
        //Création des Items
        Item vJinbaori = new Item ("Jinbaori","C'est un manteau court traditionnel japonais porté par les samouraïs pendant la période Edo (1603-1868). Il est fabriqué à partir de tissus épais et imperméables et est souvent orné de motifs colorés. Le jinbaori était souvent porté par-dessus le hitatare et le hakama pour fournir une couche supplémentaire de protection contre les éléments lors des batailles.",600);
        Item vLivreTorii = new Item("Livre.Torii", "Entre bande dessinée et carnet de voyage, ce livre plein d'humour et très documenté vous dit tout sur les sanctuaires shintoïstes, les temples bouddhistes et sur la place du sacré dans la vie des Japonais.", 500);
        Item vLivreShinto = new Item("Livre.Shinto", "C'est un livre sur la religion shintoïste, qui est la religion traditionnelle du Japon.",500);        
        Item vSauce = new Item ("Sauce.Soja","C'est une sauce salée et légèrement sucrée d'origine japonaise, faite à partir de soja, de blé, d'eau et de sel.",200);
        Item vSobas = new Item ("Sobas","Ce sont des nouilles fines et grises, généralement faites à partir de farine de sarrasin, consommées chaudes ou froides au Japon.",100);
        Item vSake = new Item ("Saké","C'est une boisson alcoolisée japonaise traditionnelle, fabriquée à partir de riz fermenté.",700);
        Item vKote = new Item ("Kote","Ce sont des gants de protection utilisés dans les arts martiaux japonais",350);      
        Item vHetH = new Item ("Hitatare.et.hakama","Il s'agit d'un costume traditionnel japonais porté par les hommes. Le hitatare est une veste ample portée sur un pantalon et le hakama est une jupe-culotte ample portée par-dessus.",3000);
        Beamer vBeamer = new Beamer("Shukkô.ki", "Il s'agit d'un objet qui permet de se téléporter dans une pièce précédemment chargée.",300);
        Item vClé = new Item ("Clé", "Il s'agit d'une clé qui permet d'ouvrir une porte verrouillée", 15);
        Item vBadge = new Item ("Badge", "Bravo, vous êtes un samouraï accompli", 10);
        Item vMengu = new Item("Mengu","masque en métal qui assure la protection complète du visage et de la gorge", 1500);
        Item vKutsu = new Item("Kutsu","chaussures qui sont faites de cuir",300);
        Item vDo = new Item("Do","une cuirasse composée de petites lamelles en cuir, trouées, et liées entre elles par un laçage de soie.", 4500) ;
        Item vSode = new Item("Sode","des épaulières. Elles s'attachent avec des cordons à la cuirasse.", 1500);
        Item vSuneate = new Item("Suneate","les jambières qui protègent le bas de la jambe, les genoux, et s'attachent à l'arrière.",760);
        Item vKusazuri = new Item("Kusazuri.et.Haidate","les jupes et sous-jupes qui protègent le bas du corps.", 2700);
        Item vKabuto = new Item("Kabuto","un casque composé de diverses plaques en métal forgé, rivetées entre elles auxquelles s'ajoute une série de panneaux souples protégeant le cou.", 3000);
        Item vYen50 = new Item("¥50", "Des chrysanthèmes avec l'inscription Japon au-dessus et la valeur faciale en-dessous",4);
        Item vMeditation = new Item("Shojin.Ryori","Il s'agit d'un repas végétarien préparé avec soin et dévotion.",170);
        Item vShi = new Item("Shi","le katana légendaire de la famille de Kiyowara Fumiaki.",900);
        
        
        //Placement des Items dans la pièce
        vKaresansui.getRoomItems().addItem("Jinbaori", vJinbaori);
        vYakushi_do.getRoomItems().addItem("Livre.Torii", vLivreTorii);
        vKyozo.getRoomItems().addItem("Livre.Shinto", vLivreShinto);
        vDojo_1.getRoomItems().addItem("Sauce.Soja", vSauce);
        vKuri.getRoomItems().addItem("Sobas", vSobas);
        vKuri.getRoomItems().addItem("Saké", vSake);
        vHojo.getRoomItems().addItem("Kote", vKote);
        vKairo.getRoomItems().addItem("Hitatare.et.hakama", vHetH);
        vKairo.getRoomItems().addItem("Shukkô.ki", vBeamer);
        vDojo_1.getRoomItems().addItem("¥50", vYen50);
        vDojo_2.getRoomItems().addItem("¥50", vYen50);
        
        vGate.lockDoor("ouest", vClé);
        vHokke_do.lockDoor("est", vClé);
        
        vKaresansui.lockDoor("est", vBadge);
        vChinjusha.lockDoor("ouest", vBadge);
        
        vChinjusha.addCharacter(new Character("Shizue","Je vois que tu possèdes le titre de samouraï et le shōjin ryōri qui atteste de ta méditation. Tu mérites donc de récupérer ce qui t'appartient.",null,vShi));
        vHokke_do.addCharacter(new Character("Sogen","Une récompense traditionnelle après une méditation intense et prolongée est appelée shōjin ryōri. Le shōjin ryōri est associé à la pratique bouddhiste zen et est réputé pour sa simplicité, sa fraîcheur et son équilibre.",null,vMeditation));
        vKaresansui.addCharacter(new SpecialCharacter("Haruki",null,null,vBadge));
        vKoro.addCharacter(new Character("Katakuri","Si tu réponds correctement à cette énigme, je te donnerai une récompense. Voici mon énigme : \n Cette chose, toutes choses dévore. Oiseaux bêtes arbres fleurs. Il ronge le fer et mord l’acier. Il réduit les cailloux en poussière.Il tue les rois et détruit les villes. Qui est-ce ?","temps", vKutsu));
        vHatto.addCharacter(new Character("Abbé","Le Bouddha a dit : Comme une mère au risque de sa vie protège son unique enfant, ainsi, avec un cœur sans bornes, devrais-je chérir tous les êtres vivants.", null, null));
        vKaresansui.addCharacter(new Character("Yuri", "Bienvenue dans le temple de Shizue. Je suis Yuri, un moine.\n Shi se trouve dans la pièce Chinjusha. Pour y accéder tu dois te munir d'une armure complète de samouraï. \nJe te conseille de charger ton Shukkô ki dans la pièce où tu le trouveras...", null,null));
        vDojo_1.addCharacter(new Character("Maître.Kai", "Je vois que tu progresses, prends cet objet, il t'aidera à progresser davantage.", null,vDo));
        vDojo_2.addCharacter(new Character("Maître.Zoro", "Je vois que tu progresses, prends cet objet, il t'aidera à progresser davantage.", null,vSode));
        vYakushi_do.addCharacter(new Character("Yakushi.Nyorai", null, null,vKabuto));
        vMi_do.addCharacter(new Character("Statue.Sacrée", null, null,vKusazuri));
        vKuri.addCharacter(new Character("Sanji","Bienvenue dans ma cuisine. Je suis Sanji, le cuistot du temple.\n Je travaille sur une recette et il me manque un ingrédient. As-tu une idée de ce que je pourrai rajouter ?\n Pourrais tu me l'amener ?",null, vSuneate ));
        vKyozo.addCharacter(new Character("Dan", "Je suis Dan et je suis en charge de protéger l'histoire du temple.\n Je connais absolument tout de l'histoire des temples. J'ai perdu cependant un livre. Pourrais-tu me l'amener ?", null,vMengu));
        vHojo.addCharacter(new Character("Prêtre", "Si tu réponds correctement à cette énigme, je te donnerai une récompense. Voici mon énigme : \n Tu mesures ma vie en heures et je te sers en expirant.\n Je suis rapide quand je suis mince et lente quand je suis grosse.\n Le vent est mon ennemi.\n Qui suis-je ?","bougie", vClé));
    }//createRooms()
    
    /**
     * Affiche une carte dans une boîte de dialogue.
     * Utilise l'image de la carte spécifiée pour afficher la carte.
     */
    private void showMap(){
        //L'image de la carte.
        final ImageIcon vMapImage = new ImageIcon(getClass().getResource(MAP_IMAGE));
        //Affiche la boîte de dialogue contenant l'image de la carte.
        //Le titre de la boîte de dialogue est "Carte".
        JOptionPane.showMessageDialog(null, vMapImage, "Carte", JOptionPane.PLAIN_MESSAGE);
    }//showMap()
        
    /**
     * Appelle la méthode souhaitée par le joueur
     * Transforme et analyse la commande de l'utilisateur
     * Interprète une commande spécifiée et exécute l'action correspondante.
     * 
     *  @param pCommandLine Commande à interpréter, écrite par le joueur
     */
    public void interpretCommand( final String pCommandLine ) 
    {
        this.aGui.println( "> " + pCommandLine );//Affiche les caractères tapés au clavier
        Command vCommand = this.aParser.getCommand( pCommandLine );//Converti en commande

        if ( vCommand.isUnknown() ) {
            this.aGui.println( "La demande ne peut pas être prise en compte..." );
            return;
        }//if

        String vCommandWord = vCommand.getCommandWord();
        switch(vCommandWord){
            case "aller":
                this.aPlayer.goRoom(vCommand);
                this.printLocationInfo();
                break;
            case "quitter":
                this.quitter(vCommand);
                break;
            case "aide":
                this.printAide();
                break;
            case "regarder":
                this.aPlayer.look(vCommand);
                break;
            case "ingérer" :
                this.aPlayer.eat(vCommand);
                break;
            case "reculer" :
                this.aPlayer.back();
                this.printLocationInfo();
                break;
            case "test":
                this.test(vCommand);
                break;
            case "prendre" :
                this.aPlayer.take(vCommand);
                break;
            case "jeter" : 
                this.aPlayer.drop(vCommand);
                break;
            case "inventaire":
                this.aPlayer.inventory();
                break;
            case "charger" :
                this.aPlayer.charge(vCommand);
                break;
            case "décharger" :
                this.aPlayer.fire(vCommand);
                break;
            case "carte":
                this.showMap();
                break;
            case "alea" :
                this.alea(vCommand);
                break;
            case "parler" :
                this.aPlayer.talk(vCommand);
                break;
            case "répondre" :
                this.aPlayer.reply(vCommand);
                break;
            case "gagner" :
                this.aPlayer.toWin();
                break;
        }//switch
    }//interpretCommand()

    /**
     * Procédure qui affiche les commandes d'aides possibles
     */
    private void printAide() 
    {      
    this.aGui.println("\n" + this.aPlayer.getPseudo() + " se balade dans le temple de Shizue. \n\n Les commandes disponibles sont :\n"); 
    this.aGui.println(this.aParser.getCommandString() + "\n");
    this.aGui.println("Taper 'carte' si " + this.aPlayer.getPseudo() + " veut voir la carte.");
    this.aGui.println("Taper 'aller + direction' pour changer de pièce ou bien les flèches directionnelles pour nord, sud, est et ouest. Utiliser Page_Up et Page_Down pour montée ou descendre. La touche back_space permet de reculer.");
    this.aGui.println("Utiliser Tabulation sur le champ de saisie, après avoir utilisé les flèches directionnelles pour pouvoir soit utiliser à présent les boutons ou la saisie de texte.");
    this.aGui.println("Taper 'quitter' si " + this.aPlayer.getPseudo() + " veut arrêter son aventure.");
    this.aGui.println("Taper 'regarder' si " + this.aPlayer.getPseudo() + " veut regarder ce qu'il se passe dans les environs...");
    this.aGui.println("Taper 'regarder' + le nom de l'objet dans l'inventaire de " + this.aPlayer.getPseudo() + " pour obtenir sa description." );
    this.aGui.println("Taper 'ingérer + nom de l'objet' si  " + this.aPlayer.getPseudo() + " veut manger.");
    this.aGui.println("Taper 'reculer' si "+ this.aPlayer.getPseudo() + " veut retourner dans la pièce précédente.");
    this.aGui.println("Taper 'prendre + nom de l'objet' si "+ this.aPlayer.getPseudo() + " veut ramasser un objet de la pièce.");
    this.aGui.println("Taper 'jeter + nom de l'objet' si " + this.aPlayer.getPseudo() + " veut jeter un objet de son inventaire.");
    this.aGui.println("Taper 'inventaire' si " + this.aPlayer.getPseudo() + " veut voir le contenu de celui-ci.");
    this.aGui.println("Taper 'charger' si " + this.aPlayer.getPseudo() + " veut charger Shukkô ki.");
    this.aGui.println("Taper 'décharger' si " + this.aPlayer.getPseudo() + " veut décharger Shukkô ki.");
    this.aGui.println("Taper 'parler + nom du personnage' si " + this.aPlayer.getPseudo() + " veut échanger avec un personnage.");
    this.aGui.println("Taper 'répondre + nom du personnage:votre réponse' si " + this.aPlayer.getPseudo() + " veut répondre à un personnage.");
    this.aGui.println("Taper 'gagner' si " + this.aPlayer.getPseudo() + " a récupéré Shi.");
    this.aGui.println(this.aPlayer.getPseudo() + " peut encore faire "+ this.aPlayer.getMaxDeplacements() + " déplacements avant que le jeu prenne fin.");
    }//printAide()
    
    /**
     * Procédure qui vérifie si le compteur de déplacements n'a pas atteint 0
     * 
     * Si le compteur de déplacements est à 0 alors le jeu s'arrête
     */
    public void timerEnd() {
        if(this.aPlayer.getMaxDeplacements() == 0){
            JOptionPane.showInternalMessageDialog(null,this.aPlayer.getPseudo() + " a fait trop de déplacements et a perdu.", "Message d'au revoir", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }//if()
    }//timerEnd()
    
    /**
     * Termine le jeu
     */
    public void endGame() 
    {
        int exit = JOptionPane.showConfirmDialog(null,"" + this.aPlayer.getPseudo() + ". Êtes-vous sûr de vouloir quitter? :/","Quitter?",JOptionPane.YES_NO_OPTION);
        if (exit == JOptionPane.YES_OPTION)
            {
                JOptionPane.showInternalMessageDialog(null,"Merci d'avoir joué au jeu " + this.aPlayer.getPseudo() + ". Au revoir !", "Message d'au revoir", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }else {
            this.aGui.enable(true); 
        }
    }//endGame()
    
    /**
     * Méthode qui permet de quitter le jeu 
     * 
     * @param pCommand Commande rentrée par le joueur
     *  Vrai si le joueur ecrit "quitter", Faux s'il y a un second mot rentre par le joueur
     */
    private void quitter(final Command pCommand) 
    {
        if (pCommand.hasSecondWord()){
          this.aGui.println("Quitter ?");
        } else {
           this.endGame();
        }//else
    }//quitter()    
    
    /**
    * Affiche les informations de la salle actuelle du joueur.
    */
    public void printLocationInfo()
    {
    this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription());
    if( this.aPlayer.getCurrentRoom().getImageName() != null)
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName());
    }//printLocationInfo()

    /**
     * Récupère une pièce par rapport à une description donnée.
     * 
     * @param pDescription Description de la pièce à rechercher
     * @return Une pièce par rapport à la description donnée ou nul s'il n'y a aucune qui ne correspond.
     */
    public Room getRoom(final String pDescription)
    {
        return this.aRooms.get(pDescription);
    }//getRoom()
    
    /**
     * Cette méthode permet à l'utilisateur de spécifier si la salle choisie dans la pièce de téléportation doit être sélectionnée de manière aléatoire ou si une salle spécifique doit être forcée. 
     * L'utilisateur peut fournir une commande avec un deuxième mot pour spécifier la salle à forcer, ou ne pas fournir de deuxième mot pour réinitialiser la sélection aléatoire.
     * 
     * @param pCommand La commande spécifiant l'action à effectuer. Si elle contient un deuxième mot, celui-ci sera utilisé comme salle à forcer.
     */
    public void alea(final Command pCommand) {
        TransporterRoom vTransporterRoom2 = (TransporterRoom) this.aRooms.get("TransporterRoom");
        if(pCommand.hasSecondWord())
        {
            this.aGui.println(vTransporterRoom2.getRoomRandomizer().setForcedRoom(pCommand.getSecondWord()));
        } else {
            vTransporterRoom2.getRoomRandomizer().setForcedRoom("");
            this.aGui.println("La sortie est de nouveau définie de manière aléatoire");
        }
    }//alea()
    
    /**
     * Cette méthode permet d'exécuter une séquence de commandes à partir d'un fichier spécifié. 
     * Le fichier doit être au format texte (.txt) et doit contenir une commande par ligne.
     * La méthode lit le fichier, interprète chaque ligne comme une commande et l'exécute en appelant la méthode `interpretCommand()`. 
     * Les résultats des commandes sont affichés dans l'interface utilisateur.
     *
     * @param pCommand La commande spécifiant le fichier à utiliser pour le test. Le deuxième mot de la commande doit être le nom du fichier.
     */
    private void test(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()) {
            this.aGui.println("Que voulez-vous tester?");
            return;
        }
        String vFile = pCommand.getSecondWord();
        if(!vFile.contains(".txt")){
            vFile += ".txt";
        }
        try {
            Scanner vScan = new Scanner(new File (vFile));
            this.aGui.println("Test " + vFile + "...");
            while(vScan.hasNextLine()){
                this.interpretCommand(vScan.nextLine());         
            }
        }catch(final FileNotFoundException pE){
            this.aGui.println("Désolé, le ficher " + vFile + " n'a pas été trouvé. Réessayer");
        }
    }//test()
}//GameEngine()