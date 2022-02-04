package com.example.wordcounter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
    @FXML
    private BarChart characterGraph;
    @FXML
    private BarChart wordGraph;
    @FXML
    private Button graphChanger;

    private Map<String, Integer> counterMap = new HashMap<>();
    private Map<String, Integer> wordMap = new HashMap<>();
    private int totalCharacters;
    private int totalWords;
    private boolean showAsPercentage = false;
    private List<Character> characterBlackList = new ArrayList<>(Arrays.asList(' ')); //here you can edit the blacklist for letters.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void generateSeries(){

    }

    public void generateSeriesPercentile(){

    }

    public void showOnGraph(){
        XYChart.Series<String, Integer> charSeries = new XYChart.Series<>();
        XYChart.Series<String, Integer> wordSeries = new XYChart.Series<>();

        for (String character:counterMap.keySet()) {
            charSeries.getData().add(new XYChart.Data<>(character, counterMap.get(character)));
        }

        for (String word:wordMap.keySet()) {
            wordSeries.getData().add(new XYChart.Data<>(word, wordMap.get(word)));
        }

        characterGraph.getData().clear();
        characterGraph.layout();
        characterGraph.getData().add(charSeries);

        wordGraph.getData().clear();
        wordGraph.layout();
        wordGraph.getData().add(wordSeries);
    }

    public void showOnGraphs(){
        XYChart.Series<String, Double> charSeriesPercentage = new XYChart.Series<>();
        XYChart.Series<String, Double> wordSeriesPercentage = new XYChart.Series<>();

        for (String character:counterMap.keySet()) {
            charSeriesPercentage.getData().add(new XYChart.Data(character, ((float)counterMap.get(character)/totalCharacters)*100));
        }

        for (String word:wordMap.keySet()) {
            wordSeriesPercentage.getData().add(new XYChart.Data(word, ((float)wordMap.get(word)/totalWords)*100));
        }

        characterGraph.getData().clear();
        characterGraph.layout();
        characterGraph.getData().add(charSeriesPercentage);
        
        wordGraph.getData().clear();
        wordGraph.layout();
        wordGraph.getData().add(wordSeriesPercentage);
    }

    public void processString(String inputString){
        totalCharacters = 0;
        totalWords = 0;
        System.out.println(inputString);
        inputString = inputString.toLowerCase();

        wordMap.clear();
        counterMap.clear();

        String inputWords[] = inputString.split(" ");

        for (int i = 0; i < inputWords.length; i++) {
            String word = inputWords[i];
            if(wordMap.containsKey(word)){
                int value = wordMap.get(word);
                wordMap.put("" + word, ++value);
                totalWords++;
            } else{
                wordMap.put(word, 1);
                totalWords++;
            }
        }

        for (int i = 0; i < inputString.length(); i++) {
            char character = inputString.charAt(i);
            if(characterBlackList.contains(character)){
                continue;
            }
            if(counterMap.containsKey("" + character)){
                int value = counterMap.get("" + character);
                counterMap.put("" + character, ++value);
                totalCharacters++;
            } else{
                counterMap.put("" + character, 1);
                totalCharacters++;
            }
        }

        System.out.println(counterMap);
        System.out.println(wordMap);
        changeGraph();
    }

    public void onOpenFIleClick(MouseEvent mouseEvent) throws FileNotFoundException {
        Window window = ((Node)mouseEvent.getTarget()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("cs mázl jseš boss");
        File chosenFile = fileChooser.showOpenDialog(window);

        loadFileString(chosenFile);
    }

    public void loadFileString(File chosenFile) throws FileNotFoundException {
        Scanner scan = new Scanner(chosenFile);
        String fileString = "";

        while (scan.hasNextLine()) {
            fileString += scan.nextLine();
            fileString += " ";
        }

        scan.close();

        processString(fileString);
    }

    public void changeGraph() {
        if(showAsPercentage == false){
            showOnGraph();
            graphChanger.setText("Změnit grafy na %");
            showAsPercentage = true;
        }

        else{
            showOnGraphs();
            graphChanger.setText("Změnit grafy na čísla");
            showAsPercentage = false;
        }
    }
}