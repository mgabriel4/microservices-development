# Play Your List - API de gerenciamento de playlists

## Sobre a atividade

Neste projeto, desenvolvi uma API REST para a **Play Your List**, uma aplicaÃ§Ã£o voltada para a criaÃ§Ã£o e execuÃ§Ã£o de playlists em empresas que utilizam o conceito HPWM (*High Productivity With Music*).

A proposta da atividade era representar uma soluÃ§Ã£o formada por quatro microserviÃ§os:

- **MÃºsicas:** responsÃ¡vel pelo cadastro, consulta, atualizaÃ§Ã£o e exclusÃ£o das mÃºsicas;
- **Playlists:** responsÃ¡vel pela criaÃ§Ã£o das playlists e pela associaÃ§Ã£o das mÃºsicas;
- **ReproduÃ§Ãµes:** responsÃ¡vel pelo registro das execuÃ§Ãµes de cada playlist;
- **API:** responsÃ¡vel por orquestrar a comunicaÃ§Ã£o entre os serviÃ§os.

Embora a arquitetura proposta represente quatro microserviÃ§os, o enunciado orientava que todos os endpoints fossem implementados dentro do mesmo projeto. Por isso, organizei as responsabilidades em pacotes separados, mas mantive uma Ãºnica aplicaÃ§Ã£o Spring Boot.

## Objetivo

Meu objetivo foi construir uma API que permitisse:

- realizar o CRUD completo das mÃºsicas;
- realizar o CRUD completo das playlists;
- adicionar e remover mÃºsicas de uma playlist;
- listar os IDs das mÃºsicas presentes em uma playlist;
- registrar a execuÃ§Ã£o de uma playlist;
- consultar o histÃ³rico e o total de reproduÃ§Ãµes;
- validar a existÃªncia das mÃºsicas e playlists antes de criar uma associaÃ§Ã£o;
- utilizar o OpenFeign para fazer a comunicaÃ§Ã£o entre os endpoints e a API orquestradora.

## Tecnologias utilizadas

Para desenvolver a atividade, utilizei:

- Java 17;
- Spring Boot;
- Spring Web;
- Spring Data JPA;
- Bean Validation;
- Spring Cloud OpenFeign;
- banco de dados H2 em memÃ³ria;
- Maven;
- JUnit 5;
- Git e GitHub.

## OrganizaÃ§Ã£o do projeto

Separei o cÃ³digo por responsabilidade para facilitar a leitura e a manutenÃ§Ã£o:

```text
src/main/java/br/com/playyourlist
â”œâ”€â”€ api
â”‚   â”œâ”€â”€ clientes OpenFeign
â”‚   â”œâ”€â”€ controller da API
â”‚   â””â”€â”€ serviÃ§o de orquestraÃ§Ã£o
â”œâ”€â”€ erros
â”‚   â”œâ”€â”€ exceÃ§Ãµes personalizadas
â”‚   â””â”€â”€ tratamento global de erros
â”œâ”€â”€ musicas
â”‚   â”œâ”€â”€ entidade
â”‚   â”œâ”€â”€ repositÃ³rio
â”‚   â”œâ”€â”€ serviÃ§o
â”‚   â””â”€â”€ controller
â”œâ”€â”€ playlists
â”‚   â”œâ”€â”€ entidades
â”‚   â”œâ”€â”€ repositÃ³rios
â”‚   â”œâ”€â”€ serviÃ§o
â”‚   â””â”€â”€ controller
â””â”€â”€ reproducoes
    â”œâ”€â”€ entidade
    â”œâ”€â”€ repositÃ³rio
    â”œâ”€â”€ serviÃ§o
    â””â”€â”€ controllers
```

Com essa separaÃ§Ã£o, os controllers recebem as requisiÃ§Ãµes HTTP, os serviÃ§os concentram as regras de negÃ³cio e os repositÃ³rios realizam o acesso ao banco de dados.

## Banco de dados

Utilizei o H2 por ser um banco leve e adequado para testes e atividades acadÃªmicas. O banco Ã© criado em memÃ³ria sempre que a aplicaÃ§Ã£o Ã© iniciada.

O arquivo `data.sql` Ã© responsÃ¡vel pela criaÃ§Ã£o e pelo preenchimento inicial das seguintes tabelas:

| Tabela | Finalidade |
| --- | --- |
| `musicas` | Armazena as mÃºsicas disponÃ­veis |
| `playlists` | Armazena o nome e a descriÃ§Ã£o das playlists |
| `playlist_musicas` | Relaciona as mÃºsicas Ã s playlists |
| `reproducoes` | Registra a data e a hora de cada execuÃ§Ã£o |

