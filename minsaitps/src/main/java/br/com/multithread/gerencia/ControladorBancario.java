package br.com.multithread.gerencia;

import br.com.multithread.IControladorBancario;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class ControladorBancario implements IControladorBancario {

    public final List<ContaBancaria> contas;

    public final List<HistoricoTransferencia> historicoTransferencias = Collections.synchronizedList(new ArrayList<>());

    public ControladorBancario(){
        this.contas = new ArrayList<>();
    }

    public ControladorBancario(List<ContaBancaria> contasIniciais){
        this.contas = new ArrayList<>(contasIniciais);
    }

    private final ConcurrentHashMap<UUID, Semaphore> uuidsOcupados = new ConcurrentHashMap<>();

    private void espacoSeguroTransacionalExecutor(ContaBancaria a, ContaBancaria b, EspacoSeguroTransacional espacoSeguroTransacinal) throws InterruptedException {

        uuidsOcupados.putIfAbsent(a.getId(), new Semaphore(1));

        uuidsOcupados.putIfAbsent(b.getId(), new Semaphore(1));

        List<ContaBancaria> determinisctOrderedList = new ArrayList<>(List.of(a, b));

        determinisctOrderedList.sort(Comparator.comparing(ContaBancaria::getCriadoEm));

        Semaphore aSem = uuidsOcupados.get(determinisctOrderedList.get(0).getId());

        Semaphore bSem = uuidsOcupados.get(determinisctOrderedList.get(1).getId());

        try{
            aSem.acquire();
            bSem.acquire();
            /*
            Regiao critica start
             */
            espacoSeguroTransacinal.execute();
            /*
            Regiao critica end
             */
        } catch (InterruptedException e){
            throw e;
        }finally {
            aSem.release();
            bSem.release();
        }

    }

    @Override
    public void transferirSaldo(ContaBancaria contaParaDebito,ContaBancaria contaParaDeposito, BigDecimal quantidade){

        if(quantidade==null){
            throw new IllegalArgumentException("Quantidade nula não pode!");
        }

        if(contaParaDebito==null){
            throw new IllegalArgumentException("Conta para debito nula!");
        }

        if(contaParaDeposito==null){
            throw new IllegalArgumentException("Conta para deposito nula!");
        }
        if(!new HashSet<>(this.contas).containsAll(List.of(contaParaDebito,contaParaDebito))){
            throw new IllegalArgumentException("Uma das contas não está presente em nossos registros!");
        }


        try {
            espacoSeguroTransacionalExecutor(contaParaDeposito,contaParaDebito, ()->{

                if(contaParaDebito.getSaldo().compareTo(quantidade)<0){

                    throw new InterruptedException("A operação teve que ser interrompida pois a conta a debitar nao tem saldo suficiente!");

                }

                contaParaDebito.debitar(quantidade);

                contaParaDeposito.depositar(quantidade);

                HistoricoTransferencia historicoTransferencia = new HistoricoTransferencia();
                historicoTransferencia.setContaParaDebito(contaParaDebito.getId());
                historicoTransferencia.setContaParaDeposito(contaParaDeposito.getId());
                historicoTransferencia.setValor(quantidade);
                historicoTransferencia.setOcorridoEm(System.nanoTime());
                historicoTransferencias.add(historicoTransferencia);

            });
        } catch (InterruptedException e) {
            System.out.println("Ocorreu um erro TRATADO ao adicionar o saldo!");
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<HistoricoTransferencia> obterHistorico() {
        return this.historicoTransferencias;
    }


}
