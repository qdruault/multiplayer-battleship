/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.module.DataException;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.PublicUser;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Displays all info of player profile.
 * Displays user's own profile (writable).
 * Displays others profiles (read-only).
 * 
 * @author Lipeining
 */

public class PlayerProfileController extends AbstractController{
    @FXML
    public  Label userID;
    @FXML
    private Label firstNameText;
    @FXML
    private Label lastNameText;
    @FXML
    private Label birthdayText;
    @FXML
    private Label rateAll;
    @FXML
    private Label rateClassic;
    @FXML
    private Label rateBelgian;  
    @FXML
    private ImageView image;
    @FXML
    private PieChart allMode;
    @FXML
    private PieChart classical;
    @FXML
    private PieChart belge;
    @FXML
    private Button playerName;
    @FXML
    private Button firstName;
    @FXML
    private Button lastName;
    @FXML
    private Button birthday;
    @FXML
    private Button password;
    @FXML
    private Button avatar;
    @FXML
    private GridPane stat;
    private PublicUser me;
    private PublicUser other;
    private boolean isOther; 
    private String attribut;
    private Image avatarImage;
    private List<Integer> dataClassic;
    private List<Integer> dataBelgian;
    private List<Integer> dataTotal;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
   
    @FXML
    @Override
    public void start(){
       refresh();
    } 
  
    @FXML
    private void back(ActionEvent event) throws IOException{
        getIhmmain().toMenu();
    }

    @FXML
    private void toPlayerList(ActionEvent event) throws IOException{
        getIhmmain().toPlayerList();
    }
    
    @FXML
    private void edit(ActionEvent event) throws IOException{
        attribut = ((Button)event.getSource()).getId();
        System.out.println(attribut);
        popup(attribut);
    }
    
