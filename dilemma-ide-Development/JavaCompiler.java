import java.io.BufferedReader;
import java.io.InputStreamReader;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class JavaCompiler extends Thread{

        private Process process;
        private TextArea terminal;

        boolean isError = false;

        JavaCompiler(Process process , TextArea terminal){
                this.process = process;
                this.terminal = terminal;

                terminal.setText("");
        }

        public void run(){

                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                try {
                 
                        Thread.sleep(1500);

                        String s = null;
                        while( ( s = errorReader.readLine() ) != null){

                                setErrorStyle();

                                String a = s;
                                Platform.runLater(() -> terminal.appendText(a + "\n"));
                                isError = true;
                        }

                        
                        if(process.isAlive() == false)
                                return;

                } catch (Exception e) {

                        
                }

        }

        boolean getStatus(){
                return isError;
        }

        private void setErrorStyle(){
                terminal.setStyle(
                    "-fx-background-color: #1c1c1c;" + 
                    "-fx-text-fill: #99FF99;" +            
                    "-fx-control-inner-background: #262626;" + 
                    "-fx-font-family: 'Arial';" +     
                    "-fx-font-size: 18px;"
                );
        }
}
