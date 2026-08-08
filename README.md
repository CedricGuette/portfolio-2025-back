
# Portfolio back

Ce projet a été réalisé afin d'avoir une vitrine personnelle de mes compétences. C'est une deuxième version, la première n'avait pas de back, je l'ai ajouté afin de gagner un modularité et en simplicité pour modifier son contenu sans avoir à l'écrire en dur.

Le front end se trouve à cette adresse :  https://github.com/CedricGuette/portfolio-2025-back

## Variable d'environnement

Pour que l'API fonctionne correctement il faudra créer une variable d'environnement qui contient ce qui suit dans un fichier .env à la racine du projet :

`URL_FRONT` : l'url du front

`PORT_BACK` : le port sur lequel tourne le front

`DATABASE_URL` : l'url de la base de données

`DATABASE_NAME` : le nom de la base de données

`DATABASE_USERNAME` : le nom d'utilisateur de la base de données

`DATABASE_PASSWORD` : le mot de passe de l'utilisateur de la base de données

`EMAIL_HOST` : l'url de l'hôte de l'adresse mail que l'API va utiliser

`EMAIL_PORT` : le port utilisé par le service smtp

`EMAIL_USERNAME` : l'adresse mail

`EMAIL_PASSWORD` : le mot de passe de cette adresse mail

`EMAIL_ADMIN` : l'adresse mail de l'administrateur de l'API (pour recevoir le code d'activation)

`SECRET_KEY` : mettez une chaine de caractère générée, utilisé pour la sécurité de l'API


## Installation

Si votre IDE vous permet de lancer l'application directement vous n'aurez qu'à cliquer sur le bouton pour ce faire.
Sinon vous devez compiler l'application avec Maven en utilisant un clean package  dans le terminal à la racine:

```bash
mvn clean package
```
    
## Déploiement

Pour lancer l'application il faudra entrer dans un terminal à la racine du script 
```bash
  java -jar --enable-preview target/portfolio-0.0.1-SNAPSHOT.jar
```

L'application tournera donc en toile de fond à l'adresse: http://localhost:8080/



## Réalisé avec


<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="spring logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" height="40" alt="mysql logo"  />
</div>


