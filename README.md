# Шагомер
___

## Описание:
Консольная версия.

### Функции

+ добовление пользователя в БД
+ добовление цели пользователя в БД
+ добовление шагов пользователя в БД
+ просмотр данных о шагах сохраненных в БД


 ## __Инструменты__

[![IntelliJ IDEA](https://github.com/Alexey7721/product-and-reviews/raw/master/icons/intellij-idea-48.png)](https://www.jetbrains.com/idea/)
[![Java](https://github.com/Alexey7721/product-and-reviews/raw/master/icons/java-coffee-cup-48.png)](https://www.java.com/ru/)
[![PostgreSQL](https://github.com/Alexey7721/product-and-reviews/raw/master/icons/pgSQL.png)](https://www.postgresql.org/)
[![Spring](https://github.com/Alexey7721/product-and-reviews/raw/master/icons/spring-48.png)](https://spring.io/)
[![XML](https://github.com/Alexey7721/product-and-reviews/raw/master/icons/xml-48.png)](https://www.xml.com/)
[![Hibenate](https://github.com/Alexey7721/product-and-reviews/raw/master/icons/hibernate-1.png)](https://hibernate.org/)
[![Visual studio code](https://github.com/Alexey7721/product-and-reviews/raw/master/icons/visual-studio-code-2019-48.png)](https://code.visualstudio.com/)



<br> 


## __Установка и настойка__
### Перед запуском данного кода:
- #### Создайте базу данных и таблицы;

```postgresql
create table purposes(
    id serial primary key,
    purpose integer
);

create table users(
    id serial primary key,
    name varchar(45),
    surname varchar(45),
    username varchar(45),
    password varchar(45),
    purpose_id integer,
    foreign key (purpose_id) references purposes(id)
);

create table months(
    id serial primary key,
    name varchar(45),
    date integer,
    count integer,
    user_id integer,
    foreign key (user_id) references users(id)
);
```


- ### В hibernate.cfg.xml укажите название, имя пользователя и пароль базы данных.
```xml
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="connection.url">jdbc:postgresql://localhost:5432/***НАЗВАНИЕ БД***?useSSL=false&amp;serverTimezone=UTC</property>
        <property name="connection.username">***ИМЯ ПОЛЬЗОАТЕЛЯ***</property>
        <property name="connection.password">***ПАРОЛЬ***</property>

        <property name="current_session_context_class">thread</property>
        <property name="dialect">org.hibernate.dialect.PostgreSQL9Dialect</property>
        <property name="show_sql">true</property>

    </session-factory>
</hibernate-configuration>
```
