 ## 3.1
 1. Primeira Forma Normal: para atender esse requisito, todas as tabelas devem ter atributos atômicos, ou seja,
somente colunas de tipos simples que não representam múltiplos valores, o que pela imagem do schema é possível ver. Assim,
concluo que as tabelas apresentadas estão normalizadas na Primeira Forma Normal.

2. Segunda Forma Normal: A 2FN exige que todos os atributos não-chave dependam da chave primária inteira, 
e não apenas de parte dela. Como todas as tabelas do schema possuem chave primária simples (ex: `order_id`, `product_id`, etc.), 
não há dependências parciais. Logo, as tabelas também estão em conformidade com a Segunda Forma Normal.

3. Terceira Forma Normal (3FN): A 3FN exige que não existam dependências transitivas entre atributos não-chave.
No schema analisado, por exemplo, os dados do cliente (`customer_name`, `email`) estão corretamente separados da tabela 
de pedidos, evitando que esses campos dependam indiretamente de `order_id`. Assim, as tabelas respeitam a 3ª Forma Normal.

## 3.2
Situações às quais eu preciso de uma lista simples de string que é muito acessada somente pela interface; Situações
onde todo o negócio segue regras relacionais, porém somente uma nova funcionalidade não, então é plausível adicionar
uma coluna TEXT ou BJSON para suportar a nova funcionalidade; Casos onde o JOIN que eu preciso para trazer uma 
informação simples mas muito utilizada está a 15 JOINS de distância, nesse caso vale a pena causar uma redundância
copiando esse novo atributo para alguma tabela mais próxima ou para a primeira, desnormalizando na segunda norma como
consequência. Existem uma gama de situações onde a desnormalização pode ser justificada em prol da performance, escalabilidade
e satisfação de requisitos não funcionais.

## 3.3
```sql
SELECT 
    o.order_id,
    o.order_date,
    o.total_amount,

    c.customer_id,
    c.customer_name,
    c.email,

    oi.order_item_id,
    oi.quantity,
    oi.price AS item_price,

    p.product_id,
    p.product_name,
    p.price AS current_product_price

FROM orders o
JOIN customers c ON o.customer_id = c.customer_id
JOIN order_items oi ON oi.order_id = o.order_id
JOIN products p ON p.product_id = oi.product_id

ORDER BY o.order_date DESC, o.order_id;
```

## 3.4
Para identificar e resolver problemas de performance em consultas com múltiplas tabelas, eu seguiria os seguintes passos:

 - **Análise com `PROFILER`**: Diversas interfaces gráficas de SGBD possuem um profiller que já trás para nós os algoritmos
utilizados, tempo de execução detalhado, quantidade de registros checados e etc... Só isso já nos ajuda a ter uma boa noção
de tudo que ocorre por trás dos panos, e nos ajuda a criar indíces mais corretos e verificar gargalos.

- **Verificação de índices**: Verifico se existem índices nos campos usados em `JOIN`, `WHERE` e `ORDER BY`. 
Faltas de índices nesses pontos são causas comuns de lentidão e em caso de necessidade, crio índices compostos ou específicos para as consultas mais críticas.

- **Redução de colunas e filtros antecipados**:  
   Trago apenas as colunas necessárias e aplico `WHERE` o quanto antes para reduzir o volume de dados processado. 

- **Paginação e limites**:  
   Sempre que possível e viável, aplico `LIMIT` e `OFFSET` para evitar trazer tudo de uma vez. 

- **Materialização **:  
   Em cenários complexos, uso views materializadas para quebrar etapas da consulta e melhorar a legibilidade e performance, através
de cargas de atualizações periódicas. <br/>

Com essas medidas, é possível diagnosticar gargalos e aplicar soluções específicas para manter as consultas rápidas e escaláveis.

## 3.5
Para garantir alta disponibilidade em uma aplicação de e-commerce, é fundamental adotar práticas consistentes de backup e
recuperação de dados. A realização de backups frequentes é essencial, combinando cópias completas diárias com backups 
incrementais realizados por hora. Isso minimiza a perda de dados em caso de falhas inesperadas. Além disso, a replicação 
do banco de dados, seja de forma síncrona ou assíncrona, permite que um servidor secundário assuma rapidamente em caso de falha 
do servidor principal, garantindo continuidade do serviço. Estratégias como os snapshots do RDS da AWS são um bom exemplo
de backup periódico consistente.
<br/>

Outra medida importante é armazenar os backups em locais externos ao ambiente principal, preferencialmente com criptografia, 
como em serviços de armazenamento em nuvem ou em servidores distantes do principal, 
o que aumenta a segurança e protege contra perdas físicas ou ataques locais. <br/>

Também é fundamental realizar testes periódicos de restauração, a fim de validar que os arquivos de backup realmente podem 
ser recuperados de forma eficaz. Por fim, a automação dos processos de backup e monitoramento por meio de ferramentas modernas,
aliada a alertas de falha, permite resposta rápida a qualquer problema, reduzindo riscos operacionais. <br/>
Essas práticas, em conjunto, asseguram maior resiliência e disponibilidade do sistema mesmo diante de falhas ou desastres.

## 3.6
Para implementar backup incremental e recuperação ponto a ponto (PITR), utilizaria um serviço como o AWS RDS com backups automáticos ativados, 
que já armazenam os arquivos de log (WAL) necessários para recuperação até um ponto específico. 
Esses backups permitem restaurar o banco até um segundo exato dentro do período de retenção.<br/>

Em paralelo, manteria snapshots periódicos para recuperação rápida e integraria scripts automatizados de restauração 
usando a CLI da AWS. Em casos extremos, também manteria dumps `.sql` como redundância adicional.<br/>

Durante o processo de recuperação, notificaria os clientes sobre possível instabilidade e prepararia a equipe de suporte para tratar 
inconsistências e tickets com agilidade.

## 3.7
Nunca tive experiência com e-commerce, porém já trabalhei em uma otimização de um relatório de auditoria geral do sistema.
Ao tentar gerar o relatório, o servidor sepre caia devido à falta de memória, para isso, particionei o relatório em vários
steps que deveriam ser processados sequencialmente; além disso, a geração do relatório se tornou um "job" condicioando ao 
clique do usuário e a operação ficava agendada para ocorrer de madrugada, horário de menor movimento. Cada parte do relatório
utilizava um índice no created_at para obter os dados cronologicamente mantendo a perforrmance.



