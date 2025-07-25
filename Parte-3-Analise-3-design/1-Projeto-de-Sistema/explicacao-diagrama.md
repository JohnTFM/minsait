Primeiramente, os requisitos dizem que as entidades: livros, autores e leitores devem coexistir, e que a
operação de empréstimo e devolução deve ser possível.

### Modelo e objetivo das classes
Para sustentar o modelo, as classes Usuario, Emprestimo, Livro, Categoria e Autor deve existir,
dessa maneira, todos os cadastros são possíveis, pesquisas e filtros também, além do cálculo de 
valores de múlta e valores que o empréstimo deverá indicar. Fora essas, a classe de Perfil e 
OcorrenciaSumico sevem para segurança do sistema.


### Como se relacionam
As classes se relacionam com a finalidade de sustentar um ambiente de gerência de empréstimos 
e devolução consistente. Usuário e empréstimo têm relações de posse e auditoria, para registrar
também os funcionários que criaram aquele registro; Emprestimo e OcorrenciaSumico se relacionam
para registrar sumiços de livros, seja por alguém que nunca devolveu, seja por alguém que roubou, 
seja por perda do livro nas prateleiras da biblioteca. A classe Perfil serve para indicar quem
são os *leitores* e quem são os *funcionários*, a Autor para indicar quem escreveu o livro e a
Categoria para facilitar a implementação de agregadores de informações, como listagens, criação
de valores padrões para algueis e o que for necessário. 

### Conclusão
Dessa forma, um sistema MVP de biblioteca poderá ser implementado seguindo a modelagem acima
sem problemas, com segurança, consistência e preparado para escalabilidade.

