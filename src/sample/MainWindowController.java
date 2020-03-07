package sample;

import Model.Commands.Command;
import Model.Commands.RunExample;
import Model.ProgramState.*;
import Model.Statements.IStatement;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.*;
import Controller.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;

public class MainWindowController implements Initializable {
    private Repository repo;
    private Controller ctr;

    private MyStack<IStatement> stk;
    private MyDictionary<String, Value> dict;
    private MyList<Value> list;
    private MyDictionary<StringValue, BufferedReader> fileTable;
    private MyHeap<Value> heap;
    private MySemaphore<Tuple3<Integer, List<Integer>, Integer>> semTable;
    private MyDictionary<String, Type> typeEnv;

    private ProgramState selectedState;

    @FXML private TextField prgNr;
    @FXML private TableView<GUIDataEntrySymbolTable> heapTable, symTable;
    @FXML private ListView<String> outList;
    @FXML private ListView<String> fileTables;
    @FXML private ListView<String> programStates;
    @FXML private ListView<String> exeStack;
    @FXML private TableView<SemaphoreGUIEntry> semTableGUI;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void updateCommand(IStatement st) {
        stk = new MyStack<IStatement>();
        dict = new MyDictionary<String, Value>();
        list = new MyList<Value>();
        fileTable = new MyDictionary<StringValue, BufferedReader>();
        heap = new MyHeap<Value>();
        semTable = new MySemaphore<Tuple3<Integer, List<Integer>, Integer>>();
        //typeEnv = new MyDictionary<String, Type>();


        //st.typecheck(typeEnv);
        //System.out.println(typeEnv);
        ProgramState prg = new ProgramState(stk, dict, list, fileTable, heap, semTable, st, 1);//Integer.parseInt(key));

        List<ProgramState> l = new LinkedList<ProgramState>();
        l.add(prg);
        selectedState = prg;
        repo = new Repo(l,"D:\\Info\\Faculta\\MAP\\A2\\src\\log.txt");
        ctr = new Controller(repo);

        Command c = new RunExample("1", st.toString(), ctr);

        System.out.println(list.toString());
        populateData();
        //c.execute();
    }

    @FXML
    public void selectPS() {
        String selected = programStates.getSelectionModel().getSelectedItem();
        for(var i : repo.getPrgList())
            if (Integer.toString(i.getId()).equals(selected)) {
                selectedState = i;
                populateData();
                return;
            }
    }

