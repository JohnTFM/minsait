## Comparação Maven e Gradle
Ambas gerenciam dependências, porém o gralde através do build incremental é mais rápido e funciona muito bem
com redeploys, além de ter uma sintaxe de arquivo em groovy/kotlin, duas linguagens simples e diretas, melhorando
a legibilidade de scripts e do seu arquivo de build. <br/>
Em contrapartida, o Maven possui um ecossistema mais maduro e com mais diagnósticos de erros
e bugs solucionados, por mais que seja mais lento e menos legível. Assim, para projetos
legados, com versões antigas de JDK, o Maven é o mais recomendado, porém para projetos onde a equipe já consegue
usar a mais nova versão de Java, o Gradle já consegue justificar a sua presença.