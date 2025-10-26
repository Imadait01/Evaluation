
## Description du Projet
Ce projet est une application de Gestion de Projets (Gestion-Projets) développée en Java. Il est structuré autour du modèle DAO (Data Access Object) et d'une couche Service pour séparer la logique métier de la persistance des données.

L'objectif principal est de gérer les entités suivantes :

Employés (Employe)

Projets (Projet)

Tâches (Tache)

L'association Employé-Tâche (EmployeTache, EmployeTacheId)

## Structure du Projet
Le projet suit une structure de répertoire standard pour les applications Java/Maven/Gradle, organisée comme suit :

Dossier/Package	Description
src/main/java/classes	Contient les classes du modèle (Entités/POJOs) du domaine.
src/main/java/dao	Contient les interfaces et implémentations du patron DAO (IDao), responsables de l'accès aux données.
src/main/java/service	Contient la logique métier (Classes Service) qui utilise les DAOs pour effectuer les opérations CRUD et les traitements spécifiques.
src/main/java/util	Contient les classes utilitaires, y compris la classe principale (Main) pour le lancement de l'application ou des tests simples.
src/main/resources	Contient les fichiers de configuration, comme application.properties pour la configuration de la base de données ou d'autres paramètres.
src/test/java	Contient les classes de tests unitaires ou d'intégration (e.g., MainTest).
## Technologies Utilisées
Langage : Java

Environnement/IDE : IntelliJ IDEA (selon la structure .idea)

Framework de Persistance : Probablement Hibernate ou JPA (en se basant sur la structure Entités-DAO-Service).

Base de Données : Dépend de la configuration dans application.properties.

Build Tool : Maven ou Gradle (Non visible, mais implicite pour ce type de projet).

## Démarrage Rapide
Prérequis
JDK 8 ou supérieur

Un outil de construction (Maven ou Gradle)

Une base de données configurée (selon application.properties)

## Configuration
Cloner le dépôt : git clone [URL_du_dépôt]

Mettre à jour le fichier de configuration src/main/resources/application.properties avec les informations de connexion à votre base de données (URL, nom d'utilisateur, mot de passe).