Na tabela `playlist_musicas`, tambÃ©m adicionei uma restriÃ§Ã£o para evitar que a mesma mÃºsica seja incluÃ­da mais de uma vez na mesma playlist.

## Entidades e persistÃªncia

Criei uma entidade JPA para cada tabela principal. Os identificadores sÃ£o gerados automaticamente com `GenerationType.IDENTITY`, seguindo a configuraÃ§Ã£o do banco H2.

Os repositÃ³rios estendem `CrudRepository`, o que permite utilizar operaÃ§Ãµes como:

- `findAll()` para listar os registros;
- `findById()` para buscar um registro pelo ID;
- `save()` para inserir ou atualizar;
- `deleteById()` para excluir;
- `existsById()` para verificar se um registro existe.

TambÃ©m utilizei consultas derivadas no repositÃ³rio de associaÃ§Ãµes, por exemplo, para verificar se determinada mÃºsica jÃ¡ pertence a uma playlist.

## ValidaÃ§Ãµes

Implementei as validaÃ§Ãµes solicitadas utilizando Bean Validation e a anotaÃ§Ã£o `@Valid` nos controllers.

### ValidaÃ§Ãµes de mÃºsica

| Campo | Regra aplicada |
| --- | --- |
| TÃ­tulo | ObrigatÃ³rio, nÃ£o pode estar vazio e possui no mÃ¡ximo 150 caracteres |
| Artista | ObrigatÃ³rio, nÃ£o pode estar vazio e possui no mÃ¡ximo 150 caracteres |
| Ãlbum | Opcional e possui no mÃ¡ximo 150 caracteres |
| DuraÃ§Ã£o | ObrigatÃ³ria e deve ser maior que zero |
| GÃªnero | Opcional e possui no mÃ¡ximo 50 caracteres |

### ValidaÃ§Ãµes de playlist

| Campo | Regra aplicada |
| --- | --- |
| Nome | ObrigatÃ³rio, nÃ£o pode estar vazio e possui no mÃ¡ximo 100 caracteres |
| DescriÃ§Ã£o | Opcional e possui no mÃ¡ximo 255 caracteres |

Quando uma requisiÃ§Ã£o possui informaÃ§Ãµes invÃ¡lidas, a API retorna o status HTTP `400 Bad Request`, indicando quais campos precisam ser corrigidos.

## Tratamento de erros

Centralizei o tratamento das exceÃ§Ãµes em uma classe anotada com `@RestControllerAdvice`. Dessa forma, os controllers nÃ£o precisam repetir a mesma lÃ³gica de erro.

A API pode retornar:

| Status | Significado no projeto |
| --- | --- |
| `400 Bad Request` | Os dados enviados nÃ£o passaram pelas validaÃ§Ãµes |
| `404 Not Found` | A mÃºsica, playlist ou associaÃ§Ã£o nÃ£o foi encontrada |
| `409 Conflict` | A associaÃ§Ã£o jÃ¡ existe ou ocorreu um conflito de integridade |
| `502 Bad Gateway` | Houve uma falha na comunicaÃ§Ã£o realizada pelo OpenFeign |

## Endpoints de mÃºsicas

| MÃ©todo | Endpoint | DescriÃ§Ã£o |
| --- | --- | --- |
| POST | `/musicas` | Cadastra uma nova mÃºsica |
| GET | `/musicas` | Lista todas as mÃºsicas |
| GET | `/musicas/{id}` | Busca uma mÃºsica pelo ID |
| PUT | `/musicas/{id}` | Atualiza uma mÃºsica existente |
| DELETE | `/musicas/{id}` | Exclui uma mÃºsica |

Exemplo de corpo para cadastro:

```json
{
  "titulo": "Hotel California",
  "artista": "Eagles",
  "album": "Hotel California",
  "duracao": 391,
  "genero": "Rock"
}
```

## Endpoints de playlists

| MÃ©todo | Endpoint | DescriÃ§Ã£o |
| --- | --- | --- |
| POST | `/playlists` | Cria uma playlist |
| GET | `/playlists` | Lista todas as playlists |
| GET | `/playlists/{playlistid}` | Busca uma playlist pelo ID |
| PUT | `/playlists/{playlistid}` | Atualiza o nome e a descriÃ§Ã£o |
| DELETE | `/playlists/{playlistid}` | Exclui a playlist e suas associaÃ§Ãµes |
| POST | `/playlists/{playlistid}/musicas/{musicaId}` | Adiciona uma mÃºsica |
| DELETE | `/playlists/{playlistid}/musicas/{musicaId}` | Remove uma mÃºsica |
| GET | `/playlists/{playlistid}/musicas` | Lista os IDs das mÃºsicas |

## Endpoints de reproduÃ§Ã£o

