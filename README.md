[![Go Reference](https://pkg.go.dev/badge/github.com/moohbr/WebMonitor.svg)](https://pkg.go.dev/github.com/moohbr/WebMonitor)
# WebMonitor

Webmonitor é um programa leve e gratuito que procura reduzir a dificuldade na monitoração das infraestruturas de sites, tendo como um de seus princípios a simplicidade e eficiência.


## Features:

- [x] monitoração do seu site.
- [x] Monitoração do seu site com um intermediário personalizado.
- [x] Notifica quando o site está offline.

## Futuras features:

- [ ] Notificação com um mensagem personalizada/intervalo personalizado/tempo limite personalizado
- [ ] Criar uma interface web para o gerenciamento do programa
- [ ] Perfil de usuparios e autenticação.


## Contribuindo

É possível contribuir com esse projeto de duas maneiras:

1. Abrir um issue com um relatório de bug ou pedido de features.
2. Abrir um pull request com uma correção de bug ou nova feature.

## Licença

Esse projeto está licenciado sob a MIT License - veja o arquivo [LICENSE](LICENSE) para mais detalhes.


## Documentação
### Environment Variables

Para executar esse projeto, você precisará adicionar as seguintes environment variables no seu arquivo `.env`:

- `SMPT_SERVER` - SMTP server address
- `SMTP_PORT` - SMTP server port
- `SMTP_USER` - SMTP server user
- `SMTP_PASSWORD` - SMTP server password
- `SMTP_INSECURE` - SMTP server insecure

### Comandos CLI

```
 Comandos disponíveis:
  install     Instala o banco de dados pela primeira vez.
  show        Mostra uma lista de servidores ou usuários.
  add         Adiciona um novo site para ser monitorado ou usuário para notificar.
  remove      Remove um site ou usuário.
  update      Atualiza um site ou usuário.
  help        Ajuda sobre qualquer comando.

Use "WebMonitor [command] --help" para mais informações sobre um comando.  

Flags:
  -h, --help      Ajuda para o WebMonitor
  -v, --verbose   verbose output
```

### Instalação


#### Manual:

1. Clone o repositório
2. Instale todas as dependências com `go build`
3. Execute o script com `WebMonitor`

### Uso


#### Manual

1. Crie um arquivo `.env` .
2. Instale as dependências com `go build` e execute o script com `webmonitor`

## Perguntas:

Se tiver alguma dúvida, abra um issue e responderemos assim que possível.

## Agradecimentos

 - [Cobra](github.com/spf13/cobra)
 - [SQLite](github.com/mattn/go-sqlite3)
 - [go-mail](github.com/go-mail/mail)
 - [go-dotenv](github.com/joho/godotenv)


## Autores

- [@moohbr](https://www.github.com/moohbr)
- [@davirodriguesp](https://github.com/davirodriguesp)
- [@davi-oliver](https://github.com/davi-oliver)
- [@Ldias18](https://github.com/Ldias18)
