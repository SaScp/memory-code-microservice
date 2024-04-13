from fastapi import FastAPI
from uvicorn import run
import asyncio

from src.app import memories_service_router
from src.eureka import register_eureka

def main() -> None:
    app = FastAPI()
    app.include_router(memories_service_router)
    run(app, host="0.0.0.0", port=8000)

if __name__ == "__main__":
    asyncio.run(register_eureka())
    main()