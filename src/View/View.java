package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Controller.Controller;
import Model.Commands.*;
import Model.Expressions.*;
import Model.MyException;
import Model.ProgramState.*;
import Model.Statements.*;
import Model.Types.*;
import Model.Values.*;
import Repository.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextFormatter;

import javax.management.ValueExp;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class View extends Application {
    /*public static void main() {
       try {
            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(CreateCmd("1", Ex1(), "D:\\Info\\Faculta\\MAP\\A2\\src\\log1.txt"));
            menu.addCommand(CreateCmd("2", Ex2(), "D:\\Info\\Faculta\\MAP\\A2\\src\\log2.txt"));
            menu.addCommand(CreateCmd("3", Ex3(), "D:\\Info\\Faculta\\MAP\\A2\\src\\log3.txt"));
            menu.addCommand(CreateCmd("4", A3Test(), "D:\\Info\\Faculta\\MAP\\A2\\src\\log4.txt"));
            menu.addCommand(CreateCmd("5", HeapTest(), "D:\\Info\\Faculta\\MAP\\A2\\src\\logHeap.txt"));
            menu.addCommand(CreateCmd("6", WhileTest(), "D:\\Info\\Faculta\\MAP\\A2\\src\\logWhile.txt"));
            menu.addCommand(CreateCmd("7", ThreadTest(), "D:\\Info\\Faculta\\MAP\\A2\\src\\logThread.txt"));
            menu.show();
        }
        catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }*/

    public static List<IStatement> getExamples() {
        List<IStatement> l = new LinkedList<IStatement>();
        l.add(Ex1());
        l.add(Ex2());
        l.add(Ex3());
        l.add(A3Test());
        l.add(HeapTest());
        l.add(WhileTest());
        l.add(ThreadTest());
        l.add(ExamTest());
        return l;
    }

    private static IStatement ExamTest() {
        /*
        Ref int v1; int cnt;
        new(v1,2);newSemaphore(cnt,rH(v1),1);
        fork(acquire(cnt);wh(v1,rh(v1)*10));print(rh(v1));release(cnt));
        fork(acquire(cnt);wh(v1,rh(v1)*10));wh(v1,rh(v1)*2));print(rh(v1));release(cnt));
        acquire(cnt);
        print(rh(v1)-1);
        release(cnt)
        The final Out should be {20,200,199} or {20,19,200}.
         */
        return new CompoundStatement(new VarDeclareStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VarDeclareStatement("cnt", new IntType()),
                    new CompoundStatement(new NewStatement("v1", new ValueExpression(new IntValue(2))),
                        new CompoundStatement(new SemaphoreStatement("cnt", new RHExpression(new VarExpression("v1")), new ValueExpression(new IntValue(1))),
                            new CompoundStatement(new ForkStatement(
                                    new CompoundStatement(new AquireStatement("cnt"),
                                        new CompoundStatement(new WHStatement("v1", new ArithmeticExpression('*',new RHExpression(new VarExpression("v1")), new ValueExpression(new IntValue(10)))),
                                            new CompoundStatement(new PrintStatement(new RHExpression(new VarExpression("v1"))),
                                                new ReleaseStatement("cnt"))))),
                                new CompoundStatement(new ForkStatement(
                                        new CompoundStatement(new AquireStatement("cnt"),
                                            new CompoundStatement(new WHStatement("v1", new ArithmeticExpression('*',new RHExpression(new VarExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                new CompoundStatement(new WHStatement("v1", new ArithmeticExpression('*',new RHExpression(new VarExpression("v1")), new ValueExpression(new IntValue(2)))),
                                                    new CompoundStatement(new PrintStatement(new RHExpression(new VarExpression("v1"))),
                                                        new ReleaseStatement("cnt")))))),
                                new CompoundStatement(new AquireStatement("cnt"),
                                    new CompoundStatement(new PrintStatement(new ArithmeticExpression('-', new RHExpression(new VarExpression("v1")), new ValueExpression(new IntValue(1)))),
                                        new ReleaseStatement("cnt")))))))));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/SelectWindow.fxml"));
        primaryStage.setTitle("My Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /*public static Command CreateCmd(String key, IStatement st, String logPath) {
        MyStack<IStatement> stk = new MyStack<IStatement>();
        MyDictionary<String, Value> dict = new MyDictionary<String, Value>();
        MyList<Value> list = new MyList<Value>();
        MyDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<StringValue, BufferedReader>();
        MyHeap<Value> heap = new MyHeap<Value>();
        MyDictionary<String, Type> typeEnv = new MyDictionary<String, Type>();
        st.typecheck(typeEnv);
        ProgramState prg = new ProgramState(stk, dict, list, fileTable, heap, st, 1);//Integer.parseInt(key));

        List<ProgramState> l = new LinkedList<ProgramState>();
        l.add(prg);
        Repository repo = new Repo(l,logPath);
        Controller ctr = new Controller(repo);
        return new RunExample(key, st.toString(),ctr);
    }*/

    private static IStatement ChangeExampleMenu() {
        System.out.println("n=[1..7]-Example n");
        Scanner myScanner = new Scanner(System.in);
        return ChangeExample(myScanner.nextLine());
    }

    private static IStatement ChangeExample(String ex) {
        switch(ex) {
            case "1":
                return Ex1();
            case "2":
                return Ex2();
            case "3":
                return Ex3();
            case "4":
                return A3Test();
            case "5":
                return HeapTest();
            case "6":
                return WhileTest();
            case "7":
                return ThreadTest();
            default:
                System.out.println("Invalid example. Defaulted to Example 1.");
                return Ex1();
        }
    }

    private static IStatement ThreadTest() {
        /*int v; Ref int a; v=10;new(a,22);
        fork(wH(a,30);v=32;print(v);print(rH(a)));
        print(v);print(rH(a))
        */
        return new CompoundStatement(new VarDeclareStatement("v", new IntType()),
                new CompoundStatement(new VarDeclareStatement("a", new RefType(new IntType())),
                    new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                        new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(22))),
                            new CompoundStatement(new ForkStatement(
                                    new CompoundStatement(new WHStatement("a", new ValueExpression(new IntValue(30))),
                                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                            new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                                new PrintStatement(new RHExpression(new VarExpression("a"))))))),
                                    new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                        new PrintStatement(new RHExpression(new VarExpression("a")))))))));
    }

    private static IStatement WhileTest() {
        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        return new CompoundStatement(new VarDeclareStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                    new CompoundStatement(
                        new WhileStatement(new RelationalExpression(">", new VarExpression("v"), new ValueExpression(new IntValue(0))),
                            new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                new AssignStatement("v", new ArithmeticExpression('-', new VarExpression("v"), new ValueExpression(new IntValue(1)))))),
                        new PrintStatement(new VarExpression("v")))));
    }

    private static IStatement HeapTest() {
        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        return new CompoundStatement(new VarDeclareStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new PrintStatement(new RHExpression(new VarExpression("v"))),
                        new CompoundStatement(new WHStatement("v", new ValueExpression(new IntValue(30))),
                            new PrintStatement(
                                new ArithmeticExpression('+', new RHExpression(new VarExpression("v")), new ValueExpression(new IntValue(5))))))));
    }

    private static IStatement A3Test () {
        /*string varf;
        varf="test.in";
        openRFile(varf);
        int varc;
        readFile(varf,varc);print(varc);
        readFile(varf,varc);print(varc)
        closeRFile(varf)*/

        return new CompoundStatement(new VarDeclareStatement("varf", new StringType()),
            new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("D:\\Info\\Faculta\\MAP\\A2\\src\\test.in"))),
                    new CompoundStatement(new OpenRFile(new VarExpression("varf")),
                            new CompoundStatement(new VarDeclareStatement("varc", new IntType()),
                                    new CompoundStatement(new ReadFile(new VarExpression("varf"), "varc"),
                                            new CompoundStatement(new PrintStatement(new VarExpression("varc")),
                                                    new CompoundStatement(new ReadFile(new VarExpression("varf"), "varc"),
                                                            new CompoundStatement(new PrintStatement(new VarExpression("varc")),
                                                                    new CloseRFile(new VarExpression("varf"))))))))));
    }

    private static IStatement Ex1() {
        /*int v; v = 2;
        Print(v) is represented as:
         */
        return new CompoundStatement(new VarDeclareStatement("v", new IntType()),
            new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                    new PrintStatement(new VarExpression("v"))));
    }
    static private IStatement Ex2() {
    /*int a;  int b;
    a=2+3*5;    b=a+1;
    Print(b) is represented as:
    */
        return new CompoundStatement( new VarDeclareStatement("a",new IntType()),
            new CompoundStatement(new VarDeclareStatement("b",new IntType()),
                new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                    ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                    new CompoundStatement(new AssignStatement("b",new ArithmeticExpression('+',new VarExpression("a"), new
                        ValueExpression(new IntValue(1)))), new PrintStatement(new VarExpression("b"))))));
    }

    static private IStatement Ex3() {
    /*boolean a; int v; a=true;
    (If a Then v=2 Else v=3);
    Print(v) is represented as
    */
        return new CompoundStatement(new VarDeclareStatement("a",new BoolType()),
            new CompoundStatement(new VarDeclareStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                    new CompoundStatement(new IfStatement(new VarExpression("a"),new AssignStatement("v",new ValueExpression(new
                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                        VarExpression("v"))))));
    }


    /*private static void Menu() {
        System.out.println("Hello,\nPlease input a program and then execute it. By default, the program is Example 1.");

        MyStack<IStatement> stk = new MyStack<IStatement>();
        MyDictionary<String, Value> dict = new MyDictionary<String, Value>();
        MyList<Value> list = new MyList<Value>();
        MyDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<StringValue, BufferedReader>();
        MyHeap<Value> heap = new MyHeap<Value>();

        System.out.println("Enter path to save log:");      // D:\Info\Faculta\MAP\A2\src\test.out
        Scanner myScanner = new Scanner(System.in);
        String path = myScanner.nextLine();

        ProgramState p = new ProgramState(stk, dict, list, fileTable, heap, Ex1());
        Repo r = new Repo(p, path);
        Controller c = new Controller(r);

        String input;
        while(true) {
            System.out.println("1-input program\n2-one step execution\n3-complete execution\n0-exit");

            input = myScanner.nextLine();
            switch (input) {
                case "0":
                    return;
                case "1":
                    stk = new MyStack<IStatement>();
                    dict = new MyDictionary<String, Value>();
                    list = new MyList<Value>();
                    fileTable = new MyDictionary<StringValue, BufferedReader>();
                    heap = new MyHeap<Value>();

                    p = new ProgramState(stk, dict, list, fileTable, heap, ChangeExampleMenu());
                    r = new Repo(p, path);
                    c = new Controller(r);

                    break;
                case "2":
                    try {
                        c.oneStep(p);
                        c.displayCurrentPrgState();
                    }
                    catch (MyException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    try {
                        c.allStep();
                    }
                    catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }
        }
    }*/

}
