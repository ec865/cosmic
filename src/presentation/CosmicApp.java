package presentation;

import business.Evaluation;

public class CosmicApp {

    public static void main(String[] args) {
        Evaluation evaluation = new Evaluation("src/input/employee_payroll_system.txt"); //Please enter the path of your SequenceDiagram output file
        evaluation.start();
    }
}
