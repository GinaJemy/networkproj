package networkproj;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FXMLDocumentController implements Initializable {
    
   public static  MenuItem wifi=new MenuItem("wifi");
      public  MenuItem eth=new MenuItem("ethernet");
        @FXML public MenuButton device;
       @FXML public TableColumn<String /*relpace  with object packet */,String> source;
       @FXML public TableView <String /*relpace  with object packet */> table;
     public void loginButtonPress(ActionEvent event)
 {
 
 }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         source.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue()/*.getHeader().getSrcAddr().toString()*//*relpace with string to be returned in the src colum*/));
                table.setItems(Networkproj.packets);
                //this is made for only colum once u add objects in the observable list the table will update itself automatically
                // ladies and gentlemen we are highly depressed people i don't why i am wiritng this right now bas it seems funny anyway 
                //good bye
    }    
}
