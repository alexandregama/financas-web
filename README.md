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

Edite o arquivo **standalone-ha.xml**
```bash
$ vim wildfly/standalone/configuration/standalone-ha.xml
```

E adicione o xml que configura o nosso novo driver

```xml
<driver name="com.mysql" module="com.mysql">
    <xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
</driver>
```

Ainda no arquivo **standalone-ha.xml** adicione o nosso datasource

```xml
 <datasource jndi-name="java:/fj25DS" pool-name="fj25DS" enabled="true" use-java-context="true">
      <connection-url>jdbc:mysql://localhost:3306/fj25</connection-url>
      <driver>com.mysql</driver>
      <pool>
          <min-pool-size>10</min-pool-size>
          <max-pool-size>100</max-pool-size>
          <prefill>true</prefill>
      </pool>
      <security>
          <user-name>root</user-name>
      </security>
  </datasource>
```

