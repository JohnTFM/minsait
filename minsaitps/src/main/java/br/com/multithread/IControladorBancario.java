package br.com.multithread;

import br.com.multithread.gerencia.ContaBancaria;
import br.com.multithread.gerencia.HistoricoTransferencia;

import java.math.BigDecimal;
import java.util.List;

public interface IControladorBancario {
    void transferirSaldo(ContaBancaria contaParaDebito, ContaBancaria contaParaDeposito, BigDecimal quantidade);
    List<HistoricoTransferencia> obterHistorico();
}
