from pydantic import BaseModel


class AdditionalInformation(BaseModel):
    title: str
    description: str