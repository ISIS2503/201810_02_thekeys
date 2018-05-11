import json
import requests
from kafka import KafkaConsumer

consumer = KafkaConsumer('hub.healthcheck.conjunto1.1-101',
                         bootstrap_servers=['172.24.42.103:8090'],
                         consumer_timeout_ms=4000)
for message in consumer:
    print("llega un healthcheck" )
url = 'http://172.24.42.45:8080/correo'
payload = {
'correo': 'propietario@gmail.com',
'asunto': 'sistema desconectado',
'cuerpo': 'se dejo de recibir información de su entidad'
}
response = requests.post(url, data=json.dumps(payload),headers={'Content-type': 'application/json'})
print("Response Status Code: "+ str(response.status_code))
