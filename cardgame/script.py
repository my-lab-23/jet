import os

path = '/home/ema/Scrivania/jet-develop/cardgame_NO_PUB/app/src/main/res/drawable'

for filename in os.listdir(path):
    if filename[0].isdigit():
        new_filename = 'c' + filename
        os.rename(os.path.join(path, filename), os.path.join(path, new_filename))
