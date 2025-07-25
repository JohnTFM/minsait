    package br.com.multithread.gerencia;

    import java.math.BigDecimal;
    import java.util.UUID;

    public class ContaBancaria {

        private UUID id;

        private BigDecimal saldo;

        private final long criadoEm;

        public ContaBancaria(BigDecimal saldoInicial){
            this.id = UUID.randomUUID();
            this.saldo = saldoInicial!=null ? saldoInicial : BigDecimal.ZERO;
            this.criadoEm = System.nanoTime();
        }


        void depositar(BigDecimal dinheiroParaAdicionar){
            this.saldo = this.saldo.add(dinheiroParaAdicionar);
        }

        void debitar(BigDecimal dinheiroParaSubtrair){
            this.saldo = this.saldo.subtract(dinheiroParaSubtrair);
        }

        public BigDecimal getSaldo() {
            return saldo;
        }

        public long getCriadoEm() {
            return criadoEm;
        }

        public UUID getId() {
            return id;
        }
    }
