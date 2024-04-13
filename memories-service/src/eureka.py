from py_eureka_client.eureka_client import EurekaClient

from config import config

async def register_eureka():
    client = EurekaClient(eureka_server=f"http://{config.DISCOVERY_HOST_CLIENT}:{config.DISCOVERY_PORT}/eureka/",
                          app_name="memories-service")
    await client.start()