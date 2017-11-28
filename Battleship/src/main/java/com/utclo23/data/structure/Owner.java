/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Davy
 */
public class Owner extends SerializableEntity{
    private PublicUser userIdentity;
    private String password;
    private List<String> discoveryNodes;
    private List<Game> savedGamesList;
    private List<StatGame> playedGamesList;
    private List<LightPublicUser> contactList;
    private byte[] avatar;
    
    public Owner()
    {
        this.discoveryNodes = new ArrayList<>();
        this.savedGamesList = new ArrayList<>();
        this.playedGamesList = new ArrayList<>();
        
    }
    
    public PublicUser getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(PublicUser userIdentity) {
        this.userIdentity = userIdentity;
    }

    /**
     * Resize the avatar to get a thumbnail
     *
     * @param ImageName
     * @return Bytes array of the thumbnail
     * @throws IOException
     */
    private byte[] createThumbnail(String ImageName) throws Exception {
        try {
            String format = "jpg"; //jpg by default
            int thumbnailWidth = 100;
            File imgPath = new File(ImageName);
            BufferedImage originalBufferedImage = ImageIO.read(imgPath);
            
            //Get image format
            ImageInputStream iis = ImageIO.createImageInputStream(imgPath);
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);

            while (imageReaders.hasNext()) {
                ImageReader reader = (ImageReader) imageReaders.next();
                format = reader.getFormatName();
            }
            
            //Resize the image and calculate the scaling depending on width and height
            int widthToScale, heightToScale;
            if (originalBufferedImage.getWidth() > originalBufferedImage.getHeight()) {

                heightToScale = (int)(1.1 * thumbnailWidth);
                widthToScale = (int)((heightToScale * 1.0) / originalBufferedImage.getHeight() 
                                * originalBufferedImage.getWidth());

            } else {
                widthToScale = (int)(1.1 * thumbnailWidth);
                heightToScale = (int)((widthToScale * 1.0) / originalBufferedImage.getWidth() 
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
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ImageIO.write(resizedImage, format, baos );
            byte[] imageInByte=baos.toByteArray();
            
            //BufferedImage imgbck = ImageIO.read(new ByteArrayInputStream(imageInByte));
            //File outputfile = new File("D:\\Fabien Boucaud\\Pictures\\markerthumbnail.PNG");
            //ImageIO.write(imgbck, format, outputfile);
            
            return (imageInByte);
        }
        catch (Exception e){
            throw new Exception("Error while trying to resize the thumbnail:" + e.getMessage());
        }
    }
    
    
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getDiscoveryNodes() {
        return discoveryNodes;
    }

    public void setDiscoveryNodes(List<String> discoveryNodes) {
        this.discoveryNodes = discoveryNodes;
    }

    public List<Game> getSavedGamesList() {
        return savedGamesList;
    }

    public void setSavedGamesList(List<Game> savedGamesList) {
        this.savedGamesList = savedGamesList;
    }

    public List<StatGame> getPlayedGamesList() {
        return playedGamesList;
    }

    public void setPlayedGamesList(List<StatGame> playedGamesList) {
        this.playedGamesList = playedGamesList;
    }

    public List<LightPublicUser> getContactList() {
        return contactList;
    }

    public void setContactList(List<LightPublicUser> contactList) {
        this.contactList = contactList;
    }


    
    
    
}
