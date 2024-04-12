import base64
from typing import Union

from ...models import Biography, AdditionalInformation

class PageUpdater:
    
    def __init__(self) -> None:
        ...
        
    def prepare_image(self, 
                      image_bytes: Union[bytes, None]) -> str:
        if not image_bytes:
            return None
        image = base64.b64encode(image_bytes).decode("utf-8")
        return f"data:image/png;base64,{image}"
    
    def prepare_biography(self, biography: Union[Biography, list[Biography]]) -> list:
        if not biography:
            return []
        return [bio.model_dump_json() for bio in biography]
    
    def prepare_additional_info(self, 
                                additional_info: Union[AdditionalInformation, list[AdditionalInformation]]) -> list:
        if not additional_info:
            return []
        return [info.model_dump_json() for info in additional_info]