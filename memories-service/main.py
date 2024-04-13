from fastapi import FastAPI
from uvicorn import run
import asyncio

from src.app import memories_service_router
from src.eureka import register_eureka

async def register_eureka():
    await register_eureka()

async def main() -> None:
    app = FastAPI()
    app.include_router(memories_service_router)
    run(app, host="0.0.0.0", port=8000)
    
if __name__ == "__main__":
    asyncio.create_task(register_eureka())
    asyncio.run(main())