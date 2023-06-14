from fastapi import FastAPI
from pydantic import BaseModel
from fastapi import Form
import hashlib

def encrypt_md5(text):
    md5_hash = hashlib.md5(text.encode('utf-8'))
    encrypted_text = md5_hash.hexdigest()
    return encrypted_text


app = FastAPI()

class Usuario(BaseModel):
    email: str
    password: str

class Cliente(BaseModel):
    documento: str
    nombres: str
    apellidos: str

usuarios = [
    Usuario(email="andres@mail.com", password=encrypt_md5("12345"))
]

clientes = [
    Cliente(documento="12345678", nombres="John", apellidos="Doe"),
    Cliente(documento="87654321", nombres="Jane", apellidos="Smith"),
    Cliente(documento="12345", nombres="Juan", apellidos="Perez")
]

@app.get("/usuarios")
async def get_usuarios():
    return usuarios

@app.post("/login")
def login(email:str = Form(...), password:str = Form(...)):
    for existing_user in usuarios:
        if existing_user.email == email and existing_user.password == encrypt_md5(password):
            return { "status":True, "message": "Login successful"}
    return {"status":False, "message": "Invalid email or password"}

@app.get("/clientes")
async def get_clientes():
    return clientes

@app.post("/clientes")
def agregar_cliente(cliente: Cliente):
    clientes.append(cliente)
    return {"mensaje": "Cliente agregado correctamente"}

@app.put("/clientes/{documento}")
def editar_cliente(documento: str, cliente: Cliente):
    for i, c in enumerate(clientes):
        if c.documento == documento:
            clientes[i] = cliente
            return {"mensaje": "Cliente editado correctamente"}
    return {"error": "Cliente no encontrado"}

@app.delete("/clientes/{documento}")
def eliminar_cliente(documento: str):
    for i, c in enumerate(clientes):
        if c.documento == documento:
            clientes.pop(i)
            return {"mensaje": "Cliente eliminado correctamente"}
    return {"error": "Cliente no encontrado"}