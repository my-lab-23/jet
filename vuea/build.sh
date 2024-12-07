#!/bin/bash

# Funzione per eseguire i comandi di Ktor
build_ktor() {
    echo "Esecuzione build per Ktor..."
    
    cd /home/ema/Scrivania/vue/kv/vuea/vue || exit
    npm run build:ktor
    
    cd /home/ema/Scrivania/vue/kv/vuea/ktor-sample || exit
    ./gradlew deploy
}

#!/bin/bash

# Funzione per verificare se rclone è montato
check_rclone_mounted() {
    if mount | grep -q '/home/ema/mylocalfolder'; then
        return 0  # Già montato
    else
        return 1  # Non montato
    fi
}

# Funzione per montare rclone se non è già montato
mount_rclone() {
    if check_rclone_mounted; then
        echo "rclone è già montato su /home/ema/mylocalfolder."
    else
        echo "Montaggio di rclone su /home/ema/mylocalfolder..."
        rclone mount -vv --daemon --daemon-timeout 10s --vfs-cache-mode writes db:/ /home/ema/mylocalfolder/
        if [ $? -eq 0 ]; then
            echo "rclone montato con successo."
        else
            echo "Errore durante il montaggio di rclone."
        fi
    fi
}

# Funzione per eseguire i comandi di Android
build_android() {
    echo "Esecuzione build per Android..."
    
    cd /home/ema/Scrivania/vue/kv/vuea/vue || exit
    npm run build:android
    
    cd ./android || exit
    ./gradlew assembleDebug
    
    mount_rclone
    cp /home/ema/Scrivania/vue/kv/vuea/vue/android/app/build/outputs/apk/debug/app-debug.apk /home/ema/mylocalfolder
}

# Funzione per eseguire entrambe le build
build_all() {
    echo "Esecuzione build per entrambi (Ktor e Android)..."
    build_ktor
    build_android
}

# Controllo dell'input
case "$1" in
    k|ktor)
        build_ktor
        ;;
    a|android)
        build_android
        ;;
    *)
        build_all
        ;;
esac
