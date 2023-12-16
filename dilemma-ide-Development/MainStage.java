import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

public class MainStage {

        File mainDirectory;
        Stage primaryStage;

        ArrayList<File> allFilesList = new ArrayList<>();

        TreeView<String> fileExplorer = new TreeView<>();
        TreeItem<String> rootItem;

        TabPane editorTabPane;
        TextArea terminal;
        MenuButton createButton;

        public static String defaultPath = null;

        MainStage(Stage primaryStage , File mainDirectory){
                this.primaryStage = primaryStage;
                this.mainDirectory = mainDirectory;
                defaultPath = mainDirectory.getAbsolutePath();

        }

        void showMainStage(){
                primaryStage.setTitle("Dellima IDE");

                BorderPane root = new BorderPane();

                editorTabPane = new TabPane();
                editorTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);

                terminal = new TextArea();
                terminal.setText(defaultPath);
                terminal.setPrefHeight(250);
                fileExplorer.setPrefWidth(350);

                root.setLeft(fileExplorer);
                root.setCenter(editorTabPane);
                root.setBottom(terminal);

                root.setStyle("-fx-background-color: #1A1A1A;"); 

                VBox separator = new VBox();
                separator.setStyle("-fx-background-color: white;");
                separator.setMinWidth(10);
                separator.setFillWidth(true);
                separator.setPrefWidth(10);
                root.getChildren().add(separator);

                setStyles();

                HBox buttonContainer = new HBox(8);
                buttonContainer.setStyle("-fx-alignment: TOP_RIGHT; -fx-padding: 10px;");

        
                Button saveButton = new Button("" , new ImageView(new Image("save.png")));
                saveButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                saveButton.setPrefWidth(90);
                buttonContainer.getChildren().add(saveButton);

                Button runButton = new Button("" , new ImageView(new Image("play.png")) );
                runButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                runButton.setPrefWidth(90);
                buttonContainer.getChildren().add(runButton);

                Button GitButton = new Button("" , new ImageView(new Image("git.png")));
                GitButton.setStyle("-fx-background-color:lightblack; -fx-text-fill: orange;");
                GitButton.setPrefWidth(90);
                buttonContainer.getChildren().add(GitButton);

                Image img = new Image("open-folder.png");
                Button openNewDirctory = new Button("Open Folder", new ImageView(img));
                openNewDirctory.setOnAction(e -> selectDirectoryToOpen());
                
                Image img1 = new Image("java.png");
                Image img2 = new Image("python.png");
                Image img3 = new Image("c-document.png");
                Image img4 = new Image("c-.png");

                createButton = new MenuButton("Create New File" , new ImageView(new Image("add-file.png")));
                createButton.getItems().addAll(
                        new MenuItem("Java", new ImageView(img1)),
                        new MenuItem("Python", new ImageView(img2)),
                        new MenuItem("C", new ImageView(img3)),
                        new MenuItem("C++", new ImageView(img4))
                );


                createButton.getItems().get(0).setOnAction(event -> createJavaFile());
                    
                createButton.getItems().get(1).setOnAction(event -> createPythonFile());
                    
                createButton.getItems().get(2).setOnAction(event -> createCFile());
                    
                createButton.getItems().get(3).setOnAction(event -> createCPPFile());   

                HBox buttonContainer2 = new HBox(10);
                buttonContainer2.setStyle("-fx-alignment: TOP_LEFT; -fx-padding: 10px;");
        
                buttonContainer2.getChildren().addAll(openNewDirctory , createButton);

                createFileStructure();

                fileExplorer.setOnMouseClicked(event -> openSelectedFile());

                runButton.setOnAction(event -> runCode());

                saveButton.setOnAction(event -> saveFile());
                
                GitButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                      //  openWebpage("https://gitlab.com"); 
                       try {
                    Desktop.getDesktop().browse(new URI("https://gitlab.com"));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                        
                }

                    }
        });
        
              //  Scene scenee = new Scene(GitButton, 200, 100);
                //primaryStage.setScene(scenee);
                  // primaryStage.show();
        
        
          

                HBox topButtonContainer = new HBox(800);
                topButtonContainer.getChildren().addAll(buttonContainer2, buttonContainer);

                HBox.setHgrow(buttonContainer, Priority.ALWAYS);
                HBox.setHgrow(buttonContainer2, Priority.ALWAYS);

                root.setTop(topButtonContainer);

                Scene scene = new Scene(root, 1200, 800 );

                primaryStage.setResizable(true);
                primaryStage.setScene(scene);
                primaryStage.show();
        }

        private String getFileName(){
                Stage stage = new Stage();
                stage.setTitle("Create File");
                stage.getIcons().add(new Image("add-file.png"));

                Label label = new Label("Give File Name");
                label.setStyle("-fx-font-size: 20px; -fx-text-fill: #0076a3;");

                TextField userInputField = new TextField();
                userInputField.setPrefSize(350, 20);

                Button submitButton = new Button("Submit");
                submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 18px;");

                StackPane sp1 = new StackPane(label);
                sp1.setLayoutX(20);
                sp1.setLayoutY(20);

                StackPane sp2 = new StackPane(userInputField);
                sp2.setLayoutX(20);
                sp2.setLayoutY(60);

                StackPane sp3 = new StackPane(submitButton);
                sp3.setLayoutX(160);
                sp3.setLayoutY(130);

                StringBuffer output = new StringBuffer();

                submitButton.setOnAction(event -> {
                String userInput = userInputField.getText();
                if (!userInput.isEmpty()) {
                        
                        output.append(userInput);
                        stage.close();

                } else {

                        label.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
                        label.setText("Please enter a file name.");
                        
                }
                });

                Group root = new Group(sp1, sp2, sp3);
                Scene scene = new Scene(root, 400, 200);

                scene.setFill(javafx.scene.paint.Color.LIGHTGRAY);

                stage.setScene(scene);
                stage.showAndWait();

                return output.toString();
        }
      
  
            
        
        

        private void createJavaFile(){
                String fileName = getFileName();

                if(fileName.contains(".java") != true)
                        fileName = fileName + ".java";

                File file = new File(defaultPath +"\\" + fileName);

                if(file.exists()){
                
                        popUpStage("File Exists", "warning.png", "File is Already Existing");
                        return;
                
                }else{

                        try{

                                file.createNewFile();
                                
                                TreeItem<String> item = new TreeItem<String>(fileName);
                                rootItem.getChildren().add(item);

                                allFilesList.add(file);

                        }catch(Exception e){



                        }
                } 
        }

        private void createPythonFile(){
                String fileName = getFileName();

                if(fileName.contains(".py") != true)
                        fileName = fileName + ".py";

                File file = new File(defaultPath +"\\" + fileName);

                if(file.exists()){
                
                        popUpStage("File Exists", "warning.png", "File is Already Existing");
                        return;
                
                }else{

                        try{

                                file.createNewFile();
                                
                                TreeItem<String> item = new TreeItem<String>(fileName);
                                rootItem.getChildren().add(item);

                                allFilesList.add(file);

                        }catch(Exception e){



                        }
                }
        }

        private void createCFile(){
                String fileName = getFileName();

                if(fileName.contains(".c") != true)
                        fileName = fileName + ".c";

                File file = new File(defaultPath +"\\" + fileName);

                if(file.exists()){
                
                        popUpStage("File Exists", "warning.png", "File is Already Existing");
                        return;
                
                }else{

                        try{

                                file.createNewFile();
                                
                                TreeItem<String> item = new TreeItem<String>(fileName);
                                rootItem.getChildren().add(item);

                                allFilesList.add(file);

                        }catch(Exception e){



                        }
                }
        }

        private void createCPPFile(){
                String fileName = getFileName();

                if(fileName.contains(".cpp") != true)
                        fileName = fileName + ".cpp";

                File file = new File(defaultPath +"\\" + fileName);

                if(file.exists()){
                
                        popUpStage("File Exists", "warning.png", "File is Already Existing");
                        return;
                
                }else{

                        try{

                                file.createNewFile();
                                
                                TreeItem<String> item = new TreeItem<String>(fileName);
                                rootItem.getChildren().add(item);

                                allFilesList.add(file);

                        }catch(Exception e){



                        }
                }
        }

        void selectDirectoryToOpen(){
        
                DirectoryChooser directoryChooser = new DirectoryChooser();
                
                File f = directoryChooser.showDialog(primaryStage);

                if(f == null){
                
                selectDirectoryToOpen();

                }else{
                
                new MainStage(primaryStage , f).showMainStage();

                }  
        }

        private void setStyles(){
                fileExplorer.setStyle(
                    "-fx-background-color: #1c1c1c;" +  
                    "-fx-text-fill: white;" +           
                    "-fx-control-inner-background: #1c1c1c;" + 
                    "-fx-font-family: 'Arial';" +        
                    "-fx-font-size: 18px;"
                );

                terminal.setStyle(
                    "-fx-background-color: #1c1c1c;" + 
                    "-fx-text-fill: #99FF99;" +            
                    "-fx-control-inner-background: #262626;" + 
                    "-fx-font-family: 'Arial';" +     
                    "-fx-font-size: 20px;"
                );
        }

        private void listFilesAndFolders(File directory, TreeItem<String> parentItem) {
                if (directory != null && directory.exists() && directory.isDirectory()) {

                        for (File file : directory.listFiles()) {
                                TreeItem<String> item = new TreeItem<>(file.getName());
                                parentItem.getChildren().add(item);

                                if (file.isDirectory())
                                        listFilesAndFolders(file, item);

                                if(file.isFile())
                                        allFilesList.add(file);
                        }
                }
        }

        private void createFileStructure(){
                rootItem = new TreeItem<>(mainDirectory.getName());
                listFilesAndFolders(mainDirectory, rootItem);
                fileExplorer.setRoot(rootItem);
        }

        private void openSelectedFile(){
                TreeItem<String> selectedItem = fileExplorer.getSelectionModel().getSelectedItem();
                if (selectedItem != null && selectedItem.isLeaf()) {
                       
                        String fileName = selectedItem.getValue();

                        if((fileName.contains(".java") || fileName.contains(".py") || fileName.contains(".c") || fileName.contains(".cpp") ) == false){
                                popUpStage("Invalid File", "warning.png", "Please Select Valid File To Open");
                                return;
                        }

                        Image img = null;
                        if(fileName.contains(".java"))
                                img = new Image("java.png");

                        else if(fileName.contains(".py"))
                                img = new Image("python.png");

                        else if(fileName.contains(".cpp"))
                                img = new Image("c-.png");

                        else
                                img = new Image("c-document.png");


                        Tab tab = new Tab(fileName);
                        tab.setGraphic(new ImageView(img));
                        TextArea editor = new TextArea();

                        tab.setStyle(
                                "-fx-background-color: #d3d3d3;" + 
                                "-fx-text-fill: white;" +             
                                "-fx-control-inner-background: #333333;" + 
                                "-fx-font-family: 'Arial';" +        
                                "-fx-font-size: 20px;"
                        );

                        editor.setStyle(
                                "-fx-background-color: #d3d3d3;" +  
                                "-fx-text-fill: #FFFFB3;" +           
                                "-fx-control-inner-background: #262626;" + 
                                "-fx-font-family: 'Arial';" +        
                                "-fx-font-size: 20px;"
                        );


                        File fileToOpen = null;
                        for(File f : allFilesList){
                                if(f.getName().equals(fileName)){
                                        fileToOpen = f;
                                        break;
                                }
                        }

                        StringBuffer sb = new StringBuffer();
                        int ch;


                        try{
                                FileReader fr = new FileReader(fileToOpen);
                                while((ch = fr.read() ) != -1 ){
                                        sb.append(((char)ch));
                                }

                                fr.close();

                        }catch(Exception e){
                                

                        }
                        
                        editor.setText(sb.toString());
                        tab.setContent(editor);
                        editorTabPane.getTabs().add(tab);
                }
        }

        private void saveFile(){

                Tab tab = editorTabPane.getSelectionModel().getSelectedItem();

                if(tab == null){
                        popUpStage("Select File", "warning.png", "No File Is Selected or Opened To Save");
                        return;
                }

                File fileTOSave = null;

                for(File f : allFilesList){
                        if(f.getName().equals(tab.getText())){
                                fileTOSave = f;
                                break;
                        }
                }

                try{
                        FileWriter fw = new FileWriter(fileTOSave);

                        TextArea textArea = (TextArea) tab.getContent();

                        fw.write(textArea.getText());

                        fw.close();

                        popUpStage("File Saved", "save.png", fileTOSave.getName() + "  Saved");

                }catch(Exception e){
                        e.printStackTrace();
                }

        }

        private void runCode(){
                
                Tab tab = editorTabPane.getSelectionModel().getSelectedItem();

                if(tab == null){
                        popUpStage("Select File", "warning.png", "No File Is Selected or Opened To Run");
                        return;
                }

                File fileTORun = null;

                for(File f : allFilesList){

                        if(f.getName().equals( tab.getText() )){
                                fileTORun = f;
                                break;
                        }
                }    
                
                String fileName = fileTORun.getName();

                if(fileName.contains(".java")){

                        runJava(fileTORun);                        

                }else if(fileName.contains(".py")){

                        runPython(fileTORun);

                }else if(fileName.contains(".cpp")){

                        runCPP(fileTORun);

                }else if(fileName.contains(".c")){

                        runC(fileTORun);

                }else{



                }
        }

        private void runJava(File fileTORun){
                try{

                        String fileName = fileTORun.getName();

                        Process p = Runtime.getRuntime().exec("javac " + fileTORun.getAbsolutePath());
                         
                        JavaCompiler javaCompiler = new JavaCompiler(p , terminal);
                        javaCompiler.start();
                        javaCompiler.join();
                        terminal.appendText(defaultPath);
                                
                        if(javaCompiler.getStatus() != true){

                                ProcessBuilder processBuilder = new ProcessBuilder("java" , fileName.replace(".java", ""));
                                processBuilder.directory(new File(fileTORun.getAbsolutePath().replace(fileTORun.getName(), "")));

                                Process run = processBuilder.start();
        
                                JavaJVM javaJVM = new JavaJVM(run , terminal);
                                javaJVM.start();

                                p.waitFor();
                                
                        }

                                
                }catch(Exception e){
                        e.printStackTrace();
                }
        }

        private void runPython(File fileTORun){

                try{
                        Process p = Runtime.getRuntime().exec("python " + fileTORun.getAbsolutePath());


                        Python python = new Python(p, terminal);
                        python.start();

                        p.waitFor();

                }catch(Exception e){
                        e.printStackTrace();
                }
        }

        private void runC(File fileToRun){

                try {
                        
                        Process p = Runtime.getRuntime().exec("gcc " + fileToRun.getAbsolutePath() + " -o " + fileToRun.getName().replace(".c", ""));
                        CCompiler cCompiler = new CCompiler(p, terminal);
                        cCompiler.start();

                        p.waitFor();

                        cCompiler.join();

                        terminal.appendText(defaultPath);

                        if(cCompiler.getStatus() == false){
        
                                Process run = Runtime.getRuntime().exec(fileToRun.getAbsolutePath().replace(fileToRun.getName(),"./"+fileToRun.getName().replace(".c", ".exe")));

                                CRunner cRunner = new CRunner(run , terminal);
                                cRunner.start();

                                p.waitFor();                              
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        private void runCPP(File fileToRun){
                try {
                        
                        Process p = Runtime.getRuntime().exec("gcc " + fileToRun.getAbsolutePath() + " -o " + fileToRun.getName().replace(".c", ""));
                        CCompiler cCompiler = new CCompiler(p, terminal);
                        cCompiler.start();

                        int ret = p.waitFor();

                        System.out.println(ret);
                        cCompiler.join();

                        terminal.appendText(defaultPath);

                        if(cCompiler.getStatus() == false){
        
                                Process run = Runtime.getRuntime().exec(fileToRun.getAbsolutePath().replace(fileToRun.getName(),"./"+fileToRun.getName().replace(".cpp", ".exe")));

                                CRunner cRunner = new CRunner(run , terminal);
                                cRunner.start();

                                ret = p.waitFor();                              
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }


        }

        private void popUpStage(String title , String imgUrl , String msg){

                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setResizable(false);
                stage.getIcons().add(new Image(imgUrl));

                Text text = new Text(msg);
                text.setStyle("-fx-font-size: 20px; -fx-text-fill: #0076a3;");
                text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
                text.setLayoutX(20);
                text.setLayoutY(50);

                Group g = new Group(text);

                Scene scene = new Scene(g, 450, 150, Color.LIGHTGRAY);

                stage.setScene(scene);
                stage.show();


        }
}