    @FXML
    private void editAvatar(ActionEvent event) throws IOException, DataException{
        String avatarPath;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open avatar file");
        File selectedFile = fileChooser.showOpenDialog(getIhmmain().primaryStage);
        if (selectedFile != null){
            avatarPath = selectedFile.getPath();
            Logger.getLogger(CreateUserController.class.getName()).log(
                    Level.INFO, "The chosen file is : {0}", avatarPath);
            getFacade().iDataIHMMain.updateFileImage(avatarPath);
            refresh();
        }        
    }
    /**
     * Generate a pop-up
     * The content of pop-up is generated dynamically.
     * For updating the birthday, the date picker replace the text field in pop-up. 
     * @param attribut:name of info that user would like to modify
     */
    private void popup(String attribut) throws IOException{
        System.out.println("Popup: "+attribut);
        final Stage primaryStage = getIhmmain().primaryStage;
        Stage popup = new Stage();
        popup.initOwner(primaryStage);
        if("birthday".equals(attribut)){
            final DatePicker date = new DatePicker();
            Button back = new Button();
            Button submit = new Button(); 
            back.setText("Back");
            back.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                }
            });
            submit.setText("Submit");
            submit.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Date birthDate = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        getFacade().iDataIHMMain.updateBirthdate(birthDate);
                        refresh();
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    } catch (DataException ex) {
                        Logger.getLogger(PlayerProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            Pane root = new Pane();
            root.setId("root");
            Scene display = new Scene(root,400,150);
            display.getStylesheets().add(getClass().getResource("/styles/ihmmain.css").toExternalForm());
            popup.setScene(display);
            root.getChildren().add(back);
            root.getChildren().add(submit);
            back.setLayoutX(60);
            back.setLayoutY(91);
            submit.setLayoutX(231);
            submit.setLayoutY(91);
            root.getChildren().add(date);
            date.setLayoutX(90);
            date.setLayoutY(35);   
        }
        else{
            String path = "/fxml/ihmmain/popup.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent sceneLoader = loader.load();
            PopupController controller=loader.getController();
            controller.setFacade(getFacade());
            controller.setIhmmain(getIhmmain());
            controller.setAttribut(attribut);
            Scene newScene;
            newScene = new Scene(sceneLoader);
            popup.setScene(newScene);
        }
        popup.show();
    } 
    
    /**
     * Get player's avatar
     * @param player:user self or other player
     */
    public void getAvatar(PublicUser player){
       byte[] thumbnail = player.getLightPublicUser().getAvatarThumbnail();
       try{
            ByteArrayInputStream inputStream = new ByteArrayInputStream(thumbnail);
            avatarImage = new Image(inputStream);
       }catch(NullPointerException e){
            Logger.getLogger(
                PlayerProfileController.class.getName()).log(Level.INFO,
                "[PlayerProfile] - error - avatar is null."
                );   
       }
       
    }
    public List<Integer> getData(PublicUser player,GameType type) throws DataException{
        List<Integer> data = new ArrayList<>();
        float rate = 0;
        if (type == GameType.CLASSIC){
            data.add(player.getNumberVictoriesClassic());
            data.add(player.getNumberDefeatsClassic());
            data.add(player.getNumberAbandonsClassic());
            data.add(data.get(0)+data.get(1)+data.get(2));
            if (data.get(3)!=0){
                rate = ((float)data.get(0))/((float)data.get(3))*100;
            }
            rateClassic.setText("win rate: "+rate+"%");
        }else if (type == GameType.BELGIAN){
            data.add(player.getNumberVictoriesBelgian());
            data.add(player.getNumberDefeatsBelgian());
            data.add(player.getNumberAbandonsBelgian());
            data.add(data.get(0)+data.get(1)+data.get(2));
            if (data.get(3)!=0){
                rate = ((float)data.get(0))/((float)data.get(3))*100;
            }
            rateBelgian.setText("win rate: "+rate+"%");
        }
        return data;
    }
    public List<Integer> getTotal( List<Integer> data1, List<Integer> data2){
        List<Integer> data = new ArrayList<>();
        float rate = 0;
        int i = 0;
        for (int a : data1){
            data.add(data1.get(i)+data2.get(i));
            i++;
        }
        if (data.get(3)!=0){
                rate = ((float)data.get(0))/((float)data.get(3))*100;
        }
        rateAll.setText("win rate: "+rate+"%");
        return data;
    }
    
    public void drawPieChart(PieChart chart,List<Integer> data) throws DataException{
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
        new PieChart.Data("Win", data.get(0)), 
        new PieChart.Data("Loss", data.get(1)), 
        new PieChart.Data("Abandonned", data.get(2))
        );
        chart.setData(pieChartData);
    }
    public void loadStat(List<Integer> data1, List<Integer> data2,List<Integer> data3){
        int i;
        int j;
        int index = 0;
        boolean firstTime = true;
        ObservableList<Node> children = stat.getChildren();
        data1.addAll(data2);
        data1.addAll(data3);
        if (children.size()>8){
            firstTime = false;
        }
        for (j=1;j<4;j++){
            for (i=1;i<5;i++){
                if (firstTime == true){
                    Label value = new Label();
                    value.setText(data1.get(index).toString());
                    stat.add(value, i, j);
                    GridPane.setColumnIndex(value, i);
                    GridPane.setRowIndex(value, j);
                }
                else{
                    for (Node node : children) {
                        if (GridPane.getColumnIndex(node)!=null && GridPane.getRowIndex(node)!=null){
                            if (GridPane.getColumnIndex(node) == i && GridPane.getRowIndex(node) == j) {
                               ((Label)node).setText(data1.get(index).toString());
                            }
                        }
                    }    
                }
                index++;
            }
        }
    }
    public void disableButton(){
        playerName.setDisable(true);
        firstName.setDisable(true);
        lastName.setDisable(true);
        birthday.setDisable(true);
        password.setDisable(true);
        avatar.setDisable(true);
    }
   
    /**
     * Initializes all the info of profile.
     */
    @Override
    public void refresh(){
        if (!isOther){
            try{
                enableButtons();
                me = getFacade().iDataIHMMain.getMyPublicUserProfile();
                getAvatar(me);
                image.setImage(avatarImage);
                userID .setText(me.getPlayerName());
                firstNameText.setText(me.getFirstName());
                lastNameText.setText(me.getLastName());
                birthdayText.setText(formatter.format(me.getBirthDate()));
                
                //get data
                dataClassic = getData(me,GameType.CLASSIC);
                dataBelgian = getData(me,GameType.BELGIAN);
                dataTotal = getTotal(dataClassic,dataBelgian);
                loadStat(dataClassic,dataBelgian,dataTotal);
                drawPieChart(allMode,dataTotal);
                drawPieChart(classical,dataClassic);
                drawPieChart(belge,dataBelgian);
            }
            catch(NullPointerException e){
                e.printStackTrace();
                Logger.getLogger(
                        PlayerProfileController.class.getName()).log(
                                Level.INFO,
                                "[PlayerProfile] error - my profile is null."
                        );
            } catch (DataException ex) {
                Logger.getLogger(PlayerProfileController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        else{
            try{
                disableButton();
                getAvatar(other);
                image.setImage(avatarImage);
                userID.setText(other.getPlayerName());
                firstNameText.setText(other.getFirstName());
                lastNameText.setText(other.getLastName());
                birthdayText.setText(formatter.format(other.getBirthDate()));
                //get data
                dataClassic = getData(other,GameType.CLASSIC);
                dataBelgian = getData(other,GameType.BELGIAN);
                dataTotal = getTotal(dataClassic,dataBelgian);
                loadStat(dataClassic,dataBelgian,dataTotal);
                drawPieChart(allMode,dataTotal);
                drawPieChart(classical,dataClassic);
                drawPieChart(belge,dataBelgian);
            }catch(NullPointerException e){
                Logger.getLogger(
                    PlayerProfileController.class.getName()).log(Level.INFO,
                    "[PlayerProfile] - error - other profile is null."
                );
            } catch (DataException ex) {
                Logger.getLogger(PlayerProfileController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
    public void displayOther(PublicUser other){
        this.other = other;
        isOther = true;
    }
    
    public void displayMe(){
       other = null;
       isOther = false;
    }
    
    public void enableButtons(){
        playerName.setDisable(false);
        firstName.setDisable(false);
        lastName.setDisable(false);
        birthday.setDisable(false);
        password.setDisable(false);
        avatar.setDisable(false);
    }
}
