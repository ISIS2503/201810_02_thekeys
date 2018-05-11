import paho.mqtt.publish as publish
import time
import json
import requests
from kafka import KafkaConsumer

# Configuration Values
domain = "isis2503-jcgloria.auth0.com" # Your Auth0 Domain
api_identifier = "uniandes.edu.co/thekeys" # API Identifier of your API
client_id = "njxtvdPndBOMDB8eTBdWsi5YYnOtFuDm" # Client ID of your Non Interactive Client
client_secret = "qNp8_GngN8ltWfsci_npGUGyEyrdr7cp6bA3Sj8384-4fzE4dxvlNvrEzPYqmaGT" # Client Secret of your Non Interactive Client
grant_type = "http://auth0.com/oauth/grant-type/password-realm" # OAuth 2.0 flow to use
scope = "openid"
realm = "Username-Password-Authentication"
username = "cerra1@yalelock.co"
password ="Nolohagas1234"

# Get an access token from Auth0
base_url = "https://isis2503-jcgloria.auth0.com/oauth/token"

payload = {
    'client_id': client_id,
    'client_secret': client_secret,
    'audience': api_identifier,
    'grant_type': grant_type,
    'realm': realm,
    'username': username,
    'password': password,
    'scope':scope
}
response = requests.post(base_url, data=json.dumps(payload), headers={'Content-type': 'application/json'})

auth0_response = response.json()
print("Id Token: " + auth0_response.get('id_token'))
print("Response Status Code: " + str(response.status_code))

consumer = KafkaConsumer('conjunto1.1-101.horarios',
                         bootstrap_servers=['172.24.42.103:8090'])

for message in consumer:

    #directorio = message.topic.split('.')
    #ubicacion=directorio[3]
    url = "http://172.24.42.79:8080/inmuebles/2/open"
    response = requests.get(url, headers={'Content-type': 'application/json','Authorization':'Bearer '+auth0_response.get('id_token')})
    #print("Response Status Code: " + str(response.status_code))
    #if  str(response.status_code)=="200":
    print(response.json())
    if response.json() ==False:
        publish.single('conjunto1.1-101.respuesta', '0', hostname= '172.24.42.103' , port=8083)
        print('no abre')
    else:
        publish.single('conjunto1.1-101.respuesta', '1', hostname= '172.24.42.103' , port=8083)
        print('abre')
