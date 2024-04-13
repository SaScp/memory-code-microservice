from fastapi import FastAPI
from uvicorn import run

from .src.app import memories_service_router

def main() -> None:
    app = FastAPI()
    app.include_router(memories_service_router)
    run(app, host="localhost", port=8000)
    
if __name__ == "__main__":
    main()
    
    