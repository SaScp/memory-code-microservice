from .manager import MemoriesManager
from ..models import UpdateMemoryPage


class MemoriesService:
    
    def __init__(self) -> None:
        self.__manager = MemoriesManager()
    
    async def get_memory_pages(self, user_id: int):
        return await self.__manager.get_memory_pages(user_id=user_id)
    
    async def update_memory_page(self, user_id: int, payload: UpdateMemoryPage):
        return await self.__manager.update_memory_page(user_id=user_id,
                                                       payload=payload)