package br.com.multithread.gerencia;

import java.math.BigDecimal;
import java.util.UUID;

public class HistoricoTransferencia {

    UUID contaParaDebito;

    UUID contaParaDeposito;

    long ocorridoEm;

    BigDecimal valor;

    public UUID getContaParaDebito() {
        return contaParaDebito;
    }

    public void setContaParaDebito(UUID contaParaDebito) {
        this.contaParaDebito = contaParaDebito;
    }

    public UUID getContaParaDeposito() {
        return contaParaDeposito;
    }

    public void setContaParaDeposito(UUID contaParaDeposito) {
        this.contaParaDeposito = contaParaDeposito;
    }

    public long getOcorridoEm() {
        return ocorridoEm;
    }

    public void setOcorridoEm(long ocorridoEm) {
        this.ocorridoEm = ocorridoEm;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
