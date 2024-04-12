from fastapi import HTTPException

from .manager import MemoriesManager

class MemoriesService:
    
    def __init__(self) -> None:
        self.__manager = MemoriesManager()