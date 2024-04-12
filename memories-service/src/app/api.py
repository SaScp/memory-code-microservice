from fastapi import APIRouter

from .service import MemoriesService

memories = APIRouter()
service = MemoriesService()