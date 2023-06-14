from pydantic import BaseModel
import hashlib

def encrypt_md5(text):
    md5_hash = hashlib.md5(text.encode('utf-8'))
    encrypted_text = md5_hash.hexdigest()
    return encrypted_text

class Usuario(BaseModel):
    email: str
    password: str

class Cliente(BaseModel):
    documento: str
    nombres: str
    apellidos: str