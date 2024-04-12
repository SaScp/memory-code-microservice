import aiohttp
from fastapi import HTTPException

from ..models import Page, UpdateMemoryPage, User
from .utils import updater, token


class MemoriesManager:
    
    def __init__(self) -> None:
        self.__updater = updater
    
    async def get_memory_pages(self, user_id: int):
        async with aiohttp.ClientSession() as session:
            
            access_token = await token(user_id=user_id).generate()
            
            headers = {
                "Authorization": f"Bearer {access_token}"
            }
            
            async with session.get(f'https://mc.dev.rand.agency/api/cabinet/individual-pages',
                                   headers=headers) as response:
                if response.status != 200:
                    raise HTTPException(status_code=404, detail="Pages not found")
                
                if response.content_type == 'application/json':
                    return [Page(**page) for page in await response.json()]

    async def update_memory_page(self, user_id: int, payload: UpdateMemoryPage):
        async with aiohttp.ClientSession() as session:        
            
            access_token = await token(user_id=user_id).generate()

            headers = {
                "Authorization": f"Bearer {access_token}"
                }    
            
            json = {
                "name": payload.name,
                "epitaph": payload.epitaph,
                "author_epitaph": payload.author_epitaph,
                "page_type_id": 1,
                "locale": payload.locale,
                "start": {
                    "day": str(payload.birth_at.day),
                    "month": str(payload.birth_at.month),
                    "year": payload.birth_at.year
                },
                "end": {
                    "day": str(payload.died_at.day),
                    "month": str(payload.died_at.month),
                    "year": payload.died_at.year
                },
                "main_image": self.__updater.prepare_image(payload.image_bytes),
                "biographies": self.__updater.prepare_biography(payload.biography),
                "page_information": self.__updater.prepare_additional_info(payload.additional_info)
            }         
            
            async with session.put('https://mc.dev.rand.agency/api/page/55272960', 
                                   json=json,
                                   headers=headers) as response:
                if response.content_type == 'application/json':
                    return await response.json()
                raise HTTPException(status_code=503, detail="Can't update page")
