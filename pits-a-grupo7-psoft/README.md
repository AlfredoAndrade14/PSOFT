# Pits A

## User Stories já implementadas
- US01 - Eu, enquanto funcionário(a), quero utilizar o sistema para criar e editar o estabelecimento
- US02 - Eu, enquanto cliente, quero utilizar o sistema para me cadastrar como cliente do estabelecimento. Mais detalhadamente, deve ser possível criar, ler, editar e remover clientes.
- US03 - Eu, enquanto funcionário(a) terceirizado(a), quero utilizar o sistema para me cadastrar como entregador(a) do estabalecimento. Mais detalhadamente, deve ser possível criar, ler, editar e remover entregadores.
- US04 - Eu, enquanto funcionário(a), quero utilizar o sistema para aprovar ou rejeitar entregadores do estabelecimento.
- US05 - Eu, enquanto funcionário(a), quero utilizar o sistema para o CRUD dos sabores de pizza vendidos pelo estabelecimento. Mais detalhadamente, deve ser possível criar, ler, editar e remover sabores.
- US06 - Eu, enquanto cliente, quero visualizar o cardápio do estabelecimento.
- US07 - Eu, enquanto cliente, quero utilizar o sistema para fazer pedidos de pizza ao estabelecimento. Mais detalhadamente, deve ser possível criar, ler, editar e remover pedidos.
- US08 - Eu, enquanto funcionário(a), quero modificar a disponibilidade dos sabores. Mais detalhadamente, deve ser possível visualizar e editar a disponibilidade dos sabores de pizza — dado que, nem sempre, todos os produtos estão disponíveis.
- US09 - Eu, enquanto cliente, quero demonstrar interesse em sabores de pizza que não estão disponíveis no momento.
- US10 - Eu, enquanto funcionário(a), quero disponibilizar diferentes meios de pagamento para os pedidos, tal que cada meio de pagamento também gere descontos distintos.
- US11 - Eu, enquanto funcionário(a), quero que o sistema garanta a corretude nas mudanças de status dos pedidos.
- US12 - Eu, enquanto cliente, quero ser notificado(a) quando meus pedidos estiverem em rota e, por medidas de segurança, quero ser informado(a) com o nome do(a) entregador(a) responsável pela entrega e os detalhes sobre seu veículo.
- US13 - Eu, enquanto cliente, quero ser responsável por confirmar a entrega dos meus pedidos.
- US14 - Eu, enquanto funcionário(a), quero que o estabelecimento seja notificado(a) sempre que o status de um pedido for modificado para “Pedido entregue”.
- US15 - Eu, enquanto cliente, quero ter a possibilidade de cancelar um pedido que fiz no estabelecimento.
- US16 - Eu, enquanto cliente, quero poder verificar os pedidos que já realizei no estabelecimento.
- US17 - Eu, enquanto funcionário(a) terceirizado(a), desejo definir se estou disponível (ou não) para realizar as entregas do estabelecimento.
- US18 - Eu, enquanto funcionário(a), gostaria que o sistema atribuísse automaticamente as entregas dos pedidos com status “Pedido Pronto” a um(a) entregador(a) que esteja disponível para realizar entregas.

## Estrutura básica
- Um projeto: Pits A;
- Controllers que implementam os endpoints da API Rest (VersionController, EstabelecimentoController, ClienteController, EntregadorController, PedidoController, SaborPizzaController);
- Rrepositórios utilizados: EstabelecimentoRepository, ClienteRepository, EntregadorRepository, PedidoRepository, PizzaPedidoRepository, SaborPizzaRepository, TamanhoPizzaRepository, TipoSaborRepository, TipoVeiculoRepository que são responsáveis por manipular as entidades Estabelecimento,...  em um banco de dados em memória;
- O modelo é composto pelas classes Estabelecimento.java, Cliente.java, Entregador.java, Pedido.java, PizzaPedido.java, SaborPizza.java, TamanhoPizza.java, TipoSabor.java, TipoVeiculo.java  que podem ser encontradas no pacote model;
- O pacote exceptions guarda as classes de exceções que podem ser levantadas
  dentro do sistema;
- Não há implementação de frontend, mas o projeto fornece uma interface de acesso à API via swagger.

## Endereços úteis

- [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- [http://localhost:8081/h2](http://localhost:8081/h2)
