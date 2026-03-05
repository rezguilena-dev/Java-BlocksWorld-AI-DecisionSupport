#!/bin/bash

# Définition des chemins 
LIB="lib/blocksworld.jar:lib/bwgenerator.jar"
SRC_PATH="src"
OUT_DIR="out"

#Étape de compilation 
echo "Lancement de la compilation"
mkdir -p $OUT_DIR 
javac -d $OUT_DIR -cp $LIB -sourcepath $SRC_PATH src/blocksworld/*.java

if [ $? -ne 0 ];then 
    echo "Echec de compilation"
    exit 1
fi

echo "fichiers compilés avec succès"

# Exécution des différentes classes selon l'argument passé en paramètre 

case "$1" in 
    # 1-Modelisation
    modeling)
        java -cp "$OUT_DIR:$LIB" blocksworld.MainModelling
        ;;
    
    # 2-Planification
    planning)
        java -cp "$OUT_DIR:$LIB" blocksworld.MainPlanning
        ;;
    
    # 3-Satisfaction de contraintes 
    csp)
        java -cp "$OUT_DIR:$LIB" blocksworld.MainCSP
        ;;
    # 4-Datamining 
    mining)
        java -cp "$OUT_DIR:$LIB" blocksworld.MainMining
        ;;
    # 5-Documentation
    doc)
        echo "Génération de la Javadoc"
        mkdir -p docs
        javadoc -d docs -cp "$LIB" -sourcepath src -subpackages blocksworld -quiet
        echo "Documentation générée dans le dossier 'docs/'"
        ;;
    
    *)
        echo "Usage: ./run_project.sh {modeling|planning|csp|mining|doc}"
        exit 1
        ;;
esac


