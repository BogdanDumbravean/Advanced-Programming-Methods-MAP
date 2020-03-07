package Controller;

import Model.MyException;
import Model.ProgramState.*;
import Model.Statements.IStatement;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.Repository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private Repository repo;
    public ExecutorService executor;

    public Controller(Repository r) {
        repo = r;
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<ProgramState> prgList) throws MyException {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());

        List<ProgramState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callList). stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new MyException(e.getMessage());
                        }
                    })
                    .filter(Objects::nonNull)
                                .collect(Collectors.toList());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assert newPrgList != null;
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        repo.setPrgList(prgList);
    }

    public void allStep() {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> prgList=removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0){
            Map<Integer,Value> heap = new HashMap<Integer, Value>();

            List<ProgramState> finalPrgList = prgList;
            prgList.forEach(prg->heap.putAll(
                    unsafeGarbageCollector(getAddrFromSymTables(finalPrgList), prg.getHeap().getContent())));

            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        // update the repository state
        repo.setPrgList(prgList);
    }


    public Map<Integer,Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }
    public List<Integer> getAddrFromSymTables(List<ProgramState> prgList){
        Collection<Value> symTableValues = new LinkedList<Value>();
        prgList.forEach(prg->symTableValues.addAll(prg.getSymTable().getContent().values()));
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }


    /*public void displayCurrentPrgState() {
        System.out.print("Exec Stack: ");
        System.out.println(repo.getCrtPrg().getStk());
        System.out.print("Symbol Table: ");
        System.out.println(repo.getCrtPrg().getSymTable());
        System.out.print("Out: ");
        System.out.println(repo.getCrtPrg().getOut());
    }*/

}
