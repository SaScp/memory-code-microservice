from fastapi import APIRouter

from .service import MemoriesService
from ..models import Page

memories = APIRouter()
service = MemoriesService()

@memories.get('/test', 
              tags=['Test endpoint'])
async def test_endpoint():
    return await service.test_endpoint()

@memories.get('/memory-pages',
              tags=['Memories'],
              description='Get memory pages by given UserID',
              response_model=list[Page])
async def get_memory_pages(user_id: int):
    return await service.get_memory_pages(user_id=user_id)