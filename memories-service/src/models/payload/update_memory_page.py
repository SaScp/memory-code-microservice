from typing import Optional, Union
from pydantic import BaseModel
from datetime import datetime

from ..biography import Biography
from ..additional_info import AdditionalInformation


class UpdateMemoryPage(BaseModel):
    name: str
    epitaph: str
    birth_at: datetime
    died_at: datetime
    author_epitaph: str
    locale: Optional[str] = "ru"
    image_bytes: Optional[bytes] = None
    biography: list[Biography] = None
    additional_info: list[AdditionalInformation] = None