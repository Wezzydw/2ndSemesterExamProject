/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;

/**
 *
 * @author Wezzy
 */
public class CreatePane
{
    public static Pane createOrderInGUI(IProductionOrder po, IDepartmentTask dpt, Double scale)       
    {
        Label invis = new Label();
        invis.setMinSize(0, 0);
        Label orderNum = new Label(po.getOrder().toString());
        Label customer = new Label("Customer: " + po.getCustomer().getName());
        
//        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        int weekNumber = dpt.getStartDate().get(weekFields.weekOfWeekBasedYear());
        int weekNumberEndDate = dpt.getEndDate().get(weekFields.weekOfWeekBasedYear());
        Label startDate = new Label("["+weekNumber + ":" +dpt.getStartDate().getDayOfWeek().getValue()+"]");
        Label endDate = new Label("["+weekNumberEndDate + ":" +dpt.getEndDate().getDayOfWeek().getValue()+"]");
        Pane orderPane = new Pane();
        orderPane.setMaxSize(200 * scale, 150 * scale);
        orderPane.getStyleClass().add("pane");
        Rectangle rec = new Rectangle(200 * scale, 150 * scale);
        rec.setArcHeight(20*scale);
        rec.setArcWidth(20*scale);
        orderPane.setShape(rec);
        Circle circle = makeCircle(po , dpt, scale);

        orderNum.setStyle("-fx-font-size:" + 15 * scale);
        customer.setStyle("-fx-font-size:" + 15 * scale);
        startDate.setStyle("-fx-font-size:" + 15 * scale);
        endDate.setStyle("-fx-font-size:" + 15 * scale);

        Pane progress = new Pane();
        progress.setMaxSize(175 * scale, 15 * scale);
        Canvas canvas = new Canvas(175 * scale, 15 * scale);
        
        makeProgressBar(scale, canvas, dpt);
        setLayouts(circle, orderNum, customer, startDate, endDate, invis, canvas, scale);

        orderPane.getChildren().addAll(circle, orderNum, startDate, endDate, customer, invis, canvas);
 
        return orderPane;

    }
    /**
     * denne metode tjekker listen af departmenttasks og deres status og laver
     * en circle på tasksne som sættes til grøn eller rød farve efter taskens 
     * status
     * @param po listen af productionorder
     * @param dpt departmentasksne som der tjekkes status på
     * @param scale sørger for at circle scaler med når vores scalering ændres
     * @return 
     */
    private static Circle makeCircle(IProductionOrder po, IDepartmentTask dpt, double scale){
        
        IDepartmentTask task = null;
        List<IDepartmentTask> tasks = po.getDepartmentTasks();
        Circle circle = new Circle(13 * scale);

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).equals(dpt) && i > 0) {
                task = tasks.get(i - 1);
            }
        }
        if (task == null || task.getFinishedOrder()) {
            circle.setFill(Paint.valueOf("Green"));
        } else {
            circle.setFill(Paint.valueOf("Red"));
        }
        return circle;
    }
    /**
     * metode sætter et canvas der bliver fyldt ud med den progress den enkelte
     * departmenttask har. Er canvas grøn betyder det progress er on time, er den
     * rød er tasken bagud
     * @param scale sørger for at progressbaren scaler med når scalingen ændres
     * @param canvas det er den bar der viser progressen
     * @param dpt det er departmenttasken vi aflæser information fra.
     */
    private static void makeProgressBar(double scale, Canvas canvas, IDepartmentTask dpt){
        
        canvas.setLayoutX(13 * scale);
        canvas.setLayoutY(130 * scale);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Long daysBetween = ChronoUnit.DAYS.between(dpt.getStartDate(), dpt.getEndDate());
        int progressInterval = (int) ((175 * scale) / daysBetween);
        LocalDateTime todayIs = LocalDateTime.now();
        Long startToNow = ChronoUnit.DAYS.between(dpt.getStartDate(), todayIs);
        double dd = progressInterval * startToNow;
        gc.setFill(Color.GREEN);
        
        if (startToNow > daysBetween)
        {
            gc.setFill(Color.RED);
            dd = 175 * scale;
        }
        gc.fillRect(0, 0, dd, 20 * scale);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, 175 * scale, 15 * scale);
        
    }
    /**
     * metoden placere alle de forskellige objekter på de rigtige positioner på panet.
     * @param circle
     * @param orderNum
     * @param customer
     * @param startDate
     * @param endDate
     * @param invis
     * @param canvas
     * @param scale 
     */
    private static void setLayouts(Circle circle, Label orderNum, Label customer, Label startDate, Label endDate, Label invis, Canvas canvas, double scale){
        circle.setLayoutX(180 * scale);
        circle.setLayoutY(20 * scale);

        orderNum.setLayoutX(25 * scale);
        orderNum.setLayoutY(40 * scale);

        customer.setLayoutX(25 * scale);
        customer.setLayoutY(60 * scale);

        startDate.setLayoutX(15 * scale);
        startDate.setLayoutY(100 * scale);

        endDate.setLayoutX(150 * scale);
        endDate.setLayoutY(100 * scale);

        invis.setLayoutX(200 * scale);
        invis.setLayoutY(150 * scale);
        
        canvas.setLayoutX(13 * scale);
        canvas.setLayoutY(130 * scale);
    }  
}
