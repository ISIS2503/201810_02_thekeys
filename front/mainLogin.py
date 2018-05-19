from flask import Flask
import requests
import json
from flask_socketio import SocketIO, emit
import threading
from threading import Lock
from flask import Flask, flash, redirect, render_template, request, session, abort
import os
from kafka import KafkaConsumer

app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret_thermalcomfort'
socketio = SocketIO(app)
thread = None
thread_lock = Lock()
arregloTopicos =[]
arregloConjuntos=[]

@app.route('/')
def home():
    if not session.get('logged_in'):
        return render_template('login.html')
    else:
        return ""

@app.route('/panel', methods=['POST'])
def do_admin_login():
    correo=  request.form['correo electronico']
    contraseña= request.form['password']
    url = 'http://172.24.42.63:8083/usuarios/login'
    payload = {
    'correo': correo,
    'contraseña' : contraseña
    }
    response = requests.post(url, data=json.dumps(payload),headers={'Content-type': 'application/json'})
    if response.status_code == 200:
        session['logged_in'] = True
        constructorTopico(correo)
        return render_template('index.html')
    else:
        flash('wrong password!')
    return home()
@app.route("/logout")
def logout():
    session['logged_in'] = False
    return home()

def constructorTopico(correo):
    indice = 1
    url1 = 'http://172.24.42.79:8080/seguridad'
    response = requests.get(url1 + '/' + correo)
    arreglo = response.json()
    for path in arreglo:
        arregloTopicos.append('alarma.conjunto1.alta.'+ path)
        arregloConjuntos.append(path)

# Consumidor del topic de Kafka "alta.piso1.local1". Cada valor recibido se envía a través del websocket.
def background_thread_websocket():
    print(arregloTopicos)
    consumer = KafkaConsumer(bootstrap_servers=['172.24.42.103:8090'])
    consumer.subscribe(arregloTopicos)
    for message in consumer:
        print('consumi')
        split = message.topic.split('.')
        apartamento= split[3]
        socketio.emit('alarmas', apartamento)
# Rutina que se ejecuta cada vez que se conecta un cliente de websocket e inicia el conmunidor de Kafka
@socketio.on('connect')
def test_connect():
    global thread
    with thread_lock:
        if thread is None:
            thread = socketio.start_background_task(target=background_thread_websocket)
            for path in arregloConjuntos:
                emit('inmuebles', path)

if __name__ == "__main__":
    app.secret_key = os.urandom(12)
    app.run(debug=True,host='0.0.0.0', port=4000)
