package view;

import com.sun.jdi.Value;
import com.sun.source.tree.CompilationUnitTree;
import controller.Controller;
import model.ProgramState;
import model.adt.*;
import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.*;
import repository.Repository;

import javax.swing.plaf.ComponentUI;
import java.util.concurrent.CountDownLatch;

public class Interpreter {
    public static void main(String[] args) {
        IStatement ex1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
        ProgramState prg1 = new ProgramState(new Heap(), new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex1);
        Repository repo1 = new Repository("log1.txt");
        repo1.addProgram(prg1);
        Controller ctr1 = new Controller(repo1);
        IStatement ex2 = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)), '*'), '+')),
                                new CompoundStatement(
                                        new AssignmentStatement("b",
                                                new ArithmeticExpression(new VariableExpression("a"),
                                                        new ValueExpression(new IntValue(1)), '+')),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
        ProgramState prg2 = new ProgramState(new Heap(), new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex2);
        Repository repo2 = new Repository("log2.txt");
        repo2.addProgram(prg2);
        Controller ctr2 = new Controller(repo2);
        IStatement ex3 = new CompoundStatement(
                new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
        ProgramState prg3 = new ProgramState(new Heap(), new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex3);
        Repository repo3 = new Repository("log3.txt");
        repo3.addProgram(prg3);
        Controller ctr3 = new Controller(repo3);
        IStatement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        ProgramState prg4 = new ProgramState(new Heap(), new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex4);
        Repository repo4 = new Repository("log4.txt");
        repo4.addProgram(prg4);
        Controller ctr4 = new Controller(repo4);
        IStatement ex5 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)),">"),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '-'))
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
        ProgramState prg5 = new ProgramState(new Heap(), new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex5);
        Repository repo5 = new Repository("log5.txt");
        repo5.addProgram(prg5);
        Controller ctr5 = new Controller(repo5);
        IStatement ex6 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(
                                                        new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                                new ValueExpression(new IntValue(5)),'+'
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        ProgramState prg6 = new ProgramState(new Heap(), new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex6);
        Repository repo6 = new Repository("log6.txt");
        repo6.addProgram(prg6);
        Controller ctr6 = new Controller(repo6);
        IStatement ex7 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                        new ReadHeapExpression(new VariableExpression("v")),
                                                        new ValueExpression(new IntValue(5)),'+'
                                                )
                                        )
                                )
                        )
                )
        );
        ProgramState prg7 = new ProgramState(new Heap(), new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex7);
        Repository repo7 = new Repository("log7.txt");
        repo7.addProgram(prg7);
        Controller ctr7 = new Controller(repo7);
        IStatement ex8 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(
                                                        new ReadHeapExpression(
                                                                new ReadHeapExpression(
                                                                        new VariableExpression("a")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        ProgramState prg8 = new ProgramState(new Heap(), new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex8);
        Repository repo8 = new Repository("log8.txt");
        repo8.addProgram(prg8);
        Controller ctr8 = new Controller(repo8);
        IStatement ex9 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        ProgramState prg9 = new ProgramState(new Heap(), new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex9);
        Repository repo9 = new Repository("log9.txt");
        repo9.addProgram(prg9);
        Controller ctr9 = new Controller(repo9);
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
        menu.show();
    }
}
