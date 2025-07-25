package br.com.multithread;

import br.com.multithread.gerencia.ContaBancaria;
import br.com.multithread.gerencia.ControladorBancario;
import br.com.multithread.gerencia.HistoricoTransferencia;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class BancoMultithread {

    public void executarCoisasDoBanco() {

        // Criação das contas com saldo inicial
        ContaBancaria conta1 = new ContaBancaria(new BigDecimal("100000"));
        ContaBancaria conta2 = new ContaBancaria(new BigDecimal("200000"));
        ContaBancaria conta3 = new ContaBancaria(new BigDecimal("300000"));
        ContaBancaria conta4 = new ContaBancaria(new BigDecimal("400000"));
        ContaBancaria conta5 = new ContaBancaria(new BigDecimal("500000"));

        List<ContaBancaria> contas = Arrays.asList(conta1, conta2, conta3, conta4, conta5);

        // Captura os saldos iniciais ANTES das transações
        Map<UUID, BigDecimal> saldosIniciais = contas.stream()
                .collect(Collectors.toMap(
                        ContaBancaria::getId,
                        ContaBancaria::getSaldo
                ));

        IControladorBancario controlador = new ControladorBancario(contas);

        BigDecimal saldoInicialTotal = contas.stream()
                .map(ContaBancaria::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalThreads = 1000;
        CountDownLatch latch = new CountDownLatch(totalThreads);
        Random random = new Random();

        for (int i = 0; i < totalThreads; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    int fromIndex = random.nextInt(contas.size());
                    int toIndex = random.nextInt(contas.size());

                    if (fromIndex == toIndex) continue;

                    ContaBancaria origem = contas.get(fromIndex);
                    ContaBancaria destino = contas.get(toIndex);
                    BigDecimal valor = BigDecimal.valueOf(random.nextInt(100) + 1L);

                    try {
                        controlador.transferirSaldo(origem, destino, valor);
                    } catch (Exception e) {
                        System.out.println("Tentativa de transferência inválida");
                    }
                }
                latch.countDown();
            }, "Thread-Transfer-" + i).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException("Erro ao aguardar as threads");
        }

        // Verificação 1: Saldo total continua o mesmo
        BigDecimal saldoFinalTotal = contas.stream()
                .map(ContaBancaria::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (saldoInicialTotal.compareTo(saldoFinalTotal) != 0) {
            throw new RuntimeException("Inconsistência total detectada!\nSaldo inicial: " +
                    saldoInicialTotal + "\nSaldo final: " + saldoFinalTotal);
        }

        // Verificação 2: Saldos calculados com base no histórico
        Map<UUID, BigDecimal> saldosCalculados = new HashMap<>(saldosIniciais);
        for (HistoricoTransferencia h : controlador.obterHistorico()) {
            saldosCalculados.computeIfPresent(h.getContaParaDebito(), (k, v) -> v.subtract(h.getValor()));
            saldosCalculados.computeIfPresent(h.getContaParaDeposito(), (k, v) -> v.add(h.getValor()));
        }

        for (ContaBancaria conta : contas) {
            BigDecimal saldoEsperado = saldosCalculados.get(conta.getId());
            BigDecimal saldoReal = conta.getSaldo();
            if (saldoEsperado.compareTo(saldoReal) != 0) {
                throw new RuntimeException("❌ Inconsistência na conta " + conta.getId() +
                        "\nSaldo esperado: " + saldoEsperado + "\nSaldo real:    " + saldoReal);
            }else{
                System.out.println("Saldo de "+conta.getId() + " validado de acordo com histórico");
            }
        }

        System.out.println("Teste com " + totalThreads + " threads passou com sucesso!");
        System.out.println("Saldo total final: " + saldoFinalTotal);
    }


    public void executarCoisasDoBancoTesteSingleThread(){
        ContaBancaria classeS = new ContaBancaria(new BigDecimal("5101000")); //5 milhoes e cento e um mil
        ContaBancaria classeA = new ContaBancaria(new BigDecimal("120100")); // cento e vinte mil e cem
        ContaBancaria classeB = new ContaBancaria(new BigDecimal("40000")); // quarenta mil
        ContaBancaria classeC = new ContaBancaria(new BigDecimal("5000")); // cinco mil
        ContaBancaria classeD = new ContaBancaria(new BigDecimal("-1000")); // menos 1000
        ControladorBancario controladorBancario = new ControladorBancario(List.of(
                classeS,
                classeA,
                classeB,
                classeC,
                classeD
        ));

        try {

            controladorBancario.transferirSaldo(classeD,classeC,new BigDecimal("20"));

        }catch (Exception e){

            System.out.println("Teste 1 passado");

        }

        controladorBancario.transferirSaldo(classeC,classeD,new BigDecimal("20"));

        if(classeD.getSaldo().compareTo(new BigDecimal("-980"))!=0){

            throw new RuntimeException("Erro no Teste 2");

        }

    }

}
