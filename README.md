# Desafio Nubank - Cálculo de Imposto sobre Operações de Ações

Este projeto implementa um sistema para calcular o imposto de operações de compra e venda de ações, conforme regras de ganho de capital.

## Pré-requisitos

- Java 21
- Maven 3.x
- IDE opcional: IntelliJ IDEA

## Executar a aplicação

0. Entre na raiz do projeto

```bash
cd br.com.nubank.desafio/
```

1. Compile o projeto:

```bash
mvn clean compile
```

2.Execute a aplicação usando um arquivo de entrada (input.txt):

```bash
mvn clean compile exec:java < input.txt
```

O resultado será exibido no terminal em formato JSON.

3. Executar testes unitários

Os testes unitários verificam o comportamento isolado da classe CapitalGainsCalculator.

```bash
mvn test
```
Local dos testes unitários: src/test/java/unit/service/CapitalGainsCalculatorTest.java

4. Executar testes integrados
Os testes integrados verificam o fluxo completo, incluindo leitura de entrada, processamento e saída.

```bash
mvn verify
```

Local dos testes integrados: src/test/java/integration/CapitalGainsCalculatorIT.java

5. Observações

O projeto utiliza Jackson (jackson-core e jackson-databind) para ler e escrever JSON.

Todos os valores monetários são representados com BigDecimal para evitar perda de precisão.

O limite de isenção de imposto é R$ 20.000 por operação de venda.