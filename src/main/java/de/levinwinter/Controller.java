package de.levinwinter;

import de.levinwinter.structure.Course;
import de.levinwinter.structure.Student;
import de.levinwinter.structure.Year;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.apache.commons.io.FilenameUtils.removeExtension;

public class Controller {

    private Path path = null;
    @FXML private TextField textField;
    private int totalStudents = 0;

    public void selectFolderClicked() {
        try {
            path = selectDirectory(Main.getPrimaryStage()).toPath();
            textField.setText(path.toString());
        }
        catch (NullPointerException e) {
            System.out.println("no file selected");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void goClicked() throws IOException {

        for(File course : getSubdirs(path)) {
            totalStudents = totalStudents + getFiles(course.toPath()).length;
        }

        File fileMasterPPTX = new File(getFiles(path)[0].getPath());

        FileInputStream masterPPTX = new FileInputStream(fileMasterPPTX);

        XMLSlideShow pptx = new XMLSlideShow(masterPPTX);

        //creating an FileOutputStream object
        String defaultName = "presentation.pptx";
        File file = new File(path.toString() + "/" + defaultName);
        FileOutputStream out = new FileOutputStream(file);

        XSLFSlideMaster pptxMaster = pptx.getSlideMasters().get(0);
        XSLFSlideLayout titleLAY = pptxMaster.getLayout("TITLE");
        XSLFSlideLayout sectionLAY = pptxMaster.getLayout("SECTION");
        XSLFSlideLayout studentLAY = pptxMaster.getLayout("PICTURE");

        pptx.createSlide(titleLAY).getPlaceholder(0).setText(path.toFile().getName());

        int i = 0;

        Year year = new Year(path.toFile().getName());

        for(File courseFile : getSubdirs(path)) {
            Course course = year.addCourse(new Course(courseFile.getName()));
            for(File studentFile : getFiles(courseFile.toPath())) {
                course.addStudent(new Student(removeExtension(studentFile.getName()), IOUtils.toByteArray(new FileInputStream(studentFile))));
            }
        }

        for(Course course : year.getCourses()) {
            pptx.createSlide(sectionLAY).getPlaceholder(0).setText(course.getName());
            for(Student student : course.getStudents()) {
                XSLFSlide slide = pptx.createSlide(studentLAY);
                slide.getPlaceholder(0).setText(student.getName());
                PictureData pictureData = pptx.addPicture(student.getImage(), PictureData.PictureType.JPEG);
                XSLFPictureShape pictureINslide = slide.createPicture(pictureData);
                pictureINslide.setAnchor(slide.getPlaceholder(1).getAnchor());
                slide.removeShape(slide.getPlaceholder(1));
                i++;
                System.out.println((float) i/totalStudents);
            }
        }

        //saving the changes to a file
        pptx.write(out);
        System.out.println("Presentation created successfully");
        out.close();
        masterPPTX.close();
        System.out.println("finish");

    }

    private File selectDirectory(Stage primaryStage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("choose root folder");
        return directoryChooser.showDialog(primaryStage);
    }

    private File[] getSubdirs(Path path) {
        return new File(path.toString()).listFiles(File::isDirectory);
    }

    private File[] getFiles(Path path) {
        return new File(path.toString()).listFiles(File::isFile);
    }

}
