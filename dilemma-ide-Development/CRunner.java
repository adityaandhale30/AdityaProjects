import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
//import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class CRunner extends Thread {
        
        private Process process;
        private TextArea terminal;

        CRunner(Process process , TextArea terminal){
                this.process = process;
                this.terminal = terminal;

                Platform.runLater(() -> terminal.setText(""));
        }

        public void run(){

                BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
                PrintWriter input = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));


                try {

                        Thread.sleep(500);
                        
                        while(process.isAlive()){

                                Thread.sleep(10);

                                if(output.ready()){
                                        String s = output.readLine();
                                        System.out.println(s);
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
