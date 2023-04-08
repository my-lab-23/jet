#!/bin/bash

# Imposta le variabili d'ambiente in base agli argomenti passati allo script
if [ "$1" == "reset" ]; then
    export EXPO_SWITCH="reset"
elif [ "$1" == "timestamp" ]; then
    export EXPO_SWITCH="timestamp"
    export EXPO_TIMESTAMP="$2"
elif [ "$1" == "last" ]; then
    export EXPO_SWITCH="last"
    export EXPO_LAST_N="$2"
elif [ "$1" == "between" ]; then
    export EXPO_SWITCH="between"
    export EXPO_DATE_1="$2"
    export EXPO_DATE_2="$3"
else
    echo "Argomento non valido"
    exit 1
fi

# Esegui il comando utilizzando le variabili d'ambiente impostate
/home/ktor/Expo-1.0-SNAPSHOT/bin/Expo 2> /dev/null
