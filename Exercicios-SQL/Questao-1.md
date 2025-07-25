## 1.1 
Eu identificaria através de uma análise de negócio. Como é umda base relacional de uma rede social,
a tabela de likes é com certeza a que tem mais inserções, dessa forma, para fins de performance, eu poderia
criar um Trigger no banco de dados para incrementar uma tabela de likes por post (ou banco de dados Redis se feito de forma cacheada) que estão em alta; 
posts em alta poderiam ser posts que acabaram de ser postados, patrocinados ou que estão viralizando; dessa forma,
a consulta de quantos likes a publicação tem seria visitar o cache ou a tabela, e em caso de nulo, contar quantos registros
de likes existem para aquele post. Além disso, sempre utilizar DTOs e imagens pngs previamente diminuídas para listar os
usuários, além de deixar cacheado celebridades e entes governamentais que recebem muitas pesquisas, nesse caso, cacheado
no lado do cliente, para caso ele comece a pesquisar, a pessoa já apareça por enquanto que lazy loadinds são feitos para o
resto. Outra otimização pode ser também a de índices; indexar o username do usuário e o created_at dos posts seriam ótimas
melhorias, pois a pesquisa de usuário é sempre por username (via cliente da rede social) e os posts geralmente são sempre
ordenados pela sua data de criação (posts novos sempre são o que fazem sucesso e viralizam, salvo raras exceções). <br/>


## 1.2
```sql
SELECT u.username, COUNT(l.id) AS total_likes FROM users u
         INNER JOIN posts p ON p.user_id = u.id
         LEFT JOIN likes l ON l.post_id = p.id
        WHERE p.created_at >= CURRENT_DATE - INTERVAL '30 days'
        GROUP BY u.username
        ORDER BY total_posts DESC
    LIMIT 5;
```

## 1.3
Para manter a integridade e performance do banco de dados, eu iria sempre vigiar as tabelas mais utilizadas,
criar os índices necessários e retirar os que não mais são demandandos, sempre checar se os mecanismos de
cache estão sendo utilizados nas áreas corretas e sempre verificar para qual direção a minha aplicação está
escalando, se existem mais pessoas entrando na plataforma, ou se cada pessoa que está nela está postando mais.<br/>
No momento que a necessidade de particionamento chegar, eu levaria em consideração campos como "last_viewed" ou 
"last_modified", campos que indicam se registros antigos ainda estão sendo demandandos, pois caso as tabelas forem
particionadas mas mesmo assim a minha aplicação ainda demandar os registros antigos na mesma intensidade que os novos, 
então o particionamento não terá um efeito tão significativo. Além disso, para particionar eu analisaria a infraestrutura
disponível e o tamanho do banco de dados.

## 1.4
Tive que otimizar uma consulta de GRVs (Guia de Recolhimento de Veículos) certa vez em um sistema legado de mais de 10 anos,
GRV é um documento que resume todas as informações sobre o seu veículo  e todos os trâmites relacionados à apreensão dele pelo estado.
Haviam diversas telas  no sistema que faziam a busca dele, porém devido as más práticas de código relacionadas ao ORM implementado e à tabela
gigante (mais de 400GB só em 1 tabela), muitas das telas estavam lentas! Como solução, analisei o negócio, implementei lazy loadings e utilizei DTOs
em todos os locais possíveis; além disso, **indexei** os campos: "numeração_grv", "data_apreensao" e "placa_veiculo", pois o caso mais comum
era a pesquisa de carros que haviam sido apreendidos nas últimas operações policiais. As GRVs eram tramitadas basicamente 
durante a primeira semana de apreensão, pois a cada dia que passava, o cidadão tinha que pagar um valor maior para retirar o veículo.
Dessa forma, telas de loadings de 17 segundos desceram para 3 e 2 segundos; 

