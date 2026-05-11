# Aula 1 — Setup do Ambiente Spring Boot

## Pré-requisitos

- Java 21 instalado (`java -version` deve mostrar versão 21)
- Maven instalado (`mvn -version`)

### Configurando o JAVA_HOME para Java 21 (macOS)

Se a máquina tiver múltiplas versões do Java instaladas, confirme qual está ativa:

```bash
java -version
```

Para usar o Java 21 especificamente, defina o `JAVA_HOME` antes de rodar a aplicação:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
export PATH="$JAVA_HOME/bin:$PATH"
```

Verifique:

```bash
java -version   # deve mostrar 21
```

Para tornar permanente, adicione as duas linhas de `export` ao arquivo `~/.zshrc` (ou `~/.bashrc`) e recarregue:

```bash
source ~/.zshrc
```

---

## 1. Criando o Projeto com Spring Initializr

Acesse [https://start.spring.io](https://start.spring.io) e preencha:

| Campo | Valor |
|---|---|
| Project | **Maven** |
| Language | Java |
| Spring Boot | 4.0.6 |
| Group | `br.edu.ifpr` |
| Artifact | `casalapp` |
| Name | `casalapp` |
| Package name | `br.edu.ifpr.casalapp` |
| Packaging | Jar |
| Java | **21** |

Em **Dependencies**, adicione:

- **Spring Web** — servidor Tomcat embutido e suporte a REST

Clique em **Generate**, extraia o `.zip` e abra a pasta na sua IDE.

---

## 2. Estrutura do Projeto

Após extrair, a estrutura principal será:

```
casalapp/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── br/edu/ifpr/casalapp/
                └── CasalappApplication.java
```

---

## 3. A Classe Principal

O arquivo `CasalappApplication.java` é o ponto de entrada da aplicação:

```java
package br.edu.ifpr.casalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CasalappApplication {
    public static void main(String[] args) {
        SpringApplication.run(CasalappApplication.class, args);
    }
}
```

### A anotação `@SpringBootApplication`

É a anotação mais importante da classe principal. Ela é um atalho que combina três outras anotações:

| Anotação interna | O que faz |
|---|---|
| `@EnableAutoConfiguration` | Configura o Spring automaticamente com base nas dependências presentes. Por exemplo: como `Spring Web` está no `pom.xml`, o Tomcat sobe automaticamente. |
| `@ComponentScan` | Varre o pacote atual e todos os subpacotes em busca de classes anotadas com `@Component`, `@Service`, `@Repository`, `@RestController`, etc. |
| `@SpringBootConfiguration` | Marca a classe como fonte de configurações do Spring. |

---

## 4. Rodando na Linha de Comando

### 4.1 Compilar e rodar com Maven

Dentro da pasta `casalapp/`, execute:

```bash
./mvnw spring-boot:run
```

> No Windows, use `mvnw.cmd spring-boot:run`

Quando aparecer a mensagem abaixo, a aplicação está no ar:

```
Started CasalappApplication in 2.3 seconds (process running for 2.6)
```

O servidor sobe por padrão em `http://localhost:8080`.

Para parar, pressione `Ctrl + C`.

### 4.2 Gerar o JAR e rodar diretamente

```bash
# Gerar o JAR (ignora testes por enquanto)
./mvnw package -DskipTests

# Rodar o JAR gerado
java -jar target/casalapp-0.0.1-SNAPSHOT.jar
```

---

## 5. Primeiro Controller

### O que é um Controller?

Um **Controller** é a camada da aplicação responsável por receber requisições HTTP e devolver respostas.

Quando um cliente (navegador, app mobile, `curl`) faz uma requisição para `http://localhost:8080/hello`, o Spring procura qual método do Controller está mapeado para aquela URL e aquele verbo HTTP (GET, POST, etc.), executa o método e devolve o resultado como resposta.

```
Cliente  ──→  requisição HTTP GET /hello  ──→  Controller  ──→  resposta "Hello!"
```

O Controller é o ponto de entrada do seu código. Ele não conhece banco de dados nem regras de negócio, seu papel é apenas traduzir requisições HTTP em chamadas Java e devolver a resposta.

### O que é uma Rota?

Uma **rota** (ou *endpoint*) é a combinação de um **verbo HTTP** com um **caminho (path)**. Por exemplo:

| Verbo | Caminho | Significado convencional |
|---|---|---|
| `GET` | `/hello` | Busca o recurso em `/hello` |
| `GET` | `/usuarios` | Lista todos os usuários |
| `POST` | `/usuarios` | Cria um novo usuário |
| `PUT` | `/usuarios/1` | Atualiza o usuário de id 1 |
| `DELETE` | `/usuarios/1` | Remove o usuário de id 1 |

No Spring, cada método do Controller é mapeado para uma rota específica usando anotações como `@GetMapping`, `@PostMapping`, etc.

### Anotações do Controller

**`@RestController`** — nível de classe

Marca a classe como um Controller que responde requisições HTTP. É um atalho para duas anotações combinadas:

- `@Controller` — registra a classe como um componente Spring que trata requisições web
- `@ResponseBody` — indica que o valor retornado pelo método vai direto no corpo da resposta HTTP (serializado como JSON ou texto), e não como nome de uma página HTML

**Anotações de mapeamento de rota** — nível de método

Definem para qual URL e qual verbo HTTP cada método responde:

| Anotação | Verbo HTTP | Uso convencional |
|---|---|---|
| `@GetMapping("/rota")` | GET | Leitura de recursos |
| `@PostMapping("/rota")` | POST | Criação de recursos |
| `@PutMapping("/rota")` | PUT | Atualização completa |
| `@PatchMapping("/rota")` | PATCH | Atualização parcial |
| `@DeleteMapping("/rota")` | DELETE | Remoção de recursos |
| `@RequestMapping(value="/rota", method=...)` | Qualquer | Forma genérica, mais verbosa |

`@RequestMapping` pode ser usado também na **classe** para definir um prefixo de path que se aplica a todos os métodos. Por exemplo, `@RequestMapping("/api/v1")` na classe faz com que `/usuarios` vire `/api/v1/usuarios`.

### Criando o arquivo

Crie o arquivo `HelloController.java` dentro de `br/edu/ifpr/casalapp/controller/`:

```java
package br.edu.ifpr.casalapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }
}
```

Com a aplicação rodando, acesse no navegador ou via `curl`:

```bash
curl http://localhost:8080/hello
```

Resposta esperada:

```
Hello, Spring Boot!
```

---

## Resumo dos Comandos

```bash
# Rodar em modo desenvolvimento
./mvnw spring-boot:run

# Compilar sem rodar testes
./mvnw package -DskipTests

# Rodar o JAR gerado
java -jar target/casalapp-0.0.1-SNAPSHOT.jar
```
