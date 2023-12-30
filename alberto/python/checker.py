import os

def is_jpeg(file_path):
    with open(file_path, 'rb') as file:
        return file.read(3) == b'\xff\xd8\xff'

def check_jpeg_in_directory(directory_path):
    for filename in os.listdir(directory_path):
        if filename.endswith('.jpg'):
            file_path = os.path.join(directory_path, filename)
            if is_jpeg(file_path):
                print(f"Il file {filename} è un'immagine JPEG.")
            else:
                print(f"Il file {filename} non è un'immagine JPEG.")

# Utilizzo della funzione
directory_path = './'
check_jpeg_in_directory(directory_path)
