from pydantic import BaseModel


class PageInformation(BaseModel):
    title: str
    description: str