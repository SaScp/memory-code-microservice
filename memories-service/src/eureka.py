from py_eureka_client.eureka_client import EurekaClient

async def register_eureka():
    client = EurekaClient(eureka_server="http://${DISCOVERY_HOST}:${DISCOVERY_PORT}/eureka/",
                          app_name="memories-service")
    await client.start()