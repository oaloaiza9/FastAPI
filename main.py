from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

class Cliente(BaseModel):
    documento: str
    nombres: str
    apellidos: str

clientes = [
    Cliente(documento="12345678", nombres="John", apellidos="Doe"),
    Cliente(documento="87654321", nombres="Jane", apellidos="Smith"),
    Cliente(documento="12345", nombres="Juan", apellidos="Perez")
]

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