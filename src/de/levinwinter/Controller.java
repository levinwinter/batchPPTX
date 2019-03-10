package de.levinwinter;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Controller {

    String path = "example.pptx";
    @FXML private TextField textField;

    @FXML public void goClicked() throws IOException {

        //creating a new empty slide show
        XMLSlideShow ppt = new XMLSlideShow();

        //creating an FileOutputStream object
        File file = new File(path);
        FileOutputStream out = new FileOutputStream(file);

        XSLFSlide slide1 = ppt.createSlide();
        XSLFSlide slide2 = ppt.createSlide();

        //saving the changes to a file
        ppt.write(out);
        System.out.println("Presentation created successfully");
        out.close();

        System.out.println("clicked!");

    }

    @FXML public void updatePath() {
        path = textField.getText();
    }




}
