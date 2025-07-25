## Pasos para identificar e resovler gargalos
Para identificar gargalos de perfomance, eu utilizo uma abordagem Top-Bottom, que envolve identificar o caso de uso 
o qual está sendo afetado pelo usuário, em seguida vou para o código e analiso através de mocks e dados simulados se
o problema é na interface ou no servidor. Em caso de problema na interface, eu realizo uma 
análise crítica de como os algoritmos estão implementados, a complexidado Big O, número de chamadas ao servidor
, tempo de execução de funções e itens renderizados, após isso, aplico soluções já comprovadas/ padrões de projeto para tentar resolver. <br/>
Agora em caso de problema em servidor, eu realizo uma tabém uma análise de código e complexidade, porém agora vejo
muito as chamadas ao banco, queries, algoritmos, aspectos da ORM implementada e throughput do servidor. Nesse ambiente,
a performance pode estar sendo afetada por inúmeros fatores, e se por via de código nada parecer ser efetivo, refatorações
arquiteturais podem se tornar necessárias, como mudança de paradigma client server para um event-based, ou criação de 
banco de dados não relacionais para replicar dados, entre outras. 

## Resumo
A minha abordagem começa Top-down, é feito uma investigação, e a cadeia de soluções voltam com o Bottom-Up,
através da visualização de soluções à nível de código, para soluções à nível arquitetural. 
 