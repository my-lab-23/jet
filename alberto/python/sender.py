import os
import requests
import base64
import json

#

def send_image(file_path):
    with open(file_path, 'rb') as img:
        file_bytes = img.read()
        file_base64 = base64.b64encode(file_bytes).decode()  # converte i byte in una stringa Base64
        response = requests.post('http://localhost:9000/upload/image', data=file_base64)
    return response.text

def send_all_images_in_directory(directory_path):
    for filename in os.listdir(directory_path):
        if filename.endswith('.jpg'):
            file_path = os.path.join(directory_path, filename)
            print(send_image(file_path))

#

def send_json(file_path):
    with open(file_path, 'r') as json_file:
        data = json.load(json_file)
        response = requests.post('http://localhost:9000/upload/json', json=data)
    return response.text

def send_all_json_in_directory(directory_path):
    for filename in os.listdir(directory_path):
        if filename.endswith('.json'):
            file_path = os.path.join(directory_path, filename)
            print(send_json(file_path))

#

if __name__ == '__main__':
    directory_path = './'
    send_all_images_in_directory(directory_path)
    send_all_json_in_directory(directory_path)
    