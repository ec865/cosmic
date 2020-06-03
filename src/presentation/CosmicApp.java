package presentation;

import business.Evaluation;

public class CosmicApp {

    public static void main(String[] args) {
        Evaluation evaluation = new Evaluation("src/input/mail_validator.txt");
        evaluation.start();
    }
}
