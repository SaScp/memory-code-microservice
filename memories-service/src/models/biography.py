from typing import Optional
from pydantic import BaseModel


class Photos(BaseModel):
    url: Optional[str]
    title: Optional[str]
    order: Optional[int]
    
class Biography(BaseModel):
    title: str
    description: str
    order: int
    checked: Optional[bool] = True
    photos: Optional[list[Photos]]
    media: Optional[list]