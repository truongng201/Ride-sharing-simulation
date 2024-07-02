import requests

url  = 'https://api-journey-map.truongng.me/auth/signup'
i = 0
while True:
    import random
    if i == 100:
        break
    # randome sttring username
    username = ''.join(random.choices('abcdefghijklmnopqrstuvwxyz', k=10))
    email = ''.join(random.choices('abcdefghijklmnopqrstuvwxyz0123456789', k=10)) + '@gmail.com'
    vehicle_type = random.choice(['CAR4', 'CAR7', 'MOTORBIKE'])
    data={
        'username': username, 
        'password': 'abcde123456',
        'email': email,
        "role": "DRIVER",
        "vehicle_type": vehicle_type
    }
    # convert data to json
    import json
    data = json.dumps(data)
    
    res = requests.post(url,
                        headers={'Content-Type': 'application/json'}, 
                        data=data)
    print(res.text)
    i += 1