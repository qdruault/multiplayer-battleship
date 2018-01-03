package com.utclo23.data.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.utclo23.com.ComFacade;
import com.utclo23.data.configuration.Configuration;
import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.structure.*;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import java.net.Inet4Address;

/**
 * User mediator related to user features
 *
 * @author wuxiaoda
 */
public class UserMediator {

    /**
     * map that store users
     */
    private Map<String, LightPublicUser> mapConnectedUser;
    /**
     * owner who is the current user
     */
    private Owner owner;
    /**
     * reference to the data facade
     */
    private DataFacade dataFacade;

    /**
     * constructor
     *
     * @param dataFacade reference to the facade
     */
    public UserMediator(DataFacade dataFacade) {
        this.dataFacade = dataFacade;
        this.mapConnectedUser = new HashMap<>();
    }

    /**
     * get connected user
     *
     * @return
     */
    public List<LightPublicUser> getConnectedUsers() {
        return  new ArrayList<>(this.mapConnectedUser.values());
  
    }

    /**
     * get the local connected user
     *
     * @return
     */
    public Owner getMyOwnerProfile() {
        return owner;
    }

    /**
     * set the owner
     *
     * @param owner
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * get facade
     *
     * @return
     */
    public DataFacade getDataFacade() {
        return dataFacade;
    }

    /**
     * set facade
     * @param dataFacade
     */
    public void setDataFacade(DataFacade dataFacade) {
        this.dataFacade = dataFacade;
    }

    /**
     * extract bytes from a file
     *
     * @param imageName
     * @return Byte array
     * @throws IOException
     */
    private byte[] extractBytes(String imageName) {
        String format = "jpg"; //jpg by default
        byte[] imageInByte = null;

        // open image
        File imgPath = new File(imageName);
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(imgPath);

            //Get image format
            ImageInputStream iis = ImageIO.createImageInputStream(imgPath);

            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);

            while (imageReaders.hasNext()) {
                ImageReader reader =  imageReaders.next();
                format = reader.getFormatName();
            }

