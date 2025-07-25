### Definição
Hibernate é uma implementação da JPA. JPA é a API padrão para persistência de dados em Java e ORM.
O hibernate serve para fornecer implementações à nível de código para a especificação. E além disso
também possui extensões de métodos que suplementam a JPA sob o custo do seu código depender mais dele.

### Como ele facilita o ORM
O Hibernate cria queries, realiza desserializações de dados do banco de dados, trata exceções, gerencia
conexões, transações e serializa classes Java do mundo relacional para tabelas de banco de dados.<br/>
Segue abaixo um exemplo de entidade mapeada com hibernate: <br/>

```java
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_nome", nullable = false)
    private String clienteNome;

    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    /**
     *  Essa annotation não pertence à JPA, porém faz parte da implementação do hibernate! E serve para
     *  realizar uma subquery para o atributo especificado!
     */
    @Formula("(SELECT SUM(i.preco_unitario * i.quantidade) FROM item_pedido i WHERE i.pedido_id = id)")
    private Double valorTotal;

    public Pedido() {}

    public Pedido(String clienteNome) {
        this.clienteNome = clienteNome;
        this.dataPedido = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Double getValorTotal() {
        return valorTotal;
    }
}

```