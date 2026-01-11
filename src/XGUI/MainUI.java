package XGUI;

import controller.IController;
import exception.MyException;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ProgramState;
import model.adt.IExecutionStack;
import model.adt.ISymbolTable;
import model.statement.IStatement;
import model.value.IValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainUI extends Application {

    public static IStatement program;
    public static IController controller;
    private TextField numberOfProgramStates;
    private TableView<HeapEntry> heapTable;
    private ListView<String> outList;
    private ListView<String> fileList;
    private ListView<Integer> programStateIds;
    private TableView<SymbolTableEntry> symbolTableView;
    private ListView<String> executionStackList;
    private Button runOneStepButton;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Program");
        numberOfProgramStates = new TextField();
        numberOfProgramStates.setEditable(false);
        heapTable = new TableView<>();
        TableColumn<HeapEntry,Integer> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().address).asObject());
        TableColumn<HeapEntry,String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(data->new SimpleStringProperty(data.getValue().value));
        heapTable.getColumns().addAll(addressColumn,valueColumn);

        outList = new ListView<>();
        fileList = new ListView<>();
        programStateIds = new ListView<>();

        symbolTableView = new TableView<>();
        TableColumn<SymbolTableEntry,String> variableColumn = new TableColumn<>("Variable");
        variableColumn.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().varName));
        TableColumn<SymbolTableEntry,String> varValueColumn = new TableColumn<>("Value");
        varValueColumn.setCellValueFactory(data->new SimpleStringProperty(data.getValue().value));
        symbolTableView.getColumns().addAll(variableColumn,varValueColumn);

        executionStackList = new ListView<>();
        runOneStepButton = new Button("Run One Step");
        runOneStepButton.setOnAction(event -> runOneStep());
        VBox left = new VBox(10, new Label("Number of Program States:"),numberOfProgramStates,new Label("Heap Table:"),heapTable,
                new Label("Out List:"),outList, new Label("File Table:"), fileList);
        VBox right = new VBox(10, new Label("Program States:"), programStateIds, new Label("Symbol Table:"), symbolTableView,
                new Label("Execution Stack:"), executionStackList, runOneStepButton);
        HBox mainLayout = new HBox(10, left, right);
        mainLayout.setPrefWidth(500);
        mainLayout.setPrefHeight(600);
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

        programStateIds.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) updateSelectedProgramState(newVal);
        });

        updateUI();
    }

    private void runOneStep() {
        try{
            List<ProgramState> programsList = controller.removeCompletedProgram(controller.getRepository().getProgramList());
            if(programsList.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Program has nothing left to execute");
                alert.showAndWait();
                return;
            }
            controller.oneStepForAllPrograms(programsList);
            updateUI();
        }catch (InterruptedException | MyException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void updateUI(){
        try{
            List<ProgramState> programsList = controller.getRepository().getProgramList();
            numberOfProgramStates.setText(String.valueOf(programsList.size()));
            heapTable.getItems().clear();
            programsList.stream().flatMap(p->p.getHeap().getAll().entrySet().stream())
                    .forEach(e->heapTable.getItems().add(new HeapEntry(e.getKey(),e.getValue().toString())));

            outList.getItems().clear();
            programsList.stream().flatMap(p -> p.getOut().getList().stream())
                    .forEach(v -> outList.getItems().add(v.toString()));

            fileList.getItems().clear();
            programsList.stream()
                    .flatMap(p -> p.getFileTable().getTable().keySet().stream())
                    .forEach(fileName -> fileList.getItems().add(fileName.toString()));

            programStateIds.getItems().clear();
            for (ProgramState p : programsList) {
                programStateIds.getItems().add(p.getId());
            }

            Integer selectedId = programStateIds.getSelectionModel().getSelectedItem();
            if (selectedId == null && !programStateIds.getItems().isEmpty()) {
                programStateIds.getSelectionModel().selectFirst();
                selectedId = programStateIds.getSelectionModel().getSelectedItem();
            }

            if(selectedId != null) updateSelectedProgramState(selectedId);
        }catch(MyException e){
            e.printStackTrace();
        }
    }

    private void updateSelectedProgramState(int selectedId){
        try{
            ProgramState selectedProgram = controller.getRepository().getProgramList().stream().filter(p->p.getId()==selectedId).findFirst().orElseThrow();
            symbolTableView.getItems().clear();
            ISymbolTable<String, IValue> symbolTable = selectedProgram.getSymbolTable();
            symbolTable.getAll().forEach((var, val) -> symbolTableView.getItems().add(new SymbolTableEntry(var, val.toString())));

            executionStackList.getItems().clear();
            IExecutionStack<IStatement> stack = selectedProgram.getExecutionStack();
            List<IStatement> reversed = new ArrayList<>(stack.getAll());
            Collections.reverse(reversed);
            reversed.forEach(stmt -> executionStackList.getItems().add(stmt.toString()));
        }catch(MyException e){
            e.printStackTrace();
        }
    }

    public static class HeapEntry {
        public int address;
        public String value;
        public HeapEntry(int address, String value) { this.address = address; this.value = value; }
    }

    public static class SymbolTableEntry {
        public String varName;
        public String value;
        public SymbolTableEntry(String varName, String value) { this.varName = varName; this.value = value; }
    }

}
