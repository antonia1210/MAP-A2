package view;

import controller.Controller;
import model.ProgramState;
import model.adt.*;
import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.*;
import repository.Repository;

public class Interpreter {
    public static void main(String[] args) {
        IStatement ex1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
        ProgramState prg1 = new ProgramState(new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex1);
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
        ProgramState prg2 = new ProgramState(new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex2);
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
        ProgramState prg3 = new ProgramState(new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex3);
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
        ProgramState prg4 = new ProgramState(new FileTable<>(), new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), ex4);
        Repository repo4 = new Repository("log4.txt");
        repo4.addProgram(prg4);
        Controller ctr4 = new Controller(repo4);
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.show();
    }
}
