from fastapi import FastAPI
from fastapi import Form
from Herramientas import *

app = FastAPI()

usuarios = [
    Usuario(email="andres@mail.com", password=encrypt_md5("12345"))
]

clientes = [
    Cliente(documento="12345678", nombres="John", apellidos="Doe"),
    Cliente(documento="87654321", nombres="Jane", apellidos="Smith"),
    Cliente(documento="12345", nombres="Juan", apellidos="Perez")
]

@app.get("/")
async def raiz():
    return {"status":True, "message": "Estamos en la API"}

@app.get("/usuarios")
async def get_usuarios():
    return usuarios

@app.get("/clientes")
async def get_clientes():
    return clientes

@app.post("/login")
def login(email:str = Form(...), password:str = Form(...)):
    for existing_user in usuarios:
        if existing_user.email == email and existing_user.password == encrypt_md5(password):
            return { "status":True, "message": "Login successful"}
    return {"status":False, "message": "Invalid email or password"}

@app.post("/clientes")
def agregar_cliente(documento:str = Form(...), nombres:str = Form(...), apellidos:str = Form(...)):
    clientes.append(Cliente(documento=documento, nombres=nombres, apellidos=apellidos))
    return {"status":True, "message": "Cliente agregado correctamente"}

@app.put("/clientes")
def editar_cliente(documento:str = Form(...), nombres:str = Form(...), apellidos:str = Form(...)):
    for i, c in enumerate(clientes):
        if c.documento == documento:
            clientes[i].nombres = nombres
            clientes[i].apellidos = apellidos
            return {"status":True, "message": "Cliente editado correctamente"}
    return {"status":False, "message": "Cliente no encontrado"}

@app.delete("/clientes")
def eliminar_cliente(documento: str = Form(...)):
    for i, c in enumerate(clientes):
        if c.documento == documento:
            clientes.pop(i)
            return {"status":True, "message": "Cliente eliminado correctamente"}
    return {"status":False, "message": "Cliente no encontrado"}