            //Get the array of bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, format, baos);
            imageInByte = baos.toByteArray();

       
        } catch (IOException ex) {
            Logger.getLogger(UserMediator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imageInByte;
    }

    /**
     * return owner public profile
     *
     * @return public user
     */
    public PublicUser getMyPublicUserProfile() {
        PublicUser publicUser = null;
        if (this.owner != null) {
            publicUser = this.owner.getUserIdentity();
        }
        return publicUser;
    }
    
    /**
     *
     * @return
     */
    public LightPublicUser getMyLightPublicUserProfile() {
        LightPublicUser user = null;
        if(this.owner != null) {
            user = this.owner.getUserIdentity().getLightPublicUser();
        }
        return user;
    }

    /**
     * create a user
     *
     * @param playerName
     * @param password
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param fileImage
     * @throws DataException
     */
    public void createUser(String playerName, String password, String firstName, String lastName, Date birthDate, String fileImage) throws DataException {

        //blank playername or password
        if (playerName.isEmpty() || password.isEmpty()) {
            throw new DataException("Data : error due to  playername or password");
        }

        //determine the parth
        String path = Configuration.SAVE_DIR + File.separator + playerName + ".json";

        //all uppercase

        firstName = firstName.toUpperCase();
        lastName = lastName.toUpperCase();

        File userFile = new File(path);

        //check the existence of the file
        if (userFile.exists()) {
            throw new DataException("account already exists"); // throw related error
        } else {

            //create user 
            String id = new UID().toString();
            LightPublicUser lightPublicUser = new LightPublicUser(id, playerName);

            PublicUser publicUser = new PublicUser(lightPublicUser, lastName, firstName, birthDate);

            //for unit test
            if (!this.dataFacade.isTestMode()) {

                //blank playername or password
                if (fileImage.isEmpty()) {
                    throw new DataException("Data : error due to empty image");
                }

                publicUser.setAvatar(this.extractBytes(fileImage));
                publicUser.getLightPublicUser().setAvatarThumbnail(this.createThumbnail(fileImage));
            }

            this.owner = new Owner();
            owner.setUserIdentity(publicUser);
            owner.setPassword(password);

            //save user in json file
            save();
            
            //Disconnect the user
            this.owner = null;

        }
    }

    /**
     * Update user
     *
     * @param password
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param fileImage
     * @throws DataException
     */
    public void updateUser(String password, String firstName, String lastName, Date birthDate, String fileImage) throws DataException {

        if (this.owner != null) {

            //blank  password
            if (password.isEmpty()) {
                throw new DataException("Data : error due to empty playername or password");
            }

            //password = password.toUpperCase();
            firstName = firstName.toUpperCase();
            lastName = lastName.toUpperCase();

            this.owner.setPassword(password);
            if (!this.getDataFacade().isTestMode()) {
                if (fileImage.isEmpty()) {
                    throw new DataException("Data : error due to empty image");
                }
                this.owner.getUserIdentity().setAvatar(this.extractBytes(fileImage));
                this.owner.getUserIdentity().getLightPublicUser().setAvatarThumbnail(this.createThumbnail(fileImage));

            }

            this.owner.getUserIdentity().setBirthDate(birthDate);
            this.owner.getUserIdentity().setFirstName(firstName);
            this.owner.getUserIdentity().setLastName(lastName);

            save();

            //remove old profile and add new one
            //to check by data module
            ComFacade comFacade = this.dataFacade.getComfacade();
            if (comFacade != null) {
                if (this.owner != null) {
                    comFacade.notifyUserSignedOut();
                    comFacade.notifyUserSignedIn();
                }
            }

        } else {
            throw new DataException("Data : error in updating");
        }

    }

    /**
     * get user profile
     *
     * @param id
     * @return
     */
    public LightPublicUser getLightPublicUser(String id) {
        LightPublicUser lightPublicUser = null;

        if (this.mapConnectedUser.containsKey(id)) {
            lightPublicUser = this.mapConnectedUser.get(id);
        }

        return lightPublicUser;
    }

    /**
     * connection
     *
     * @param username
     * @param password
     * @throws DataException
     */
    public void signIn(String username, String password) throws DataException {

        //uppercase
   
        //already connected
        if (this.owner != null) {
            throw new DataException("Data : already connected"); //throw related error
        }

        //determine path
        String path = Configuration.SAVE_DIR + File.separator + username + ".json";
        File userFile = new File(path);
        //check the existence
        if (!userFile.exists()) {
            throw new DataException("Data : error in save user file"); //throw related error
        }

        ObjectMapper mapper = new ObjectMapper();

        //retrieval
        Owner user = null;
        try {
            user = mapper.readValue(userFile, Owner.class);
        } catch (Exception e) {
            Logger.getLogger("log").info(e.getMessage());
            throw new DataException("Data : error in reading file");
        }

        //password matching 
        if (user != null && !user.getPassword().equals(password)) {
            throw new DataException("Data : error in password");
        } else {

            this.owner = user;

            //notification
            ComFacade comFacade = this.dataFacade.getComfacade();
            if (comFacade != null) {
                if (this.owner != null) {
                    comFacade.notifyUserSignedIn();

                    Inet4Address ip;

                    List<Inet4Address> listIpTarget = new ArrayList<>();
                    for (String ipString : owner.getDiscoveryNodes()) {
                        try {
                            ip = (Inet4Address) Inet4Address.getByName(ipString);
                            listIpTarget.add(ip);
                        } catch (Exception e) {
                            throw new DataException("Data : IP not valid");
                        }
                    }
                    //comFacade.sendDiscovery(listIpTarget);
                }
            }

        }
    }

    /**
     * disconnection
     * @throws com.utclo23.data.module.DataException
     */
    public void signOut() throws DataException {
        if (this.owner != null) {

            this.save(); //Save the file

            //notification
            ComFacade comFacade = this.dataFacade.getComfacade();
            if (comFacade != null) {
                if (this.owner != null) {
                    comFacade.notifyUserSignedOut();
                    this.getConnectedUsers().clear();
                }
            }

            //owner destroyed
            this.owner = null;

        } else {
            throw new DataException("Data : no connected user");
        }

    }

    /**
     * save
     */
    private void save() throws DataException {

        try {
            //determine the path
            String path = Configuration.SAVE_DIR + File.separator + owner.getUserIdentity().getPlayerName() + ".json";

            //save into a json file
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(path), owner);      
        } catch (Exception e) {
            throw new DataException("Data : error in save process"); //throw related error by using exception of DataModule
        }

    }

    /**
     * add user to the list of connected users
     *
     * @param usr
     * @exception RuntimeException if the user is already in the list of
     * connected users.
     */
    public void addConnectedUser(LightPublicUser usr) {
        if (!this.mapConnectedUser.containsKey(usr.getId())) {
            this.mapConnectedUser.put(usr.getId(), usr);
        } 
    }

    /**
     * remove connected user
     *
     * @param usr
     */
    public void removeConnectedUser(LightPublicUser usr) {
        if (this.mapConnectedUser.containsKey(usr.getId())) {
            this.mapConnectedUser.remove(usr.getId());
        } 
    }

    /**
     * get the discovery nodes
     *
     * @return
     */
    public List<String> getIPDiscovery() {
        return this.owner.getDiscoveryNodes();
    }

    /**
     * Resize the avatar to get a thumbnail
     *
     * @param ImageName
     * @return Bytes array of the thumbnail
     * @throws IOException
     */
    private byte[] createThumbnail(String ImageName) throws DataException {
        try {
            String format = "jpg"; //jpg by default
            int thumbnailWidth = 100;
            File imgPath = new File(ImageName);
            BufferedImage originalBufferedImage = ImageIO.read(imgPath);

            //Get image format
            ImageInputStream iis = ImageIO.createImageInputStream(imgPath);

            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);

            while (imageReaders.hasNext()) {
                ImageReader reader =  imageReaders.next();
                format = reader.getFormatName();
            }

            //Resize the image and calculate the scaling depending on width and height
            int widthToScale, heightToScale;
            if (originalBufferedImage.getWidth() > originalBufferedImage.getHeight()) {

                heightToScale = (int) (1.1 * thumbnailWidth);
                widthToScale = (int) ((heightToScale * 1.0) / originalBufferedImage.getHeight()
                        * originalBufferedImage.getWidth());

            } else {
                widthToScale = (int) (1.1 * thumbnailWidth);
                heightToScale = (int) ((widthToScale * 1.0) / originalBufferedImage.getWidth()
                        * originalBufferedImage.getHeight());
            }

            BufferedImage resizedImage = new BufferedImage(widthToScale,
                    heightToScale, originalBufferedImage.getType());
            Graphics2D g = resizedImage.createGraphics();

            //Interpolation to avoid loss in quality
            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.drawImage(originalBufferedImage, 0, 0, widthToScale, heightToScale, null);
            g.dispose();

            int x = (resizedImage.getWidth() - thumbnailWidth) / 2;
            int y = (resizedImage.getHeight() - thumbnailWidth) / 2;

            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("Width of new thumbnail is bigger than original image");
            }

            //Get the array of bytes of the image (serialization)
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, format, baos);
            byte[] imageInByte = baos.toByteArray();

            //BufferedImage imgbck = ImageIO.read(new ByteArrayInputStream(imageInByte));
            //File outputfile = new File("D:\\Fabien Boucaud\\Pictures\\markerthumbnail.PNG");
            //ImageIO.write(imgbck, format, outputfile);
            return (imageInByte);
        } catch (Exception e) {
            throw new DataException("Error while trying to resize the thumbnail:" + e.getMessage());
        }
    }

    /**
     * set the discovery nodes
     *
     * @param discoveryNodes
     * @throws com.utclo23.data.module.DataException
     */
    public void setIPDiscovery(List<String> discoveryNodes) throws DataException {
        try {
            if (this.owner != null) {
                this.owner.setDiscoveryNodes(discoveryNodes);
                save();

                // Create the Inet4Address list
                List<Inet4Address> ips = new ArrayList<>();
                for (String stringIp : discoveryNodes) {

                    Inet4Address inetIp = (Inet4Address) InetAddress.getByName(stringIp);
                    ips.add(inetIp);
                }
                if (!this.dataFacade.isTestMode() && this.getDataFacade().getComfacade() != null) {
                    this.getDataFacade().getComfacade().sendDiscovery(ips);
                }
            } else {
 
                throw new DataException("Data : error in setting discovery nodes");
            }
        } catch (Exception e) {
            
            throw new DataException("Data : error in setting discovery nodes");
        }

    }

    /**
     *
     * @param game
     * @throws DataException
     */
    public void saveGame(Game game) throws DataException {
        if (this.owner != null) {
            game.prepareToBeSaved();
            owner.getSavedGamesList().add(game);

            //Save 
            this.save();

        }
    }
    
    /**
     * Add a played game to the owner's played games list.
     * 
     * @param game 
     */
    public void addPlayedGame(StatGame game) {
        this.owner.addPlayedGame(game);
    }
    
       /**
     * Update user
     *
     * @param playername
     * @throws DataException
     */
    public void updatePlayername(String playername) throws DataException {

        if (this.owner != null) {

            //blank playername  
            if (playername.isEmpty()) {
                throw new DataException("Data : error due to empty playername");
            }            

           
            if (!this.getDataFacade().isTestMode()) {
                this.owner.getUserIdentity().getLightPublicUser().setPlayerName(playername);
            }
            

            //remove old profile and add new one
          
            
            save();
            
            //to check by data module
            ComFacade comFacade = this.dataFacade.getComfacade();
            if (comFacade != null) {
                if (this.owner != null) {
                    comFacade.notifyUserSignedOut();
                    comFacade.notifyUserSignedIn();
                }
            }

        } else {
            throw new DataException("Data : error in updating");
        }

    }
    
    /**
     *
     * @param firstName
     * @throws DataException
     */
    public void updateFirstname(String firstName) throws DataException {

        if (this.owner != null) {

            //blank  password
            if (firstName.isEmpty()) {
                throw new DataException("Data : error due to empty first name");
            }
            firstName = firstName.toUpperCase();          
            this.owner.getUserIdentity().setFirstName(firstName);

            save();

            //remove old profile and add new one
            //to check by data module
            ComFacade comFacade = this.dataFacade.getComfacade();
            if (comFacade != null) {
                if (this.owner != null) {
                    comFacade.notifyUserSignedOut();
                    comFacade.notifyUserSignedIn();
                }
            }
        } else {
            throw new DataException("Data : error in updating");
        }

    }

    /**
     *
     * @param lastName
     * @throws DataException
     */
    public void updateLastname(String lastName) throws DataException {
        if (this.owner != null) {

            if (lastName.isEmpty()) {
                throw new DataException("Data : error due to empty lastName");
            }

            lastName = lastName.toUpperCase();
            this.owner.getUserIdentity().setLastName(lastName);

            save();

            //remove old profile and add new one
            //to check by data module
            ComFacade comFacade = this.dataFacade.getComfacade();
            if (comFacade != null) {
                if (this.owner != null) {
                    comFacade.notifyUserSignedOut();
                    comFacade.notifyUserSignedIn();
                }
            }

        } else {
            throw new DataException("Data : error in updating");
        }

    }
    
    /**
     *
     * @param birthDate
     * @throws DataException
     */
    public void updateBirthdate(Date birthDate) throws DataException {

        if (this.owner != null) {     
            this.owner.getUserIdentity().setBirthDate(birthDate);
            save();

            ComFacade comFacade = this.dataFacade.getComfacade();
            if (comFacade != null) {
                if (this.owner != null) {
                    comFacade.notifyUserSignedOut();
                    comFacade.notifyUserSignedIn();
                }
            }

        } else {
            throw new DataException("Data : error in updating");
        }

    }
    
    /**
     *
     * @param fileImage
     * @throws DataException
     */
    public void updateFileImage(String fileImage) throws DataException {

        if (this.owner != null) {
            if (!this.getDataFacade().isTestMode()) {
                if (fileImage.isEmpty()) {
                    throw new DataException("Data : error due to empty image");
                }
                this.owner.getUserIdentity().setAvatar(this.extractBytes(fileImage));
                this.owner.getUserIdentity().getLightPublicUser().setAvatarThumbnail(this.createThumbnail(fileImage));

            }      
            save();

            //remove old profile and add new one
            //to check by data module
            ComFacade comFacade = this.dataFacade.getComfacade();
            if (comFacade != null) {
                if (this.owner != null) {
                    comFacade.notifyUserSignedOut();
                    comFacade.notifyUserSignedIn();
                }
            }

        } else {
            throw new DataException("Data : error in updating");
        }

    }
    
    /**
     *
     * @param password
     * @throws DataException
     */
    public void updatePassword(String password) throws DataException {

        if (this.owner != null) {

            //blank  password
            if (password.isEmpty()) {
                throw new DataException("Data : error due to empty  password");
            }

            this.owner.setPassword(password);
            save();

            //remove old profile and add new one
            //to check by data module
            ComFacade comFacade = this.dataFacade.getComfacade();
            if (comFacade != null) {
                if (this.owner != null) {
                    comFacade.notifyUserSignedOut();
                    comFacade.notifyUserSignedIn();
                }
            }

        } else {
            throw new DataException("Data : error in updating");
        }

    }

    /**
     *
     * send number of victories
     *
     * @return int number of victories
     */
    public int getNumberVictories()  {
        if(this.owner != null){
        List<StatGame> games = this.getMyOwnerProfile().getPlayedGamesList() ;
        LightPublicUser user = this.getMyLightPublicUserProfile() ; 
        int nbVictories = 0;
        for (StatGame g : games) {
            if ((g.getWinner() == user) && !(g.isGameAbandonned())) {
                nbVictories++ ;
            }                
        }
        return nbVictories ; 
        }
        return 0;
    }

    /**
     *
     * send number of defeats
     *
     * @return int number of defeats
     */
    public int getNumberDefeats()  {
        if(this.owner != null){
        List<StatGame> games = this.getMyOwnerProfile().getPlayedGamesList() ;
        LightPublicUser user = this.getMyLightPublicUserProfile() ; 
        int nbDefeats = 0;
        for (StatGame g : games) {
            if ((g.getWinner() != user) && !(g.isGameAbandonned())) {
                nbDefeats++ ;
            }                
        }
        return nbDefeats ; 
        }
        return 0;
    }
    
    /**
     *
     * send number of abandons
     *
     * @return int number of abandons
     */
    public int getNumberAbandons() {
        if(this.owner!=null){
        List<StatGame> games = this.getMyOwnerProfile().getPlayedGamesList() ;
        //LightPublicUser user = this.getMyLightPublicUserProfile() ; 
        int nbAbandons = 0;
        for (StatGame g : games) {
            if ((g.getWinner() == null) && (g.isGameAbandonned())) {
                nbAbandons++ ;
            }                
        }
        return nbAbandons ; 
        }
        return 0;
    }
}
