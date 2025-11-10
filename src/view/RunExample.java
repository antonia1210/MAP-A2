package view;
import controller.Controller;
import exception.MyException;

public class RunExample extends Command {
    private Controller controller;
    private boolean executed = false;
    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }
    @Override
    public void execute() {
        if (executed){
            System.out.println("Already executed");
            return;
        }
        try{
            controller.allSteps();
        }catch(MyException e){
            System.out.println("Error during execution of example" + e.getMessage());
        }
        executed = true;
    }
}
