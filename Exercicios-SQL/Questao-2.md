## 2.1 
Procedure em um PostgreSQL
```sql
CREATE OR REPLACE FUNCTION insert_transaction(
    p_account_id INT,
    p_amount DECIMAL(15,2)
) RETURNS VOID AS $$
DECLARE
    v_old_balance DECIMAL(15,2);
    v_new_balance DECIMAL(15,2);
    v_transaction_id INT;
BEGIN
    BEGIN

        SELECT balance INTO v_old_balance
        FROM accounts
        WHERE account_id = p_account_id
        FOR UPDATE;  

        IF NOT FOUND THEN
            RAISE EXCEPTION 'Conta % não encontrada', p_account_id;
        END IF;

        v_new_balance := v_old_balance + p_amount;

        UPDATE accounts
        SET balance = v_new_balance
        WHERE account_id = p_account_id;

        INSERT INTO transactions (account_id, amount, transaction_date)
        VALUES (p_account_id, p_amount, CURRENT_TIMESTAMP)
        RETURNING transaction_id INTO v_transaction_id;

        INSERT INTO audit_log (
            transaction_id,
            account_id,
            old_balance,
            new_balance,
            change_date
        ) VALUES (
            v_transaction_id,
            p_account_id,
            v_old_balance,
            v_new_balance,
            CURRENT_TIMESTAMP
        );

        COMMIT;

    EXCEPTION WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
    END;
END;
$$ LANGUAGE plpgsql;
```

## 2.2
```sql
CREATE OR REPLACE FUNCTION log_transaction_change()
RETURNS TRIGGER AS $$
DECLARE
    v_old_balance DECIMAL(15,2);
    v_new_balance DECIMAL(15,2);
BEGIN
    SELECT balance INTO v_old_balance
    FROM accounts
    WHERE account_id = NEW.account_id
    FOR UPDATE;

    v_new_balance := v_old_balance + NEW.amount;

    UPDATE accounts
    SET balance = v_new_balance
    WHERE account_id = NEW.account_id;

    INSERT INTO audit_log (
        transaction_id,
        account_id,
        old_balance,
        new_balance,
        change_date
    ) VALUES (
        NEW.transaction_id,
        NEW.account_id,
        v_old_balance,
        v_new_balance,
        CURRENT_TIMESTAMP
    );

    RETURN NULL;  
END;
$$ LANGUAGE plpgsql;
```
- Criando o trigger
```sql
CREATE TRIGGER trg_after_transaction_insert
AFTER INSERT ON transactions
FOR EACH ROW
EXECUTE FUNCTION log_transaction_change();
```

## 2.3
O uso de transactions COMMIT e ROLLBACK ajudam a manter a integridade pois elas separam operações
de banco de dados em espaços seguros e com erros tratados. Inserts errôneos, dados inconsistentes
e exceções inesperadas podem ser tratadas graças ao sistema de transactions e graças ao ROLLBACK,
além de evitar que consultas concorrentes vejam os seus dados "no meio do caminho das operações",
e evitar inserções indevidas no meio da transaction graças ao bloqueio de tabelas.

## 2.4
Triggers são fundamentais para garantir integridade e segurança dos dados, pois permitem que ações automáticas sejam 
executadas diante de eventos como inserções, atualizações ou exclusões. Eles ajudam a manter regras de negócio consistentes 
sem depender exclusivamente da lógica da aplicação, o que protege o banco contra usos indevidos ou erros de desenvolvedores externos.
<br/>
Além disso, triggers permitem registrar logs de auditoria, impedir alterações não autorizadas e validar dados em tempo real. 
Com isso, evitam que informações inválidas sejam persistidas, reforçando a segurança e a consistência do banco de dados como um todo.

## 2.5
A procedure insert_transaction foi criada para inserir uma nova transação em uma conta bancária de forma segura e consistente. <br/>
Ela executa três passos principais dentro de um bloco de transação com BEGIN, COMMIT e ROLLBACK:

1. Consulta o saldo atual da conta (accounts) com bloqueio (FOR UPDATE) para evitar concorrência.

2. Calcula o novo saldo com base no valor da transação e atualiza a tabela de contas.

3. Insere o registro da transação (transactions) e grava os dados de auditoria na tabela audit_log, contendo o saldo anterior, 
o novo saldo e a data da mudança. Caso ocorra qualquer erro no processo, o ROLLBACK garante que nenhuma operação parcial seja salva.

Já o trigger trg_after_transaction_insert foi implementado para ser executado automaticamente após a inserção de uma transação. 
Ele utiliza a função log_transaction_change, que:
1. Lê o saldo atual da conta envolvida

2. Calcula o novo saldo somando o valor da transação

3. Atualiza o saldo na tabela accounts

4. Registra a alteração na tabela audit_log com os saldos antigo e novo

Dessa forma, o trigger garante que qualquer nova transação gravada gere automaticamente a atualização de saldo e a respectiva auditoria, reforçando a integridade e a rastreabilidade dos dados no banco.


## 2.6
A procedure e o trigger foram implementados de forma segura para evitar vulnerabilidades como SQL Injection. 
Todos os parâmetros foram feitos de maneira fortemente tipadas (`INT`, `DECIMAL`), e não há uso de SQL dinâmico, 
assim, tudo foi feito com comandos estáticos.

A procedure utiliza blocos de transação com `BEGIN`, `COMMIT` e `ROLLBACK`, garantindo consistência em caso de erro. 
Também há validação da existência da conta antes de atualizar os valores.

Como nenhuma query é montada dinamicamente e todos os dados são tratados como parâmetros, o risco de injeção é eliminado. 
Além disso, o trigger executa apenas ações controladas, sem expor o banco a comandos externos, o que garante a segurança
e previne novamente contra SQL Injection.

## 2.7
Utilizei uma procedure para obter o próximo número de um Acórdão, documento que contém a resolução de um processo jurídico,
eles são númerados com base em um cálculo que deve ser consistente e atômico, já que múltiplos Acórdão são cadastrados 
simultâneamente na plataforma. Além disso, um trigger simples estava envolvido para incrementar o contador e registrar o
uso do número pelo processo jurídico. 





