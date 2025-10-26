
## Description du Projet
Ce projet est une Application de Gestion de Stock (ou Gestion des Commandes et Produits).

Il s'agit d'une application Java standard, structurée en couches (Modèle-DAO-Service) pour 
une architecture propre et maintenable.L'application gère les entités principales
suivantes :Catégories (Categorie)Produits (Produit)Commandes (Commande)Lignes de 
Commande Produit (LigneCommandeProduit) - pour détailler les articles dans chaque commande.

## ️ Architecture et Structure du Projet

suit le pattern Modèle-DAO-Service :
Dossier/PackageRôleDescriptionsrc/main/java/classesModèleClasses

du domaine (Entités/POJOs).src/main/java/daoDAO (Data Access Object)Contient

l'interface générique (IDao) pour la persistance des données.src/main/java/serviceLogique

MétierCouche de Service qui implémente les règles métier et coordonne les opérations 

via les DAOs.src/main/java/utilUtilitairesContient les classes d'aide, notamment 

HibernateUtil pour la gestion de la session de 

persistance. src/main/resourcesConfigurationFichiers de propriétés,

y compris application.properties pour la base de données.src/test/javaTestsContient

les classes de tests (AppTest).


## Technologies UtiliséesLangage : 


JavaFramework de Persistance : Probablement Hibernate ou JPA, attesté par la présence

de HibernateUtil.Couche de Persistance : Patron DAO.Configuration : 

Fichier application.properties.


## Démarrage et Installation


PrérequisJDK 8 ou supérieurUne base de données (MySQL, PostgreSQL, etc.)

Un outil de construction (Maven ou Gradle)Configuration de la Base de DonnéesLocaliser

le fichier de configuration : src/main/resources/application.properties.

Mettre à jour les propriétés de connexion de la base de données (URL, utilisateur, mot de

passe) selon votre environnement.

<img width="1882" height="367" alt="Exo1" src="https://github.com/user-attachments/assets/03a0dd05-7b02-49b5-9a99-e1b9846e3d0d" />
<img width="1826" height="976" alt="Exo11" src="https://github.com/user-attachments/assets/98abce83-a3f2-4b5f-89da-32f4644faad8" />

