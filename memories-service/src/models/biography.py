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
    photos: list[Photos] = [Photos() for _ in range(0, 2)]
    media: list = []