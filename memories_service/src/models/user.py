from pydantic import BaseModel
from typing import Optional

class AuthData(BaseModel):
    login: str
    password: str

class User(BaseModel):
    user_id: int
    lang_code: str
    t_auth: Optional[AuthData]