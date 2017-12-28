package FileLoding;

import API.InterfaceAPI;
import Exceptions.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import Utils.Utils;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Optional;

public class FileLoading extends Task<Boolean>  {

    private InterfaceAPI model;
    String fullPath;
    SimpleBooleanProperty isFileLoadingSucceed;
    SimpleBooleanProperty isErrorHappend;
    SimpleStringProperty errorMessage;
    Optional<Runnable> onFinish;



    public FileLoading(InterfaceAPI engine, String fullPath) {
        this.model = engine;
        this.fullPath = fullPath;
        this.isFileLoadingSucceed= new SimpleBooleanProperty(false);
        this.isErrorHappend = new SimpleBooleanProperty(false);
        this.errorMessage = new SimpleStringProperty("");
    }




    @Override
    protected Boolean call() throws Exception {
        updateProgress(0,2);
        updateMessage("Start Loading");

        return  loadXML();
    }


    public Boolean loadXML() {
        System.out.println("I Am Here2");

        try {
            System.out.println("I Am Here3");
            model.LoadFromXML(fullPath);
        } catch (GameStartedException e) {
            errorMessage.set("Game Already Start");
            isErrorHappend.set(true);
            return false;
        } catch (UnexpectedObjectException e) {
            errorMessage.set("No XML file exist");
            isErrorHappend.set(true);
            return false;
        } catch (FileNotFoundException e) {
            errorMessage.set("No XML file exist");
            isErrorHappend.set(true);
            return false;
        } catch (BigSmallMismatchException e) {
            errorMessage.set("Problem in XML file, Big Blind and Small Blind Value is incorrect");
            isErrorHappend.set(true);
            return false;
        } catch (PlayerDataMissingException e) {
            errorMessage.set("Problem in XML file, player data missing");
            isErrorHappend.set(true);
            return false;
        } catch (HandsCountDevideException e) {
            errorMessage.set("Problem in XML file, Hand count do not divine by number of players");
            isErrorHappend.set(true);
            return false;
        } catch (WrongFileNameException e) {
            errorMessage.set("Problem in XML file, wrong File Name");
            isErrorHappend.set(true);
            return false;
        } catch (HandsCountSmallerException e) {
            errorMessage.set("Problem in XML file,Hand Count smaller than the number of players ");
            isErrorHappend.set(true);
            return false;
        } catch (JAXBException e) {
            System.out.println("Problem in XML file loading...");
            isErrorHappend.set(true);
            return false;
        } catch (FileNotXMLException e) {
            errorMessage.set("Problem in XML file, This File is not XML");
            isErrorHappend.set(true);
            return false;
        } catch (MinusZeroValueException e) {
            errorMessage.set("Problem in XML file, One of the values is negative or zero");
            isErrorHappend.set(true);
            return false;
        } catch (BigBiggerThanBuyException e) {
            errorMessage.set("Problem in XML file, The big blind is bigger than the buy");
            isErrorHappend.set(true);
            return false;
        }
        System.out.println("After the Loading and Finised OK!!");
        updateProgress(1,2);
        updateMessage("Validate File");
        if(this.isErrorHappend.get() == false)
            model.StartGame();
        Utils.sleepForAWhile(500);
        updateProgress(2,2);
        Utils.sleepForAWhile(500);
        return true;
    }
}