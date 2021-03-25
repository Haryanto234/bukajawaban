import requests

App_URL=" https://jsonplaceholder.typicode.com/posts"
payload={
        "title": "recommendation",
        "body": "motorcycle",
        "userId": 12
    }

def testGet():
    response = requests.get(App_URL)
    json_response = response.json()
    assert response.status_code == 200
    for data in json_response:
        assert isinstance(data["id"], int)
        assert isinstance(data["userId"], int)
        assert isinstance(data["title"], str)
        assert isinstance(data["body"], str)

def testPost():
    response = requests.post(App_URL, payload)
    data = response.json()
    assert response.status_code == 201
    assert data['userId'] == str(payload['userId'])
    assert data['title'] == payload["title"]
    assert data['body'] == payload["body"]