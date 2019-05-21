/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.bll.PassThrough;

/**
 *
 * @author marce
 */
public class ManagerModel
{
    private PassThrough managerPassThrough;
    private TableView tableView;

    public ManagerModel(TableView tableView) throws IOException
    {
        this.tableView = tableView;
        managerPassThrough = new PassThrough();
    }
    
        public List<IProductionOrder> getProductionOrders() throws SQLException{
        return managerPassThrough.getAllProductionOrders();
        
        
    }
          public StringProperty stringConverter(String string)
            {
                return new SimpleStringProperty(string);              
            }

    public void scanFolderForNewFiles() {
        managerPassThrough.scanFolderForNewFiles();
    }
 
          
          
          
          
}
