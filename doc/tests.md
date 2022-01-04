# Backend

Eita ...

## Testes

**1 - MethodName_StateUnderTest_ExpectedBehavior**

_NomeDoMetodo_EstadoEmTeste_Comportamento esperado_

Existem alguns argumentos contra esta estratégia de que se os nomes dos métodos mudam como parte da refatoração do código, então o nome do teste como este também deve mudar ou se torna difícil de compreender em um estágio posterior.

Exemplos:

```
isAdult_AgeLessThan18_False
retirMoney_InvalidAccount_ExceptionThrown
admitStudent_MissingMandatoryFields_FailToAdmit
```

**2 - MethodName_ExpectedBehavior_StateUnderTest**

_NomDoMetodo_EstadoEsperado_EstadoEmTeste_

Alguns desenvolvedores também recomendam o uso dessa técnica de nomenclatura.

Essa técnica tem a desvantagem de que, se os nomes dos métodos forem alterados, será difícil de compreender em um estágio posterior.

A seguir temos o nome dos testes usando no primeiro item definidos usando esta técnica:

```
isAdult_False_AgeLessThan18
retirMoney_ThrowsException_IfAccountIsInvalid
admitStudent_FailToAdmit_IfMandatoryFieldsAreMissing
```

**3 - test[Nome do Recurso sendo testado]**

Esta nomenclatura torna mais fácil ler o teste, pois o recurso a ser testado é escrito como parte do nome do teste. Embora haja argumentos de que o prefixo “test” seja redundante.

Abaixo temos os nomes dos testes usando esta estratégia de nomenclatura:

```
testIsNotAnAdultIfAgeLessThan18
testFailToWithdrawMoneyIfAccountIsInvalid
testStudentIsNotAdmittedIfMandatoryFieldsAreMissing
```

**4 - Nome do Recurso a ser testado**

Muitos desenvolvedores sugerem que é melhor simplesmente escrever o recurso a ser testado, porque, de qualquer forma, se usa anotações para identificar o método como método de teste.

Outra recomendação a esta estratégia é pelo motivo de fazer testes de unidade como forma alternativa de documentação e evitar 'odores' de código.

A seguir temos os nomes dos testes no primeiro exemplo nesta abordagem:

```
IsNotAnAdultIfAgeLessThan18
FailToWithdrawMoneyIfAccountIsInvalid
StudentIsNotAdmittedIfMandatoryFieldsAreMissing
```

**5 - Should_ExpectedBehavior_When_StateUnderTest**

Esta técnica também é usada por muitos, pois torna mais fácil a leitura dos testes.

A seguir os nomes dos testes nesta abordagem:

```
Should_ThrowException_When_AgeLessThan18
Should_FailToWithdrawMoney_ForInvalidAccount
Should_FailToAdmit_IfMandatoryFieldsAreMissing
```

**6 - When_StateUnderTest_Expect_ExpectedBehavior**

Veja a seguir como os testes do primeiro exemplo seriam parecidos se fossem nomeados usando esta técnica:

```
When_AgeLessThan18_Expect_isAdultAsFalse
When_InvalidAccount_Expect_WithdrawMoneyToFail
When_MandatoryFieldsAreMissing_Expect_StudentAdmissionToFail
```

**7 - Given_Preconditions_When_StateUnderTest_Then_ExpectedBehavior**

Esta abordagem é baseada na convenção de nomenclatura desenvolvida como parte do Behavior-Driven Development (BDD).

A ideia é dividir os testes em três partes, de forma que se possa chegar a pré-condições, estado em teste e comportamento esperado a ser escrito no formato acima.

A seguir temos o nome do primeiro exemplo se fossem nomeados usando esta técnica:

```
Given_UserIsAuthenticated_When_InvalidAccountNumberIsUsedToWithdrawMoney_Then_TransactionsWillFail
```

O nome resultante fica um pouco longo e talvez não seja o mais indicado.

## Referências

- https://www.baeldung.com/role-and-privilege-for-spring-security-registration
- https://medium.com/geekculture/role-based-access-control-rbac-with-spring-boot-and-jwt-bc20a8c51c15

```

```

```

```
