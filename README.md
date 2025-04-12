Projeto de uma página Web utilizadno Wildfly, Maven, Java, Javasript, Css.

Versão Wilfly: wildfly-34.0.1.Final

Versão Apache: apache-maven-3.9.9

Colocados dentro da Org - Wildfly
Versão driver Postgresql: postgresql-42.7.4
Versão Primefaces: primefaces-11.0.0

Configuração Standalone Wildfly:
<datasource jndi-name="java:jboss/datasources/PostgresDS" pool-name="PostgresDS" enabled="true" use-java-context="true" statistics-enabled="${wildfly.datasources.statistics-enabled:${wildfly.statistics-enabled:false}}">
<connection-url>jdbc:postgresql://localhost:5432/sharingan</connection-url>
<driver>postgresql</driver>
<security user-name="myuser" password="veloxi"/>
</datasource>


Tabela Banco de Dados:
CREATE TABLE equipamentos (
id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,   
nome VARCHAR(255) NOT NULL,            
descricao TEXT NOT NULL,              
data DATE NOT NULL,         
validade DATE NOT NULL,               
valor DECIMAL(10, 2) NOT NULL,         
status VARCHAR(20) DEFAULT 'ativo',    
);
