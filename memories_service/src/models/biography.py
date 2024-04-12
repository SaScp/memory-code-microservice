from typing import Optional
from pydantic import BaseModel


class Photos(BaseModel):
    url: Optional[str] = None
    title: Optional[str] = None
    order: Optional[int] = None
    
class Biography(BaseModel):
    title: str
    description: str
    order: int
    checked: bool = True
    photos: Optional[list[Photos]]
    media: list = []