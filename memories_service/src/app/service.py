from fastapi import HTTPException

from .manager import MemoriesManager

class MemoriesService:
    
    def __init__(self) -> None:
        self.__manager = MemoriesManager()
        
    async def test_endpoint(self):
        return await self.__manager.test_endpoint()
    
    async def get_memory_pages(self, user_id: int):
        return await self.__manager.get_memory_pages(user_id=user_id)