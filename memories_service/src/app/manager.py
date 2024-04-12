import aiohttp
import base64

from ..models import Page, UpdateMemoryPage, Biography, AdditionalInformation
from .utils import PageUpdater

class MemoriesManager:
    
    def __init__(self) -> None:
        self.__updater = PageUpdater()
        self.__access_token = "2627|m08sbNj1Nr6qWgFx4ABqebEqScUsyvZkB6cu0kwv"
        
    async def test_endpoint(self):
        return {"message": "Hello, World!"}
    
    async def get_memory_pages(self, user_id: int):
        async with aiohttp.ClientSession() as session:
            
            headers = {
                "Authorization": f"Bearer {self.__access_token}"
            }
            
            async with session.get(f'https://mc.dev.rand.agency/api/cabinet/individual-pages',
                                   headers=headers) as response:
                if response.content_type == 'application/json':
                    return [Page(**page) for page in await response.json()]

    async def update_memory_page(self, user_id: int, payload: UpdateMemoryPage):
        async with aiohttp.ClientSession() as session:        
            
            headers = {
                "Authorization": f"Bearer {self.__access_token}"
                }    
            
            json = {
                "name": payload.name,
                "surname": "",
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
