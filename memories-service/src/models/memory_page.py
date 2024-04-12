from typing import Optional, Union
from pydantic import BaseModel, HttpUrl


class End(BaseModel):
    day: str
    month: str
    year: int

class Start(BaseModel):
    day: str
    month: str
    year: int

class Person(BaseModel):
    access_password: Optional[str]
    accessible_by_password: Optional[bool]
    author_epitaph: Optional[str]
    banner_enabled: Optional[bool]
    birthday_at: Optional[str]
    blank_id: Optional[int]
    burial_id: Optional[int]
    burial_place: Optional[bool]
    commission: Optional[str]
    count_fields: Optional[int]
    count_filled_fields: Optional[int]
    created_at: Optional[str]
    custom_birthday_at: Optional[str]
    custom_died_at: Optional[str]
    deleted_at: Optional[str]
    died_at: Optional[str]
    end: Optional[End]
    epitaph: Optional[str]
    external_links: Optional[str]
    filled_fields: Optional[list[str]]
    firstName: Optional[str]
    free_access: Optional[bool]
    full_name: Optional[str]
    historical_status_id: Optional[int]
    id: Optional[int]
    index_page: Optional[bool]
    is_blank: Optional[bool]
    is_referral: Optional[bool]
    is_vip: Optional[bool]
    lastName: Optional[str]
    lead_id: Optional[int]
    link: Optional[HttpUrl]
    locale: Optional[str]
    main_image: Optional[str]
    master_id: Optional[int]
    media: Optional[list[Union[str, int]]]
    name: Optional[str]
    page_type_id: Optional[int]
    page_type_name: Optional[str]
    parent_tree_id: Optional[int]
    patronym: Optional[str]
    payment_id: Optional[int]
    position: Optional[str]
    price: Optional[float]
    published_page: Optional[bool]
    qr_hidden: Optional[bool]
    slug: Optional[int]
    start: Optional[Start]
    surname: Optional[str]
    updated_at: Optional[str]
    user_id: Optional[int]
    video_images: Optional[list[str]]
    video_links: Optional[list[str]]
    views: Optional[int]
    visitors: Optional[int]
    was_indexed: Optional[bool]
