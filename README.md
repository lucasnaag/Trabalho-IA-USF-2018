# Trabalho-IA-USF-2018
-----

|__Aluno__|__RA__|
|:----------------------------|:------------:|
|Higor Matheus da Silva Santos|002201400826|
|Lucas Nascimento Aguiar|002201400157|
|Yesica Beatriz Aveldaño|002201401558|

-----

## Inicialização:

Ao instanciar tanto a classe _Agente_ (package _agente.buscaCega_) ou a classe _AgenteHeuristica_ (package _agente.buscaHeurística_) a partir de seus respectivos construtores, é gerado a tela base e os lixos a serem coletados. A quantidade e a posição de cada lixo são gerados randomicamente.

### Parametrização:

O código pode ser parametrizado através das variáveis:
* *TIME:* Tempo que o algoritmo utiliza para "_dormir_"
* *MAX_X:* Tamanho máximo da tela em coordenada _x_
* *MIN_X:* Tamanho minimo da tela em coordenada _x_
* *MAX_Y:* Tamanho máximo da tela em coordenada _y_
* *MIN_Y:* Tamanho minimo da tela em coordenada _y_

## Algoritmos comentados:
### Busca cega

O algoritmo de busca cega, implementado pela classe _Agente_, possui a inteligencia de percorrer uma árvore de estados de forma pré-ordenada.

Quando o agente é inicializado é construído uma árvore com 4 níveis que contém os estados suficientes para investigar todos os estados da tela, utilizando uma busca em extensão.

### Busca Heurística

O algoritmo de busca com heurística, implementado pela classe AgenteHeuristica, realiza a coleta dos "lixos" utilizando a técnica de _greedy search_ (busca gulosa) no qual sua função global é a quantidade de lixos a ser coletado e a sua função local é o seu campo de visão. A variável CAMPO_VISAO recebe o valor que indica quantos passos o agente consegue "enxergar" tanto no eixo X como no eixo Y. Caso o agente não encontre nenhum lixo próximo o mesmo realiza uma busca cega até que encontre algum "lixo" no seu campo de visão