    private void populateData () {
        // prgNr
        prgNr.setText(Integer.toString(repo.getPrgList().size()));

        // heap
        TableColumn heapTableAddressColumn = new TableColumn("Address");
        TableColumn heapTableValueColumn = new TableColumn("Value");
        heapTableAddressColumn.setCellValueFactory(new PropertyValueFactory<GUIDataEntrySymbolTable,String>("variableName"));
        heapTableValueColumn.setCellValueFactory(new PropertyValueFactory<GUIDataEntrySymbolTable,String>("value"));
        heapTable.getColumns().clear();
        heapTable.getColumns().add(heapTableAddressColumn);
        heapTable.getColumns().add(heapTableValueColumn);

        List<GUIDataEntrySymbolTable> listOfEntriesHeap = new ArrayList<GUIDataEntrySymbolTable>();
        for (Integer index:selectedState.getHeap().getContent().keySet()) {
            GUIDataEntrySymbolTable entry = new GUIDataEntrySymbolTable(index.toString(), selectedState.getHeap().getContent().get(index).toString());
            listOfEntriesHeap.add(entry);
        }

        ObservableList<GUIDataEntrySymbolTable> symbolTableObservableListHeap = FXCollections.observableList(listOfEntriesHeap);
        heapTable.setItems(symbolTableObservableListHeap);

        // out
        ObservableList<String> oitems = FXCollections.observableArrayList();

        for(var i : list.getList())
            oitems.add(i.toString());
        outList.setItems(oitems);

        // file table
        ObservableList<String> ftitems = FXCollections.observableArrayList();

        ftitems.addAll(String.valueOf(fileTable.getContent().entrySet()));
        fileTables.setItems(ftitems);

        //exe stack
        ObservableList<String> sitems = FXCollections.observableArrayList();

        for(var i : selectedState.getStk().getStack())
            sitems.add(i.toString());
        exeStack.setItems(sitems);

        //program states
        ObservableList<String> psitems = FXCollections.observableArrayList();

        for(var i : repo.getPrgList())
            psitems.add(Integer.toString(i.getId()));
        programStates.setItems(psitems);

        // sym table
        TableColumn symbolTableAddressColumn = new TableColumn("Variable name");
        TableColumn symbolTableValueColumn = new TableColumn("Value");
        symbolTableAddressColumn.setCellValueFactory(new PropertyValueFactory<GUIDataEntrySymbolTable,String>("variableName"));
        symbolTableValueColumn.setCellValueFactory(new PropertyValueFactory<GUIDataEntrySymbolTable,String>("value"));
        symTable.getColumns().clear();
        symTable.getColumns().add(symbolTableAddressColumn);
        symTable.getColumns().add(symbolTableValueColumn);

        List<GUIDataEntrySymbolTable> listOfEntriesSym = new ArrayList<GUIDataEntrySymbolTable>();
        for (String index:selectedState.getSymTable().getContent().keySet()) {
            GUIDataEntrySymbolTable entry = new GUIDataEntrySymbolTable(index, selectedState.getSymTable().getContent().get(index).toString());
            listOfEntriesSym.add(entry);
        }

        ObservableList<GUIDataEntrySymbolTable> symbolTableObservableListSym = FXCollections.observableList(listOfEntriesSym);
        symTable.setItems(symbolTableObservableListSym);




        // semaphore
        TableColumn semaphoreTableAddressColumn = new TableColumn("Index");
        TableColumn semaphoreTableValueColumn = new TableColumn("Value");
        TableColumn semaphoreTableListOfValuesColumn = new TableColumn("List");
        semaphoreTableAddressColumn.setCellValueFactory(new PropertyValueFactory<SemaphoreGUIEntry,String>("index"));
        semaphoreTableValueColumn.setCellValueFactory(new PropertyValueFactory<SemaphoreGUIEntry,String>("value"));
        semaphoreTableListOfValuesColumn.setCellValueFactory(new PropertyValueFactory<SemaphoreGUIEntry,String>("listOfValues"));
        semTableGUI.getColumns().clear();
        semTableGUI.getColumns().add(semaphoreTableAddressColumn);
        semTableGUI.getColumns().add(semaphoreTableValueColumn);
        semTableGUI.getColumns().add(semaphoreTableListOfValuesColumn);

        List<SemaphoreGUIEntry> listOfEntriesSemaphore = new ArrayList<SemaphoreGUIEntry>();
        for (Integer index:selectedState.getSemaphoreTable().getContent().keySet()) {
            SemaphoreGUIEntry entry = new SemaphoreGUIEntry(index.toString(), selectedState.getSemaphoreTable().getContent().get(index).t1.toString()
                                                            , selectedState.getSemaphoreTable().getContent().get(index).t2.toString());
            listOfEntriesSemaphore.add(entry);
        }

        ObservableList<SemaphoreGUIEntry> symbolTableObservableListSemaphore = FXCollections.observableList(listOfEntriesSemaphore);
        semTableGUI.setItems(symbolTableObservableListSemaphore);
    }

    @FXML
    public void allStep() {
            //while(prgList.size() > 0){
            ctr.executor = Executors.newFixedThreadPool(2);
            //remove the completed programs
            List<ProgramState> prgList = ctr.removeCompletedPrg(repo.getPrgList());

            Map<Integer, Value> heap = new HashMap<Integer, Value>();

            List<ProgramState> finalPrgList = prgList;
            prgList.forEach(prg -> heap.putAll(
                    ctr.unsafeGarbageCollector(ctr.getAddrFromSymTables(finalPrgList), prg.getHeap().getContent())));

            ctr.oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList = ctr.removeCompletedPrg(repo.getPrgList());

            ctr.executor.shutdownNow();
            //HERE the repository still contains at least one Completed Prg
            // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
            //setPrgList of repository in order to change the repository

            // update the repository state
            repo.setPrgList(prgList);

            populateData();

        /*catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }*/
    }
}
