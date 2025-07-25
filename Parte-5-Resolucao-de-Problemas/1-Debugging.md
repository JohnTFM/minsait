Em aplicações complexas de java, eu vejo que o debbuging se dá em duas situações: <br/>

1. Ambiente controlado de desenvolvimento 
2. Software em produção em execução
<br/>

### Primeiro caso
Em ambientes de desenvolvimento, o meu debugging sempre envolve aproveitar o máximo de ferramentas ao meu dispor,
sejam elas testes automatizados de regressão, JMeter ou própria IDE. No caso mais comum, eu sempre estarei utilizando
o debugger do IntelliJ, o qual utiliza a API da JVM para inserir breakpoints arbitrários no código quando ele executa
em modo de desenvolvimento. Em troca de perfomance local, a IDE é capaz de me dar todas as informações em tempo de
execução e parar a thread atual para que eu possa inspecionar todas as variáveis presentes naquele contexto, e até mesmo
alterar os seus valores (funciona em alguns casos). Além dos breakpoints, analisadores de perfomance, como diversos pacotes
do Linux ou o Gerenciador de Tarefa do Windos servem para visualizar leaks de memórias e alguns problemas de perfomance.

## Segundo caso
Em contraponto, em produção, onde o erro aparentemente só ocorre nesse ambiente, o debug é muito mais difícil, porém
com ferramentas modernas ainda é possível! Por meio da observabilidade de Logs do servidor, ferramentas de vigia de perfomance,
gráficos gerados com os logs por meio de algum software, e alertas de ambientes Cloud são maneiras inteligentes de lidar
com o problema. Eu sempre opto por ter algum gerenciador de contâiner o qual registra os logs antes do servidor cair ou
ficar indisponível, como o Kubernetes e o Portainer. Através dos dados dessas ferramentas, eu consigo simular as mesmas
condições até reproduzir o erro localmente, assim, com a análise crítica e com a experiência que tenho, eu consigo 
decobrir onde provavelmente está o erro e lançar um hotfix. Caso não resolva, o processo é repetido ou a funcionalidade
é desativada até que o problema seja resolvido para preservar a usabilidade (dos clientes/usuários do sistema/stakeholders).