| MÃ©todo | Endpoint | DescriÃ§Ã£o |
| --- | --- | --- |
| POST | `/reproducao` | Registra uma execuÃ§Ã£o |
| GET | `/reproducao/{playlistid}` | Lista as execuÃ§Ãµes da playlist |
| GET | `/reproducao/total/{playlistid}` | Retorna o total de execuÃ§Ãµes |
| POST | `/statistic` | Registra uma execuÃ§Ã£o pela API orquestradora |

Para criar uma reproduÃ§Ã£o diretamente, utilizo o seguinte corpo:

```json
{
  "playlistid": 1
}
```

A data e a hora nÃ£o precisam ser enviadas, porque sÃ£o geradas pela aplicaÃ§Ã£o com `LocalDateTime.now()`.

## API orquestradora e OpenFeign

A parte mais importante da integraÃ§Ã£o foi implementada na API orquestradora.

### AdiÃ§Ã£o de mÃºsica

```http
POST /api/adicionar/{playlistId}/musicas/{musicaId}
```

Antes de criar a associaÃ§Ã£o, a API:

1. busca a mÃºsica pelo ID utilizando OpenFeign;
2. busca a playlist pelo ID utilizando OpenFeign;
3. confirma que os dois recursos existem;
4. chama o endpoint responsÃ¡vel por criar a associaÃ§Ã£o;
5. retorna uma mensagem com o nome da mÃºsica e da playlist.

Exemplo de resposta:

```json
{
  "mensagem": "MÃºsica Billie Jean adicionada com sucesso Ã  playlist ClÃ¡ssicos do Rock"
}
```

### ExecuÃ§Ã£o de playlist

```http
PUT /api/executar/{playlistId}
```

Nesse fluxo, a API valida a playlist e utiliza o OpenFeign para chamar `POST /statistic`, criando um novo registro de reproduÃ§Ã£o.

O enunciado apresentava `POST /reproducao` na lista de endpoints, mas solicitava `POST /statistic` na parte da orquestraÃ§Ã£o. Para atender aos dois trechos, mantive os dois endpoints e configurei o OpenFeign para utilizar `/statistic`.

## Como executar

Primeiro, Ã© necessÃ¡rio ter o JDK 17 ou uma versÃ£o superior instalada. Para conferir:

```bash
java -version
javac -version
```

Na raiz do projeto, concedo permissÃ£o ao Maven Wrapper:

```bash
chmod +x mvnw
```

Depois, executo os testes:

```bash
./mvnw clean test
```

Para iniciar a API:

```bash
./mvnw spring-boot:run
```

A aplicaÃ§Ã£o fica disponÃ­vel em:

```text
http://localhost:8080
```

Como o projeto Ã© uma API, nÃ£o existe uma pÃ¡gina configurada para a rota `/`. Portanto, acessar somente `localhost:8080` pode apresentar uma pÃ¡gina de erro 404. Para testar a aplicaÃ§Ã£o, Ã© necessÃ¡rio acessar um dos endpoints, por exemplo:

```text
http://localhost:8080/musicas
```

## Acesso ao H2 Console

O console do banco pode ser acessado em:

```text
http://localhost:8080/h2-console
```

ConfiguraÃ§Ã£o de acesso:

| Campo | Valor |
| --- | --- |
| JDBC URL | `jdbc:h2:mem:playyourlist` |
| User Name | `sa` |
| Password | `password` |

Consultas que podem ser executadas:

```sql
SELECT * FROM musicas;
SELECT * FROM playlists;
SELECT * FROM playlist_musicas;
SELECT * FROM reproducoes;
```

## Testes

Criei testes automatizados para verificar:

- o carregamento dos dados iniciais;
- as validaÃ§Ãµes de mÃºsica;
- a inclusÃ£o e a remoÃ§Ã£o de mÃºsicas das playlists;
- a comunicaÃ§Ã£o entre os endpoints pelo OpenFeign;
- a criaÃ§Ã£o de registros de reproduÃ§Ã£o.

TambÃ©m deixei o arquivo `requests.http` com exemplos de requisiÃ§Ãµes para testar os endpoints pelo VS Code, utilizando a extensÃ£o REST Client.

## ConclusÃ£o

Com esta atividade, consegui aplicar os principais conceitos estudados nas aulas: criaÃ§Ã£o de endpoints REST, verbos HTTP, injeÃ§Ã£o de dependÃªncias, persistÃªncia com JPA, validaÃ§Ã£o de dados, tratamento de exceÃ§Ãµes e comunicaÃ§Ã£o entre serviÃ§os com OpenFeign.

A organizaÃ§Ã£o em pacotes separados permitiu representar as responsabilidades dos microserviÃ§os, mesmo com todos os endpoints executando dentro de uma Ãºnica aplicaÃ§Ã£o, conforme solicitado no enunciado.
