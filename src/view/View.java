package view;

import java.util.Scanner;
import controller.Controller;
import model.ProgramState;
import model.adt.ExecutionStack;
import model.adt.Out;
import model.adt.SymbolTable;
import model.exception.MyException;
import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;
import repository.Repository;

public class View {
    void menu(){
        System.out.println("Menu");
        System.out.println("1. Run example 1");
        System.out.println("2. Run example 2");
        System.out.println("3. Run example 3");
        System.out.println("4. Set Display Flag, currently ON");
        System.out.println("0. Exit");
    }
    IStatement example1(){
        IStatement ex1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
        return ex1;
    }
    IStatement example2(){
        IStatement ex2 = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a",
                                        new ArithmeticExpression(new ValueExpression(new IntValue(2)), new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), '*'), '+')
                                ),
                                new CompoundStatement(
                                        new AssignmentStatement("b",
                                                new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), '+')
                                        ),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
        return ex2;
    }
    IStatement example3(){
        IStatement ex3 = new CompoundStatement(
                new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
        return ex3;
    }
    void runExample(IStatement example, boolean DisplayFlag){
        ProgramState program = new ProgramState(new ExecutionStack<>(), new SymbolTable<>(), new Out<>(), example);
        Repository repo = new Repository();
        repo.addProgram(program);
        Controller controller = new Controller(repo);
        controller.setDisplayFlag(DisplayFlag);
        try{
            controller.allSteps();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void start() {
        IStatement example1 = example1();
        IStatement example2 = example2();
        IStatement example3 = example3();
        boolean DisplayFlag = true;
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            menu();
            System.out.print("Enter Your Choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 0 -> running = false;
                case 1 -> runExample(example1, DisplayFlag);
                case 2 -> runExample(example2, DisplayFlag);
                case 3 -> runExample(example3, DisplayFlag);
                case 4 -> {
                    DisplayFlag = !DisplayFlag;
                    System.out.println("Display Flag is set to: " + DisplayFlag);
                }
                default -> System.out.println("Invalid Choice");
            }
        }
        scanner.close();
    }
}