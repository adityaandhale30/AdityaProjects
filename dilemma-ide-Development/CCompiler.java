import java.io.BufferedReader;
import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
//import javafx.scene.input.KeyCode;

public class CCompiler extends Thread {
        
        private Process process;
        private TextArea terminal;

        public static boolean isError = false;

        CCompiler(Process process , TextArea terminal){
                this.process = process;
                this.terminal = terminal;

                terminal.setText("");
        }

        public void run(){

                BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                try {

                        Thread.sleep(500);
                        
                        while(process.isAlive()){

                                Thread.sleep(10);

                                if(error.ready()){

                                        String s = null;
                                        while( (s = error.readLine()) != null ){
                                                String a = s;
                                                System.out.println(a);
                                                Platform.runLater(() -> terminal.appendText(a));
                                        }

                                        isError = true;
                                        break;
                                }

                        
                        }

                        Thread.sleep(1000);

                        int retCode = process.exitValue();
                        Platform.runLater(() -> terminal.appendText("Exited with code :- " + retCode + "\n" ));
                        Platform.runLater(() -> terminal.appendText(MainStage.defaultPath));

                } catch (Exception e) {
                        
                        e.printStackTrace();
                }

        }

        public boolean getStatus(){
                return isError;
        }

}
