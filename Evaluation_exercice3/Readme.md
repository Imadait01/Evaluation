## Description du projet

Ce projet État-Civil est une application Java utilisant Hibernate pour la gestion des entités d’un registre civil.
Il permet de manipuler les données relatives aux personnes, hommes, femmes et mariages, tout en respectant une architecture en couches (Beans, DAO, Service, Utilitaire).

L’objectif est de mettre en pratique les concepts de la programmation orientée objet, la persistance des données avec Hibernate, et la séparation des responsabilités selon le modèle DAO/Service.

## Architecture du projet
src/
└── main/
├── java/
│    ├── beans/
│    │     ├── Personne.java       # Classe mère représentant une personne
│    │     ├── Homme.java          # Sous-classe de Personne
│    │     ├── Femme.java          # Sous-classe de Personne
│    │     └── Mariage.java        # Représente un mariage entre deux personnes
│    │
│    ├── dao/
│    │     └── IDao.java           # Interface générique pour les opérations CRUD
│    │
│    ├── service/
│    │     ├── HommeService.java   # Gestion des entités Homme
│    │     ├── FemmeService.java   # Gestion des entités Femme
│    │     └── MariageService.java # Gestion des entités Mariage
│    │
│    └── util/
│          ├── HibernateUtil.java  # Configuration et session factory Hibernate
│          └── Main.java           # Classe principale d’exécution
│
└── resources/
└── application.properties   # Fichier de configuration Hibernate (base de données)
└── test/
└── java/
└── Test3.java              # Classe de test unitaire

## Technologies utilisées

Java 17+

Hibernate ORM 5+

MySQL / PostgreSQL (selon configuration)

Maven (ou IntelliJ build system)

JPA annotations

## Configuration Hibernate

Le fichier application.properties contient la configuration de la base de données :

hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
hibernate.connection.driver_class=com.mysql.cj.jdbc.Driver
hibernate.connection.url=jdbc:mysql://localhost:3306/etat_civil
hibernate.connection.username=root
hibernate.connection.password=
hibernate.hbm2ddl.auto=update
hibernate.show_sql=true



