import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
//import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class Python extends Thread {
        
        private Process process;
        private TextArea terminal;

        Python(Process process , TextArea terminal){
                this.process = process;
                this.terminal = terminal;

                terminal.setText("");
        }

        public void run(){

                BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
                PrintWriter input = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
                BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                try {

                        Thread.sleep(100);
                        
                        while(process.isAlive()){

                                Thread.sleep(10);

                                if(error.ready()){

                                        String s = null;
                                        while( (s = error.readLine()) != null ){
                                                String a = s;
                                                Platform.runLater(() -> terminal.appendText(a));
                                        }

                                        break;
                                }

                                if(output.ready()){
                                        String s = output.readLine();
                                        Platform.runLater(() -> terminal.appendText(s + "\n"));
                                }else{
                                        terminal.setOnKeyPressed(e ->{
                                                KeyCode code = e.getCode();
                                                if(code == KeyCode.ENTER){
                                                        String ans[]  = terminal.getText().split("\n");
                                                        input.println(ans[ans.length - 1]);
                                                        input.flush();
                                                
                                                }
                                        });
                                }  

                        }

                        String s = null;
                        while((s = output.readLine()) != null){
                                String a = s;
                                Platform.runLater(() -> terminal.appendText(a + "\n"));
                        }

                        Thread.sleep(1000);

                        int retCode = process.exitValue();
                        Platform.runLater(() -> terminal.appendText("Exited with code :- " + retCode + "\n" ));
                        Platform.runLater(() -> terminal.appendText(MainStage.defaultPath));

                } catch (Exception e) {
                        
                        e.printStackTrace();
                }

        }

}
