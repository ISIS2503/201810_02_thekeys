
import paho.mqtt.publish as publish
print('Escriba 6 para no recibir notificacion o 7 de lo contrario')
numero = input()
if numero == '6':
    publish.single('alarma.conjunto1.baja.1-101', 6, hostname= '172.24.42.103' , port=8083)
elif numero == '7':
    publish.single('alarma.conjunto1.baja.1-101', 7, hostname= '172.24.42.103' , port=8083)
else:
    print('numero erroneo')
print('termine')
