import aiohttp

from ..models import Page

class MemoriesManager:
    
    def __init__(self) -> None:
        ...
        
    async def test_endpoint(self):
        return {"message": "Hello, World!"}
    
    async def get_memory_pages(self, user_id: int):
        async with aiohttp.ClientSession() as session:
            
            access_token = "2449|ADxXiLxMClRtf6w6bGW35MBxftV8EDdRuBNS2xiJ"
            
            headers = {
                "Authorization": f"Bearer {access_token}"
            }
            
            async with session.get(f'https://mc.dev.rand.agency/api/cabinet/individual-pages',
                                   headers=headers) as response:
                if response.content_type == 'application/json':
                    return [Page(**page) for page in await response.json()]
