FROM python:3.11
COPY . /memoryies-service
COPY ./requirements.txt /memoryies-service/requirements.txt
WORKDIR /memoryies-service
RUN pip install -r requirements.txt
EXPOSE 8000:8000


