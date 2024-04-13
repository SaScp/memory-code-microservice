from fastapi import APIRouter

from .service import MemoriesService
from ..models import Page, UpdateMemoryPage

memories = APIRouter()
service = MemoriesService()


@memories.get('/memory-pages',
              tags=['Memories'],
              description='Get memory pages by given UserID',
              response_model=list[Page])
async def get_memory_pages(user_id: int):
    return await service.get_memory_pages(user_id=user_id)

@memories.post('/update-memory-page',
               tags=['Memories'],
               description='Update memory page by given UserID')
async def update_memory_page(user_id: int,
                             payload: UpdateMemoryPage):
    return await service.update_memory_page(user_id=user_id,
                                            payload=payload)