from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    YANDEXGPT_SERVICE_URL: str
    USER_SERVICE_URL: str
    
    DISCOVERY_HOST_CLIENT: str
    DISCOVERY_HOST: str
    DISCOVERY_PORT: int
    
    model_config: SettingsConfigDict = SettingsConfigDict(
        env_file=".env",
        env_file_encoding="utf-8"
    )
    
config: Settings = Settings()