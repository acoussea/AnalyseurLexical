# AnalyseurLexical - Sujet Projet 

## Description du projet
Mettre en œuvre, en Java, une application relative aux AEF qui sera à même d’effectuer l’essentiel du travail d’un analyseur lexical.

# Moteur d’AEF
## Objectifs
Le but de cette première partie est de construire un moteur d’exécution d’automates a états
finis, déterministes et sans λ-transitions.  
Le moteur commence par consulter la description de l’automate A qu’il incarne et afficher cette description, a fins de vérification.
Vous implémenterez également une méthode permettant d’exporter vos automates au format
.dot utilise par Graphviz. En utilisant Graphviz, vous pourrez alors générer des images .png
de vos automates.   
Le moteur d’automate est capable de traiter les entrées qu’on lui fournit. Les différents mots
fournis seront sépares par des espaces ou des retours a la ligne. Pour signifier la fin de la saisie
des entrées vous utiliserez ###. Il est également rappelé que la phrase vide est pertinente.  

Pour chacune des entrées qu’il analyse, le moteur produit :  
— la liste des configurations successives de l’automate,  
— la sortie éventuellement produite,  
— le diagnostic d’acceptation de l’entrée ou l’explication d’un éventuel blocage.  


Rappels :  
— Un automate déterministe possédé un seul état initial, mais peut avoir plusieurs états
acceptants.  
— Un automate qui a plusieurs états initiaux est nécessairement non déterministe.  
— Un automate peut générer des sorties. Nous ne considérerons pas d’automates non-deterministes générant des sorties, par contre vous devez dans cette première partie prendre
en compte les éventuelles sorties.  
— Un automate non-complet peut bloquer au milieu d’un mot, faute de trouver une transition.  


## Description de l’automate
Elle est contenue dans un fichier .descr et respecte la syntaxe définie en annexe.

Dans un premier temps, vous ne travaillez qu’avec des automates déterministes mais moteur
devra ensuite être capable de travailler avec tous les automates définis par un fichier .descr.  
En particulier, un automate ayant plusieurs état initiaux, ayant des λ-transitions, etc.



# Determinisation d’un AEFND
## Objectif
Le but de cette partie est d’étendre le travail précédent avec la determinisation d’un automate
N non-deterministe avec λ−transitions.  
Votre application construira l’automate déterministe D équivalent a N et en produira une
représentation compatible avec le moteur que vous avez mis au point dans la première partie. 

Vous devez donc pouvoir faire fonctionner N en deux étapes :
1. Determinisation de N avec cette seconde partie (donne D)
2. Émulation de  D par le moteur de la première partie.



## Spécifications de l’application
### Description de l’AEF non déterministe
C’est la même que celle de la partie précédente (fichier .descr). Vous aurez probablement a
modifier les méthodes de lecture d’un automate a partir d’un fichier .descr pour tenir compte
des états initiaux multiples, des λ−transitions et des configurations a états successeurs multiples.



### Description de l’AEF déterministe produit
Elle sera contenue dans un fichier texte et respectera les contraintes de la première partie. Vous
implémenterez donc une méthode permettant d’exporter au format .descr.
Elle devra pouvoir être fournie au moteur de la première partie, sans modification.



### Algorithme mis en œuvre
C’est celui vu en cours. Vous n’avez pas, ici, a minimiser l’automate obtenu.  
Pour illustrer votre compréhension de cet algorithme et favoriser la mise au point de votre
application, il serait souhaitable que les méthodes calculant λ-fermeture(T) et transiter(T, a)
soient explicitées dans votre code.  
Ce n’est donc peut-être pas une perte de temps, que de formuler la structure de votre code, en
utilisant au plus prés le formalisme du cours . . .



### Bilan
Une fois cette deuxième étape terminée, votre projet doit être capable de :  
— Lire un fichier .descr d’automate quelconque (déterministe ou non, avec lambda-transitions,
plusieurs états initiaux, etc.). 
— Produire un fichier .descr ou .dot.  
— Une fois l’automate charge en mémoire, lire des phrases et les analyser.  
— Determiniser un automate.  



## Annexe - Fichier .descr
Format de fichier .descr utilisé pour la description d'AEF.  
Le fichier de description d'un AEF se compose comme suit :

**< AEF >  ::=  [< ligneC >]  [< ligneM >]  < ligneV >  [< ligneO >]  < ligneE >  [< ligneI >]  < ligneF >  [< ligneT >]** (1)
  
ligne C (commentaire) ::= C  
ligne M (méta) ::= M µ : le méta-caractère représentant lambda (défaut : #) (ici : µ)  
ligne V (entrée) ::= V "c[c]*" : le vocabulaire d'entrée (pas de défaut) (2)  
ligne O (sortie) ::= O "c[c]*" : le vocabulaire de sortie (défaut : pas de sortie) (2)  
ligne E (nbre) ::= E i : nombre d'états (E = 0..N-1) (pas de défaut)  
ligne I (init) ::= I i[ i]* : les états initiaux (défaut : 0)  
ligne F (final) ::= F [i[ i]*] : les états acceptants (pas de défaut) (5)  
ligne T (trans) ::= i 'x' i 'x' : une transition de ExVxExO (3) (4)  


(1) <ligneT> ne peut être suivie que par <ligneT>. Le fichier peut éventuellement se terminer par une ligne vide, ou non.  
(2) "c[c]*" doit être fourni entre guillemets.  
(3) i 'x' i 'µ' peut aussi s'écrire i 'x' i  
(4) i 'c' j 'x' représente la transition de i vers j par c avec sortie de x (ou rien si x=µ)  
 i 'µ' j 'x' représente la transition de i vers j par lambda avec sortie de x (ou rien si x=µ)  
(5) En cas d'états multiples, ceux-ci sont séparés par un espace.  
  
Légende :  
i représente un entier sans signe  
c représente un caractère quelconque, différent de la représentation du méta-caractère lambda  
x représente un caractère quelconque, ou le méta-caractère ( x ::= c | µ )  
[q] indique que q est facultatif ( [q] ::= q | µ )  
[q]* indique que q peut être fourni 0 ou plusieurs fois ( [q]* ::= q[q]* | µ )  

Note :  
- les espaces ne sont pas considérés. Ils doivent être ignorés lors de l'analyse.  

Exemple de l'automate codeur vu en cours :  

M #  
V "01"  
O "abcd"  
E 3  
F 0  
T 0 '0' 0 'a'  
T 0 '1' 1 '#'  
T 1 '0' 0 'b'  
T 1 '1' 2 '#'  
T 2 '0' 0 'c'  
T 2 '1' 0 'd'  

Notes :  
 - I 0 est sous-entendue.  
 - on aurait pu écrire T 0 '1' 1 au lieu de T 0 '1' 1 '#'.  
