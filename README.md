# Financas Web
Projeto criado para o curso FJ-25 da Caelum, turma de Setembro 2015

#### Configurando o módulo do MySQL no WildFly

Entre no diretório de módulos do WildFly

```bash
$ cd wildfly-FJ-25-8.2.0/modules/system/layers/base
```

Crie a pasta **mysql** neste diretório

```bash
$ mkdir mysql
```

Dentro da pasta **mysql** crie a pasta main

```bash
$ mkdir main
```

Copiar o arquivo **module.xml** e o **jar** do mysql para a pasta main
O arquivo module.xml possui o seguinte conteúdo:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<module xmlns="urn:jboss:module:1.1" name="com.mysql">
  <resources>
    <resource-root path="mysql-connector-java-5.1.24-bin.jar"/>
  </resources>
  <dependencies>
    <module name="javax.api"/>
  </dependencies>
</module>
```


