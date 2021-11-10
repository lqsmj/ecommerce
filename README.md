PayGO Dev Java

Este projeto foi desenvolvido para o teste de Backend Java. 
O projeto possui os seguintes topicos Kafka:


topic.name=produtos
topic.name.item-carrinho=item-carrinho
topic.name.carrinho=carrinho
topic.deadletter=deadletter

O desenvolvimento foi feito no Kafka instalado de forma local com as seguintes configurações:
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.group-id=appProduto

No projeto são utilizado os seguintes consumers:

1- ProdutoConsumer (para cadastro do produto), ex:
{
"nome" : "Produto 1",
"preco" : 1.99
}

2- ItemCarrinhoConsumer(para cadastro das informações do carrinho), ex:

{
"idProduto" : 1,
"email" : "lqsmj@hotmail.com",
"quantidade" : 2
}

3- CarrinhoConsumer(para solicitar na tela de checkout uma listagem dos produtos a partir do email do usuario),ex:
{
"email" : "lqsmj@hotmail.com"
}


Foram criados 2 producers:

1- DeadLetterKafkaConfig/DeadLetterService (Para monitoramento dos dados com problema.), ex:

"ProdutoDto(id=null, nome=null, preco=10.0)"



2- CarrinhoKafkaConfig/CarrinhoService (Para leitura dos dados obtidos através da solicitação do "CarrinhoConsumer"), ex:

{
"numeroCarrinho":2,
"email":"lqsmj@hotmail.com",
"listaItemCarrinhoResponse":[
{
"nomeProduto":"produto1",
"quantidade":2,
"preco":30.0
},
{
"nomeProduto":"produto2",
"quantidade":1,
"preco":20.0
},
{
"nomeProduto":"produto3",
"quantidade":3,
"preco":10.0
}
],
"totalCompra":110.0
}

O projeto utiliza Maven, SpringBoot 2.5.6 e Java 11.




Sugestão de melhorias:

Execução dos testes unitarios com Junit.
Um tratamento de exeções mais robusto
Validação de cada campo das requisições de forma individual.
Criação de ferramenta para tratamento dos itens enviados ao DeadLetter.
Um melhor aproveitamento do codigo em relação a implementação das configurações, consumers e producers do Kafka.
Modelagem do Banco de Dados, criação das seguintes tabelas:
+Produto
+ItemCarrinho
+Carrinho
+Usuario




Comentarios:
A modelagem do banco de dados atual, apesar de não atender as boas praticas, vizava cobrir a necessidade de criação de um novo carrinho para cada produto inserido de um usuario novo.
Essa verificação poderia ter sido feita exclusivamente pelo campo email, porém não iria existir a validação solicitada, apenas a inserção e caso não tivesse um produto ainda seria adicionado sem verificação.
(o email serviria como numeroCarrinho ).
Devido ao curto tempo para desenvolvimento e para atender os demais requisitos não houve tempo para atualização e criação das tabelas
de acordo com a sugestão de melhoria citada acima.


Correção imediata:
Em alguns momentos(ainda não identificados) o consumer "CarrinhoConsumer" entra em loop e fica repetindo a mesma solicitação,
a alternativa para finalizar o teste foi usando a opção de excluir o topico no Conduktor(ferramenta de monitoramento dos topicos Kafka) e fazer uma nova solicitação.


