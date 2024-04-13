import aiohttp
from fastapi import HTTPException

from ...models import User
from config import config


class AccessTokenGenerator:
    
    def __init__(self,
                 user_id: int) -> None:
        self.user_id = user_id

    async def generate(self) -> str:
        async with aiohttp.ClientSession() as session:
            async with session.get(f"{config.USER_SERVICE_URL}/user/get/{self.user_id}") as response:
                if response.status != 200:
                    raise HTTPException(status_code=404, detail="User not found")
                user = User(**await response.json())
                json = {
                    "login": user.userAuth.login,
                    "password": user.userAuth.password,
                }
                
                async with session.post(f"{config.USER_SERVICE_URL}/auth/login", json=json) as response:
                    if response.status != 200:
                        raise HTTPException(status_code=401, detail="Can't login user")
                    response = await response.json()
                
                return response['access_token']    