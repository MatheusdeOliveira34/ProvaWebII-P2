# Ca$alApp

## Índice de Casos de Uso

| Identificador | Nome |
| :---- | :---- |
| [UC 1](#uc-1--cadastrar-usuário) | Cadastrar usuário |
| [UC 2](#uc-2--login) | Login |
| [UC 3](#uc-3--recuperar-senha) | Recuperar senha |
| [UC 4](#uc-4--gerenciar-perfil) | Gerenciar perfil |
| [UC 5](#uc-5--gerenciar-casa) | Gerenciar "Casa" |
| [UC 6](#uc-6--gerenciar-categorias-de-despesas) | Gerenciar categorias de despesas |
| [UC 7](#uc-7--gerenciar-orçamento) | Gerenciar orçamento |
| [UC 8](#uc-8--gerir-lançamentos-receitasdespesas) | Gerir lançamentos (receitas/despesas) |
| [UC 9](#uc-9--relatório) | Relatório |
| [UC 10](#uc-10--gerir-rateio-de-contas) | Gerir Rateio de contas |
| [UC 11](#uc-11--associação-de-casa) | Associação de Casa |

O aplicativo é uma ferramenta mobile para casais controlarem suas finanças compartilhadas. Permite que cada membro registre despesas e receitas, informe quem pagou cada item e personalize categorias. O app calcula o total gasto por pessoa e gera relatórios e rateios, podendo dividir as despesas igualmente ou proporcional à renda de cada um. Ideal para casais que desejam transparência, organização e equilíbrio financeiro no dia a dia.

## Proposta de arquitetura

Firebase  
React-Native

## Casos de Uso

| Identificador | Nome |
| :---- | :---- |
| UC 1 | Cadastrar usuário |
| UC 2 | Login |
| UC 3| Recuperar senha |
| UC 4 | Gerenciar perfil |
| UC 5 | Gerenciar "Casa" |
| UC 6 | Gerenciar categorias de despesas |
| UC 7 | Gerenciar orçamento |
| UC 8 | Gerir lançamentos (receitas/despesas) |
| UC 9 | Relatório |
| UC 10 | Gerir Rateio de contas |
| UC 11 | Associação de Casa |

### Diagrama de caso de uso

### Detalhamento dos casos de uso

#### **UC 1 – Cadastrar usuário** 

**Nome:** Cadastrar usuário

**Objetivo:** Permitir que novos usuários criem uma conta e iniciem o uso vinculando-se a uma Casa existente ou criando sua própria Casa.

**Requisitos:**

* O usuário deve informar nome, e-mail e senha.  
* O sistema deve validar o formato do e-mail.  
* A senha deve ter no mínimo 6 caracteres.  
* Após autenticação inicial, o sistema deve obrigatoriamente solicitar associação/criação de Casa (UC 11 incluído).

**Atores:** Usuário

**Prioridade:** Alta

**Pré-condições:** O usuário não possui cadastro prévio.

**Frequência de uso:** Baixa (normalmente uma vez por usuário).

**Criticalidade:** Alta

**Condição de entrada:** Acesso à tela de cadastro através da tela inicial.

**Fluxo principal:**

* Usuário acessa a tela de cadastro.  
* Informa nome, e-mail e senha.  
* Sistema valida e cria a conta autenticando a sessão.  
* Sistema inicia fluxo obrigatório de associação/criação de Casa (UC 11 - include).  
* Usuário insere código de convite OU opta por criar uma nova Casa.  
* Sistema confirma vínculo e redireciona à área principal.

**Fluxo alternativo:**

* 3a. E-mail já em uso: sistema exibe erro e orienta tentar outro ou recuperar senha.  
* 3b. Dados inválidos (formato de e-mail ou senha fraca): exibe erro específico.  
* 4a. Usuário abandona associação: permanece autenticado em estado restrito (somente fluxo UC 11 disponível).  
* 5a. Código de convite inválido: sistema informa erro e permite nova tentativa ou criação de Casa.

**Pós-condições:** Usuário autenticado e vinculado a exatamente uma Casa ativa (ou em estado restrito aguardando conclusão de UC 11 se abandonado).

**Regras de negócio:**

* E-mail único.  
* Senha mínimo 6 caracteres.  
* Nome obrigatório.  
* Associação a uma Casa é obrigatória antes do uso pleno (UC 11 incluído).  
* Não prossegue a funcionalidades financeiras sem vínculo de Casa.

#### **UC 2 – Login** 

**Nome:** Login

**Objetivo:** Permitir que usuários autenticados acessem o aplicativo. Caso o usuário não esteja vinculado a nenhuma Casa, o sistema redireciona automaticamente para o fluxo de associação (UC 11).

**Requisitos:**

* Informar e-mail e senha válidos.  
* Sistema valida credenciais.  
* Sistema verifica se há vínculo de Casa; caso não exista, estende para UC 11 (Associação de Casa).

**Atores:** Usuário

**Prioridade:** Alta

**Pré-condições:** Conta previamente cadastrada (UC 1).

**Frequência de uso:** Alta (toda vez que o usuário acessa o aplicativo).

**Criticalidade:** Alta

**Condição de entrada:** Acesso à tela de login.

---

### Fluxo Principal

1. Usuário acessa a tela de login.  
2. Usuário informa e-mail e senha.  
3. Sistema valida as credenciais.  
4. Sistema verifica se o usuário está vinculado a uma Casa:  
   - **4.1. Se ESTÁ vinculado:** Sistema redireciona para a tela principal do aplicativo.  
   - **4.2. Se NÃO ESTÁ vinculado:** Sistema redireciona automaticamente para a tela de Associação de Casa (**<<extend>> UC 11**).  
5. Após conclusão da associação (UC 11), sistema redireciona para a área principal.

---

### Fluxo Alternativo

- **3a. Credenciais inválidas:**  
  - Sistema exibe mensagem de erro:  
    > "E-mail ou senha incorretos. Tente novamente."  
  - Usuário pode tentar novamente ou acessar "Esqueci minha senha" (UC 3).  
  - Após 3 tentativas falhas, sistema pode bloquear temporariamente (5 minutos).

- **4.2a. Usuário abandona o fluxo de associação:**  
  - Sistema mantém usuário autenticado, mas em **estado restrito**.  
  - Apenas o fluxo de Associação de Casa (UC 11) fica disponível.  
  - Usuário não pode acessar funcionalidades financeiras até concluir a associação.

---

### Pós-condições

- Usuário autenticado e sessão iniciada.  
- Se vinculado a uma Casa: acesso completo às funcionalidades do aplicativo.  
- Se não vinculado: acesso restrito até concluir UC 11.

---

### Regras de Negócio

- **Limite de tentativas:** Máximo de 3 tentativas de login falhas consecutivas resulta em bloqueio temporário de 5 minutos.  
- **Sessão persistente:** Usuário permanece logado até fazer logout explícito ou sessão expirar (configurable).  
- **Obrigatoriedade de Casa:** Usuário sem vínculo de Casa não pode acessar funcionalidades principais (lançamentos, categorias, orçamentos, relatórios, rateios).  
- **Redirecionamento automático:** Se usuário não tem Casa, é redirecionado imediatamente para UC 11 após autenticação bem-sucedida.

---

### Relacionamento com Outros Casos de Uso

- **<<extend>> UC 11 (Associação de Casa):** Executado automaticamente quando usuário não está vinculado a nenhuma Casa.  
- **<<include>> UC 3 (Recuperar Senha):** Disponível na tela de login para usuários que esqueceram a senha.

#### **UC 3 – Recuperar senha** 

**Nome:** Recuperar senha  
**Objetivo:** Permitir que o usuário redefina sua senha caso tenha esquecido ou perdido o acesso.  
**Requisitos:**

* O usuário deve informar o e-mail cadastrado.  
* O sistema deve verificar se o e-mail existe.  
* O sistema deve enviar um link ou código de recuperação para o e-mail informado.

**Atores:** Usuário

**Prioridade:** Alta

**Pré-condições:** O usuário deve ter uma conta com e-mail válido cadastrado.

**Frequência de uso:** Média (acionado quando há esquecimento de senha)

**Criticalidade:** Média

**Condição de entrada:** Acesso à opção "Esqueci minha senha" na tela de login

**Fluxo principal:**

* O usuário acessa a opção "Esqueci minha senha".  
* O usuário informa o e-mail cadastrado.  
* O sistema verifica se o e-mail existe.  
* O sistema envia um link ou código de recuperação por e-mail.  
* O usuário acessa o link ou insere o código e redefine a senha.  
* O sistema valida e atualiza a nova senha.

**Fluxo alternativo:**

* 3a. E-mail não encontrado:  
  * O sistema informa que não há conta associada ao e-mail informado.  
  * O usuário é orientado a tentar novamente ou criar uma nova conta.  
* 5a. Link ou código expirado:  
  * O sistema solicita uma nova requisição de recuperação.

**Pós-condições:** Senha atualizada e usuário pode acessar normalmente.

**Regra de negócio:**

* O link ou código de recuperação deve expirar após determinado tempo (ex: 1 hora).  
* A nova senha deve seguir os mesmos critérios de complexidade definidos no cadastro.  
* O processo de recuperação não deve informar se o e-mail é válido para fins de segurança (mensagens genéricas como "Se o e-mail existir, enviaremos instruções").

#### **UC 4 – Gerenciar perfil** 

**Nome:** Gerenciar perfil

**Objetivo:** Permitir que o usuário visualize e atualize suas informações pessoais no aplicativo.

**Requisitos:**

* O usuário deve estar autenticado.  
* O sistema deve permitir a edição de nome e senha.  
* O sistema deve validar os dados informados.

**Atores:** Usuário

**Prioridade:** Média

**Pré-condições:** Usuário autenticado no sistema

**Frequência de uso:** Baixa (usado eventualmente para ajustes)

**Criticalidade:** Média

**Condição de entrada:** Acesso à tela de perfil via menu de configurações ou ícone de usuário

**Fluxo principal:**

* O usuário acessa a tela de perfil.  
* O sistema exibe os dados atuais do usuário.  
* O usuário edita as informações desejadas (nome, e-mail, senha).  
* O sistema valida os dados.  
* O sistema atualiza as informações no banco de dados.  
* O sistema confirma a atualização com mensagem de sucesso.

**Fluxo alternativo:**

* 4a. Dados inválidos (ex: e-mail malformado ou senha fraca):  
  * O sistema exibe mensagem de erro e bloqueia a atualização.  
* 4b. E-mail já cadastrado para outro usuário:  
  * O sistema informa que o e-mail está em uso.

**Pós-condições:** Informações do usuário atualizadas no sistema

**Regra de negócio:**

* O nome não pode ser vazio.  
* O e-mail deve ser único e válido.  
* A senha deve seguir os critérios de complexidade (mínimo de caracteres, por exemplo).  
* Caso o e-mail seja alterado, uma verificação por e-mail pode ser exigida (dependendo do nível de segurança desejado).

#### **UC 5 – Gerenciar "Casa"** 

**Nome:** Gerenciar "Casa"

**Objetivo:** Permitir que o usuário crie, edite e participe de uma "Casa" compartilhada, onde os lançamentos de despesas e receitas são vinculados.

**Requisitos:**

* O sistema deve permitir criar uma nova Casa com um nome.  
* O sistema deve gerar um código de convite para convidar o parceiro(a).  
* O sistema deve permitir que o usuário aceite convite para uma Casa existente.  
* O sistema deve limitar um usuário a participar de uma Casa por vez.

**Atores:** Usuário

**Prioridade:** Alta

**Pré-condições:** Usuário autenticado

**Frequência de uso:** Baixa (geralmente usada uma única vez ou em caso de reestruturação)

**Criticalidade:** Alta (todas as despesas e funcionalidades estão ligadas à Casa)

**Condição de entrada:** Acesso à área de "Casa" pelo menu do aplicativo

**Fluxo principal:**

* **Criar uma Casa:**  
  * O usuário acessa a opção "Criar Casa".  
  * O usuário define o nome da Casa.  
  * O sistema cria a Casa e associa o usuário como administrador.  
  * O sistema gera um código de convite para outro usuário.  
* **Entrar em uma Casa existente:**  
  * O usuário acessa a opção "Entrar em uma Casa".  
  * O usuário informa o código de convite.  
  * O sistema valida o código e adiciona o usuário à Casa.

**Fluxo alternativo:**

* 2a. Código de convite inválido ou expirado:  
  * O sistema informa erro e solicita novo código.  
* 3a. Usuário já participa de outra Casa:  
  * O sistema bloqueia a entrada até que o usuário saia da Casa atual.

**Pós-condições:**

* Casa criada ou vínculo com Casa existente realizado com sucesso.  
* O usuário passa a ver os dados compartilhados da Casa (categorias, lançamentos, orçamentos, etc).

**Regra de negócio:**

* Um usuário só pode estar vinculado a uma Casa por vez.  
* Apenas o criador (administrador) pode remover usuários ou excluir a Casa.  
* O código de convite tem validade limitada (ex: 24h).  
* Todos os dados financeiros são vinculados à Casa, e não individualmente.

#### **UC 6 – Gerenciar categorias de despesas** 

**Nome:** Gerenciar categorias de despesas

**Objetivo:** Permitir que os usuários visualizem, criem, editem e excluem categorias de despesas personalizadas para organização dos lançamentos.

**Requisitos:**

* O usuário deve estar vinculado a uma Casa.  
* O sistema deve permitir criar categorias com nome.  
* O sistema deve impedir a exclusão de categorias que estejam em uso.
* O sistema deve permitir criar sub-categorias a uma categoria.

**Atores:** Usuário

**Prioridade:** Média

**Pré-condições:**

* Usuário autenticado  
* Usuário vinculado a uma Casa

**Frequência de uso:** Média (durante a configuração e ao longo do uso)

**Criticalidade:** Média

**Condição de entrada:** Acesso à área de categorias pelo menu do app ou ao cadastrar um lançamento

**Fluxo principal:**

* **Criar categoria:**  
  * O usuário acessa a tela de categorias.  
  * O usuário seleciona “Nova categoria”.  
  * O usuário informa nome e, opcionalmente, uma categoria "pai". No caso de haver uma categoria pai, a nova categoria é considerada uma subcategoria.  
  * O sistema salva a categoria e a disponibiliza para uso.

* **Editar categoria:**  
  * O usuário acessa a lista de categorias.  
  * O usuário seleciona uma categoria existente.  
  * O usuário altera as informações.  
  * O sistema atualiza os dados.

* **Excluir categoria:**  
  * O usuário acessa a lista de categorias.  
  * O usuário seleciona a opção de exclusão.  
  * O sistema verifica se a categoria está em uso.  
  * Se não estiver em uso, a exclusão é realizada (quando houver despesas ou subcategorias associadas a categoria que tenta remover).

**Fluxo alternativo:**

* 4a. Categoria em uso:  
  * O sistema bloqueia a exclusão e informa o motivo.

**Pós-condições:**

* Categoria criada, atualizada ou excluída (caso possível).  
* As categorias personalizadas ficam disponíveis para os lançamentos da Casa.

**Regra de negócio:**

* Categorias são compartilhadas entre os membros da Casa.  
* Não é permitido duplicar nomes de categorias dentro da mesma Casa.  
* Categorias em uso por lançamentos ou se há subcategorias, não podem ser excluídas.

#### **UC 11 – Associar-se / Criar Casa após entrada**

**Nome:** Associar-se / Criar Casa após entrada

**Objetivo:** Vincular usuário autenticado (recém cadastrado ou logado) a uma Casa existente via código ou a uma nova Casa criada, habilitando o uso completo da plataforma.

**Requisitos:**
* Usuário autenticado sem vínculo de Casa.  
* Código de convite válido (para associação) ou nome de nova Casa.  
* Validação de unicidade/validade.

**Atores:** Usuário

**Prioridade:** Alta

**Pré-condições:** Sessão autenticada e ausência de vínculo.

**Frequência de uso:** Ocasional.

**Criticalidade:** Alta.

**Condição de entrada:** Sistema detecta ausência de Casa após cadastro ou login.

**Relações:** Include em UC 1. Extend em UC 2.

**Fluxo principal:**
* Sistema apresenta opções: "Inserir código" ou "Criar nova Casa".  
* Usuário escolhe opção.  
* (Associação) Informa código; sistema valida e vincula.  
* (Criação) Define nome; sistema cria e vincula.  
* Sistema confirma e libera funcionalidades.

**Fluxo alternativo:**
* Código inválido/expirado: solicitar nova tentativa ou trocar para criação.  
* Nome inválido/vazio: solicitar correção.  
* Falha de rede: manter estado e permitir retry.  
* Abandono: usuário permanece restrito até concluir.

**Pós-condições:** Usuário vinculado a uma única Casa ativa.

**Regras de negócio:**
* Um vínculo de Casa por usuário simultaneamente.  
* Código tem validade definida.  
* Sem Casa = modo restrito (somente associação / logout).  
* Criação gera código de convite válido por período configurado.

#### **UC 7 – Gerenciar orçamento** 
**Nome:** Gerenciar orçamento

**Objetivo:** Permitir que os usuários definam e acompanhem limites de gastos por categoria e por mês, ajudando no controle financeiro do casal.

**Requisitos:**

* O usuário deve estar vinculado a uma Casa.  
* O sistema deve permitir criar, editar e excluir orçamentos mensais por categoria.  
* O sistema deve exibir alertas quando os gastos se aproximarem ou ultrapassarem o limite.

**Atores:** Usuário

**Prioridade:** Média

**Pré-condições:**

* Usuário autenticado  
* Usuário vinculado a uma Casa

**Frequência de uso:** Média (configurado mensalmente, visualizado com frequência)

**Criticalidade:** Média

**Condição de entrada:** Acesso à seção de orçamento no app

**Fluxo principal:**

* **Criar orçamento:**  
  * O usuário acessa a seção de orçamento.  
  * O usuário escolhe uma categoria.  
  * O usuário define um valor limite para o mês e ano.  
  * O sistema salva o orçamento.  
* **Editar orçamento:**  
  * O usuário acessa o orçamento da categoria.  
  * O usuário altera o valor.  
  * O sistema atualiza o limite.  
* **Excluir orçamento:**  
  * O usuário acessa a categoria com orçamento definido.  
  * O usuário opta por remover o orçamento.  
  * O sistema exclui o valor limite.
* **Copiar Orçamento:**
  * O usuário acessa o orçamento de um determinado mês de um ano.
  * Se não existir um orçamento para aquele mês, o sistema pergunta se ele quer copiar o último orçamento copiado.
  * Se o usuário aceitar, o sistema copia para o mês selecionado o orçamento.


**Fluxo alternativo:**

* 3a. Tentativa de definir orçamento em categoria inexistente:  
  * O sistema bloqueia e informa erro.

**Pós-condições:**

* Orçamento mensal salvo, alterado ou removido.  
* O sistema monitora os lançamentos da Casa em relação ao orçamento definido.

**Regra de negócio:**

* Os orçamentos são mensais e por categoria.  
  * O sistema deve alertar quando os gastos de uma categoria atingirem 80% e 100% do orçamento.  
  * Os orçamentos são compartilhados entre os membros da Casa.

#### **UC 8 – Gerir lançamentos (receitas/despesas)** 

**Nome:** Gerir lançamentos (receitas/despesas)

**Objetivo:** Permitir que os usuários registrem, visualizem, editem e removam lançamentos financeiros (receitas ou despesas), vinculando-os a uma categoria e identificando quem pagou.

**Requisitos:**

* O usuário deve estar autenticado e vinculado a uma Casa.  
* Cada lançamento deve conter: valor, tipo (receita ou despesa), data, categoria, descrição (opcional) e autor do pagamento.  
* O sistema deve atualizar os relatórios e orçamentos automaticamente com base nos lançamentos.

**Atores:** Usuário

**Prioridade:** Alta

**Pré-condições:**

* Usuário autenticado  
* Usuário vinculado a uma Casa

**Frequência de uso:** Alta (atividade central do app)

**Criticalidade:** Alta

**Condição de entrada:** Acesso à área de lançamentos (tela principal, botão flutuante ou menu)

**Fluxo principal:** 

* **Criar lançamento:**  
  * O usuário acessa a opção de novo lançamento.  
  * O usuário preenche os dados obrigatórios (valor, data, tipo, categoria, autor).  
  * O sistema valida e salva o lançamento.  
  * O sistema atualiza o saldo e os relatórios da Casa.  
* **Editar lançamento:**  
  * O usuário acessa a lista de lançamentos.  
  * O usuário seleciona um item e edita os campos.  
  * O sistema salva as alterações e recalcula os dados relacionados.  
* **Excluir lançamento:**  
  * O usuário seleciona um lançamento existente.  
  * O usuário confirma a exclusão.  
  * O sistema remove o item e atualiza os dados afetados.

**Fluxo alternativo:**

* 2a. Dados obrigatórios não preenchidos ou inválidos:  
  * O sistema exibe mensagens de erro e bloqueia o envio.

**Pós-condições:**

* Lançamento financeiro registrado, atualizado ou excluído com sucesso.  
* Os relatórios e orçamentos refletem as mudanças em tempo real.

**Regra de negócio:**

* Todo lançamento deve estar vinculado a uma categoria e a um responsável pelo pagamento.  
* Lançamentos são visíveis para todos os membros da Casa.  
* É possível filtrar lançamentos por período, categoria ou autor.  
* Receitas aumentam o saldo total da Casa; despesas reduzem.  
* O campo “quem pagou” será usado no cálculo do rateio.

### UC 9 – Relatório (DRE – Ano)

**Nome:** Relatório DRE – Ano (Demonstrativo de Resultados por Exercício)

**Objetivo:**  
Apresentar, para um ano selecionado, a evolução mensal de **receitas** e **despesas** da Casa, detalhando por **membro**, **categoria** e **subcategoria**, permitindo que o usuário visualize e explore os dados por meio de cliques (drill-down).

---

### Requisitos

- O usuário deve estar autenticado e vinculado a uma Casa.  
- O relatório deve apresentar os **meses do ano selecionado** em colunas (Jan–Dez).  
- O sistema deve mostrar:  
  - O **total de receita** de cada mês.  
  - O **total de receita de cada membro** da Casa.  
  - O **total de despesa** de cada mês.  
  - O **total de despesa de cada membro** da Casa.  
- O relatório deve exibir as **despesas por categoria e subcategoria**, mês a mês.  
- O usuário pode clicar em uma **categoria ou subcategoria** para visualizar os **lançamentos detalhados** do mês selecionado.

---

### Atores
Usuário  

**Prioridade:** Alta  
**Frequência de uso:** Alta (usado para acompanhamento mensal e anual)  
**Criticalidade:** Média  
**Condição de entrada:** Acesso à aba ou menu de “Relatórios” no app, selecionando o modo “DRE – Ano”.

---

### Fluxo Principal

1. O usuário acessa a seção de relatórios e seleciona o modo **DRE – Ano**.  
2. O sistema solicita o **ano** desejado.  
3. O sistema carrega os dados agregados de receitas e despesas mês a mês.  
4. O sistema exibe o relatório em formato tabular, contendo:  
   - Total de receitas e despesas mensais.  
   - Totais por membro.  
   - Totais por categoria e subcategoria.  
5. O usuário pode clicar em uma **categoria/subcategoria** em qualquer mês para visualizar os lançamentos detalhados.  
6. O sistema exibe os **lançamentos daquele mês e categoria**, com colunas: Data, Descrição, Valor e Quem pagou.  

---

### Fluxo Alternativo

- **4a.** Se não houver dados no período selecionado:  
  O sistema exibe a mensagem:  
  > “Nenhum lançamento encontrado para o ano selecionado.”  
  O usuário pode alterar o filtro de ano ou período.

---

### Pós-condições

- O usuário tem acesso visual e analítico aos dados financeiros da Casa.  
- O relatório reflete os valores reais dos lançamentos vinculados à Casa, conforme filtros aplicados.  

---

### Regras de Negócio

- Todos os valores são exibidos em **BRL (R$)**.  
- Os cálculos consideram apenas os lançamentos com **tipo = receita ou despesa**.  
- A soma das receitas e despesas por membro deve igualar os totais mensais.  
- O clique (drill-down) em uma categoria ou subcategoria mostra apenas os lançamentos do mês e categoria selecionados.  
- Meses sem lançamentos exibem o valor **R$ 0,00**.  

---

### Exemplo de Relatório (Ano 2025)

#### Receitas (total e por membro)

| Linha / Mês        | Jan     | Fev     | Mar     | Abr     |
|--------------------|---------|---------|---------|---------|
| **Total Receitas** | 7.000,00 | 7.000,00 | 7.000,00 | 7.000,00 |
| Ana (receitas)     | 3.000,00 | 3.000,00 | 3.000,00 | 3.000,00 |
| Bruno (receitas)   | 4.000,00 | 4.000,00 | 4.000,00 | 4.000,00 |

#### Despesas (total e por membro)

| Linha / Mês         | Jan     | Fev     | Mar     | Abr     |
|---------------------|---------|---------|---------|---------|
| **Total Despesas**  | 4.350,00 | 4.150,00 | 4.250,00 | 4.050,00 |
| Ana (despesas pagas)| 2.100,00 | 2.050,00 | 2.150,00 | 1.950,00 |
| Bruno (despesas pagas)| 2.250,00 | 2.100,00 | 2.100,00 | 2.100,00 |

#### Despesas por Categoria e Subcategoria (mês a mês)

| Categoria / Subcategoria | Jan     | Fev     | Mar     | Abr     |
|---------------------------|---------|---------|---------|---------|
| **Moradia (Total)**       | 2.500,00 | 2.500,00 | 2.500,00 | 2.500,00 |
| ├─ Aluguel                | 2.000,00 | 2.000,00 | 2.000,00 | 2.000,00 |
| └─ Energia                | 500,00  | 500,00  | 500,00  | 500,00  |
| **Mercado (Total)**       | 1.850,00 | 1.650,00 | 1.750,00 | 1.550,00 |
| ├─ Alimentos              | 1.600,00 | 1.450,00 | 1.550,00 | 1.350,00 |
| └─ Higiene                | 250,00  | 200,00  | 200,00  | 200,00  |
| **Conferência (Moradia+Mercado)** | **4.350,00** | **4.150,00** | **4.250,00** | **4.050,00** |

> **Drill-down:**  
> Ao clicar em `Mercado ▸ Alimentos` na coluna **Mar**, o sistema abre a lista de lançamentos de **março/2025**, filtrada por **categoria = Mercado** e **subcategoria = Alimentos**, com colunas:  
> **Data**, **Descrição**, **Valor**, **Quem pagou**.

---

### Fórmulas de Consistência

- `TotalReceitas[mês] = Σ (receitas no mês)`  
- `ReceitaPorMembro[mês, uid] = Σ (receitas do membro no mês)`  
- `TotalDespesas[mês] = Σ (despesas no mês)`  
- `DespesaPorMembro[mês, uid] = Σ (despesas do membro no mês)`  
- `CategoriaTotal[mês, cat] = Σ (despesas da categoria no mês)`  
- `SubcategoriaTotal[mês, cat, sub] = Σ (despesas da subcategoria no mês)`  
- **Consistência:**  
  - `TotalDespesas[mês] = Σ DespesaPorMembro[mês]`  
  - `CategoriaTotal[mês, cat] = Σ SubcategoriaTotal[mês, cat]`  
  - `Σ Categorias = TotalDespesas[mês]`

---

### Critérios de Aceite

- CA1: O relatório deve refletir fielmente as somas de receitas e despesas de cada mês.  
- CA2: O total por membro deve somar exatamente ao total geral do mês.  
- CA3: O clique em uma categoria/subcategoria deve exibir corretamente os lançamentos do mês e segmento selecionado.  
- CA4: Meses sem lançamentos devem exibir **R$ 0,00** e mensagem “Sem lançamentos no período”.  
- CA5: O layout deve ser responsivo, permitindo visualização em smartphones (scroll horizontal por mês).

---


#### **UC 10 – Gerir Rateio de contas** 

**Nome:** Gerir Rateio de contas

**Objetivo:** Calcular automaticamente quanto cada pessoa da Casa deve pagar ou receber, com base nos lançamentos realizados e na regra de rateio escolhida (média ou proporcional à receita).

**Requisitos:**

* Cada lançamento deve estar vinculado a quem pagou.  
* O sistema deve permitir selecionar o tipo de rateio:  
  * **Rateio por média**: divide igualmente o total entre os membros.  
  * **Rateio proporcional à receita**: divide o total conforme o percentual da receita de cada um.  
* Para o rateio proporcional, cada membro precisa informar sua receita mensal.

**Atores:** Usuário

**Prioridade:** Alta

**Pré-condições:**

* Usuário autenticado  
* Usuário vinculado a uma Casa com ao menos dois membros  
* Existência de lançamentos com autor definido

**Frequência de uso:** Média

**Criticalidade:** Alta

**Condição de entrada:** Acesso à opção "Rateio" no menu ou relatórios

**Fluxo principal:**

* O usuário acessa a seção de rateio.  
* O sistema solicita o tipo de rateio: por média ou proporcional à receita.  
* O sistema seleciona os lançamentos do período.  
* O sistema soma o total de despesas e quanto cada pessoa pagou.  
* O sistema calcula a média ou percentual conforme a escolha:  
  * **Por média:** total dividido igualmente.  
  * **Proporcional à receita:** cada um contribui conforme sua porcentagem da soma das receitas.  
* O sistema calcula e exibe o saldo (quanto cada um deve ou tem a receber).

**Fluxo alternativo:**

* 2a. Membro(s) não informaram receita:  
  * O sistema bloqueia a opção proporcional e solicita o preenchimento.  
* 3a. Não há lançamentos no período:  
  * O sistema informa que não é possível calcular o rateio.

**Pós-condições:**

* Usuário visualiza quem pagou mais/menos que o combinado e quanto deve ou tem a receber.

**Regra de negócio:**

* A escolha do tipo de rateio pode ser alterada a qualquer momento.  
* O cálculo só considera lançamentos com autor definido.  
* A receita informada deve ser mensal e atualizada pelos próprios membros.  
* O app apenas apresenta os valores de ajuste — nenhuma transferência é feita.

---

## UC 11 – Associação de Casa

**Nome:** Associação de Casa  
**Objetivo:** Permitir que o usuário recém-cadastrado crie uma nova Casa ou se associe a uma Casa existente, vinculando suas futuras operações financeiras ao grupo correspondente.

### Requisitos
- O usuário deve estar autenticado (UC 1 – Cadastrar Usuário).  
- O sistema deve permitir criar uma nova Casa informando um nome.  
- O sistema deve permitir associar-se a uma Casa existente informando o código da Casa.  
- O código de associação é o e-mail do dono (administrador) da Casa.  
- O sistema deve validar se o código informado corresponde a uma Casa existente.

**Atores:** Usuário  
**Prioridade:** Alta  

### Pré-condições
- O usuário deve ter concluído o cadastro (UC 1).  
- O usuário não deve estar vinculado a nenhuma Casa ainda.

**Frequência de uso:** Baixa (geralmente executado apenas uma vez após o cadastro)  
**Criticalidade:** Alta (a associação à Casa é pré-requisito para o uso das demais funcionalidades)  
**Condição de entrada:** O usuário acessa o fluxo de associação à Casa imediatamente após o cadastro ou através de uma tela dedicada.

---

### Fluxo Principal

1. O sistema apresenta ao usuário as opções:  
   a) Criar nova Casa  
   b) Associar-se a uma Casa existente  

2. Caso o usuário escolha **Criar nova Casa**:  
   2.1. O usuário informa o nome da Casa.  
   2.2. O sistema cria a Casa e associa o usuário como administrador.  
   2.3. O sistema confirma a criação e redireciona o usuário à tela principal do aplicativo.  

3. Caso o usuário escolha **Associar-se a uma Casa existente**:  
   3.1. O usuário informa o código da Casa (e-mail do dono).  
   3.2. O sistema verifica se existe Casa associada a esse código.  
   3.3. Se existir, o sistema vincula o usuário à Casa correspondente.  
   3.4. O sistema confirma a associação e redireciona o usuário à tela principal.  

---

### Fluxos Alternativos

- **3a. Código de Casa inválido ou inexistente:**  
  O sistema exibe mensagem:  
  > "Não existe Casa associada a este código. Verifique o e-mail informado."  
  O usuário pode tentar novamente ou optar por criar uma nova Casa.  

- **2a. Nome da Casa não informado:**  
  O sistema exibe mensagem:  
  > "O nome da Casa é obrigatório."  

---

### Pós-condições
- O usuário passa a estar vinculado a uma Casa.  
- O sistema associa automaticamente todos os lançamentos, categorias e orçamentos à Casa correspondente.  

---

### Regras de Negócio
- Um usuário só pode estar vinculado a uma Casa por vez.  
- O código de associação é o e-mail do dono da Casa.  
- Apenas o administrador da Casa pode removê-la ou excluir membros.  
- Caso o usuário já esteja vinculado a uma Casa, não poderá criar ou associar-se a outra sem antes sair da atual.
