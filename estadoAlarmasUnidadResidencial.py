import paho.mqtt.subscribe as subscribe
print('Escriba el nombre de la unidad de residencia y oprima enter')
residencia = input()
while True:
    msg = subscribe.simple('alarma.'+residencia+'.#', hostname= '172.24.42.103' , port=8083)
    split = msg.topic.split('/')
    apartamento= split[3]
    tipo = ' '
    print(msg.topic)
    if '2' in msg:
        tipo = 'contraseña incorrecta 3 veces'
    elif '1' in msg:
        tipo = 'sensor de movimiento activado'
    elif '3' in msg:
        tipo = 'puerta abierta'
    elif '4' in msg:
        tipo = 'bateria baja en la cerradura'
    print(apartamento +' Estado: ' + tipo)
