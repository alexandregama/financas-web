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

#### Aumentando o Pool de objetos

Edite o arquivo **standalone-ha.xml** novamente:

```bash
$ vim wildfly/standalone/configuration/standalone-ha.xml
```

Altere o valor do **max-pool-size** da tag xml **bean-instance-pools**:

```xml
<pools>
    <bean-instance-pools>
       <strict-max-pool name="slsb-strict-max-pool" max-pool-size="5" instance-acquisition-timeout="5" instance-acquisition-timeout-unit="MINUTES"/>
       <strict-max-pool name="mdb-strict-max-pool" max-pool-size="20" instance-acquisition-timeout="5" instance-acquisition-timeout-unit="MINUTES"/>
    </bean-instance-pools>
</pools>
```


## Second Level Cache - Infinispan

Agora temos o Infinispan no lugar do JBoss Cache para fazer o second level cache
Para habilitar o second level cache precisamos das seguintes configurações:

- Editar o arquivo **persistence.xml** e adicionar:
```xml
<shared-cache-mode>ENABLE_SELECTIVE</shared-cache-mode>
```

- Ainda no **persistence.xml** devemos indicar para o Hibernateque vamos usar o second level cache
```xml
<property name="hibernate.cache.use_second_level_cache" value="true" />
```

- Agora basta adicionar a annotation **@Cacheable** na entidade a ser adicionada no Cache
```java
@Cacheable
@Entity
public class Conta {

}
```

- Podemos ainda fazer o cache de collections usando **@Cache**, como abaixo:
```java
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL) //Também suporta o READ_ONLY
  private List<Movimentacao> movimentacoes;
```

- Como **@Cache** é uma annotation específica do Hibernate, precisamos adicionar essa dependência no Wildfly. No **WebContent/WEB-INF** devemos adicionar no **MANIFEST.MF**
```
Dependencies: org.hibernate
```

### Estratégias para invalidar o Cache

O infinispan possui um limite para armazenar os objetos. Quando o limite é atingido, ele invalida o Cache. Existem 3 formas de invalidação do Cache:

- LFU - Less Frequently Used
- LRU - Least Recently Used
- FIFO - First in First out

O padrão do Infinispan é o LRU, onde o objeto **menos utilizado recentemente** é descartado.
Essa estratégia pode ser modificada no arquivo de configuração **standalone/configuration/standalone.xml**

```xml
<cache-container name="hibernate" default-cache="local-query" module="org.hibernate">
    <local-cache name="entity">
        <transaction mode="NON_XA"/>
        <eviction strategy="LRU" max-entries="10000"/>
        <expiration max-idle="100000"/>
    </local-cache>
    <local-cache name="local-query">
        <transaction mode="NONE"/>
        <eviction strategy="LRU" max-entries="10000"/>
        <expiration max-idle="100000"/>
    </local-cache>
    <local-cache name="timestamps">
        <transaction mode="NONE"/>
        <eviction strategy="NONE"/>
    </local-cache>
</cache-container>
```

### Cache de Queries

Também podemos fazer o cache de queries utilizando o **org.hibernate.cacheable** na query a ser executada:

```java
Query query = manager.createQuery(sql);

query.setHint("org.hibernate.cacheable");
```
