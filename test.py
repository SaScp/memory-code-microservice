from fastapi import FastAPI
from uvicorn import run

from memories_service import memories_service_router

def create_app():
    app = FastAPI()
    app.include_router(memories_service_router)
    return app

def main():
    app = create_app()
    run(app, port=8000)
    
if __name__ == '__main__':
    main()
