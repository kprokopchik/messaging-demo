from locust import HttpUser, task

i = 0


class OrderServiceLoadTest(HttpUser):

    @task
    def create_order(self):
        global i
        i = i + 1
        order = {
            "id": str(i),
            "userId": "Lara Grey",
            "orderContent": [
                {
                    "itemId": "latch",
                    "count": 1
                }
            ]
        }
        self.client.post("/order/async", json=order)
