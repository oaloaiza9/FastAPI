from fastapi import FastAPI

app = FastAPI()

# Datos de ejemplo de clientes
clientes = [
                {
                    "documento": "12345678",
                    "nombres": "Juan",
                    "apellidos": "Pérez"
                },
                {
                    "documento": "98765432",
                    "nombres": "María",
                    "apellidos": "Gómez"
                }
            ]

@app.get("/clientes")
async def get_clientes():
    return clientes