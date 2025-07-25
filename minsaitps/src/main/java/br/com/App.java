package br.com;

import br.com.files.FileManipulator;
import br.com.multithread.BancoMultithread;

public class App {

    public static void main(String[] args) {

        FileManipulator f = new FileManipulator();

        f.manipulate();

        BancoMultithread bancoMultithread = new BancoMultithread();

        bancoMultithread.executarCoisasDoBanco();

    }

}


