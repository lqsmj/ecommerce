# ecommerce
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
