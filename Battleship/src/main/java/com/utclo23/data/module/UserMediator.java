package com.utclo23.data.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.utclo23.data.configuration.Configuration;
import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.structure.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Création du mediator");

        this.dataFacade = dataFacade;
        this.mapConnectedUser = new HashMap<String, LightPublicUser>();
    }

    /**
     * get connected user
     *
     * @return
     */
    public List<LightPublicUser> getConnectedUsers() {
        List<LightPublicUser> listConnectedUser = new ArrayList<LightPublicUser>(this.mapConnectedUser.values());
        return listConnectedUser;
    }

    /**
     * get the local connected user
     *
     * @return
     */
    public Owner getOwner() {
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
     */
    public void setDataFacade(DataFacade dataFacade) {
        this.dataFacade = dataFacade;
    }

    /**
     * extract bytes from a file
     *
     * @param ImageName
     * @return
     * @throws IOException
     */
    private byte[] extractBytes(String ImageName) throws IOException {
        // open image
        File imgPath = new File(ImageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
        return (data.getData());
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
     * create a user
     *
     * @param playerName
     * @param password
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param fileImage
     * @throws Exception
     */
    public void createUser(String playerName, String password, String firstName, String lastName, Date birthDate, String fileImage) throws Exception {
        String path = Configuration.SAVE_DIR + File.separator + playerName + ".json";
        File userFile = new File(path);
        if (userFile.exists()) {
            throw new Exception("account already exists");
        } else {

            String id = new UID().toString();
            LightPublicUser lightPublicUser = new LightPublicUser(id, playerName);
            //TODO thumbnail
            PublicUser publicUser = new PublicUser(lightPublicUser, lastName, firstName, birthDate);
            publicUser.setAvatar(this.extractBytes(fileImage));

            this.owner = new Owner();
            owner.setUserIdentity(publicUser);
            owner.setPassword(password);

            save();

        }
    }

    public void updateUser(String password, String firstName, String lastName, Date birthDate, String fileImage) throws Exception {

        if (this.owner != null) {

            this.owner.setPassword(password);
            this.owner.getUserIdentity().setAvatar(this.extractBytes(fileImage));
            //TODO update thumbnail
            this.owner.getUserIdentity().setBirthDate(birthDate);
            this.owner.getUserIdentity().setFirstName(firstName);
            this.owner.getUserIdentity().setLastName(lastName);
            
            save();

            //TODO notify network
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
     * @throws Exception
     */
    public void signIn(String username, String password) throws Exception {
      
        String path = Configuration.SAVE_DIR + File.separator + username + ".json";
        File userFile = new File(path);
        if (!userFile.exists()) {
            throw new Exception("erreur");
        }

        ObjectMapper mapper = new ObjectMapper();
        Owner user = mapper.readValue(userFile, Owner.class);

        if (!user.getPassword().equals(password)) {
            throw new Exception("erreur");
        } else {
          
            this.owner = user;
            //connection
            //TODO notify network
            
        }

    }

    /**
     * disconnection
     */
    public void singOut() throws IOException {
        this.save();
        this.owner = null;

        System.out.println("Disconnection");
    }

    /**
     * save
     */
    private void save() throws IOException {

        String path = Configuration.SAVE_DIR + File.separator + owner.getUserIdentity().getPlayerName() + ".json";
        System.out.println(path);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(path), owner);

    }

    /**
     * add user to the list of connected users
     *
     * @param usr
     * @exception RuntimeException if the user is already in the list of connected users.
     */
    public void addConnectedUser(LightPublicUser usr) {
        if (!this.mapConnectedUser.containsKey(usr.getId())) {
            this.mapConnectedUser.put(usr.getId(), usr);
        }else {
            throw new RuntimeException("User "+ usr.getPlayerName() +" was already in the list of connected users.");
        }
    }

    /**
     * remove connected user
     *
     * @param usr
     * @return
     */
    public boolean removeConnectedUser(LightPublicUser usr) {
        boolean remove = false;
        if (this.mapConnectedUser.containsKey(usr.getId())) {
            remove = true;
            this.mapConnectedUser.remove(usr.getId());
        }
        return remove;
    }
}
